package com.jeanpires.objetivobackmetaway.api.inputs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoInput {

    @NotNull
    @Valid
    private PetId pet;
    @Schema(example = "Banho")
    private String descricao;
    @Schema(example = "50.00")
    private BigDecimal valor;
}
