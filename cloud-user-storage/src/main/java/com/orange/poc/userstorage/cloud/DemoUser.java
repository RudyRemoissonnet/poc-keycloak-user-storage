package com.orange.poc.userstorage.cloud;

public class DemoUser {

    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String siuId;
    private String contract;

    public DemoUser() {
    }

    public DemoUser(String id, String username) {
        this.id = id;
        this.username = username;
        this.password = username;
        this.email = this.username + "@orange.com";
        this.siuId = "monSiuId-" + this.id;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSiuId() {
        return siuId;
    }

    public void setSiuId(String siuId) {
        this.siuId = siuId;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

}
