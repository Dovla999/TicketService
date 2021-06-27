package controller;

import Util.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.ManifestationDao;
import model.User;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.LocalDateTime;

public class ManifestationController {
    public static ManifestationDao manifestationDao;
    public static User currentUser;
    static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static Route getAllManifestations = (Request request, Response response)
            -> gson.toJson(manifestationDao.allManifestations());

}
