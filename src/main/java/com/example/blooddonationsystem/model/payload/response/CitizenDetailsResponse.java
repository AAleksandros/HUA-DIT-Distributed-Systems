package com.example.blooddonationsystem.model.payload.response;

import java.time.LocalDateTime;

public class CitizenDetailsResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private DonationApplicationDetails donationDetails;

    // Constructor
    public CitizenDetailsResponse() {
    }

    // Getters and Setters for CitizenDetailsResponse
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public DonationApplicationDetails getDonationDetails() {
        return donationDetails;
    }

    public void setDonationDetails(DonationApplicationDetails donationDetails) {
        this.donationDetails = donationDetails;
    }

    public static class DonationApplicationDetails {
        private Long id;
        private String status;
        private LocalDateTime createdAt;
        private String processedBySecretary;
        private LocalDateTime processedAt;
        private boolean freeOfInfections;
        private boolean hasNoTattoosOrPiercings;
        private boolean hasNoRecentProcedures;
        private boolean hasNoTravelToRiskAreas;
        private boolean hasNoRiskBehavior;
        private boolean notRecentlyPregnant;
        private boolean notBreastfeeding;
        private boolean hasNoDrugUse;
        private boolean hasAIDS;
        private String rejectionReason;

        // Constructor
        public DonationApplicationDetails() {
        }

        // Getters and Setters for DonationApplicationDetails

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

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public String getProcessedBySecretary() {
            return processedBySecretary;
        }

        public void setProcessedBySecretary(String processedBySecretary) {
            this.processedBySecretary = processedBySecretary;
        }

        public LocalDateTime getProcessedAt() {
            return processedAt;
        }

        public void setProcessedAt(LocalDateTime processedAt) {
            this.processedAt = processedAt;
        }

        public boolean isFreeOfInfections() {
            return freeOfInfections;
        }

        public void setFreeOfInfections(boolean freeOfInfections) {
            this.freeOfInfections = freeOfInfections;
        }

        public boolean isHasNoTattoosOrPiercings() {
            return hasNoTattoosOrPiercings;
        }

        public void setHasNoTattoosOrPiercings(boolean hasNoTattoosOrPiercings) {
            this.hasNoTattoosOrPiercings = hasNoTattoosOrPiercings;
        }

        public boolean isHasNoRecentProcedures() {
            return hasNoRecentProcedures;
        }

        public void setHasNoRecentProcedures(boolean hasNoRecentProcedures) {
            this.hasNoRecentProcedures = hasNoRecentProcedures;
        }

        public boolean isHasNoTravelToRiskAreas() {
            return hasNoTravelToRiskAreas;
        }

        public void setHasNoTravelToRiskAreas(boolean hasNoTravelToRiskAreas) {
            this.hasNoTravelToRiskAreas = hasNoTravelToRiskAreas;
        }

        public boolean isHasNoRiskBehavior() {
            return hasNoRiskBehavior;
        }

        public void setHasNoRiskBehavior(boolean hasNoRiskBehavior) {
            this.hasNoRiskBehavior = hasNoRiskBehavior;
        }

        public boolean isNotRecentlyPregnant() {
            return notRecentlyPregnant;
        }

        public void setNotRecentlyPregnant(boolean notRecentlyPregnant) {
            this.notRecentlyPregnant = notRecentlyPregnant;
        }

        public boolean isNotBreastfeeding() {
            return notBreastfeeding;
        }

        public void setNotBreastfeeding(boolean notBreastfeeding) {
            this.notBreastfeeding = notBreastfeeding;
        }

        public boolean isHasNoDrugUse() {
            return hasNoDrugUse;
        }

        public void setHasNoDrugUse(boolean hasNoDrugUse) {
            this.hasNoDrugUse = hasNoDrugUse;
        }

        public boolean isHasAIDS() {
            return hasAIDS;
        }

        public void setHasAIDS(boolean hasAIDS) {
            this.hasAIDS = hasAIDS;
        }

        public String getRejectionReason() {
            return rejectionReason;
        }

        public void setRejectionReason(String rejectionReason) {
            this.rejectionReason = rejectionReason;
        }

    }
}

