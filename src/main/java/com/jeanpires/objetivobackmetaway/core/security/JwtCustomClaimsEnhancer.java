package com.jeanpires.objetivobackmetaway.core.security;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;

public class JwtCustomClaimsEnhancer implements TokenEnhancer {


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if(authentication.getPrincipal() instanceof AuthUser) {
            var authUser = (AuthUser) authentication.getPrincipal();

            var info = new HashMap<String, Object>();

            info.put("nome", authUser.getNome());
            info.put("cpf", authUser.getCpf());
            info.put("id", authUser.getId());


            var oAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
            oAuth2AccessToken.setAdditionalInformation(info);
        }
        return accessToken;
    }
}
