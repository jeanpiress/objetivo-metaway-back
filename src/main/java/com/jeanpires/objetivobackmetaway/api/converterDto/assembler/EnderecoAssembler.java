package com.jeanpires.objetivobackmetaway.api.converterDto.assembler;

import com.jeanpires.objetivobackmetaway.api.dtos.EnderecoDto;
import com.jeanpires.objetivobackmetaway.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EnderecoAssembler {

    @Autowired
    private ModelMapper modelMapper;


    public EnderecoDto toModel(Endereco endereco){
        return modelMapper.map(endereco, EnderecoDto.class);
    }

    public List<EnderecoDto> collectionToModel(List<Endereco> enderecos){
         return enderecos.stream().map(endereco -> toModel(endereco))
                .collect(Collectors.toList());
    }

}
