package com.jeanpires.objetivobackmetaway.api.converterDto.assembler;

import com.jeanpires.objetivobackmetaway.api.dtos.ClienteDto;
import com.jeanpires.objetivobackmetaway.domain.model.Cliente;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClienteAssembler {

    @Autowired
    private ModelMapper modelMapper;


    public ClienteDto toModel(Cliente cliente){
        return modelMapper.map(cliente, ClienteDto.class);
    }

    public List<ClienteDto> collectionToModel(List<Cliente> clientes){
         return clientes.stream().map(cliente -> toModel(cliente))
                .collect(Collectors.toList());
    }

}
