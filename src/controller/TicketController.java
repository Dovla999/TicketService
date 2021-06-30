package controller;

import Util.LocalDateTimeAdapter;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.TicketDao;
import model.Manifestation;
import model.Ticket;
import model.TicketType;
import spark.Request;
import spark.Response;
import spark.Route;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TicketController {
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static TicketDao ticketDao;
    public static List<Ticket> cart = new ArrayList<>();
    public static Route cartPrice = (Request request, Response response) -> {
        double totalPrice = 0;
        for (var ticket : cart
        ) {
            int modifier = 1;
            if (ticket.getTicketType().equals(TicketType.VIP)) modifier = 4;
            if (ticket.getTicketType().equals(TicketType.FAN_PIT)) modifier = 2;
            totalPrice += ticket.getManifestation().getTicketPrice() * modifier;
        }

        return totalPrice;
    };
    static ExclusionStrategy strategy = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            return field.getDeclaringClass() == Manifestation.class && field.getName().equals("image");
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    };
    static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .addSerializationExclusionStrategy(strategy)
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();


    public static Route addToCart = (Request request, Response response) ->
    {
        var body = gson.fromJson((request.body()), HashMap.class);

        try {

            var manif = ManifestationController.manifestationDao.findById(
                    ((String) body.get("manifestation"))
            );

            for (int i = 0; i < Integer.parseInt((String) body.get("count")); i++) {
                var t = new Ticket();

                t.setUuid(UUID.randomUUID());
                t.setActive(false);
                t.setOwner(UserController.currentUser);
                t.setManifestation(manif);
                t.setTicketType(TicketType.valueOf((String) body.get("ticketType")));
                int modifier = 1;
                if (t.getTicketType().equals(TicketType.VIP)) modifier = 4;
                if (t.getTicketType().equals(TicketType.FAN_PIT)) modifier = 2;
                t.setTicketPrice(t.getManifestation().getTicketPrice() * modifier);
                cart.add(t);
            }


        } catch (Exception exception) {
            response.status(400);
            response.body("Something went wrong");
            return response;
        }

        response.status(200);
        response.body("Tickets added to cart");
        return response;
    };
    public static Route getCart = (Request request, Response response) ->
            gson.toJson(cart);
    public static Route removeFromCart = (Request request, Response response) -> {
        cart.removeIf(ticket -> ticket.getUuid().toString().equals(request.params(":id")));
        response.status(200);
        return gson.toJson(cart);
    };
    public static Route clientTickets = (Request request, Response response) ->
            gson.toJson(
                    ticketDao.getTicketsForUser(UserController.currentUser)
            );
    static SecureRandom rnd = new SecureRandom();
    public static Route buyCart = (Request request, Response response) ->
    {
        try {
            cart
                    .forEach(ticket -> {
                        if (ticketDao.numberOfTickets(ticket.getManifestation()) < ticket.getManifestation().getCapacity()) {
                            ticket.setId(randomString(10));
                            ticket.getOwner().getTickets().add(ticket);
                            ticketDao.addTicket(ticket);
                            ticket.setActive(true);
                            int modifier = 1;
                            if (ticket.getTicketType().equals(TicketType.VIP)) modifier = 4;
                            if (ticket.getTicketType().equals(TicketType.FAN_PIT)) modifier = 2;
                            ticket.setTicketPrice(ticket.getManifestation().getTicketPrice() * modifier);
                            ticket.getOwner().setPoints(
                                    ticket.getOwner().getPoints()
                                            + ticket.getManifestation().getTicketPrice() * modifier
                            );
                        }
                    });
            cart.clear();
            response.status(200);
            response.body("Successfully bought tickets");
        } catch (Exception exception) {
            response.status(400);
            response.body("Something went wrong");
        }


        return response;
    };

    public static String randomString(int len) {
        return IntStream.range(0, len).mapToObj(i -> String.valueOf(AB.charAt(rnd.nextInt(AB.length())))).collect(Collectors.joining());
    }

    public static Route adminTickets = (Request request, Response response) ->
            gson.toJson(
                    ticketDao.getTickets().values()
            );

    public static Route getTicketsForSeller = (Request request, Response response) ->
            gson.toJson(ticketDao.getTicketsForSeller(UserController.currentUser));

    public static Route deleteTicket = (request, response) ->
            ticketDao.deleteTicket(request.params("id"));

}
