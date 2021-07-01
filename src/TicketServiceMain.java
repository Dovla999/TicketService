import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.CommentController;
import controller.ManifestationController;
import controller.TicketController;
import controller.UserController;
import dao.CommentDao;
import dao.ManifestationDao;
import dao.TicketDao;
import dao.UserDao;
import model.LoyaltyCategory;
import model.LoyaltyProgram;
import model.User;
import model.UserRole;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static spark.Spark.*;


public class TicketServiceMain {


    public static User currentUser = null;

    static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();


    public static void main(String[] args) throws IOException {

        port(8080);

        try {
            staticFiles.externalLocation(new File("./static").getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoyaltyCategory lc1 = new LoyaltyCategory("BRONZE", 0, (double) 1);
        LoyaltyCategory lc2 = new LoyaltyCategory("SILVER", 3000, 0.97);
        LoyaltyCategory lc3 = new LoyaltyCategory("GOLDEN", 4000, 0.95);

        List<LoyaltyCategory> lc = new ArrayList<>();
        lc.add(lc1);
        lc.add(lc2);
        lc.add(lc3);

        LoyaltyProgram.INSTANCE.setCategories(
                lc
        );

        setUpUsers();
        setUpManifestations();
        setUpTickets();
        setUpComments();
        fixUpRelations();


        before((request, response) -> {
            UserController.currentUser = request.session().attribute("currentUser");
            if (UserController.currentUser != null && UserController.currentUser.getUserRole().equals(UserRole.CLIENT))
                TicketController.cart = request.session().attribute("cart");
            else TicketController.cart = new ArrayList<>();
        });

        after((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "*");
        });

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        post("/api/comments/putComment", CommentController.putComment);

        get("/api/manifestations/all", ManifestationController.getAllManifestations);
        get("/api/manifestations/allForAdmin", ManifestationController.getAllManifestationsForAdmin);

        post("/api/users/newClient", UserController.newClient);
        post("/api/users/newSeller", UserController.newSeller);
        post("/api/users/logIn", UserController.logIn);
        get("/api/users/logout", UserController.logOut);
        get("/api/users/categories", (request, response) -> LoyaltyProgram.INSTANCE.getCategories().stream().map(LoyaltyCategory::getName).collect(Collectors.toList()));
        before("/api/users/newClient", (request, response) -> {
            if (currentUser != null) halt();
        });
        get("/api/users/currentUser", UserController.loggedInUser);
        put("api/users/update", "application/json", UserController.updateUser);
        put("/api/users/block/:id", UserController.blockUser);


        delete("/api/manifestations/delete/:id", ManifestationController.deleteManifestation);
        delete("/api/users/delete/:id", UserController.deleteUser);
        delete("/api/tickets/delete/:id", TicketController.deleteTicket);
        delete("/api/comments/delete/:id", CommentController.deleteComment);

        post("/api/manifestations/newManifestation", ManifestationController.newManifestation);
        get("/api/manifestations/sellerManifestations", ManifestationController.getSellerManifestations);
        put("/api/manifestations/updateManifestation", ManifestationController.updateManifestation);
        get("/api/manifestations/getTypes", ManifestationController.getTypes);
        get("/api/manifestations/isCommentable/:id", ManifestationController.canComment);
        get("/api/manifestations/canBuyTickets/:id", ManifestationController.canBuyTickets);

        post("/api/tickets/addToCart", TicketController.addToCart);
        get("/api/tickets/removeFromCart/:id", TicketController.removeFromCart);
        get("/api/tickets/seller", TicketController.getTicketsForSeller);
        get("/api/tickets/buyCart", TicketController.buyCart);
        get("/api/tickets/allClientTickets", TicketController.clientTickets);
        get("/api/tickets/getCart", TicketController.getCart);
        get("/api/tickets/cartPrice", TicketController.cartPrice);
        get("/api/tickets/adminTickets", TicketController.adminTickets);
        put("/api/tickets/cancel/:id", TicketController.cancelTicket);

        get("/api/users/allForAdmin", UserController.usersForAdmin);


        get("/api/comments/allForAdmin", CommentController.allForAdmin);
        get("/api/comments/allForSeller", CommentController.allForSeller);
        put("/api/comments/activate/:id", CommentController.putActive);
        get("/api/comments/getForManifestation/:id", CommentController.getForManifestation);


        get("api/manifestations/:id", ManifestationController.getOneManifestation);

        get("/api/manifestations/activate/:id", ManifestationController.changeActiveManifestation);

      /*  Thread t = new Thread(() -> {
            try {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        saveData();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }); */
        afterAfter((request, response) -> saveData());


    }

    private static void fixUpRelations() {
        UserController.userDao.getUsers().values()
                .forEach(user ->
                {
                    if (user.getUserRole().equals(UserRole.CLIENT))
                        user.setLoyaltyCategory(LoyaltyProgram.INSTANCE.getLoyaltyCategoryByPoints((int) Math.round(user.getPoints())));
                    TicketController.ticketDao.getTickets().values()
                            .forEach(
                                    ticket -> {
                                        if (ticket.getOwner().getUuid().equals(user.getUuid())) {
                                            user.getTickets().add(ticket);
                                            ticket.setOwner(user);
                                            ticket.setManifestation(ManifestationController.manifestationDao
                                                    .getManifestations()
                                                    .values()
                                                    .stream()
                                                    .filter(manifestation -> manifestation.getUuid().equals(ticket.getManifestation().getUuid()))
                                                    .findFirst()
                                                    .orElse(null));
                                        }
                                    }
                            );
                    ManifestationController.manifestationDao.getManifestations().values()
                            .forEach(
                                    manifestation -> {
                                        if (manifestation.getCreator().getUuid().equals(user.getUuid())) {
                                            user.getManifestations().add(manifestation);
                                            manifestation.setCreator(user);
                                        }
                                    }
                            );
                    CommentController.commentDao.getComments().values()
                            .forEach(comment -> {
                                if (comment.getCommenter().getUuid().equals(user.getUuid())) {
                                    user.getComments().add(comment);
                                    comment.setCommenter(user);
                                    comment.setManifestation(ManifestationController.manifestationDao
                                            .getManifestations()
                                            .values().stream()
                                            .filter(manifestation -> manifestation.getUuid().equals(comment.getManifestation().getUuid()))
                                            .findFirst()
                                            .orElse(null));
                                }
                            });
                });

    }

    private static void saveData() throws IOException {
        FileWriter fw = new FileWriter("users.json");
        fw.write(gson.toJson(UserController.userDao));
        fw.close();
        fw = new FileWriter("manifestations.json");
        fw.write(gson.toJson(ManifestationController.manifestationDao));
        fw.close();
        fw = new FileWriter("comments.json");
        fw.write(gson.toJson(CommentController.commentDao));
        fw.close();
        fw = new FileWriter("tickets.json");
        fw.write(gson.toJson(TicketController.ticketDao));
        fw.close();
    }

    private static void setUpUsers() throws IOException {

        UserController.userDao = gson.fromJson(new BufferedReader(new FileReader("users.json"))
                .lines().collect(Collectors.joining(System.lineSeparator())), UserDao.class);
        UserController.currentUser = currentUser;
    }

    private static void setUpComments() {
        try {
            CommentController.commentDao = gson.fromJson(new BufferedReader(new FileReader("comments.json"))
                    .lines().collect(Collectors.joining(System.lineSeparator())), CommentDao.class);
        } catch (FileNotFoundException e) {
            CommentController.commentDao = new CommentDao();
        }

    }

    private static void setUpTickets() {
        try {
            TicketController.ticketDao = gson.fromJson(new BufferedReader(new FileReader("tickets.json"))
                    .lines().collect(Collectors.joining(System.lineSeparator())), TicketDao.class);
        } catch (FileNotFoundException e) {
            TicketController.ticketDao = new TicketDao();
        }


    }

    public static void setUpManifestations() {


        try {
            ManifestationController.manifestationDao = gson.fromJson(new BufferedReader(new FileReader("manifestations.json"))
                    .lines().collect(Collectors.joining(System.lineSeparator())), ManifestationDao.class);
        } catch (FileNotFoundException e) {
            ManifestationController.manifestationDao = new ManifestationDao();
        }

    }
}
