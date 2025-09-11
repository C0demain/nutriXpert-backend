package br.com.codemain.nutrixpertai.service;

import java.util.List;
import java.util.UUID;

import br.com.codemain.nutrixpertai.dto.UserCreateDTO;
import br.com.codemain.nutrixpertai.dto.UserUpdateDTO;
import br.com.codemain.nutrixpertai.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    public User create(UserCreateDTO user);

    public User update(UUID id, UserUpdateDTO userDTO);

    public List<User> getAll();

    public User getById(UUID id);

    public void delete(UUID id);
}
