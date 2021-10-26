package com.manager.service.controller;

import com.manager.service.model.Cliente;
import com.manager.service.repository.ClienteRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/clientes")
public class ClienteController {
    
    private final ClienteRepository clienteRepository;
    
    @Autowired
    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody @Valid Cliente cliente) {
        cliente.setDataCadastro(LocalDate.now());
        Cliente nov = clienteRepository.save(cliente);
        return new ResponseEntity<>(nov, HttpStatus.CREATED);
    }
    
    @GetMapping("porId/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id) {
        Optional<Cliente> optionaCliente = clienteRepository.findById(id);
        if(optionaCliente.isPresent()) {
            return new ResponseEntity<>(optionaCliente.get(), HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("porCpf/{cpf}")
    public Cliente buscarClientePorCpf(@PathVariable String cpf) {
        return clienteRepository.findByCpf(cpf).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
    }
    
    @DeleteMapping("excluir/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        clienteRepository.findById(id)
            .map(cliente -> {
                clienteRepository.deleteById(id);
                return Void.TYPE;
            }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    
    @PutMapping("{codigo}")
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable(name = "codigo") Long codigo, @RequestBody @Valid Cliente cliente) {
        clienteRepository.findById(codigo)
            .map(c -> {
                cliente.setId(c.getId());
                return clienteRepository.save(cliente);
            }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/todos")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<List<Cliente>> todos() {
        return new ResponseEntity<>(clienteRepository.todos(), HttpStatus.OK);
    }
    
    
}
