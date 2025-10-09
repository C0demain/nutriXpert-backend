package br.com.codemain.nutrixpertai.controller;

import br.com.codemain.nutrixpertai.dto.anamnese.AnamneseRequestDTO;
import br.com.codemain.nutrixpertai.dto.anamnese.AnamneseResponseDTO;
import br.com.codemain.nutrixpertai.service.impl.AnamneseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("user/{userId}/anamnese")
@Tag(name = "Anamnese", description = "Endpoints para gerenciamento da Anamnese do Usuário")
@SecurityRequirement(name = "Bearer Authentication")
public class AnamneseController {

    private final AnamneseService anamneseService;

    public AnamneseController(AnamneseService anamneseService) {
        this.anamneseService = anamneseService;
    }


    @Operation(summary = "Cria uma nova anamnese para um usuário",
            description = "Cria um registro de anamnese e o associa ao usuário especificado pelo ID. Um usuário só pode ter uma anamnese.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Anamnese criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos ou usuário já possui uma anamnese"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado com o ID fornecido")
    })
    @PostMapping
    public ResponseEntity<AnamneseResponseDTO> create(
            @Parameter(description = "ID do usuário para associar a anamnese", required = true)
            @PathVariable UUID userId,
            @RequestBody AnamneseRequestDTO requestDTO) {
        AnamneseResponseDTO responseDTO = anamneseService.create(userId, requestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).body(responseDTO);
    }


    @Operation(summary = "Busca a anamnese de um usuário pelo ID do usuário",
            description = "Retorna os dados da anamnese associada ao usuário especificado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anamnese encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma anamnese encontrada para o usuário com o ID fornecido")
    })
    @GetMapping
    public ResponseEntity<AnamneseResponseDTO> getByUserId(
            @Parameter(description = "ID do usuário cuja anamnese será buscada", required = true)
            @PathVariable UUID userId) {
        AnamneseResponseDTO responseDTO = anamneseService.getByUserId(userId);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Atualiza a anamnese de um usuário",
            description = "Atualiza os dados da anamnese existente associada ao usuário especificado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anamnese atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Nenhuma anamnese encontrada para o usuário com o ID fornecido")
    })
    @PutMapping
    public ResponseEntity<AnamneseResponseDTO> update(
            @Parameter(description = "ID do usuário cuja anamnese será atualizada", required = true)
            @PathVariable UUID userId,
            @RequestBody AnamneseRequestDTO requestDTO) {
        AnamneseResponseDTO responseDTO = anamneseService.update(userId, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Deleta a anamnese de um usuário",
            description = "Remove o registro da anamnese associada ao usuário especificado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Anamnese deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma anamnese encontrada para o usuário com o ID fornecido")
    })
    @DeleteMapping
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do usuário cuja anamnese será deletada", required = true)
            @PathVariable UUID userId) {
        anamneseService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
