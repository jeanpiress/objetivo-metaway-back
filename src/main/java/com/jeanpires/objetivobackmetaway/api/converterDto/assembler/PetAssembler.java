package com.jeanpires.objetivobackmetaway.api.converterDto.assembler;

import com.jeanpires.objetivobackmetaway.api.dtos.PetDto;
import com.jeanpires.objetivobackmetaway.domain.model.Pet;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PetAssembler {

    @Autowired
    private ModelMapper modelMapper;


    public PetDto toModel(Pet pet){
        return modelMapper.map(pet, PetDto.class);
    }

    public List<PetDto> collectionToModel(List<Pet> pets){
         return pets.stream().map(pet -> toModel(pet))
                .collect(Collectors.toList());
    }

}
