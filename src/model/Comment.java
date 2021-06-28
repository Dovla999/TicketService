package model;

import java.util.UUID;

public class Comment {
    private UUID uuid;
    private User commenter;
    private Manifestation manifestation;
    private String text;
    private Double rating;
    private boolean deleted = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (deleted != comment.deleted) return false;
        if (uuid != null ? !uuid.equals(comment.uuid) : comment.uuid != null) return false;
        if (commenter != null ? !commenter.equals(comment.commenter) : comment.commenter != null) return false;
        if (manifestation != null ? !manifestation.equals(comment.manifestation) : comment.manifestation != null)
            return false;
        if (text != null ? !text.equals(comment.text) : comment.text != null) return false;
        return rating != null ? rating.equals(comment.rating) : comment.rating == null;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (commenter != null ? commenter.hashCode() : 0);
        result = 31 * result + (manifestation != null ? manifestation.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (deleted ? 1 : 0);
        return result;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

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
