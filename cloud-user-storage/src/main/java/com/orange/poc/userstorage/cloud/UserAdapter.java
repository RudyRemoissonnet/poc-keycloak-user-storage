package com.orange.poc.userstorage.cloud;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

import java.util.logging.Logger;

public class UserAdapter extends AbstractUserAdapterFederatedStorage {

    private Logger logger = Logger.getLogger("DemoRepository");

    private final DemoUser user;

    public UserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, DemoUser user) {
        super(session, realm, model);
        logger.info("building user adapter for user : " + user);
        this.user = user;
        setSingleAttribute("siuId", user.getSiuId());
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public void setUsername(String username) {
        user.setUsername(username);
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public void setEmail(String email) {
        user.setEmail(email);
    }

    @Override
    public String getFirstName() {
        return user.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        user.setFirstName(firstName);
    }

    @Override
    public String getLastName() {
        return user.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        user.setLastName(lastName);
    }
}
