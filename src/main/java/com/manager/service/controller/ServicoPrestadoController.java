package com.manager.service.controller;

import com.manager.service.model.dto.ServicoPrestadoDTO;
import com.manager.service.model.ServicoPrestado;
import com.manager.service.repository.ClienteRepository;
import com.manager.service.repository.ServicoPrestadoRepository;
import com.manager.service.util.ConversorBigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/servicos-prestados")
public class ServicoPrestadoController {
    
    private final ServicoPrestadoRepository servicoPrestadoRepository;
    private final ClienteRepository clienteRepository;
    private final ConversorBigDecimal conversorBigDecimal;

    @Autowired
    public ServicoPrestadoController(ServicoPrestadoRepository servicoPrestadoRepository, ClienteRepository clienteRepository, ConversorBigDecimal conversorBigDecimal) {
        this.servicoPrestadoRepository = servicoPrestadoRepository;
        this.clienteRepository = clienteRepository;
        this.conversorBigDecimal = conversorBigDecimal;
    }

    @PostMapping
    public ResponseEntity<ServicoPrestado> salvar(@RequestBody @Valid ServicoPrestadoDTO servicoPrestadoDTO) {
        var cliente = clienteRepository.findById(servicoPrestadoDTO.getClienteId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cliente inexistente"));
        var servicoPrestado = ServicoPrestado
                .builder()
                .descricao(servicoPrestadoDTO.getDescricao())
                .valor(conversorBigDecimal.converterStringForBigDeciaml(servicoPrestadoDTO.getPreco()))
                .dataServicoPrestado(servicoPrestadoDTO.getDataServicoPrestado())
                .cliente(cliente)
                .build();
        return new ResponseEntity<>(servicoPrestadoRepository.save(servicoPrestado),HttpStatus.CREATED);
    }
    
    @PutMapping("{codigo}")
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable(name = "codigo") Long codigo, @RequestBody @Valid ServicoPrestado servicoPrestado) {
        servicoPrestadoRepository.findById(codigo)
            .map(c -> {
                servicoPrestado.setId(c.getId());
                return servicoPrestadoRepository.save(servicoPrestado);
            }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("buscarPorNomeData")
    public ResponseEntity<List<ServicoPrestado>> buscarPorNomeData(
            @RequestParam(defaultValue = "", name = "nomeCliente", required = false) String nomeCliente,
            @RequestParam(name = "dataServicoPrestado", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataServicoPrestado) {
        
        List<ServicoPrestado> listaDeServicos = servicoPrestadoRepository.buscarPorNomeData(nomeCliente, dataServicoPrestado);
        if(listaDeServicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(servicoPrestadoRepository.buscarPorNomeData(nomeCliente, dataServicoPrestado));
    }
    
    @GetMapping("buscarPorId/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServicoPrestado getServicoPrestadoById(@PathVariable Long id) {
        return servicoPrestadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servico prestado não encontrado!"));
    }
    
    @DeleteMapping("excluir/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        servicoPrestadoRepository.findById(id)
            .map(cliente -> {
                servicoPrestadoRepository.deleteById(id);
                return Void.TYPE;
            }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
}
