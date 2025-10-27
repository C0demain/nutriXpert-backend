package br.com.codemain.nutrixpertai.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.com.codemain.nutrixpertai.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.codemain.nutrixpertai.dto.User.UserPhysicalDTO;
import br.com.codemain.nutrixpertai.dto.User.UserResponseDTO;
import br.com.codemain.nutrixpertai.dto.User.UserUpdateDTO;
import br.com.codemain.nutrixpertai.entity.User;
import br.com.codemain.nutrixpertai.repository.UserRepository;
import br.com.codemain.nutrixpertai.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserResponseDTO updatePhysical(UUID id, UserPhysicalDTO userPhysicalDTO) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(userPhysicalDTO.weight() != null){
            user.setWeight(userPhysicalDTO.weight());
        }
        if(userPhysicalDTO.height() != null){
            user.setHeight(userPhysicalDTO.height());
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
        if (userDTO.email() != null) {
            // Checa unicidade do e-mail se mudou
            if (!user.getEmail().equalsIgnoreCase(userDTO.email())
                    && userRepository.existsByEmailAndIdNot(userDTO.email(), id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já em uso por outro usuário.");
            }
            user.setEmail(userDTO.email());
        }

        // Atualiza só os campos enviados
        if (userDTO.name() != null) {
            user.setName(userDTO.name());
        }
        if (userDTO.email() != null) {
            user.setEmail(userDTO.email());
        }
        if (userDTO.role() != null) {
            user.setRole(userDTO.role());
        }
        if (userDTO.password() != null) {
            String hashedPassword = new BCryptPasswordEncoder().encode(userDTO.password());
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

        return  userMapper.toDTO(user);
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
