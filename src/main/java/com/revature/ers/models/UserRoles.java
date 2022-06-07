package com.revature.ers.models;

public class UserRoles {

    private String id;
    private String role;

    public UserRoles() { super(); }

    public UserRoles(String id, String role) {
        this.id = id;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserRoles{" +
                "id='" + id + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
