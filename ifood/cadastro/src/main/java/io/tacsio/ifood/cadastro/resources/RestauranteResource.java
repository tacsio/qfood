package io.tacsio.ifood.cadastro.resources;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import io.tacsio.ifood.cadastro.model.Restaurante;

@Tag(name = "restaurante")
@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestauranteResource {

  @GET
  public List<Restaurante> index() {
    return Restaurante.listAll();
  }

  @Transactional
  @POST
  public Response create(Restaurante dto) {
    dto.persist();

    return Response.status(Status.CREATED).build();
  }

  @Transactional
  @PUT
  @Path("/{id}")
  public Response update(@PathParam("id") Long id, Restaurante dto) {

    var restaurante = (Restaurante) Restaurante.findByIdOptional(id).orElseThrow(() -> new NotFoundException());
    restaurante.nome = dto.nome;

    return Response.status(Status.ACCEPTED).build();
  }

  @Transactional
  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam("id") Long id) {
    Optional<Restaurante> restaurante = Restaurante.findByIdOptional(id);

    restaurante.ifPresentOrElse(Restaurante::delete, () -> {
      throw new NotFoundException();
    });

    return Response.status(Status.NO_CONTENT).build();
  }
}