package com.manager.service.repository;

import com.manager.service.model.ServicoPrestado;
import com.manager.service.repository.querys.servprest.ServicoPrestadoCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoPrestadoRepository extends JpaRepository<ServicoPrestado, Long>, ServicoPrestadoCustom {
    
}
