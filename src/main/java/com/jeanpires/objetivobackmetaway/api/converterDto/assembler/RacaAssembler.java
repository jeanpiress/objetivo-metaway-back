package com.jeanpires.objetivobackmetaway.api.converterDto.assembler;

import com.jeanpires.objetivobackmetaway.api.dtos.RacaDto;
import com.jeanpires.objetivobackmetaway.domain.model.Raca;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RacaAssembler {

    @Autowired
    private ModelMapper modelMapper;


    public RacaDto toModel(Raca raca){
        return modelMapper.map(raca, RacaDto.class);
    }

    public List<RacaDto> collectionToModel(List<Raca> racas){
         return racas.stream().map(raca -> toModel(raca))
                .collect(Collectors.toList());
    }

}
