package com.jeanpires.objetivobackmetaway.api.controller;

import com.jeanpires.objetivobackmetaway.api.converterDto.assembler.AtendimentoAssembler;
import com.jeanpires.objetivobackmetaway.api.converterDto.disassembler.AtendimentoInputDisasembler;
import com.jeanpires.objetivobackmetaway.api.dtos.AtendimentoDto;
import com.jeanpires.objetivobackmetaway.api.inputs.AtendimentoInput;
import com.jeanpires.objetivobackmetaway.domain.model.Atendimento;
import com.jeanpires.objetivobackmetaway.domain.repositories.AtendimentoRepository;
import com.jeanpires.objetivobackmetaway.domain.service.AtendimentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController()
@RequestMapping(path = "/atendimentos", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Atendimentos")
public class AtendimentoController {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private AtendimentoService atendimentoService;

    @Autowired
    private AtendimentoAssembler atendimentoAssembler;
    
    @Autowired
    private AtendimentoInputDisasembler atendimentoDisasembler;


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<AtendimentoDto> buscarPorId(@PathVariable Long id) {
        Atendimento atendimento = atendimentoService.buscarPorId(id);
        AtendimentoDto atendimentoDto = atendimentoAssembler.toModel(atendimento);
        return  ResponseEntity.ok(atendimentoDto);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<AtendimentoDto> adicionar(@RequestBody @Valid AtendimentoInput atendimentoInput) {
        Atendimento atendimento = atendimentoDisasembler.toDomainObject(atendimentoInput);
        Atendimento atendimentoCriado = atendimentoService.adicionar(atendimento);
        AtendimentoDto atendimentoDto = atendimentoAssembler.toModel(atendimentoCriado);
        return ResponseEntity.status(HttpStatus.CREATED).body(atendimentoDto);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<AtendimentoDto> alterar(@RequestBody @Valid AtendimentoInput atendimentoInput, @PathVariable Long id) {
        Atendimento atendimento = atendimentoService.buscarPorId(id);
        atendimentoDisasembler.copyToDomainObject(atendimentoInput, atendimento);
        AtendimentoDto atendimentoDto = atendimentoAssembler.toModel(atendimentoRepository.save(atendimento));
        return ResponseEntity.status(HttpStatus.CREATED).body(atendimentoDto);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id){
        atendimentoService.remover(id);

    }
}
