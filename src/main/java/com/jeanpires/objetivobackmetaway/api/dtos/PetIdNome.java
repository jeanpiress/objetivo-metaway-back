package com.jeanpires.objetivobackmetaway.api.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetIdNome {

    @Schema(description = "Id do Pet", example = "1")
    private Long id;
    @Schema(example = "Rex")
    private String nome;


}
