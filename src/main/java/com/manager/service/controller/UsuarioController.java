package com.manager.service.controller;

import com.manager.service.model.Usuario;
import com.manager.service.repository.UsuarioRepository;
import com.manager.service.service.UserDetailService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {
    
    private final UsuarioRepository usuarioRepository;
    private final UserDetailService userDetailService;
    
    @Autowired
    public UsuarioController(UsuarioRepository usuarioRepository, UserDetailService userDetailService) {
        this.usuarioRepository = usuarioRepository;
        this.userDetailService = userDetailService;
    }
    
    @PostMapping
    public ResponseEntity<Usuario> salvar(@RequestBody @Valid Usuario usuario) {
        try {
            Usuario novo = userDetailService.salvar(usuario);
            return ResponseEntity.created(URI.create("/buscar-por-id/" + novo.getId())).allow(HttpMethod.GET).build();
        } catch(RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage());
        }
    }
    
    @GetMapping("buscar-por-id/{id}")
    public Usuario buscarPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuário não encontrado!"));
    }
    
}
