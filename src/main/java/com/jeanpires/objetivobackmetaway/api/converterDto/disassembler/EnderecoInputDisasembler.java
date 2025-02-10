package com.jeanpires.objetivobackmetaway.api.converterDto.disassembler;

import com.jeanpires.objetivobackmetaway.api.inputs.EnderecoInput;
import com.jeanpires.objetivobackmetaway.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnderecoInputDisasembler {

    @Autowired
    private ModelMapper modelMapper;

    public Endereco toDomainObject (EnderecoInput enderecoInput) {

        return modelMapper.map(enderecoInput, Endereco.class);
    }

    public void copyToDomainObject(EnderecoInput enderecoInput, Endereco endereco) {

        modelMapper.map(enderecoInput, endereco);
    }
}
