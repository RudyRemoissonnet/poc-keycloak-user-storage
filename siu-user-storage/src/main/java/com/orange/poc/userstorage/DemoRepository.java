package com.orange.poc.userstorage;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;
import static java.lang.System.nanoTime;

class DemoRepository {

    private Logger logger = Logger.getLogger("DemoRepository");

    private Set<DemoUser> users = new HashSet<>();

    private String makeId() {
        return valueOf(nanoTime());
    }

    Set<DemoUser> getAllUsers() {
        return users;
    }

    int getUsersCount() {
        return users.size();
    }

    DemoUser findUserById(String id) {
        logger.info("findUserById : " + id);
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    DemoUser findUserByUsernameOrEmail(String username) {
        logger.info("user count : " + users.size());
        logger.info("findUserByUsername : " + username);
        Optional<DemoUser> demoUser = users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username) || user.getEmail().equalsIgnoreCase(username))
                .findFirst();
        if (demoUser.isPresent()) {
            return demoUser.get();
        } else {
            DemoUser user = new DemoUser(makeId(), username, "");
            users.add(user);
            logger.info("user count : " + users.size());
            return user;
        }
    }

    List<DemoUser> findUsers(String query) {
        return users.stream()
                .filter(user -> user.getUsername().contains(query) || user.getEmail().contains(query))
                .collect(Collectors.toList());
    }

    boolean validateCredentials(String username, String password) {
        logger.info("validateCredentials : " + username + ", password : " + password);
        DemoUser user = findUserByUsernameOrEmail(username);
        if ("siu".equalsIgnoreCase(username)) {
            logger.info("validated by siu");
            user.setFirstName("first-from-siu");
            user.setLastName("last-from-siu");
           return true;
        }
        logger.info("not validated");
        users.remove(user);
        return false;
    }

    boolean updateCredentials(String username, String password) {
        findUserByUsernameOrEmail(username).setPassword(password);
        return true;
    }

}
