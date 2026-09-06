package com.niet.gatepass.model;

public class Warden {
    private int wardenId;
    private String name;
    private String email;
    private String passwordHash;
    private int hostelId;

    public Warden() {}

    public int getWardenId() { return wardenId; }
    public void setWardenId(int wardenId) { this.wardenId = wardenId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public int getHostelId() { return hostelId; }
    public void setHostelId(int hostelId) { this.hostelId = hostelId; }
}
