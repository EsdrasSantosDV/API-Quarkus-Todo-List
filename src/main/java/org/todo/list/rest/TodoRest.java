package org.todo.list.rest;


import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.todo.list.dto.TodoDto;
import org.todo.list.model.Todo;
import org.todo.list.service.TodoService;

import javax.inject.Inject;
import javax.validation.Constraint;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//URI
@Path("todo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoRest {

    @Inject
    TodoService service;

    @Inject
    Validator validator;


//    @GET
//    @Path("/{nome}")
//    public Response adicionarLista(@PathParam("nome") String nome)
//    {
//        service.adicionar(nome);
//        return Response.status(Response.Status.ACCEPTED).
//                entity(service.listar()).
//                build();
//    }

    @GET
    @Path("/")
    @Operation(
	summary = "Listar todos os Todo",
	description = "Retorna uma Lista de Todo"
    )
    @APIResponse(
	responseCode = "200",
	description = "lista de tarefas",
	content ={
            @Content(mediaType="application/json",
            schema = @Schema(implementation = TodoDto.class,type= SchemaType.ARRAY))
    })
    public Response listar()
    {
        return Response.status(Response.Status.OK).
                entity(service.listar()).
                build();
    }

    @POST
    @Path("/")
    @Operation(
            summary = "Incluir uma Tarefa",
            description = "Incluir uma Tarefa"
    )
    @APIResponse(
            responseCode = "201",
            description = "tarefa",
            content ={
                    @Content(mediaType="application/json",
                            schema = @Schema(implementation = TodoDto.class))
            })
    public Response incluir(TodoDto todo){
        Set<ConstraintViolation<TodoDto>> erros=validator.validate(todo);
        if(erros.isEmpty())
        {
            service.inserir(todo);
        }
        else
        {
            List<String> listaErros=erros.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
//            listaErros.forEach(i->{
//                System.out.println(i);
//            });
            throw new NotFoundException(listaErros.get(0));
        }


        return Response.status(Response.Status.CREATED)
                .build();
    }
    @GET
    @Path("/{id}")
    @Operation(
            summary = "Buscar uma Tarefa",
            description = "Buscar uma Tarefa por Id"
    )
    @APIResponse(
            responseCode = "200",
            description = "tarefa",
            content ={
                    @Content(mediaType="application/json",
                            schema = @Schema(implementation = TodoDto.class))
            })
    public Response buscarPorId(@PathParam("id") Long id)
    {
        return Response.status(Response.Status.OK)
                .entity(service.buscar(id))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(
            summary = "Atualizar uma Tarefa",
            description = "Atualizar uma Tarefa pelo Id"
    )
    @APIResponse(
            responseCode = "200",
            description = "atualizar tarefa",
            content ={
                    @Content(mediaType="application/json",
                            schema = @Schema(implementation = TodoDto.class))
            })
    public Response atualizar(@PathParam("id") Long id,TodoDto todo)
    {
        service.atualizar(id,todo);
        return Response.status(Response.Status.OK).build();

        //PODERIA RETORANR O ENTITY
    }


    @DELETE
    @Path("/{id}")
    @Operation(
            summary = "Excluir uma Tarefa",
            description = "Excluir uma Tarefa"
    )
    @APIResponse(
            responseCode = "202",
            description = "excluir tarefa",
            content ={
                    @Content(mediaType="application/json",
                            schema = @Schema(implementation = TodoDto.class))
            })
    public Response excluir(@PathParam("id") Long id){
        service.excluir(id);
        return Response.status(Response.Status.ACCEPTED)
                .build();
    }


}
