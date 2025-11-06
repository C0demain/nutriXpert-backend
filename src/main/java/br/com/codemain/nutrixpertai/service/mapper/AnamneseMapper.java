package br.com.codemain.nutrixpertai.service.mapper;


import br.com.codemain.nutrixpertai.dto.anamnese.AnamneseRequestDTO;
import br.com.codemain.nutrixpertai.dto.anamnese.AnamneseResponseDTO;
import br.com.codemain.nutrixpertai.entity.Anamnese;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AnamneseMapper {

    AnamneseResponseDTO toResponseDTO(Anamnese anamnese);

    Anamnese toEntity(AnamneseRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAnamneseFromDto(AnamneseRequestDTO dto, @MappingTarget Anamnese entity);
}
