package controller;

import Util.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.ManifestationDao;
import model.Location;
import model.Manifestation;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.UUID;

public class ManifestationController {
    public static ManifestationDao manifestationDao;
    static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static Route getAllManifestations = (Request request, Response response)
            -> gson.toJson(manifestationDao.allManifestations());

    public static Route newManifestation = (Request request, Response response) ->
    {

        response.status(200);

        System.out.println(gson.toJson(request.body()));

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


}
