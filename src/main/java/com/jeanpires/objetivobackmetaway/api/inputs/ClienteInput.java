package com.jeanpires.objetivobackmetaway.api.inputs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClienteInput {

    @Schema(example = "11122233344", required = true)
    private String cpf;
    @Schema(example = "Jean", required = true)
    @NotBlank
    private String nome;
}
