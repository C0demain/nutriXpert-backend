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

import br.com.codemain.nutrixpertai.dto.UserCreateDTO;
import br.com.codemain.nutrixpertai.dto.UserUpdateDTO;
import br.com.codemain.nutrixpertai.entity.User;
import br.com.codemain.nutrixpertai.repository.UserRepository;
import br.com.codemain.nutrixpertai.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User create(UserCreateDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());

        // gerar o hash antes de salvar depois
        user.setPassword(userDTO.getPassword());

        user.setHeight(userDTO.getHeight());
        user.setWeight(userDTO.getWeight());
        user.setHabits(userDTO.getHabits());
        user.setIllnesses(userDTO.getIllnesses());

        return userRepository.save(user);
    }

    @Override
    public User getById(UUID id) {
        Optional<User> userOp = userRepository.findById(id);

        if (userOp.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não existe!");
        }
        return userOp.get();
    }

    @Override
    public User update(UUID id, UserUpdateDTO userDTO) {
        User user = getById(id);

        // Checa unicidade do e-mail se mudou
        if (!user.getEmail().equalsIgnoreCase(userDTO.getEmail())
                && userRepository.existsByEmailAndIdNot(userDTO.getEmail(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já em uso por outro usuário.");
        }

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setPassword(userDTO.getPassword());

        user.setHeight(userDTO.getHeight());
        user.setWeight(userDTO.getWeight());
        user.setHabits(userDTO.getHabits());
        user.setIllnesses(userDTO.getIllnesses());

        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
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
