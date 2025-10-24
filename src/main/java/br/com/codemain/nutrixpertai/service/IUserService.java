package br.com.codemain.nutrixpertai.service;

import java.util.List;
import java.util.UUID;

import br.com.codemain.nutrixpertai.dto.User.UserPhysicalDTO;
import br.com.codemain.nutrixpertai.dto.User.UserResponseDTO;
import br.com.codemain.nutrixpertai.dto.User.UserUpdateDTO;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    public UserResponseDTO updatePhysical(UUID id, UserPhysicalDTO userPhysicalDTO);

    public UserResponseDTO update(UUID id, UserUpdateDTO userDTO);

    public List<UserResponseDTO> getAll();

    public UserResponseDTO getById(UUID id);

    public void delete(UUID id);

}
