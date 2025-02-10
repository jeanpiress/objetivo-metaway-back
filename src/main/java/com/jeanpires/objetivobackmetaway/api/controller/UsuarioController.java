package com.jeanpires.objetivobackmetaway.api.controller;

import com.jeanpires.objetivobackmetaway.api.converterDto.assembler.UsuarioAssembler;
import com.jeanpires.objetivobackmetaway.api.converterDto.disassembler.UsuarioInputDisasembler;
import com.jeanpires.objetivobackmetaway.api.dtos.UsuarioDto;
import com.jeanpires.objetivobackmetaway.api.inputs.UsuarioInput;
import com.jeanpires.objetivobackmetaway.domain.model.Usuario;
import com.jeanpires.objetivobackmetaway.domain.repositories.UsuarioRepository;
import com.jeanpires.objetivobackmetaway.domain.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController()
@RequestMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioAssembler usuarioAssembler;
    
    @Autowired
    private UsuarioInputDisasembler usuarioDisasembler;


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioDto> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        UsuarioDto usuarioDto = usuarioAssembler.toModel(usuario);
        return  ResponseEntity.ok(usuarioDto);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<UsuarioDto> adicionar(@RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuario = usuarioDisasembler.toDomainObject(usuarioInput);
        Usuario usuarioCriado = usuarioService.adicionar(usuario);
        UsuarioDto usuarioDto = usuarioAssembler.toModel(usuarioCriado);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDto);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<UsuarioDto> alterar(@RequestBody @Valid UsuarioInput usuarioInput, @PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        usuarioDisasembler.copyToDomainObject(usuarioInput, usuario);
        UsuarioDto usuarioDto = usuarioAssembler.toModel(usuarioRepository.save(usuario));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDto);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id){
        usuarioService.remover(id);

    }
}
