package com.jeanpires.objetivobackmetaway.api.inputs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetId {

    @NotNull
    @Schema(description = "Id do Pet", example = "1")
    private Long id;


}
