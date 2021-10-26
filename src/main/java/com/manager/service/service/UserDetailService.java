package com.manager.service.service;

import com.manager.service.model.Usuario;
import com.manager.service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author hallef.silva
 */
@Service
public class UserDetailService implements UserDetailsService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public Usuario salvar(Usuario usuario) {
        boolean exists = usuarioRepository.existsByUsername(usuario.getUsername());
        if(exists) {
            throw new RuntimeException("Esse usuário já está cadastrado!");
        }
        return usuarioRepository.save(usuario);
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByUsernameContainingIgnoreCase(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuário não encontrado!"));
        return User.builder()
                .password(usuario.getPassword())
                .username(usuario.getUsername())
                .roles("USER").build();
    }
    
}
