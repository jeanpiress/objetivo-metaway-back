package com.jeanpires.objetivobackmetaway.domain.model;

import javax.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    private String logradouro;
    private String cidade;
    private String bairro;
    private String complemento;
    private String tag;
}
