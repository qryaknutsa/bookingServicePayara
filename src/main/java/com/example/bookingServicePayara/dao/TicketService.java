package com.example.bookingServicePayara.dao;

import com.example.bookingServicePayara.dto.TicketWithEventWrite;
import com.example.bookingServicePayara.dto.TicketWrite;
import com.example.bookingServicePayara.exception.TicketServiceNotAvailable;
import com.example.bookingServicePayara.model.Person;
import com.example.bookingServicePayara.model.Ticket;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;


import java.util.List;

public class TicketService {
//    private final static String SPRING_SERVICE_URL = "https://localhost:9011/ticketservicepayara/TMA/api/v2/tickets";
    private final static String SPRING_SERVICE_URL = "http://localhost:8081/ticketservicepayara/TMA/api/v2/tickets";

    public static Object saveTicket(TicketWrite ticket) {
        try (Client client = ClientBuilder.newClient()) {
            Response response = client.target(SPRING_SERVICE_URL)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(ticket, MediaType.APPLICATION_JSON));

            if (response.getStatus() == 201) return response.readEntity(Ticket.class);
            else throw new TicketServiceNotAvailable(response.readEntity(String.class));
        }
    }

    public static Object saveTickets(TicketWithEventWrite ticket, int num) {
        List<Integer> ids;

        try (Client client = ClientBuilder.newClient()) {
            Response response = client.target(SPRING_SERVICE_URL + "/bulk/" + num)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(ticket, MediaType.APPLICATION_JSON));

            if (response.getStatus() == 201) ids = (List<Integer>) response.readEntity(Object.class);
            else throw new TicketServiceNotAvailable(response.readEntity(String.class));
        }
        return ids;
    }


    public static void deleteTickets(int id) {
        try (Client client = ClientBuilder.newClient()) {
            Response response = client.target(SPRING_SERVICE_URL + "/bulk/" + id)
                    .request(MediaType.APPLICATION_XML)
                    .delete();

            if (response.getStatus() != 200) { // Проверяем статус HTTP ответа
                throw new TicketServiceNotAvailable("Failed to delete tickets. Status: " + response.getStatus());
            }

            String xmlResponse = response.readEntity(String.class);
            int status = extractStatusFromXml(xmlResponse);

            if (status != 204) {
                throw new TicketServiceNotAvailable("Delete operation failed with status: " + status);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static Integer findTicketsByEventId(int id) {
        String s = SPRING_SERVICE_URL + "/events/" + id;
        try (Client client = ClientBuilder.newClient()) {
            Response response = client.target(s)
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) return response.readEntity(Integer.class);
            else if (response.getStatus() == 404) return null;
//            else throw new TicketServiceNotAvailable(response.readEntity(String.class));
            else throw new TicketServiceNotAvailable("сервис не доступен");
        }
    }

    public static TicketWithEventWrite findTicket(int id) {
        String s = SPRING_SERVICE_URL + "/" + id;
        try (Client client = ClientBuilder.newClient()) {
            Response response = client.target(s)
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) return response.readEntity(TicketWithEventWrite.class);
            else if (response.getStatus() == 404) return null;
            else throw new TicketServiceNotAvailable(response.readEntity(String.class));
        }
    }

    public static Person findPerson(int id) {
        String s = SPRING_SERVICE_URL + "/people/" + id;
        try (Client client = ClientBuilder.newClient()) {
            Response response = client.target(s)
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            if (response.getStatus() == 200) return response.readEntity(Person.class);
            else if (response.getStatus() == 404) return null;
            else throw new TicketServiceNotAvailable(response.readEntity(String.class));
        }
    }


    private static int extractStatusFromXml(String xml) throws Exception {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml)));
        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName("status");
        if (nodeList.getLength() > 0) {
            return Integer.parseInt(nodeList.item(0).getTextContent());
        }
        throw new Exception("Status not found in response");
    }
}
