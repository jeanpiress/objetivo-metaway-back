package com.jeanpires.objetivobackmetaway.api.inputs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetInput {

    @Valid
    @NotNull
    private ClienteId cliente;
    @Valid
    @NotNull
    private RacaId raca;
    @Schema(example = "2023-02-01")
    private LocalDate dataNascimento;
    @Schema(example = "Rex")
    @NotNull
    private String nome;

}
