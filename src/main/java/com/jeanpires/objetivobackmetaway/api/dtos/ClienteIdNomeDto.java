package com.jeanpires.objetivobackmetaway.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClienteIdNomeDto {

    @Schema(description = "Id do cliente", example = "1")
    private Long id;
    @Schema(example = "Jean")
    private String nome;

}
