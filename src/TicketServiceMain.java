import controller.CommentController;
import controller.ManifestationController;
import controller.TicketController;
import controller.UserController;
import dao.CommentDao;
import dao.ManifestationDao;
import dao.TicketDao;
import dao.UserDao;
import model.Location;
import model.Manifestation;
import model.User;
import model.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

import static spark.Spark.*;


public class TicketServiceMain {


    public static User currentUser = null;


    public static void main(String[] args) {

        port(8080);
        setUpUsers();
        setUpManifestations();
        setUpTickets();
        setUpComments();

        post("/api/comments/putComment", CommentController.putComment);

        get("/api/manifestations/all", ManifestationController.getAllManifestations);

        post("/api/users/newClient", UserController.newClient);
        post("/api/users/newSeller", UserController.newSeller);
        post("/api/users/logIn", UserController.logIn);
        get("/api/users/logout", UserController.logOut);
        before("/api/users/newClient", (request, response) -> {
            if (currentUser != null) halt();
        });
        get("/api/users/currentUser", UserController.loggedInUser);


        post("/api/manifestations/newManifestation", ManifestationController.newManifestation);
        get("/api/manifestations/sellerManifestations", ManifestationController.getSellerManifestations);

        post("/api/tickets/addToCart", TicketController.addToCart);
        get("/api/tickets/removeFromCart/:id", TicketController.removeFromCart);
        get("/api/tickets/buyCart", TicketController.buyCart);
        get("/api/tickets/allClientTickets", TicketController.clientTickets);
        get("/api/tickets/getCart", TicketController.getCart);
        get("/api/tickets/cartPrice", TicketController.cartPrice);
        get("/api/tickets/adminTickets", TicketController.adminTickets);
        get("/api/users/allForAdmin", UserController.usersForAdmin);
        get("/api/comments/allForAdmin", CommentController.allForAdmin);


        get("api/manifestations/:id", ManifestationController.getOneManifestation);

        get("/api/manifestations/activate/:id", ManifestationController.changeActiveManifestation);


        after((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET");
            response.header("Access-Control-Allow-Methods", "POST");
            response.header("Access-Control-Allow-Methods", "PUT");
            response.header("Access-Control-Allow-Methods", "DELETE");
        });

    }

    private static void setUpUsers() {

        User admin = new User();
        admin.setUserRole(UserRole.ADMIN);
        admin.setUuid(UUID.randomUUID());
        admin.setPassword("admin");
        admin.setUsername("admin");


        UserController.userDao = new UserDao();
        UserController.userDao.getUsers().put(admin.getUuid(), admin);
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


        Manifestation manifestation = new Manifestation(UUID.randomUUID(), "Within Temptation", "Koncert", 600, LocalDateTime.now(),
                600.34, true, new Location(25.579977, 47.040182, "Novi Sad"), "none", admin);
        Manifestation manifestation1 = new Manifestation(UUID.randomUUID(), "Nightwish", "Koncert", 300, LocalDateTime.now(),
                600.34, true, new Location(9.419579, 51.179343, "Novi Sad"), "none", admin);
        Manifestation manifestation2 = new Manifestation(UUID.randomUUID(), "Tarja", "Koncert", 640, LocalDateTime.now(),
                600.34, true, new Location(52.637814, 57.891497, "Novi Sad"), "none", admin);
        ManifestationDao manifestationDao = new ManifestationDao();
        manifestationDao.addManifestation(manifestation);
        manifestationDao.addManifestation(manifestation1);
        manifestationDao.addManifestation(manifestation2);
        UserController.userDao.getUsers().put(admin.getUuid(), admin);
        ManifestationController.manifestationDao = manifestationDao;
    }
}
