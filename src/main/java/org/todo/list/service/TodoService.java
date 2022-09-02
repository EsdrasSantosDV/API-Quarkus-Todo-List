package org.todo.list.service;

import org.eclipse.microprofile.opentracing.Traced;
import org.todo.list.dao.TodoDao;
import org.todo.list.dto.TodoDto;
import org.todo.list.model.Parser.TodoParser;
import org.todo.list.model.Todo;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
//COLOCANDO ISSO O CICLO DE VIDA DA REQUISIÇÃO E MENOR

//PARA BANCO DE DADOS O REQUEST SCOPED E O MELHOR
//PQ
@RequestScoped
//APPLICATION SCOPED MANTEM OS DADOS NA APLICAÇÃO
//ApplicationScoped
@Traced
public class TodoService {

    @Inject
    TodoDao dao;

    private void validar(Todo todo){
        //VALIDAR REGRA DE NEGOCIO
        //HIBERNATE VALIDATOR CUIDA
//        if(todo.getNome()==null)
//        {
//            throw new NotFoundException();
//        }

        if(dao.IsnomeRepetido(todo.getNome())==Boolean.TRUE){
            throw new NotFoundException();
        }



    }

    //ROLLBACK REMOVER OS DADOS QUE TINHA INSERIDO NA TABELA
    @Transactional(rollbackOn = Exception.class)
    public void inserir(@Valid TodoDto todoDto)
    {
        //VALIDAÇÃO
        Todo todo=TodoParser.get().entidade(todoDto);
        validar(todo);
        todo.setDataCriacao(LocalDateTime.now());
        //CHAMADA DA DAO
        dao.inserir(todo);
    }
    //NO LISTAR EU NÃO PRECISO FAZER UMA TRANSACTIONAL COM O BANCO
    public List<TodoDto> listar()
    {
        //CHAMADA DO DAO
        return dao.
                listar().
                stream().
                map(TodoParser.get()::dto).
                collect(Collectors.toList());
    }


    public void excluir(Long id)
    {
        //VALIDAR SE O ID E VALIDO
        if(dao.buscarPorId(id)==null)
        {
            throw new NotFoundException();
        }
        dao.excluir(id);
    }

    public TodoDto buscar(Long id)
    {
        return TodoParser.get().dto(buscarPorId(id));
    }
    @Transactional(rollbackOn = Exception.class)
    public void atualizar(Long id, TodoDto dto) {
        Todo todo=TodoParser.get().entidade(dto);
        Todo todoBanco=buscarPorId(id);
        todoBanco.setNome(todo.getNome());
        dao.atualizar(todoBanco);
    }
    private Todo buscarPorId(Long id)
    {
        Todo todo=dao.buscarPorId(id);
        if(todo==null)
        {
            throw new NotFoundException();
        }
        return todo;
    }
}
