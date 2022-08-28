package org.todo.list.rest;


import org.todo.list.service.TodoService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//URI
@Path("todo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoRest {

    @Inject
    TodoService service;

    @GET
    @Path("/{nome}")
    public Response adicionarLista(@PathParam("nome") String nome)
    {
        service.adicionar(nome);
        return Response.status(Response.Status.ACCEPTED).
                entity(service.listar()).
                build();
    }

    @GET
    @Path("/")
    public Response obterLista()
    {
        return Response.status(Response.Status.OK).
                entity(service.listar()).
                build();
    }


}
