package com.jeanpires.objetivobackmetaway.api.dtos;

import com.jeanpires.objetivobackmetaway.domain.enuns.Perfil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    @Schema(description = "ID do usuario", example = "1")
    private Long id;
    @Schema(example = "12345678900")
    private String cpf;
    @Schema(example = "Jean")
    private String nome;
    @Schema(example = "CLIENTE")
    private Perfil perfil;
}
