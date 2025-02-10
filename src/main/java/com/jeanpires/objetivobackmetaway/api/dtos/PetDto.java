package com.jeanpires.objetivobackmetaway.api.dtos;


import com.jeanpires.objetivobackmetaway.api.inputs.ClienteId;
import com.jeanpires.objetivobackmetaway.api.inputs.RacaId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetDto {

    @Schema(description = "Id do Pet", example = "1")
    private Long id;
    private ClienteId cliente;
    private RacaId raca;
    @Schema(example = "2023-02-01")
    private LocalDate dataNascimento;
    @Schema(example = "Rex")
    private String nome;


}
