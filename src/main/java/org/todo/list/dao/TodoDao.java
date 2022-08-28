package org.todo.list.dao;


import org.todo.list.model.Todo;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

//OBJETO DE ACESSO A DADOS

//DADOS TEM QUE SER MINIMAMENT TRATADOS
@RequestScoped
public class TodoDao {
    //INJETAR O CONTEXTO
    @PersistenceContext
    EntityManager em;



    public void inserir()
    {

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
