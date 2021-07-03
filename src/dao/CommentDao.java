package dao;

import model.Comment;
import model.Manifestation;
import model.User;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CommentDao {
    private HashMap<UUID, Comment> comments = new HashMap<>();

    public CommentDao() {
    }

    public CommentDao(HashMap<UUID, Comment> comments) {
        this.comments = comments;
    }

    public HashMap<UUID, Comment> getComments() {
        return comments;
    }

    public void setComments(HashMap<UUID, Comment> comments) {
        this.comments = comments;
    }

    public void putComment(Comment comment) {
        var c = comments.values()
                .stream()
                .filter(comment1 -> !comment1.isDeleted())
                .filter(comment1 -> comment1.getCommenter().getUuid().equals(comment.getCommenter().getUuid())
                        && comment1.getManifestation().getUuid().equals(comment.getManifestation().getUuid()))
                .findFirst();
        c.ifPresent(value -> comments.remove(value.getUuid()));
        comments.put(comment.getUuid(), comment);
        recalculateRating(comment.getManifestation());

    }

    public List<Comment> allComments() {
        return comments.values()
                .stream()
                .filter(comment -> !comment.isDeleted())
                .collect(Collectors.toList());
    }

    public List<Comment> commentsSeller(User currentUser) {
        return comments.values()
                .stream()
                .filter(comment -> !comment.isDeleted())
                .filter(comment -> comment.getManifestation().getCreator().getUuid().equals(currentUser.getUuid()))
                .collect(Collectors.toList());
    }

    public List<Comment> changeActive(String id, User user) {
        comments.values()
                .stream()
                .filter(comment -> comment.getUuid().equals(UUID.fromString(id)))
                .findAny()
                .ifPresent(comment -> {
                    comment.setActive(!comment.isActive());
                    recalculateRating(comment.getManifestation());
                });


        return commentsSeller(user);
    }

    public List<Comment> getForManifestation(String id) {
        return comments.values()
                .stream()
                .filter(comment -> !comment.isDeleted())
                .filter(comment -> comment.getManifestation().getUuid().equals(UUID.fromString(id)))
                .filter(Comment::isActive)
                .collect(Collectors.toList());
    }

    public boolean deleteManifestation(String id) {
        comments.values()
                .stream()
                .filter(comment -> comment.getUuid().equals(UUID.fromString(id)))
                .findFirst()
                .ifPresent(
                        comment -> {
                            comment.setDeleted(true);
                            recalculateRating(comment.getManifestation());
                        }
                );
        return true;
    }

    private void recalculateRating(Manifestation manifestation) {

        manifestation.setRating((double) Math.round(comments.values()
                .stream()
                .filter(comment -> comment.getManifestation().getUuid().equals(manifestation.getUuid()))
                .filter(comment -> comment.isActive() && !comment.isDeleted())
                .collect(Collectors.averagingDouble(Comment::getRating)) * 100) / 100);
    }
}
