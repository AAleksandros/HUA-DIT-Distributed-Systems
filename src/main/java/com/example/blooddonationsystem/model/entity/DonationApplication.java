package com.example.blooddonationsystem.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "donation_application")
public class DonationApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "citizen_id", nullable = false)
    private Citizen citizen;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ApplicationStatus status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "approval_date")
    private LocalDateTime approvalDate;

    // Health History Attributes
    @NotNull(message = "This field is required")
    private boolean isFreeOfInfections;
    @NotNull(message = "This field is required")
    private boolean hasNoTattoosOrPiercings;
    @NotNull(message = "This field is required")
    private boolean hasNoRecentProcedures;
    @NotNull(message = "This field is required")
    private boolean hasNoTravelToRiskAreas;
    @NotNull(message = "This field is required")
    private boolean hasNoRiskBehavior;
    @NotNull(message = "This field is required")
    private boolean hasNoHIVOrDrugUse;
    @NotNull(message = "This field is required")
    private boolean isNotRecentlyPregnant;
    @NotNull(message = "This field is required")
    private boolean isNotBreastfeeding;
    @NotNull(message = "This field is required")
    private boolean hasAIDS;
    @Column(name = "rejection_reason")
    private String rejectionReason;

    public DonationApplication() {
    }

    public DonationApplication(Long id, Citizen citizen, boolean isFreeOfInfections, boolean hasNoTattoosOrPiercings, boolean hasNoRecentProcedures, boolean hasNoTravelToRiskAreas, boolean hasNoRiskBehavior, boolean hasNoHIVOrDrugUse, boolean isNotRecentlyPregnant, boolean isNotBreastfeeding, boolean hasAIDS, String rejectionReason) {
        this.id = id;
        this.citizen = citizen;
        this.isFreeOfInfections = isFreeOfInfections;
        this.hasNoTattoosOrPiercings = hasNoTattoosOrPiercings;
        this.hasNoRecentProcedures = hasNoRecentProcedures;
        this.hasNoTravelToRiskAreas = hasNoTravelToRiskAreas;
        this.hasNoRiskBehavior = hasNoRiskBehavior;
        this.hasNoHIVOrDrugUse = hasNoHIVOrDrugUse;
        this.isNotRecentlyPregnant = isNotRecentlyPregnant;
        this.isNotBreastfeeding = isNotBreastfeeding;
        this.hasAIDS = hasAIDS;
        this.rejectionReason = rejectionReason;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isFreeOfInfections() {
        return isFreeOfInfections;
    }

    public void setFreeOfInfections(boolean freeOfInfections) {
        isFreeOfInfections = freeOfInfections;
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

    public boolean isHasNoHIVOrDrugUse() {
        return hasNoHIVOrDrugUse;
    }

    public void setHasNoHIVOrDrugUse(boolean hasNoHIVOrDrugUse) {
        this.hasNoHIVOrDrugUse = hasNoHIVOrDrugUse;
    }

    public boolean isNotRecentlyPregnant() {
        return isNotRecentlyPregnant;
    }

    public void setNotRecentlyPregnant(boolean notRecentlyPregnant) {
        isNotRecentlyPregnant = notRecentlyPregnant;
    }

    public boolean isNotBreastfeeding() {
        return isNotBreastfeeding;
    }

    public void setNotBreastfeeding(boolean notBreastfeeding) {
        isNotBreastfeeding = notBreastfeeding;
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

    public enum ApplicationStatus {
        PENDING, APPROVED, REJECTED
    }
}
