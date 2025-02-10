package com.jeanpires.objetivobackmetaway.api.inputs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RacaInput {

    @NotBlank
    @Schema(example = "Labrador")
    private String descricao;
}
