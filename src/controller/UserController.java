package controller;

import Util.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.UserDao;
import model.User;
import model.UserGender;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;


public class UserController {
    public static UserDao userDao;
    public static User currentUser;
    static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    public static Route newClient = (Request request, Response response) -> {

        if (currentUser != null) {
            response.body("User logged in");
            response.status(400);
            return response;
        }


        var body = gson.fromJson((request.body()), HashMap.class);


        User user = new User();
        var message = "Registration successful";
        try {
            user.setFirstName((String) body.get("firstname"));
            if (user.getFirstName().equals("")) message = "First name can't be empty";
            user.setLastName((String) body.get("lastname"));
            if (user.getLastName().equals("")) message = "Last name can't be empty";
            user.setBirthDate(LocalDate.parse((String) body.get("birthdate")));
            user.setUserGender(UserGender.valueOf((String) body.get("gender")));
            user.setUsername((String) body.get("username"));
            if (user.getUsername().equals("")) message = "Username can't be empty";
            user.setPassword((String) body.get("password"));
            if (user.getPassword().equals("")) message = "Password can't be empty";
        } catch (Exception e) {
            message = "Please fill in all fields";
            response.body(message);
            response.status(400);
            return response;
        }
        var addedUser = userDao.newClient(user);

        if (addedUser != null) {
            response.body("Registration successful");
            response.status(200);
            currentUser = addedUser;
        } else {
            message = "Username already exists";
            response.body(message);
            response.status(400);
        }

        return response;
    };


    public static Route newSeller = (Request request, Response response) -> {

      /*  if (!currentUser.getUserRole().equals(UserRole.ADMIN)) {
            response.body("User logged in");
            response.status(400);
            return response;
        } */


        var body = gson.fromJson((request.body()), HashMap.class);


        User user = new User();
        var message = "Registration successful";
        try {
            user.setFirstName((String) body.get("firstname"));
            if (user.getFirstName().equals("")) message = "First name can't be empty";
            user.setLastName((String) body.get("lastname"));
            if (user.getLastName().equals("")) message = "Last name can't be empty";
            user.setBirthDate(LocalDate.parse((String) body.get("birthdate")));
            user.setUserGender(UserGender.valueOf((String) body.get("gender")));
            user.setUsername((String) body.get("username"));
            if (user.getUsername().equals("")) message = "Username can't be empty";
            user.setPassword((String) body.get("password"));
            if (user.getPassword().equals("")) message = "Password can't be empty";
        } catch (Exception e) {
            message = "Please fill in all fields";
            response.body(message);
            response.status(400);
            return response;
        }
        var addedUser = userDao.newSeller(user);

        if (addedUser != null) {
            response.body("New seller added");
            response.status(200);
        } else {
            message = "Username already exists";
            response.body(message);
            response.status(400);
        }

        return response;
    };

    public static Route loggedInUser = (Request request, Response response)
            ->
    {
        response.body(gson.toJson(currentUser));
        return response;
    };

    public static Route logIn = (Request request, Response response)
            ->
    {
        if (currentUser != null) {
            response.body("User logged in");
            response.status(400);
            return response;
        }

        var body = gson.fromJson((request.body()), HashMap.class);


        User user = userDao.findByUsername(body.getOrDefault("username", "-1").toString(),
                body.getOrDefault("password", "-1").toString());

        if (user != null) {
            currentUser = user;
            response.body(gson.toJson(user));
            response.status(200);
        } else {
            response.body("Username and password incorrect");
            response.status(400);
        }

        return response;
    };

    public static Route logOut = (Request request, Response response)
            ->
    {
        currentUser = null;
        return response;
    };


    public static Route usersForAdmin = (Request request, Response response)
            ->
            gson.toJson(userDao.getForAdmin());
}
