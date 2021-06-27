package dao;

import model.Comment;

import java.util.HashMap;
import java.util.UUID;

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
}
