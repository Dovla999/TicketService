import controller.CommentController;
import controller.ManifestationController;
import controller.TicketController;
import controller.UserController;
import dao.CommentDao;
import dao.ManifestationDao;
import dao.TicketDao;
import dao.UserDao;
import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static spark.Spark.*;


public class TicketServiceMain {


    public static User currentUser = null;


    public static void main(String[] args) {

        port(8080);


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


    }

    private static void setUpUsers() {

        User admin = new User();
        admin.setUserRole(UserRole.ADMIN);
        admin.setUuid(UUID.randomUUID());
        admin.setPassword("admin");
        admin.setUsername("admin");
        admin.setFirstName("admin");
        admin.setLastName("admin");

        User buyer = new User();
        buyer.setUserRole(UserRole.CLIENT);
        buyer.setUuid(UUID.randomUUID());
        buyer.setPassword("buyer");
        buyer.setUsername("buyer");
        buyer.setFirstName("buyer");
        buyer.setLastName("buyer");

        UserController.userDao = new UserDao();
        UserController.userDao.getUsers().put(admin.getUuid(), admin);
        UserController.userDao.getUsers().put(buyer.getUuid(), buyer);
        UserController.currentUser = currentUser;
    }

    private static void setUpComments() {
        CommentController.commentDao = new CommentDao();

    }

    private static void setUpTickets() {
        TicketController.ticketDao = new TicketDao();

    }

    public static void setUpManifestations() {

        User admin = new User();
        admin.setUserRole(UserRole.SELLER);
        admin.setUuid(UUID.randomUUID());
        admin.setPassword("seller");
        admin.setUsername("seller");
        admin.setFirstName("Jovan");
        admin.setLastName("Jovanovic");


        Manifestation manifestation = new Manifestation(UUID.randomUUID(), "Within Temptation", "Concert", 600, LocalDateTime.now(),
                700.34, true, new Location(25.579977, 47.040182, "Novi Sad"), "none", admin);
        Manifestation manifestation1 = new Manifestation(UUID.randomUUID(), "Nightwish", "Theater", 300, LocalDateTime.now(),
                800.34, true, new Location(9.419579, 51.179343, "Novi Sad"), "none", admin);
        Manifestation manifestation2 = new Manifestation(UUID.randomUUID(), "Tarja", "Concert", 640, LocalDateTime.now(),
                900.34, true, new Location(52.637814, 57.891497, "Novi Sad"), "none", admin);
        ManifestationDao manifestationDao = new ManifestationDao();
        manifestation.setTicketsRemaining(manifestation.getCapacity());
        manifestation1.setTicketsRemaining(manifestation1.getCapacity());
        manifestation2.setTicketsRemaining(manifestation2.getCapacity());
        manifestationDao.addManifestation(manifestation);
        manifestationDao.addManifestation(manifestation1);
        manifestationDao.addManifestation(manifestation2);
        UserController.userDao.getUsers().put(admin.getUuid(), admin);
        ManifestationController.manifestationDao = manifestationDao;
    }
}
