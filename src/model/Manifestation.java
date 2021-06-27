package model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Manifestation {

    private UUID uuid;
    private String name;
    private String type;
    private Integer capacity;
    private LocalDateTime dateTime;
    private Double ticketPrice;
    private Boolean active;
    private Location location;
    private String image;
    private User creator;
    private boolean deleted = false;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Manifestation(UUID uuid, String name, String type, Integer capacity, LocalDateTime dateTime, Double ticketPrice, Boolean active, Location location, String image, User creator) {
        this.uuid = uuid;
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.dateTime = dateTime;
        this.ticketPrice = ticketPrice;
        this.active = active;
        this.location = location;
        this.image = image;
        this.creator = creator;
    }

    public Manifestation() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
