package com.jeanpires.objetivobackmetaway.api.converterDto.disassembler;

import com.jeanpires.objetivobackmetaway.api.inputs.PetInput;
import com.jeanpires.objetivobackmetaway.domain.model.Pet;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PetInputDisasembler {

    @Autowired
    private ModelMapper modelMapper;

    public Pet toDomainObject (PetInput petInput) {

        return modelMapper.map(petInput, Pet.class);
    }

    public void copyToDomainObject(PetInput petInput, Pet pet) {
        modelMapper.map(petInput, pet);
    }
}
