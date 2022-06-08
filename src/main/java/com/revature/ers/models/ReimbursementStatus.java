package com.revature.ers.models;

public class ReimbursementStatus {

    private String id;
    private String status;

    public ReimbursementStatus() { super(); }

    public ReimbursementStatus(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReimbursementStatus{" +
                "id='" + id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
