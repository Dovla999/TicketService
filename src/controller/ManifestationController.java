package controller;

import Util.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.ManifestationDao;
import dto.ManifSellerDto;
import model.Location;
import model.Manifestation;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ManifestationController {
    public static ManifestationDao manifestationDao;
    static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static Route getAllManifestations = (Request request, Response response)
            -> {
        Map<String, String> sfs = new HashMap<>();
        request.queryParams()
                .forEach(s -> sfs.put(s, request.queryParams(s)));
        return gson.toJson(manifestationDao.getAllForAllUsers(sfs));


    };

    public static Route newManifestation = (Request request, Response response) ->
    {

        response.status(200);


        var body = gson.fromJson((request.body()), HashMap.class);

        if (body.containsKey("capacity") &&
                body.containsKey("name") &&
                body.containsKey("type") &&
                body.containsKey("image") &&
                body.containsKey("price") &&
                body.containsKey("date") &&
                body.containsKey("time") &&
                body.containsKey("location") &&
                body.containsKey("latitude") &&
                body.containsKey("longitude")
        ) {
            Manifestation manifestation = new Manifestation();
            manifestation.setActive(false);
            manifestation.setCapacity(Integer.valueOf((String) body.get("capacity")));
            manifestation.setDeleted(false);
            manifestation.setCreator(UserController.currentUser);
            manifestation.setActive(false);
            manifestation.setImage((String) body.get("image"));
            manifestation.setLocation(new Location((Double) body.get("longitude"), (Double) body.get("latitude"), (String) body.get("location")));
            manifestation.setName((String) body.get("name"));
            manifestation.setTicketPrice(Double.valueOf((String) body.get("price")));
            manifestation.setType((String) body.get("type"));
            manifestation.setDateTime(LocalDateTime.of(LocalDate.parse((CharSequence) body.get("date")), LocalTime.parse((CharSequence) body.get("time"))));
            manifestation.setUuid(UUID.randomUUID());
            manifestationDao.addManifestation(manifestation);
            response.status(200);
            response.body("New manifestation added");
        } else {
            response.status(400);
            response.body("Check that you have filled in all fields");
        }
        return response;
    };

    public static Route updateManifestation = (Request request, Response response) ->
    {
        response.status(200);


        var body = gson.fromJson((request.body()), HashMap.class);

        if (body.containsKey("capacity") &&
                body.containsKey("name") &&
                body.containsKey("type") &&
                body.containsKey("image") &&
                body.containsKey("ticketPrice") &&
                body.containsKey("date") &&
                body.containsKey("time") &&
                body.containsKey("location") &&
                body.containsKey("latitude") &&
                body.containsKey("longitude")
        ) {
            Manifestation manifestation = manifestationDao.findById((String) body.get("uuid"));
            manifestation.setCapacity(Integer.valueOf(body.get("capacity").toString().replace(".0", "")));
            manifestation.setDeleted(false);
            manifestation.setCreator(UserController.currentUser);
            manifestation.setImage((String) body.get("image"));
            manifestation.setLocation(new Location((Double) body.get("longitude"), (Double) body.get("latitude"), (String) body.get("location")));
            manifestation.setName((String) body.get("name"));
            manifestation.setTicketPrice(Double.valueOf(body.get("ticketPrice").toString()));
            manifestation.setType((String) body.get("type"));
            manifestation.setDateTime(LocalDateTime.of(LocalDate.parse((CharSequence) body.get("date")), LocalTime.parse((CharSequence) body.get("time"))));
            response.status(200);
            response.body("Manifestation updated");
        } else {
            response.status(400);
            response.body("Check that you have filled in all fields");
        }
        return response;
    };


    public static Route getOneManifestation = (Request request, Response response) ->
            gson.toJson(manifestationDao.findById(request.params(":id")));

    public static Route changeActiveManifestation = (Request request, Response response) ->
    {
        if (manifestationDao.changeActiveById(request.params("id"))) {
            response.status(200);
            response.body("Success");
        } else {
            response.status(400);
            response.body("Wrong id");
        }
        return response;
    };


    public static Route getSellerManifestations = (Request request, Response response) ->
            gson.toJson(manifestationDao.getManifestationsForSeller(UserController.currentUser)
                    .stream().map(ManifSellerDto::new)
                    .collect(Collectors.toList()));

    public static Route deleteManifestation = (request, response) ->
            manifestationDao.deleteManifestation(request.params("id"));

    public static Route getTypes = (request, response) ->
            gson.toJson(manifestationDao.getTypes());

    public static Route getAllManifestationsForAdmin = (request, response) -> {
        Map<String, String> sfs = new HashMap<>();
        request.queryParams()
                .forEach(s -> sfs.put(s, request.queryParams(s)));
        return TicketController.gson.toJson(manifestationDao.getAllForAdmin(sfs));

    };

    public static Route canComment = (request, response) -> manifestationDao.isCommentable(UserController.currentUser, request.params("id"));

    public static Route canBuyTickets = (request, response) -> manifestationDao.ticketsAvailable(UserController.currentUser, request.params("id"));

}
