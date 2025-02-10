package com.jeanpires.objetivobackmetaway.api.converterDto.disassembler;

import com.jeanpires.objetivobackmetaway.api.inputs.RacaInput;
import com.jeanpires.objetivobackmetaway.domain.model.Raca;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RacaInputDisasembler {

    @Autowired
    private ModelMapper modelMapper;

    public Raca toDomainObject (RacaInput racaInput) {

        return modelMapper.map(racaInput, Raca.class);
    }

    public void copyToDomainObject(RacaInput racaInput, Raca raca) {

        modelMapper.map(racaInput, raca);
    }
}
