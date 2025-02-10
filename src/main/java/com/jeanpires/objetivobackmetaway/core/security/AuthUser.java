package com.jeanpires.objetivobackmetaway.core.security;

import com.jeanpires.objetivobackmetaway.domain.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AuthUser extends User {
    private static  final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String cpf;


    public AuthUser(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
       super(usuario.getCpf(), usuario.getSenha(), authorities);

        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.cpf = usuario.getCpf();
    }
}
