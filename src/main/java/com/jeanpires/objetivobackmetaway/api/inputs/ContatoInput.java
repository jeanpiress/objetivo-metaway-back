package com.jeanpires.objetivobackmetaway.api.inputs;

import com.jeanpires.objetivobackmetaway.domain.enuns.TipoContato;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContatoInput {

    @Valid
    @NotNull
    private ClienteId cliente;
    @Schema(example = "ASSINANTE")
    private String tag;
    @Schema(example = "TELEFONE")
    private TipoContato tipo;
    @Schema(example = "34999999999")
    private String valor;
}
