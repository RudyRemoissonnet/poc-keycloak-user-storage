package com.orange.poc.userstorage.cloud;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.CredentialModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DemoUserStorageProvider implements UserStorageProvider,
        UserLookupProvider, UserQueryProvider, CredentialInputUpdater, CredentialInputValidator {

    private Logger logger = Logger.getLogger("DemoUserStorageProvider");

    private final KeycloakSession session;
    private final ComponentModel model;
    private final DemoRepository repository;

    DemoUserStorageProvider(KeycloakSession session, ComponentModel model, DemoRepository repository) {
        this.session = session;
        this.model = model;
        this.repository = repository;
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        logger.info("supportsCredentialType");
        return CredentialModel.PASSWORD.equals(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        logger.info("isConfiguredFor");
        return supportsCredentialType(credentialType);
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput input) {
        logger.info("isValid : " + user.getUsername());
        if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel)) {
            return false;
        }
        UserCredentialModel cred = (UserCredentialModel) input;
        return repository.validateCredentials(user.getUsername(), cred.getValue());
    }

    @Override
    public boolean updateCredential(RealmModel realm, UserModel user, CredentialInput input) {
        logger.info("updateCredential");
        if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel)) {
            return false;
        }
        UserCredentialModel cred = (UserCredentialModel) input;
        return repository.updateCredentials(user.getUsername(), cred.getValue());
    }

    @Override
    public void disableCredentialType(RealmModel realm, UserModel user, String credentialType) {
        logger.info("disableCredentialType");
    }

    @Override
    public Set<String> getDisableableCredentialTypes(RealmModel realm, UserModel user) {
        logger.info("getDisableableCredentialTypes");
        return Collections.emptySet();
    }

    @Override
    public void preRemove(RealmModel realm) {
        logger.info("preRemove");
    }

    @Override
    public void preRemove(RealmModel realm, GroupModel group) {
        logger.info("preRemove");
    }

    @Override
    public void preRemove(RealmModel realm, RoleModel role) {
        logger.info("preRemove");
    }

    @Override
    public void close() {
        logger.info("close");
    }

    @Override
    public UserModel getUserById(String id, RealmModel realm) {
        logger.info("getUserById : " + id);
        String externalId = StorageId.externalId(id);
        return new UserAdapter(session, realm, model, repository.findUserByUsernameOrEmail(externalId));
    }

    @Override
    public UserModel getUserByUsername(String username, RealmModel realm) {
        logger.info("getUserByUsername");
        DemoUser user = repository.findUserByUsernameOrEmail(username);
        if (user != null) {
            return new UserAdapter(session, realm, model, user);
        }
        return null; // user is unknown, try in another user storage...
    }

    @Override
    public UserModel getUserByEmail(String email, RealmModel realm) {
        logger.info("getUserByEmail");
        return getUserByUsername(email, realm);
    }

    @Override
    public int getUsersCount(RealmModel realm) {
        logger.info("getUsersCount");
        return repository.getUsersCount();
    }

    @Override
    public List<UserModel> getUsers(RealmModel realm) {
        logger.info("getUsers : " + realm);
        return repository.getAllUsers().stream()
                .map(user -> new UserAdapter(session, realm, model, user))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserModel> getUsers(RealmModel realm, int firstResult, int maxResults) {
        logger.info("getUsers : " + realm + ", firstResult: " + firstResult + ", maxResult: " + maxResults);
        return getUsers(realm);
    }

    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm) {
        logger.info("searchForUser with search : " + search + ", realm : " + realm);
        return repository.findUsers(search).stream()
                .map(user -> new UserAdapter(session, realm, model, user))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm, int firstResult, int maxResults) {
        logger.info("searchForUser with searh : " + search + ", realm : " + realm + ", firstResult : " + firstResult + ", maxResult : " + maxResults);
        return searchForUser(search, realm);
    }

    @Override
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm) {
        logger.info("searchForUser with params : " + params + ", realm : " + realm);
        return repository.getAllUsers().stream()
                .map(user -> new UserAdapter(session, realm, model, user))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm, int firstResult, int maxResults) {
        logger.info("searchForUser with params : " + params + ", realm : " + realm + ", firstResult : " + firstResult + ", maxResult : " + maxResults);
        return repository.getAllUsers().stream()
                .map(user -> new UserAdapter(session, realm, model, user))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group, int firstResult, int maxResults) {
        return Collections.emptyList();
    }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group) {
        return Collections.emptyList();
    }

    @Override
    public List<UserModel> searchForUserByUserAttribute(String attrName, String attrValue, RealmModel realm) {
        logger.info("searchForUserByUserAttribute : " + attrName + ", attrValue : " + attrValue + ", realm : " + realm);
        return searchForUser(attrValue, realm);
    }
}
