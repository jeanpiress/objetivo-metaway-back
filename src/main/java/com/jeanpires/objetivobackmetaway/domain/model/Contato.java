package com.jeanpires.objetivobackmetaway.domain.model;

import com.jeanpires.objetivobackmetaway.domain.enuns.TipoContato;
import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class Contato {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    private String tag;
    @Enumerated(EnumType.STRING)
    private TipoContato tipo;
    private String valor;
}
