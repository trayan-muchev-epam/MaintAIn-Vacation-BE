package com.maintain.vacation.dto;
public class UserResponse {
    private String id;
    private String username;
    private int remainingVacationDays;
    public UserResponse() {
    }
    public UserResponse(String id, String username, int remainingVacationDays) {
        this.id = id;
        this.username = username;
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
    public int getRemainingVacationDays() {
        return remainingVacationDays;
    }
    public void setRemainingVacationDays(int remainingVacationDays) {
        this.remainingVacationDays = remainingVacationDays;
    }
}
