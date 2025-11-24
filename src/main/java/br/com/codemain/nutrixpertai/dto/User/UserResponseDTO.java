package br.com.codemain.nutrixpertai.dto.User;

import java.util.List;
import java.util.UUID;

import br.com.codemain.nutrixpertai.dto.Goal.GoalResponseDTO;
import br.com.codemain.nutrixpertai.dto.anamnese.AnamneseResponseDTO;
import br.com.codemain.nutrixpertai.enums.Role;

public record UserResponseDTO(
        UUID id,

        String name,

        String email,

        Role role,

        Integer height,

        Double weight,

        AnamneseResponseDTO anamnese,

        List<GoalResponseDTO> goals
) {

}
