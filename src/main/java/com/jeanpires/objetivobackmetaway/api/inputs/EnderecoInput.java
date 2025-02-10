package com.jeanpires.objetivobackmetaway.api.inputs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoInput {

    @NotNull
    @Valid
    private ClienteId cliente;
    @Schema(example = "Rua Belgica")
    private String logradouro;
    @Schema(example = "Uberlandia")
    private String cidade;
    @Schema(example = "Centro")
    private String bairro;
    @Schema(example = "Ape 101")
    private String complemento;
    @Schema(example = "RESIDENCIAL")
    private String tag;
}
