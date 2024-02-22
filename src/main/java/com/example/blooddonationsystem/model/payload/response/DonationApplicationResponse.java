package com.example.blooddonationsystem.model.payload.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DonationApplicationResponse {
    private Long id;
    private String status;
    private String createdAt;
    private Long citizenId;


    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public DonationApplicationResponse(Long id, String status, LocalDateTime createdAt, Long citizenId) {
        this.id = id;
        this.status = status;
        this.createdAt = createdAt != null ? createdAt.format(formatter) : null; // Format the date
        this.citizenId = citizenId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt != null ? createdAt.format(formatter) : null;
    }

    public Long getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(Long citizenId) {
        this.citizenId = citizenId;
    }

}
