package br.com.codemain.nutrixpertai.service.mapper;

import br.com.codemain.nutrixpertai.dto.Goal.CreateGoalDTO;
import br.com.codemain.nutrixpertai.dto.Goal.GoalResponseDTO;
import br.com.codemain.nutrixpertai.entity.Goal;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GoalMapper {
    GoalResponseDTO toResponseDTO(Goal goal);

    Goal toEntity(CreateGoalDTO dto);

    List<GoalResponseDTO> toResponseDTOList(List<Goal> goals);
}
