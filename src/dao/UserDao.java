package dao;

import model.User;

import java.util.HashMap;
import java.util.UUID;

public class UserDao {
    private HashMap<UUID, User> users;

    public UserDao(HashMap<UUID, User> users) {
        this.users = users;
    }

    public UserDao() {
    }

    public HashMap<UUID, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<UUID, User> users) {
        this.users = users;
    }
}
