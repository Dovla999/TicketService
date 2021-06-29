package dao;

import model.Comment;

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
        if (c.isPresent()) {
            comments.remove(c.get().getUuid());
        }
        comments.put(comment.getUuid(), comment);

    }

    public List<Comment> allComments() {
        return comments.values()
                .stream()
                .filter(comment -> !comment.isDeleted())
                .collect(Collectors.toList());
    }
}
