package com.jeanpires.objetivobackmetaway.api.converterDto.assembler;

import com.jeanpires.objetivobackmetaway.api.dtos.UsuarioDto;
import com.jeanpires.objetivobackmetaway.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioAssembler {

    @Autowired
    private ModelMapper modelMapper;


    public UsuarioDto toModel(Usuario usuario){
        return modelMapper.map(usuario, UsuarioDto.class);
    }

    public List<UsuarioDto> collectionToModel(List<Usuario> usuarios){
         return usuarios.stream().map(usuario -> toModel(usuario))
                .collect(Collectors.toList());
    }

}
