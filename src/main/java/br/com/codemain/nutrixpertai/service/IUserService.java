package br.com.codemain.nutrixpertai.service;

import java.util.List;
import java.util.UUID;

import br.com.codemain.nutrixpertai.dto.User.UserAnamneseDTO;
import br.com.codemain.nutrixpertai.dto.User.UserCreateDTO;
import br.com.codemain.nutrixpertai.dto.User.UserResponseDTO;
import br.com.codemain.nutrixpertai.dto.User.UserUpdateDTO;

public interface IUserService {

    public UserResponseDTO create(UserCreateDTO user);

    public UserResponseDTO updateAnamnese(UUID id, UserAnamneseDTO userAnamneseDTO);

    public UserResponseDTO update(UUID id, UserUpdateDTO userDTO);

    public List<UserResponseDTO> getAll();

    public UserResponseDTO getById(UUID id);

    public void delete(UUID id);
}
