package com.jeanpires.objetivobackmetaway.api.converterDto.disassembler;

import com.jeanpires.objetivobackmetaway.api.inputs.AtendimentoInput;
import com.jeanpires.objetivobackmetaway.domain.model.Atendimento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AtendimentoInputDisasembler {

    @Autowired
    private ModelMapper modelMapper;

    public Atendimento toDomainObject (AtendimentoInput atendimentoInput) {

        return modelMapper.map(atendimentoInput, Atendimento.class);
    }

    public void copyToDomainObject(AtendimentoInput atendimentoInput, Atendimento atendimento) {

        modelMapper.map(atendimentoInput, atendimento);
    }
}
