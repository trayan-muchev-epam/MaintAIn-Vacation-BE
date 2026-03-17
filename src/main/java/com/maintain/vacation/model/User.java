package com.maintain.vacation.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int remainingVacationDays;

    public User() {
    }

    public User(String username, String password, int remainingVacationDays) {
        this.username = username;
        this.password = password;
        this.remainingVacationDays = remainingVacationDays;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRemainingVacationDays() {
        return remainingVacationDays;
    }

    public void setRemainingVacationDays(int remainingVacationDays) {
        this.remainingVacationDays = remainingVacationDays;
    }
}
