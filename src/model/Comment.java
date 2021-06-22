package model;

import java.util.UUID;

public class Comment {
    private UUID uuid;
    private User commenter;
    private Manifestation manifestation;
    private String text;
    private Double rating;

    public Comment(UUID uuid, User commenter, Manifestation manifestation, String text, Double rating) {
        this.uuid = uuid;
        this.commenter = commenter;
        this.manifestation = manifestation;
        this.text = text;
        this.rating = rating;
    }

    public Comment() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public Manifestation getManifestation() {
        return manifestation;
    }

    public void setManifestation(Manifestation manifestation) {
        this.manifestation = manifestation;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
