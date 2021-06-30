package dao;

import model.User;
import model.UserRole;

import java.util.*;
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
                .filter(user -> !user.isBlocked())
                .filter(user -> !user.isDeleted())
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst().orElse(null);

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

    public boolean deleteUser(String id) {
        var user = users.values()
                .stream()
                .filter(user1 -> !user1.isDeleted())
                .filter(user1 -> user1.getUuid().equals(UUID.fromString(id)))
                .findFirst();
        if (user.isPresent()) {
            if (user.get().getUserRole().equals(UserRole.ADMIN)) return false;
            user.get().setDeleted(true);
        }
        return true;

    }


    public List<User> applySFS(Map<String, String> sfs, List<User> users) {
        var list = users.stream()
                .filter(manifestation -> manifestation.getUsername().toLowerCase().contains(sfs.getOrDefault("username", manifestation.getUsername()).toLowerCase()))
                .filter(manifestation -> manifestation.getFirstName().toLowerCase().contains(sfs.getOrDefault("firstname", manifestation.getFirstName()).toLowerCase()))
                .filter(manifestation -> manifestation.getLastName().toLowerCase().contains(sfs.getOrDefault("lastname", manifestation.getLastName()).toLowerCase()))
                .collect(Collectors.toList());

        if (sfs.containsKey("filterRole") && !sfs.get("filterRole").equals("ALL")) {
            list = list.stream()
                    .filter(manifestation -> manifestation.getUserRole().toString().equals(sfs.get("filterRole")))
                    .collect(Collectors.toList());
        }

        if (sfs.containsKey("filterType") && !sfs.get("filterType").equals("ALL")) {
            list = list.stream()
                    .filter(user -> user.getUserRole().equals(UserRole.CLIENT))
                    .filter(manifestation -> manifestation.getLoyaltyCategory().getName().equals(sfs.get("filterType")))
                    .collect(Collectors.toList());
        }


        if (sfs.getOrDefault("sortDirection", "ASC").equals("DESC")) {
            switch (sfs.getOrDefault("sortCrit", "NAME")) {
                case "FIRST_NAME":
                    list = list.stream()
                            .sorted(Comparator.comparing(User::getFirstName).reversed())
                            .collect(Collectors.toList());
                    break;
                case "LAST_NAME":
                    list = list.stream()
                            .sorted(Comparator.comparing(User::getLastName).reversed())
                            .collect(Collectors.toList());
                    break;
                case "POINTS":
                    list = list.stream()
                            .sorted(Comparator.comparing(User::getPoints).reversed())
                            .collect(Collectors.toList());
                    break;
                default:
                    list = list.stream()
                            .sorted(Comparator.comparing(User::getUsername).reversed())
                            .collect(Collectors.toList());
                    break;
            }
        }
        if (sfs.getOrDefault("sortDirection", "ASC").equals("ASC")) {
            switch (sfs.getOrDefault("sortCrit", "NAME")) {
                case "FIRST_NAME":
                    list = list.stream()
                            .sorted(Comparator.comparing(User::getFirstName))
                            .collect(Collectors.toList());
                    break;
                case "LAST_NAME":
                    list = list.stream()
                            .sorted(Comparator.comparing(User::getLastName))
                            .collect(Collectors.toList());
                    break;
                case "POINTS":
                    list = list.stream()
                            .sorted(Comparator.comparing(User::getPoints))
                            .collect(Collectors.toList());
                    break;
                default:
                    list = list.stream()
                            .sorted(Comparator.comparing(User::getUsername))
                            .collect(Collectors.toList());
                    break;
            }
        }
        return list;
    }


    public List<User> getAllForAdmin(Map<String, String> sfs) {
        users.values()
                .forEach(User::checkSuspicious);
        return applySFS(sfs,
                users.values().stream().filter(user -> !user.isDeleted())
                        .collect(Collectors.toList()));
    }

    public void blockUser(String id) {
        users.values()
                .stream()
                .filter(user -> !user.isDeleted())
                .filter(user -> !user.getUserRole().equals(UserRole.ADMIN))
                .filter(user -> user.getUuid().equals(UUID.fromString(id)))
                .findFirst()
                .ifPresent(
                        user -> user.setBlocked(!user.isBlocked())
                );
    }
}
