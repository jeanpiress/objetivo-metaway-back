package com.jeanpires.objetivobackmetaway.api.controller;

import com.jeanpires.objetivobackmetaway.api.converterDto.assembler.ClienteAssembler;
import com.jeanpires.objetivobackmetaway.api.converterDto.disassembler.ClienteInputDisasembler;
import com.jeanpires.objetivobackmetaway.api.dtos.ClienteDto;
import com.jeanpires.objetivobackmetaway.api.inputs.ClienteInput;
import com.jeanpires.objetivobackmetaway.domain.model.Cliente;
import com.jeanpires.objetivobackmetaway.domain.repositories.ClienteRepository;
import com.jeanpires.objetivobackmetaway.domain.service.ClienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController()
@RequestMapping(path = "/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteAssembler clienteAssembler;
    
    @Autowired
    private ClienteInputDisasembler clienteDisasembler;


    @PreAuthorize("hasAuthority('CLIENTE')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDto> buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        ClienteDto clienteDto = clienteAssembler.toModel(cliente);
        return  ResponseEntity.ok(clienteDto);
    }
    @PreAuthorize("hasAuthority('CLIENTE')")
    @PostMapping
    public ResponseEntity<ClienteDto> adicionar(@RequestBody @Valid ClienteInput clienteInput) {
        Cliente cliente = clienteDisasembler.toDomainObject(clienteInput);
        Cliente clienteCriado = clienteService.adicionar(cliente);
        ClienteDto clienteDto = clienteAssembler.toModel(clienteCriado);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteDto);
    }
    @PreAuthorize("hasAuthority('CLIENTE')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDto> alterar(@RequestBody @Valid ClienteInput clienteInput, @PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        clienteDisasembler.copyToDomainObject(clienteInput, cliente);
        ClienteDto clienteDto = clienteAssembler.toModel(clienteRepository.save(cliente));
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteDto);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id){
        clienteService.remover(id);

    }
}
