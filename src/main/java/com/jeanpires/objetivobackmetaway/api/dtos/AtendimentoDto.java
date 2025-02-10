package com.jeanpires.objetivobackmetaway.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoDto {

    @Schema(description = "ID do Atendimento", example = "1")
    private Long id;
    private PetIdNome pet;
    @Schema(example = "Banho")
    private String descricao;
    @Schema(example = "50.00")
    private BigDecimal valor;
    @Schema(example = "2025-02-09T13:00:00")
    private LocalDateTime data;
}
