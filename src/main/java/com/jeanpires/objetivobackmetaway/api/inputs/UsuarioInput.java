package com.jeanpires.objetivobackmetaway.api.inputs;

import com.jeanpires.objetivobackmetaway.domain.enuns.Perfil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioInput {

    @Schema(example = "11122233344", required = true)
    @NotBlank
    private String cpf;
    @Schema(example = "Jean", required = true)
    @NotBlank
    private String nome;
    @Schema(example = "CLIENTE", required = true)
    @NotNull
    private Perfil perfil;
    @Schema(example = "123456", required = true)
    @NotBlank
    private String senha;
}
