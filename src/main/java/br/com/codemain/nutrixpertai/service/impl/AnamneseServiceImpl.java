package br.com.codemain.nutrixpertai.service.impl;

import br.com.codemain.nutrixpertai.dto.anamnese.AnamneseRequestDTO;
import br.com.codemain.nutrixpertai.dto.anamnese.AnamneseResponseDTO;
import br.com.codemain.nutrixpertai.entity.Anamnese;
import br.com.codemain.nutrixpertai.entity.User;
import br.com.codemain.nutrixpertai.repository.AnamneseRepository;
import br.com.codemain.nutrixpertai.repository.UserRepository;
import br.com.codemain.nutrixpertai.service.IAnamneseService;
import br.com.codemain.nutrixpertai.service.mapper.AnamneseMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AnamneseServiceImpl implements IAnamneseService {

    private final UserRepository userRepository;
    private final AnamneseRepository anamneseRepository;
    private final AnamneseMapper anamneseMapper;


    public AnamneseServiceImpl(UserRepository userRepository, AnamneseRepository anamneseRepository, AnamneseMapper anamneseMapper) {
        this.userRepository = userRepository;
        this.anamneseRepository = anamneseRepository;
        this.anamneseMapper = anamneseMapper;
    }

    @Override
    @Transactional
    public AnamneseResponseDTO create(UUID userId, AnamneseRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + userId));

        if (user.getAnamnese() != null) {
            throw new IllegalStateException("Este usuário já possui uma anamnese cadastrada.");
        }

        Anamnese anamnese = anamneseMapper.toEntity(dto);
        anamnese.setUser(user);
        user.setAnamnese(anamnese);

        Anamnese savedAnamnese = anamneseRepository.save(anamnese);
        return toResponseDTO(savedAnamnese);
    }

    @Override
    @Transactional()
    public AnamneseResponseDTO getByUserId(UUID userId) {
        return anamneseRepository.findByUser_Id(userId)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Anamnese não encontrada para o usuário com ID: " + userId));
    }


    @Override
    @Transactional
    public AnamneseResponseDTO update(UUID userId, AnamneseRequestDTO dto) {
        Anamnese existingAnamnese = anamneseRepository.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Anamnese não encontrada para o usuário com ID: " + userId));

        updateEntityFromDto(existingAnamnese, dto);

        Anamnese updatedAnamnese = anamneseRepository.save(existingAnamnese);
        return toResponseDTO(updatedAnamnese);
    }

    @Override
    @Transactional
    public AnamneseResponseDTO patch(UUID userId, AnamneseRequestDTO patchRequest) {
        Anamnese existingAnamnese = anamneseRepository.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Anamnese não encontrada para o usuário com ID: " + userId));

        applyPatchToEntity(existingAnamnese, patchRequest);

        Anamnese patchedAnamnese = anamneseRepository.save(existingAnamnese);
        return toResponseDTO(patchedAnamnese);
    }

    @Override
    @Transactional
    public void delete(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + userId));

        if (user.getAnamnese() == null) {
            throw new EntityNotFoundException("Anamnese não encontrada para o usuário com ID: " + userId);
        }
        user.setAnamnese(null);
        userRepository.save(user);
    }


    private AnamneseResponseDTO toResponseDTO(Anamnese entity) {
        return anamneseMapper.toResponseDTO(entity);
    }

    private void updateEntityFromDto(Anamnese entity, AnamneseRequestDTO dto) {
        anamneseMapper.updateAnamneseFromDto(dto, entity);
    }

    private void applyPatchToEntity(Anamnese entity, AnamneseRequestDTO patchRequest) {
        anamneseMapper.updateAnamneseFromDto(patchRequest, entity);
    }

}
