package io.tacsio.ifood.cadastro.resources;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
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

import io.tacsio.ifood.cadastro.model.Prato;
import io.tacsio.ifood.cadastro.model.Restaurante;

@Tag(name = "prato")
@Path("/restaurantes/{idRestaurante}/pratos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PratoResource {

  @GET
  public List<Prato> index(@PathParam("idRestaurante") Long idRestaurante) {

    var restaurante = (Restaurante) Restaurante.findByIdOptional(idRestaurante).orElseThrow(() -> {
      throw new NotFoundException();
    });

    return Prato.list("restaurante", restaurante);
  }

  @Transactional
  @POST
  public Response create(@PathParam("idRestaurante") Long idRestaurante, Prato dto) {
    var restaurante = (Restaurante) Restaurante.findByIdOptional(idRestaurante).orElseThrow(() -> {
      throw new NotFoundException();
    });

    var novoPrato = new Prato();
    novoPrato.nome = dto.nome;
    novoPrato.descricao = dto.descricao;
    novoPrato.preco = dto.preco;
    novoPrato.restaurante = restaurante;

    novoPrato.persist();
    return Response.status(Status.CREATED).build();
  }

  @Transactional
  @Path("{id}")
  @PUT
  public Response update(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id, Prato dto) {
    var restaurante = (Restaurante) Restaurante.findByIdOptional(idRestaurante).orElseThrow(() -> {
      throw new NotFoundException();
    });

    var prato = (Prato) Prato.findByIdOptional(id).orElseThrow(() -> {
      throw new NotFoundException();
    });

    if (prato.restaurante.id != restaurante.id) {
      throw new BadRequestException("Prato inválido para o restaurante.");
    }

    prato.nome = dto.nome;
    prato.preco = dto.preco;
    prato.descricao = dto.descricao;

    return Response.status(Status.OK).build();
  }

  @Transactional
  @Path("{id}")
  @DELETE
  public Response delete(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id) {

    var restaurante = (Restaurante) Restaurante.findByIdOptional(idRestaurante).orElseThrow(() -> {
      throw new NotFoundException();
    });

    var prato = (Prato) Prato.findByIdOptional(id).orElseThrow(() -> {
      throw new NotFoundException();
    });

    if (prato.restaurante.id != restaurante.id) {
      throw new BadRequestException("Prato inválido para o restaurante.");
    }

    prato.delete();

    return Response.status(Status.NO_CONTENT).build();
  }
}
