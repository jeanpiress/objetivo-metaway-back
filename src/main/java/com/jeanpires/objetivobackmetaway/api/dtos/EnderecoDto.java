package com.jeanpires.objetivobackmetaway.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDto {

    @Schema(description = "Id do endereco", example = "1")
    private Long id;
    private ClienteDto cliente;
    @Schema(example = "Rua Belgica")
    private String logradouro;
    @Schema(example = "Uberlândia")
    private String cidade;
    @Schema(example = "Centro")
    private String bairro;
    @Schema(example = "Ape 101")
    private String complemento;
    @Schema(example = "NÃO ENCONTRADO")
    private String tag;
}
