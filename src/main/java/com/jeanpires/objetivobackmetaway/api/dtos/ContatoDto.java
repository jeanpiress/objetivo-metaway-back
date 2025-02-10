package com.jeanpires.objetivobackmetaway.api.dtos;

import com.jeanpires.objetivobackmetaway.domain.enuns.TipoContato;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContatoDto {

    @Schema(description = "Id do contato", example = "10")
    private Long id;
    private ClienteDto cliente;
    @Schema(example = "Assinante")
    private String tag;
    @Schema(example = "TELEFONE")
    private TipoContato tipo;
    @Schema(example = "34999999999")
    private String valor;
}
