package com.jeanpires.objetivobackmetaway.api.converterDto.assembler;

import com.jeanpires.objetivobackmetaway.api.dtos.ContatoDto;
import com.jeanpires.objetivobackmetaway.domain.model.Contato;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContatoAssembler {

    @Autowired
    private ModelMapper modelMapper;


    public ContatoDto toModel(Contato contato){
        return modelMapper.map(contato, ContatoDto.class);
    }

    public List<ContatoDto> collectionToModel(List<Contato> contatos){
         return contatos.stream().map(contato -> toModel(contato))
                .collect(Collectors.toList());
    }

}
