package dao;

import model.User;
import model.UserRole;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserDao {
    private HashMap<UUID, User> users = new HashMap<>();

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

    public User newClient(User user) {
        var exists = users.values()
                .stream()
                .filter(user1 -> user1.getUsername().equals(user.getUsername()) && !user1.isDeleted())
                .collect(Collectors.toList());
        if (exists.size() == 0) {
            user.setUuid(UUID.randomUUID());
            user.setUserRole(UserRole.CLIENT);
            users.put(user.getUuid(), user);
            return user;
        }
        return null;
    }

    public User findByUsername(String username, String password) {

        return users.values()
                .stream()
                .filter(user -> !user.isDeleted())
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .get();

    }

    public List<User> getForAdmin() {
        return users.values()
                .stream()
                .filter(user -> !user.isDeleted())
                .filter(user -> user.getUserRole() != UserRole.ADMIN)
                .collect(Collectors.toList());
    }

    public User newSeller(User user) {
        var exists = users.values()
                .stream()
                .filter(user1 -> user1.getUsername().equals(user.getUsername()) && !user1.isDeleted())
                .collect(Collectors.toList());
        if (exists.size() == 0) {
            user.setUuid(UUID.randomUUID());
            user.setUserRole(UserRole.SELLER);
            users.put(user.getUuid(), user);
            return user;
        }
        return null;
    }

    public User findById(String uuid) {
        return users.getOrDefault(UUID.fromString(uuid), null);
    }
}
