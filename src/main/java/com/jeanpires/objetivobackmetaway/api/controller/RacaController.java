package com.jeanpires.objetivobackmetaway.api.controller;

import com.jeanpires.objetivobackmetaway.api.converterDto.assembler.RacaAssembler;
import com.jeanpires.objetivobackmetaway.api.converterDto.disassembler.RacaInputDisasembler;
import com.jeanpires.objetivobackmetaway.api.dtos.RacaDto;
import com.jeanpires.objetivobackmetaway.api.inputs.RacaInput;
import com.jeanpires.objetivobackmetaway.domain.model.Raca;
import com.jeanpires.objetivobackmetaway.domain.repositories.RacaRepository;
import com.jeanpires.objetivobackmetaway.domain.service.RacaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController()
@RequestMapping(path = "/racas", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Raca")
public class RacaController {

    @Autowired
    private RacaRepository racaRepository;

    @Autowired
    private RacaService racaService;

    @Autowired
    private RacaAssembler racaAssembler;
    
    @Autowired
    private RacaInputDisasembler racaDisasembler;


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<RacaDto> buscarPorId(@PathVariable Long id) {
        Raca raca = racaService.buscarPorId(id);
        RacaDto racaDto = racaAssembler.toModel(raca);
        return  ResponseEntity.ok(racaDto);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<RacaDto> adicionar(@RequestBody @Valid RacaInput racaInput) {
        Raca raca = racaDisasembler.toDomainObject(racaInput);
        Raca racaCriado = racaRepository.save(raca);
        RacaDto racaDto = racaAssembler.toModel(racaCriado);
        return ResponseEntity.status(HttpStatus.CREATED).body(racaDto);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<RacaDto> alterar(@RequestBody @Valid RacaInput racaInput, @PathVariable Long id) {
        Raca raca = racaService.buscarPorId(id);
        racaDisasembler.copyToDomainObject(racaInput, raca);
        RacaDto racaDto = racaAssembler.toModel(racaRepository.save(raca));
        return ResponseEntity.status(HttpStatus.CREATED).body(racaDto);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id){
        racaService.remover(id);

    }
}
