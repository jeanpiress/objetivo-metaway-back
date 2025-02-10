package com.jeanpires.objetivobackmetaway.api.controller;

import com.jeanpires.objetivobackmetaway.api.converterDto.assembler.ContatoAssembler;
import com.jeanpires.objetivobackmetaway.api.converterDto.disassembler.ContatoInputDisasembler;
import com.jeanpires.objetivobackmetaway.api.dtos.ContatoDto;
import com.jeanpires.objetivobackmetaway.api.inputs.ContatoInput;
import com.jeanpires.objetivobackmetaway.domain.model.Contato;
import com.jeanpires.objetivobackmetaway.domain.repositories.ContatoRepository;
import com.jeanpires.objetivobackmetaway.domain.service.ContatoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController()
@RequestMapping(path = "/contatos", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Contatos")
public class ContatoController {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private ContatoService contatoService;

    @Autowired
    private ContatoAssembler contatoAssembler;
    
    @Autowired
    private ContatoInputDisasembler contatoDisasembler;


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ContatoDto> buscarPorId(@PathVariable Long id) {
        Contato contato = contatoService.buscarPorId(id);
        ContatoDto contatoDto = contatoAssembler.toModel(contato);
        return  ResponseEntity.ok(contatoDto);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ContatoDto> adicionar(@RequestBody @Valid ContatoInput contatoInput) {
        Contato contato = contatoDisasembler.toDomainObject(contatoInput);
        Contato contatoCriado = contatoRepository.save(contato);
        ContatoDto contatoDto = contatoAssembler.toModel(contatoCriado);
        return ResponseEntity.status(HttpStatus.CREATED).body(contatoDto);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ContatoDto> alterar(@RequestBody @Valid ContatoInput contatoInput, @PathVariable Long id) {
        Contato contato = contatoService.buscarPorId(id);
        contatoDisasembler.copyToDomainObject(contatoInput, contato);
        ContatoDto contatoDto = contatoAssembler.toModel(contatoRepository.save(contato));
        return ResponseEntity.status(HttpStatus.CREATED).body(contatoDto);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id){
        contatoService.remover(id);

    }
}
