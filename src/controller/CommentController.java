package controller;

import Util.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.CommentDao;
import model.Comment;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class CommentController {
    public static CommentDao commentDao;
    static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .addSerializationExclusionStrategy(TicketController.strategy)
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();


    public static Route putComment = (Request request, Response response) ->
    {
        var body = gson.fromJson((request.body()), HashMap.class);

        if (body.containsKey("manifestation") &&
                body.containsKey("text") &&
                body.containsKey("rating")
        ) {
            Comment comment = new Comment();
            comment.setCommenter(UserController.currentUser);
            comment.setDeleted(false);
            comment.setUuid(UUID.randomUUID());
            comment.setManifestation(ManifestationController.manifestationDao.findById((String) body.get("manifestation")));
            comment.setRating(Double.valueOf((body.get("rating").toString())));
            comment.setText((String) body.get("text"));
            commentDao.putComment(comment);
            response.status(200);
        } else {
            response.status(400);
            response.body("Something went wrong");
        }
        return response;

    };

    public static Route allForAdmin = (Request request, Response response) ->
            gson.toJson(commentDao.allComments());
}
