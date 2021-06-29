package model;


import java.time.LocalDate;
import java.util.HashSet;
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
    private transient Set<Ticket> tickets = new HashSet<>();
    private transient Set<Comment> comments = new HashSet<>();
    private transient Set<Manifestation> manifestations = new HashSet<>();
    private LoyaltyCategory loyaltyCategory;
    private boolean deleted = false;
    private boolean suspicious = false;
    private Double points = (double) 0;

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (deleted != user.deleted) return false;
        if (uuid != null ? !uuid.equals(user.uuid) : user.uuid != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (userGender != user.userGender) return false;
        if (birthDate != null ? !birthDate.equals(user.birthDate) : user.birthDate != null) return false;
        if (userRole != user.userRole) return false;
        if (tickets != null ? !tickets.equals(user.tickets) : user.tickets != null) return false;
        if (comments != null ? !comments.equals(user.comments) : user.comments != null) return false;
        if (manifestations != null ? !manifestations.equals(user.manifestations) : user.manifestations != null)
            return false;
        if (loyaltyCategory != null ? !loyaltyCategory.equals(user.loyaltyCategory) : user.loyaltyCategory != null)
            return false;
        return points != null ? points.equals(user.points) : user.points == null;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (userGender != null ? userGender.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        result = 31 * result + (tickets != null ? tickets.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (manifestations != null ? manifestations.hashCode() : 0);
        result = 31 * result + (loyaltyCategory != null ? loyaltyCategory.hashCode() : 0);
        result = 31 * result + (deleted ? 1 : 0);
        result = 31 * result + (points != null ? points.hashCode() : 0);
        return result;
    }

    public boolean isSuspicious() {
        return suspicious;
    }

    public void setSuspicious(boolean suspicious) {
        this.suspicious = suspicious;
    }
}
