package com.victor.agendadortarefas.infraestructure.security;


import com.victor.agendadortarefas.business.dto.UsuarioDTO;
import com.victor.agendadortarefas.infraestructure.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class UserDetailsServiceImpl{

    @Autowired
    private UsuarioClient usuarioClient;

    public UserDetails carregaDadosUsuario(String email, String token){
        UsuarioDTO usuarioDTO = usuarioClient.buscaUsuarioPorEmail(email, token);
        return User.withUsername(usuarioDTO.getEmail()).password(usuarioDTO.getSenha()).build();
    }
}
