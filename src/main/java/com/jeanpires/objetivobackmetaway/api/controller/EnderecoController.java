package com.jeanpires.objetivobackmetaway.api.controller;

import com.jeanpires.objetivobackmetaway.api.converterDto.assembler.EnderecoAssembler;
import com.jeanpires.objetivobackmetaway.api.converterDto.disassembler.EnderecoInputDisasembler;
import com.jeanpires.objetivobackmetaway.api.dtos.EnderecoDto;
import com.jeanpires.objetivobackmetaway.api.inputs.EnderecoInput;
import com.jeanpires.objetivobackmetaway.domain.model.Endereco;
import com.jeanpires.objetivobackmetaway.domain.repositories.EnderecoRepository;
import com.jeanpires.objetivobackmetaway.domain.service.EnderecoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController()
@RequestMapping(path = "/enderecos", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private EnderecoAssembler enderecoAssembler;
    
    @Autowired
    private EnderecoInputDisasembler enderecoDisasembler;



    @GetMapping(value = "/{id}")
    public ResponseEntity<EnderecoDto> buscarPorId(@PathVariable Long id) {
        Endereco endereco = enderecoService.buscarPorId(id);
        EnderecoDto enderecoDto = enderecoAssembler.toModel(endereco);
        return  ResponseEntity.ok(enderecoDto);
    }

    @PostMapping
    public ResponseEntity<EnderecoDto> adicionar(@RequestBody @Valid EnderecoInput enderecoInput) {
        Endereco endereco = enderecoDisasembler.toDomainObject(enderecoInput);
        Endereco enderecoCriado = enderecoRepository.save(endereco);
        EnderecoDto enderecoDto = enderecoAssembler.toModel(enderecoCriado);
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EnderecoDto> alterar(@RequestBody @Valid EnderecoInput enderecoInput, @PathVariable Long id) {
        Endereco endereco = enderecoService.buscarPorId(id);
        enderecoDisasembler.copyToDomainObject(enderecoInput, endereco);
        EnderecoDto enderecoDto = enderecoAssembler.toModel(enderecoRepository.save(endereco));
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id){
        enderecoService.remover(id);

    }
}
