package com.jeanpires.objetivobackmetaway.api.converterDto.disassembler;

import com.jeanpires.objetivobackmetaway.api.inputs.ContatoInput;
import com.jeanpires.objetivobackmetaway.domain.model.Contato;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContatoInputDisasembler {

    @Autowired
    private ModelMapper modelMapper;

    public Contato toDomainObject (ContatoInput contratoInput) {

        return modelMapper.map(contratoInput, Contato.class);
    }

    public void copyToDomainObject(ContatoInput contratoInput, Contato contrato) {

        modelMapper.map(contratoInput, contrato);
    }
}
