package com.jeanpires.objetivobackmetaway.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RacaDto {

    @Schema(description = "Id da Raça", example = "1")
    private Long id;
    @Schema(example = "Golden Retriver")
    private String descricao;

}
