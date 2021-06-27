package model;


import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;


public class User {

    private UUID uuid;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private UserGender userGender;
    private LocalDate birthDate;
    private UserRole userRole;
    private transient Set<Ticket> tickets;
    private transient Set<Comment> comments;
    private transient Set<Manifestation> manifestations;
    private LoyaltyCategory loyaltyCategory;
    private boolean deleted = false;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public User() {
    }

    public User(UUID uuid, String username, String password, String firstName, String lastName, UserGender userGender, LocalDate birthDate, UserRole userRole, Set<Ticket> tickets, Set<Comment> comments, Set<Manifestation> manifestations, LoyaltyCategory loyaltyCategory) {
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userGender = userGender;
        this.birthDate = birthDate;
        this.userRole = userRole;
        this.tickets = tickets;
        this.comments = comments;
        this.manifestations = manifestations;
        this.loyaltyCategory = loyaltyCategory;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserGender getUserGender() {
        return userGender;
    }

    public void setUserGender(UserGender userGender) {
        this.userGender = userGender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Manifestation> getManifestations() {
        return manifestations;
    }

    public void setManifestations(Set<Manifestation> manifestations) {
        this.manifestations = manifestations;
    }

    public LoyaltyCategory getLoyaltyCategory() {
        return loyaltyCategory;
    }

    public void setLoyaltyCategory(LoyaltyCategory loyaltyCategory) {
        this.loyaltyCategory = loyaltyCategory;
    }
}
