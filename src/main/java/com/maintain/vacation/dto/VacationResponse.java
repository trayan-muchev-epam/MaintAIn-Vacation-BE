package com.maintain.vacation.dto;
public class VacationResponse {
    private String id;
    private String startDate;
    private String endDate;
    private int days;
    private String status;
    public VacationResponse() {
    }
    public VacationResponse(String id, String startDate, String endDate, int days, String status) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
        this.status = status;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public int getDays() {
        return days;
    }
    public void setDays(int days) {
        this.days = days;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
