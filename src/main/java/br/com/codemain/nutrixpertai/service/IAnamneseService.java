package br.com.codemain.nutrixpertai.service;

import br.com.codemain.nutrixpertai.dto.User.UserResponseDTO;
import br.com.codemain.nutrixpertai.dto.anamnese.AnamneseRequestDTO;
import br.com.codemain.nutrixpertai.dto.anamnese.AnamneseResponseDTO;

import java.util.UUID;

public interface IAnamneseService {

    public AnamneseResponseDTO create(UUID userId, AnamneseRequestDTO dto);

    public UserResponseDTO createAgent(UUID userId, AnamneseRequestDTO dto);

    public AnamneseResponseDTO getByUserId(UUID userId);

    public AnamneseResponseDTO update(UUID userId, AnamneseRequestDTO dto);

    public AnamneseResponseDTO patch(UUID userId, AnamneseRequestDTO patchRequest);

    public UserResponseDTO patchAgent(UUID userId, AnamneseRequestDTO patchRequest);

    public void delete(UUID userId);


}
