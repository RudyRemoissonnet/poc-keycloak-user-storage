package com.orange.poc.userstorage.cloud;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.lang.String.format;

class DemoRepository {

    private Logger logger = Logger.getLogger("DemoRepository");

    private final Map<String, DemoUser> users;

    DemoRepository() {
        users = new HashMap<>();
        users.put("cloud", new DemoUser("1", "cloud"));
    }

    Collection<DemoUser> getAllUsers() {
        return users.values();
    }

    int getUsersCount() {
        return users.size();
    }

    DemoUser findUserByUsernameOrEmail(String username) {
        logger.info("findUserByUsername : " + username);
        return users.get(username);
    }

    List<DemoUser> findUsers(String query) {
        return users.values().stream()
                .filter(user -> user.getUsername().contains(query) || user.getEmail().contains(query))
                .collect(Collectors.toList());
    }

    boolean validateCredentials(String username, String password) {
        logger.info(format("validateCredentials : %s, %s",  username, password));
        DemoUser user = findUserByUsernameOrEmail(username);
        if (user != null) {
            logger.info("cloud user storage implementation");
            return true;
        }
        logger.info("not validated");
        return false;
    }

    boolean updateCredentials(String username, String password) {
        logger.info(format("updateCredentials : %s, %s",  username, password));
        logger.info("external api should be call to update credential");
        findUserByUsernameOrEmail(username).setPassword(password);
        return true;
    }

}
