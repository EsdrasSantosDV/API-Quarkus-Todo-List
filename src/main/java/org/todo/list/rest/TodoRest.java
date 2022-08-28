package org.todo.list.rest;


import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.todo.list.model.Todo;
import org.todo.list.service.TodoService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//URI
@Path("todo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoRest {

    @Inject
    TodoService service;

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
            schema = @Schema(implementation = Todo.class,type= SchemaType.ARRAY))
    })
    public Response listar()
    {
        return Response.status(Response.Status.OK).
                entity(service.listar()).
                build();
    }


}
