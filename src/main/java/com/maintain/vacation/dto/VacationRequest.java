package com.maintain.vacation.dto;
import jakarta.validation.constraints.NotNull;
public class VacationRequest {
    @NotNull(message = "startDate is required")
    private String startDate;
    @NotNull(message = "endDate is required")
    private String endDate;
    public VacationRequest() {
    }
    public VacationRequest(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
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
}
