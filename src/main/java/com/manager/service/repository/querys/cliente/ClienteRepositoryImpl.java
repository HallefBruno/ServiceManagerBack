package com.manager.service.repository.querys.cliente;

import com.manager.service.model.Cliente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


public class ClienteRepositoryImpl implements ClienteRepositoryCustom{
    
    @PersistenceContext
    private EntityManager manager;
    
    @Override
    public List<Cliente> todos() {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Cliente> query = cb.createQuery(Cliente.class);
        Root<Cliente> produto = query.from(Cliente.class);
        query.select(produto);
        TypedQuery<Cliente> typedQuery = manager.createQuery(query);
        return typedQuery.getResultList();
    }
    
}
