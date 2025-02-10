package com.jeanpires.objetivobackmetaway.api.controller;

import com.jeanpires.objetivobackmetaway.api.converterDto.assembler.PetAssembler;
import com.jeanpires.objetivobackmetaway.api.converterDto.disassembler.PetInputDisasembler;
import com.jeanpires.objetivobackmetaway.api.dtos.PetDto;
import com.jeanpires.objetivobackmetaway.api.inputs.PetInput;
import com.jeanpires.objetivobackmetaway.domain.model.Pet;
import com.jeanpires.objetivobackmetaway.domain.repositories.PetsRepository;
import com.jeanpires.objetivobackmetaway.domain.service.PetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController()
@RequestMapping(path = "/pets", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Pets")
public class PetController {

    @Autowired
    private PetsRepository petsRepository;

    @Autowired
    private PetService petService;

    @Autowired
    private PetAssembler petAssembler;
    
    @Autowired
    private PetInputDisasembler petDisasembler;


    @PreAuthorize("hasAuthority('CLIENTE')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PetDto> buscarPorId(@PathVariable Long id) {
        Pet pet = petService.buscarPorId(id);
        PetDto petDto = petAssembler.toModel(pet);
        return  ResponseEntity.ok(petDto);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<PetDto> adicionar(@RequestBody @Valid PetInput petInput) {
        Pet pet = petDisasembler.toDomainObject(petInput);
        Pet petCriado = petsRepository.save(pet);
        PetDto petDto = petAssembler.toModel(petCriado);
        return ResponseEntity.status(HttpStatus.CREATED).body(petDto);
    }
    @PreAuthorize("hasAuthority('CLIENTE')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<PetDto> alterar(@RequestBody @Valid PetInput petInput, @PathVariable Long id) {
        Pet pet = petService.buscarPorId(id);
        petDisasembler.copyToDomainObject(petInput, pet);
        PetDto petDto = petAssembler.toModel(petsRepository.save(pet));
        return ResponseEntity.status(HttpStatus.CREATED).body(petDto);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id){
        petService.remover(id);

    }
}
