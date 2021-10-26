package com.manager.service.repository.querys.servprest;

import com.manager.service.model.ServicoPrestado;
import java.time.LocalDate;
import java.util.List;

public interface ServicoPrestadoCustom {
    
    List<ServicoPrestado> buscarPorNomeData(String nomeCliente, LocalDate dataServicoPrestado);
    
}
