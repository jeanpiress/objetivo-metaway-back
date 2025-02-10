package com.jeanpires.objetivobackmetaway.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class MetaWaySecurity {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getUsuarioCpf(){
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();
        return jwt.getClaim("cpf");
    }

    public Long getUsuarioId(){
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();
        return jwt.getClaim("id");
    }

    public boolean temAutorizacao(String role) {
        Collection<? extends GrantedAuthority> authorities = getAuthentication().getAuthorities();
        return authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role));
    }
}
