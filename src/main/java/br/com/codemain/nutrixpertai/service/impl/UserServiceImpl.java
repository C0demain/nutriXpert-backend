package br.com.codemain.nutrixpertai.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.codemain.nutrixpertai.dto.User.UserAnamneseDTO;
import br.com.codemain.nutrixpertai.dto.User.UserCreateDTO;
import br.com.codemain.nutrixpertai.dto.User.UserResponseDTO;
import br.com.codemain.nutrixpertai.dto.User.UserUpdateDTO;
import br.com.codemain.nutrixpertai.entity.User;
import br.com.codemain.nutrixpertai.repository.UserRepository;
import br.com.codemain.nutrixpertai.service.IUserService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponseDTO create(UserCreateDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());

        // gerar o hash antes de salvar depois
        user.setPassword(userDTO.getPassword());

        // Checa unicidade do e-mail se mudou
        if (!user.getEmail().equalsIgnoreCase(userDTO.getEmail())
                && userRepository.existsByEmailAndIdNot(userDTO.getEmail(), user.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já em uso por outro usuário.");
        }

        userRepository.save(user);

        return toDTO(user);
    }

    @Override
    public UserResponseDTO updateAnamnese(UUID id, UserAnamneseDTO userAnamneseDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        user.setHeight(userAnamneseDTO.getHeight());
        user.setWeight(userAnamneseDTO.getWeight());
        user.setHabits(userAnamneseDTO.getHabits());
        user.setIllnesses(userAnamneseDTO.getIllnesses());

        userRepository.save(user);

        return toDTO(user);
    }

    @Override
    public UserResponseDTO getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não existe!"));

        return toDTO(user);
    }

    @Override
    public UserResponseDTO update(UUID id, UserUpdateDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não existe!"));

        // Checa unicidade do e-mail se mudou
        if (!user.getEmail().equalsIgnoreCase(userDTO.getEmail())
                && userRepository.existsByEmailAndIdNot(userDTO.getEmail(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já em uso por outro usuário.");
        }

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setPassword(userDTO.getPassword());

        userRepository.save(user);

        return toDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAll() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(this::toDTO)
                .toList();
    }

    private UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setHeight(user.getHeight());
        dto.setWeight(user.getWeight());
        dto.setHabits(user.getHabits());
        dto.setIllnesses(user.getIllnesses());

        return dto;
    }

    @Override
    public void delete(UUID id) {
        Optional<User> userOp = userRepository.findById(id);

        if (userOp.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não existe!");
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Username = E-mail do usuário
        return userRepository.findByEmail(username);
    }
}
