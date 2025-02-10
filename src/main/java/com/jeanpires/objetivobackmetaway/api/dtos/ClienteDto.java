package com.jeanpires.objetivobackmetaway.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClienteDto {

    @Schema(description = "Id do cliente", example = "1")
    private Long id;
    @Schema(example = "12345678900")
    private String cpf;
    @Schema(example = "Jean")
    private String nome;
    @Schema(example = "1991-11-13")
    private LocalDate dataCadastro;

}
