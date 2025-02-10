package com.jeanpires.objetivobackmetaway.api.converterDto.assembler;

import com.jeanpires.objetivobackmetaway.api.dtos.AtendimentoDto;
import com.jeanpires.objetivobackmetaway.domain.model.Atendimento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AtendimentoAssembler {

    @Autowired
    private ModelMapper modelMapper;


    public AtendimentoDto toModel(Atendimento atendimento){
        return modelMapper.map(atendimento, AtendimentoDto.class);
    }

    public List<AtendimentoDto> collectionToModel(List<Atendimento> atendimentos){
         return atendimentos.stream().map(atendimento -> toModel(atendimento))
                .collect(Collectors.toList());
    }

}
