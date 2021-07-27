package io.tacsio.ifood;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
}
