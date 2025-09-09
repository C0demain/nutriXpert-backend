package br.com.codemain.nutrixpertai.service;

import java.util.List;
import java.util.UUID;

import br.com.codemain.nutrixpertai.dto.UserCreateDTO;
import br.com.codemain.nutrixpertai.dto.UserUpdateDTO;
import br.com.codemain.nutrixpertai.entity.User;

public interface IUserService {

    public User create(UserCreateDTO user);

    public User update(UUID id, UserUpdateDTO userDTO);

    public List<User> getAll();

    public User getById(UUID id);

    public void delete(UUID id);
}
