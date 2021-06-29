package dto;

import model.Manifestation;

import java.time.LocalDateTime;
import java.util.UUID;

public class ManifSellerDto {
    private UUID uuid;
    private String name;
    private Integer capacity;
    private Double ticketPrice;
    private LocalDateTime dateTime;
    private String type;
    private Double rating;

    public ManifSellerDto(Manifestation m) {
        name = m.getName();
        capacity = m.getCapacity();
        ticketPrice = m.getTicketPrice();
        dateTime = m.getDateTime();
        type = m.getType();
        rating = m.getRating();
        uuid = m.getUuid();
    }

    public ManifSellerDto() {
    }

    public ManifSellerDto(String name, Integer capacity, Double ticketPrice, LocalDateTime dateTime, String type, Double rating) {
        this.name = name;
        this.capacity = capacity;
        this.ticketPrice = ticketPrice;
        this.dateTime = dateTime;
        this.type = type;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
