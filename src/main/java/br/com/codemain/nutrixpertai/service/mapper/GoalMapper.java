package br.com.codemain.nutrixpertai.service.mapper;

import br.com.codemain.nutrixpertai.dto.Goal.CreateGoalDTO;
import br.com.codemain.nutrixpertai.dto.Goal.GoalResponseDTO;
import br.com.codemain.nutrixpertai.entity.Goal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GoalMapper {
    @Mapping(target = "userId", source = "user.id")
    GoalResponseDTO toResponseDTO(Goal goal);

    @Mapping(target = "user", ignore = true)
    Goal toEntity(CreateGoalDTO dto);

    List<GoalResponseDTO> toResponseDTOList(List<Goal> goals);
}
