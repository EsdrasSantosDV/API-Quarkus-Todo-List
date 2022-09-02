package org.todo.list.dao;


import org.eclipse.microprofile.opentracing.Traced;
import org.todo.list.model.Todo;

import javax.enterprise.context.RequestScoped;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

//OBJETO DE ACESSO A DADOS

//DADOS TEM QUE SER MINIMAMENT TRATADOS
@RequestScoped
@Traced
public class TodoDao {
    //INJETAR O CONTEXTO
    @PersistenceContext
    EntityManager em;


    @Transactional
    public void inserir(Todo todo)
    {
        String nomeSql="INSERIR_TODO";
        Query query=em.createNamedQuery(nomeSql);
        query.setParameter("nome",todo.getNome());
        query.setParameter("dataCriacao",todo.getDataCriacao());

        query.executeUpdate();



    }
    @Transactional
    public void atualizar(Todo todo)
    {
        String nomeSql="ATUALIZAR_TODO";
        Query query=em.createNamedQuery(nomeSql);

        query.setParameter("id",todo.getId());
        query.setParameter("nome",todo.getNome());
        query.setParameter("dataCriacao",todo.getDataCriacao());

        query.executeUpdate();
    }

//    @Transactional
//    private void inserirOuAtualizar(String nomeSql,Todo todo)
//    {
//        Query query=em.createNamedQuery(nomeSql);
//
//        query.setParameter("id",todo.getId());
//        query.setParameter("nome",todo.getNome());
//        query.setParameter("dataCriacao",todo.getDataCriacao());
//
//        query.executeUpdate();
//    }
    public Todo buscarPorId(Long id){
        String nomeConsulta="CONSULTAR_TODO_ID";
        Todo todo;
        TypedQuery<Todo> query=em.createNamedQuery(nomeConsulta,Todo.class);
        query.setParameter("id",id);

        try{
            todo=  query.getSingleResult();
        }catch (NoResultException e){
            todo=null;
        }

        return todo;

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


    @Transactional
    public void excluir(Long id)
    {
        String nomeConsulta="EXCLUIR_TODO";
        Query query=em.createNamedQuery(nomeConsulta);

        //PASSAR OS PARAMETROS PARA O DELETE

        query.setParameter("id",id);


        query.executeUpdate();
    }

    public Boolean IsnomeRepetido(String nome)
    {
        String nomeConsulta="CONSULTAR_NOME_REPETIDO_TODO";
        Boolean nomeRepetido=Boolean.FALSE;
        TypedQuery<Todo> query=em.createNamedQuery(nomeConsulta,Todo.class);

        //PODE COLOCAR TAMBEM
        //
        query.setParameter("nome",nome);

        nomeRepetido=  query.getResultList().size()>0;


        return nomeRepetido;
    }


}
