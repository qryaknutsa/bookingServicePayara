package com.example.bookingServicePayara.controller;

import com.example.bookingServicePayara.dao.EventDao;
import com.example.bookingServicePayara.model.Event;
import com.example.bookingServicePayara.model.Ticket;
import jakarta.inject.Inject;
import jakarta.json.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.StringReader;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Path("/events")
//@ApplicationScoped
public class EventController {
    @Inject
    private EventDao rm;

    private final String justOk = "Ok";

    // All Exceptions will be intercepted via ExceptionToStatus class
    private Response okWith(Object entity) {
        return Response.ok(entity).build();
    }

    @GET
    @Produces("application/json")
    public Response getAllRoutes() {
        return Response.ok("{\"message\": \"qwe\"}").build(); // Возвращаем корректный JSON
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvent(@PathParam("id") long id) {
        return okWith(rm.getById(id));
    }

    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewRoute() {
        List<Ticket> tickets = getAllTickets();
        Event event = new Event("qwe", "qweqweqwe", tickets);
        rm.save(event);
        return okWith(justOk);
    }


    private List<Ticket> getAllTickets(){
        Client client = ClientBuilder.newClient();
        String springServiceUrl = "http://localhost:8080/ticketservicepayara/TMA/api/v2/tickets";
        Response response = client.target(springServiceUrl)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        String responseBody = response.readEntity(String.class);
        Json.createArrayBuilder().build();
        JsonArray jsonArray;
        try (JsonReader jsonReader = Json.createReader(new StringReader(responseBody))) {
            jsonArray = jsonReader.readArray();
        }

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.getJsonObject(i);
            JsonValue creationDateValue = jsonObject.get("creationDate");
            String creationDateString;
            if (creationDateValue instanceof JsonNumber) {
                creationDateString = ((JsonNumber) creationDateValue).toString();
            } else {
                throw new RuntimeException("Неправильный тип значения creationDate");
            }
            double epochSecondsDouble = Double.parseDouble(creationDateString);
            long epochSeconds = (long) epochSecondsDouble; // Преобразуем в секунды
            Instant instant = Instant.ofEpochSecond(epochSeconds);
            ZonedDateTime creationDate = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);

            jsonObject = Json.createObjectBuilder(jsonObject)
                    .add("creationDate", creationDate.toString())
                    .build();
            jsonArrayBuilder.add(jsonObject);
        }
        JsonArray newJsonArray = jsonArrayBuilder.build();

        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < newJsonArray.size(); i++) {
            JsonObject jsonObject = newJsonArray.getJsonObject(i);
            tickets.add(jsonObjectToTicket(jsonObject));
        }

        for(Ticket ticket : tickets){
            System.out.println(ticket.getCreationDate());
        }
        client.close();
        return tickets;
    }

    private Ticket jsonObjectToTicket(JsonObject jsonObject) {
        Ticket ticket = new Ticket();
        ticket.setCreationDate(ZonedDateTime.parse(jsonObject.getString("creationDate")));
        // установите остальные поля ticket
        return ticket;
    }

//    private List<Ticket> getAllTickets(){
//        Client client = ClientBuilder.newClient();
//        String springServiceUrl = "http://localhost:8080/ticketservicepayara/TMA/api/v2/tickets";
//        Response response = client.target(springServiceUrl)
//                .request(MediaType.APPLICATION_JSON_TYPE)
//                .get();
//
//        List<Ticket> tickets = response.readEntity(new GenericType<List<Ticket>>() {});
//
//        for(Ticket ticket : tickets){
//            System.out.println(ticket.getCreationDate());
//        }
//
//
////        long epochSeconds = (long) Double.parseDouble(creationDateString); // Преобразуем в секунды
////        Instant instant = Instant.ofEpochSecond(epochSeconds);
////        ZonedDateTime creationDate = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
//        client.close();
//        return tickets;
//    }
}