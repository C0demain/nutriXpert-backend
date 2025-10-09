package br.com.codemain.nutrixpertai.service.impl;

import br.com.codemain.nutrixpertai.dto.anamnese.AnamneseRequestDTO;
import br.com.codemain.nutrixpertai.dto.anamnese.AnamneseResponseDTO;
import br.com.codemain.nutrixpertai.entity.Anamnese;
import br.com.codemain.nutrixpertai.entity.User;
import br.com.codemain.nutrixpertai.repository.AnamneseRepository;
import br.com.codemain.nutrixpertai.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AnamneseService {


    private final UserRepository userRepository;
    private final AnamneseRepository anamneseRepository;

    public AnamneseService(UserRepository userRepository, AnamneseRepository anamneseRepository) {
        this.userRepository = userRepository;
        this.anamneseRepository = anamneseRepository;
    }

    @Transactional
    public AnamneseResponseDTO create(UUID userId, AnamneseRequestDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + userId));

        if (user.getAnamnese() != null) {
            throw new IllegalStateException("Este usuário já possui uma anamnese cadastrada.");
        }

        Anamnese anamnese = toEntity(dto);
        anamnese.setUser(user);
        user.setAnamnese(anamnese);

        Anamnese savedAnamnese = anamneseRepository.save(anamnese);
        return toResponseDTO(savedAnamnese);
    }

    @Transactional()
    public AnamneseResponseDTO getByUserId(UUID userId) {
        return anamneseRepository.findByUser_Id(userId)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Anamnese não encontrada para o usuário com ID: " + userId));
    }


    @Transactional
    public AnamneseResponseDTO update(UUID userId, AnamneseRequestDTO dto) {
        Anamnese existingAnamnese = anamneseRepository.findByUser_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException("Anamnese não encontrada para o usuário com ID: " + userId));

        updateEntityFromDto(existingAnamnese, dto);

        Anamnese updatedAnamnese = anamneseRepository.save(existingAnamnese);
        return toResponseDTO(updatedAnamnese);
    }

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


    private Anamnese toEntity(AnamneseRequestDTO dto) {
        Anamnese entity = new Anamnese();

        entity.setGoalType(dto.goalType());
        entity.setGoalTypeOther(dto.goalTypeOther());
        entity.setHealthConditionType(dto.healthConditionType());
        entity.setHealthConditionOther(dto.healthConditionOther());
        entity.setAllergyIntoleranceType(dto.allergyIntoleranceType());
        entity.setAllergyIntoleranceOther(dto.allergyIntoleranceOther());
        entity.setSurgeryType(dto.surgeryType());
        entity.setSurgeryTypeOther(dto.surgeryTypeOther());
        entity.setPhysicalActivityType(dto.physicalActivityType());
        entity.setPhysicalActivityOther(dto.physicalActivityOther());
        entity.setPhysicalActivityFrequency(dto.physicalActivityFrequency());
        entity.setPhysicalActivityDuration(dto.physicalActivityDuration());
        entity.setSleepQuality(dto.sleepQuality());
        entity.setNightAwakeningFrequency(dto.nightAwakeningFrequency());
        entity.setEvacuationFrequencyType(dto.evacuationFrequencyType());
        entity.setStressLevel(dto.stressLevel());
        entity.setAlcoholConsumption(dto.alcoholConsumption());
        entity.setTabagism(dto.tabagism());
        entity.setHydration(dto.hydration());
        entity.setContinuousMedication(dto.continuousMedication());

        return entity;
    }

    private AnamneseResponseDTO toResponseDTO(Anamnese entity) {
        return new AnamneseResponseDTO(
                entity.getId(),
                entity.getGoalType(),
                entity.getGoalTypeOther(),
                entity.getHealthConditionType(),
                entity.getHealthConditionOther(),
                entity.getAllergyIntoleranceType(),
                entity.getAllergyIntoleranceOther(),
                entity.getSurgeryType(),
                entity.getSurgeryTypeOther(),
                entity.getPhysicalActivityType(),
                entity.getPhysicalActivityOther(),
                entity.getPhysicalActivityFrequency(),
                entity.getPhysicalActivityDuration(),
                entity.getSleepQuality(),
                entity.getNightAwakeningFrequency(),
                entity.getEvacuationFrequencyType(),
                entity.getStressLevel(),
                entity.getAlcoholConsumption(),
                entity.getTabagism(),
                entity.getHydration(),
                entity.getContinuousMedication()
        );
    }

    private void updateEntityFromDto(Anamnese entity, AnamneseRequestDTO dto) {
        entity.setGoalType(dto.goalType());
        entity.setGoalTypeOther(dto.goalTypeOther());
        entity.setHealthConditionType(dto.healthConditionType());
        entity.setHealthConditionOther(dto.healthConditionOther());
        entity.setAllergyIntoleranceType(dto.allergyIntoleranceType());
        entity.setAllergyIntoleranceOther(dto.allergyIntoleranceOther());
        entity.setSurgeryType(dto.surgeryType());
        entity.setSurgeryTypeOther(dto.surgeryTypeOther());
        entity.setPhysicalActivityType(dto.physicalActivityType());
        entity.setPhysicalActivityOther(dto.physicalActivityOther());
        entity.setPhysicalActivityFrequency(dto.physicalActivityFrequency());
        entity.setPhysicalActivityDuration(dto.physicalActivityDuration());
        entity.setSleepQuality(dto.sleepQuality());
        entity.setNightAwakeningFrequency(dto.nightAwakeningFrequency());
        entity.setEvacuationFrequencyType(dto.evacuationFrequencyType());
        entity.setStressLevel(dto.stressLevel());
        entity.setAlcoholConsumption(dto.alcoholConsumption());
        entity.setTabagism(dto.tabagism());
        entity.setHydration(dto.hydration());
        entity.setContinuousMedication(dto.continuousMedication());
    }
}
