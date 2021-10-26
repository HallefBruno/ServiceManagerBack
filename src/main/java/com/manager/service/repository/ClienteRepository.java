package com.manager.service.repository;

import com.manager.service.model.Cliente;
import com.manager.service.repository.querys.cliente.ClienteRepositoryCustom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteRepositoryCustom {
    Optional<Cliente> findByCpf(String cpf);
}
