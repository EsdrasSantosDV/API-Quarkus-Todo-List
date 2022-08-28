package org.todo.list.model.Parser;

import org.todo.list.dto.TodoDto;
import org.todo.list.model.Todo;

public class TodoParser {

    public static TodoParser get()
    {
        return new TodoParser();
    }

    public Todo entidade(TodoDto dto)
    {
        Todo entidade=new Todo();
        entidade.setNome(dto.getNome());
        entidade.setId(dto.getId());
        entidade.setDataCriacao(dto.getDataCriacao());
        return entidade;
    }

    public TodoDto dto(Todo entidade)
    {
        TodoDto dto=new TodoDto();
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setDataCriacao(entidade.getDataCriacao());
        return dto;
    }
}
