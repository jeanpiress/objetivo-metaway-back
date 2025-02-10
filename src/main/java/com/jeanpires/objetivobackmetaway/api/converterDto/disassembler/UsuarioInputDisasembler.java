package com.jeanpires.objetivobackmetaway.api.converterDto.disassembler;

import com.jeanpires.objetivobackmetaway.api.inputs.UsuarioInput;
import com.jeanpires.objetivobackmetaway.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioInputDisasembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toDomainObject (UsuarioInput usuarioInput) {

        return modelMapper.map(usuarioInput, Usuario.class);
    }

    public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {

        modelMapper.map(usuarioInput, usuario);
    }
}
