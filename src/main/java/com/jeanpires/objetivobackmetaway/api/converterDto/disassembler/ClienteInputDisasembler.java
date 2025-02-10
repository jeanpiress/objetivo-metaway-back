package com.jeanpires.objetivobackmetaway.api.converterDto.disassembler;

import com.jeanpires.objetivobackmetaway.api.inputs.ClienteInput;
import com.jeanpires.objetivobackmetaway.domain.model.Cliente;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteInputDisasembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cliente toDomainObject (ClienteInput clienteInput) {

        return modelMapper.map(clienteInput, Cliente.class);
    }

    public void copyToDomainObject(ClienteInput clienteInput, Cliente cliente) {

        modelMapper.map(clienteInput, cliente);
    }
}
