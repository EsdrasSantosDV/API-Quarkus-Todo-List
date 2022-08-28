package org.todo.list.service;

import org.todo.list.dao.TodoDao;
import org.todo.list.model.Todo;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
//COLOCANDO ISSO O CICLO DE VIDA DA REQUISIÇÃO E MENOR

//PARA BANCO DE DADOS O REQUEST SCOPED E O MELHOR
//PQ
@RequestScoped
//APPLICATION SCOPED MANTEM OS DADOS NA APLICAÇÃO
//ApplicationScoped
public class TodoService {

    @Inject
    TodoDao dao;

    private void validar(Todo todo){
        //VALIDAR REGRA DE NEGOCIO
        if(todo.getNome()==null)
        {
            throw new NotFoundException();
        }
    }

    //ROLLBACK REMOVER OS DADOS QUE TINHA INSERIDO NA TABELA
    @Transactional(rollbackOn = Exception.class)
    public void inserir(Todo todo)
    {
        //VALIDAÇÃO
        validar(todo);
        todo.setDataCriacao(LocalDateTime.now());
        //CHAMADA DA DAO
        dao.inserir(todo);
    }
    //NO LISTAR EU NÃO PRECISO FAZER UMA TRANSACTIONAL COM O BANCO
    public List<Todo> listar()
    {
        //CHAMADA DA DAO
        return dao.listar();
    }



}
