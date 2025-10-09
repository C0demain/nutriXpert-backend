package br.com.codemain.nutrixpertai.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.codemain.nutrixpertai.dto.User.UserAnamneseDTO;
import br.com.codemain.nutrixpertai.dto.User.UserResponseDTO;
import br.com.codemain.nutrixpertai.dto.User.UserUpdateDTO;
import br.com.codemain.nutrixpertai.entity.User;
import br.com.codemain.nutrixpertai.repository.UserRepository;
import br.com.codemain.nutrixpertai.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponseDTO updateAnamnese(UUID id, UserAnamneseDTO userAnamneseDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (userAnamneseDTO.getHeight() != null) {
            user.setHeight(userAnamneseDTO.getHeight());
        }

        if (userAnamneseDTO.getWeight() != null) {
            user.setWeight(userAnamneseDTO.getWeight());
        }

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
        if (userDTO.getEmail() != null) {
            // Checa unicidade do e-mail se mudou
            if (!user.getEmail().equalsIgnoreCase(userDTO.getEmail())
                    && userRepository.existsByEmailAndIdNot(userDTO.getEmail(), id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já em uso por outro usuário.");
            }
            user.setEmail(userDTO.getEmail());
        }

        // Atualiza só os campos enviados
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getRole() != null) {
            user.setRole(userDTO.getRole());
        }
        if (userDTO.getPassword() != null) {
            String hashedPassword = new BCryptPasswordEncoder().encode(userDTO.getPassword());
            user.setPassword(hashedPassword);
        }

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
