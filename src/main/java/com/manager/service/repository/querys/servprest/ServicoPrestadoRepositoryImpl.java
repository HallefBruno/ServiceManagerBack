package com.manager.service.repository.querys.servprest;

import com.manager.service.model.Cliente;
import com.manager.service.model.ServicoPrestado;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ServicoPrestadoRepositoryImpl implements ServicoPrestadoCustom {
    
    @PersistenceContext
    private EntityManager manager;
    
    @Override
    public List<ServicoPrestado> buscarPorNomeData(String nomeCliente, LocalDate dataServicoPrestado) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<ServicoPrestado> query = cb.createQuery(ServicoPrestado.class);
        Root<ServicoPrestado> servicoPrestado = query.from(ServicoPrestado.class);
        Join<ServicoPrestado,Cliente> cliente = (Join) servicoPrestado.fetch("cliente");
        List<Predicate> predicates = new ArrayList<>();
        
        Predicate predicateNome = cb.like(cb.upper(cliente.get("nome")), "%" + nomeCliente.toUpperCase() + "%");
        predicates.add(predicateNome);
        
        if (Objects.nonNull(dataServicoPrestado)) {
            Predicate predicateData = cb.equal(servicoPrestado.get("dataServicoPrestado"), dataServicoPrestado);
            predicates.add(predicateData);
        }
        
        query.select(servicoPrestado);
        query.where(predicates.toArray(new Predicate[]{}));
        
        TypedQuery<ServicoPrestado> typedQuery = manager.createQuery(query);
        return typedQuery.getResultList();
    }
    
}
