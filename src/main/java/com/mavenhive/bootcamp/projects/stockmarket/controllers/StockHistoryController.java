package com.mavenhive.bootcamp.projects.stockmarket.controllers;

import com.mavenhive.bootcamp.projects.stockmarket.model.Stock;
import com.mavenhive.bootcamp.projects.stockmarket.services.StockHistoryService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/stock")
@Component
public class StockHistoryController {

  @Inject
  StockHistoryService stockHistoryService;

  @GET
  @Path("/history/dataset/{dataset}/{entity}")
  @Produces("application/json")
  @Consumes("application/json")
  public Response stockPrice(@PathParam("dataset") String dataSet, @PathParam("entity") String entity) {
    Stock stock = stockHistoryService.price(dataSet, entity);

    return Response.status(200).entity(stock).build();
  }
}