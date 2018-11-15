package com.orange.poc.userstorage;

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

    public DemoUser(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = (firstName + lastName).toLowerCase();
        this.email = this.username + "@orange.com";
        this.password = firstName.toLowerCase();
        this.siuId = "monSiuId-" + id;
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
