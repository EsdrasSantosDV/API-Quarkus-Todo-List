package org.todo.list.dao;


import org.todo.list.model.Todo;

import javax.enterprise.context.RequestScoped;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

//OBJETO DE ACESSO A DADOS

//DADOS TEM QUE SER MINIMAMENT TRATADOS
@RequestScoped
public class TodoDao {
    //INJETAR O CONTEXTO
    @PersistenceContext
    EntityManager em;


    @Transactional
    public void inserir(Todo todo)
    {
        String nomeConsulta="INSERIR_TODO";
        Query query=em.createNamedQuery(nomeConsulta);

        //PASSAR OS PARAMETROS PARA O INSERT

        query.setParameter("nome",todo.getNome());
        query.setParameter("dataCriacao",todo.getDataCriacao());

        query.executeUpdate();
    }

    public List<Todo> listar(){
        String nomeConsulta="CONSULTAR_TODO";

        TypedQuery<Todo> query=em.createNamedQuery(nomeConsulta,Todo.class);
        List<Todo> listaRetorno;
        try{
          listaRetorno=  query.getResultList();
        }catch (NoResultException e){
            listaRetorno=new ArrayList();
        }
        return listaRetorno;
    }


}
