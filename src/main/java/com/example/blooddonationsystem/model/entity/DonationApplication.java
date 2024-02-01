package com.example.blooddonationsystem.model.entity;

import jakarta.persistence.*;
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
    private boolean isFreeOfInfections;
    private boolean hasNoTattoosOrPiercings;
    private boolean hasNoRecentProcedures;
    private boolean hasNoTravelToRiskAreas;
    private boolean hasNoRiskBehavior;
    private boolean hasNoHIVOrDrugUse;
    private boolean isNotRecentlyPregnant;
    private boolean isNotBreastfeeding;

    public DonationApplication() {
    }

    public DonationApplication(Citizen citizen, ApplicationStatus status, boolean isFreeOfInfections, boolean hasNoTattoosOrPiercings, boolean hasNoRecentProcedures, boolean hasNoTravelToRiskAreas, boolean hasNoRiskBehavior, boolean hasNoHIVOrDrugUse, boolean isNotRecentlyPregnant, boolean isNotBreastfeeding) {
        this.citizen = citizen;
        this.status = status;
        this.isFreeOfInfections = isFreeOfInfections;
        this.hasNoTattoosOrPiercings = hasNoTattoosOrPiercings;
        this.hasNoRecentProcedures = hasNoRecentProcedures;
        this.hasNoTravelToRiskAreas = hasNoTravelToRiskAreas;
        this.hasNoRiskBehavior = hasNoRiskBehavior;
        this.hasNoHIVOrDrugUse = hasNoHIVOrDrugUse;
        this.isNotRecentlyPregnant = isNotRecentlyPregnant;
        this.isNotBreastfeeding = isNotBreastfeeding;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDateTime approvalDate) {
        this.approvalDate = approvalDate;
    }

    public boolean isFreeOfInfections() {
        return isFreeOfInfections;
    }

    public void setFreeOfInfections(boolean freeOfInfections) {
        isFreeOfInfections = freeOfInfections;
    }

    public boolean hasNoTattoosOrPiercings() {
        return hasNoTattoosOrPiercings;
    }

    public void setHasNoTattoosOrPiercings(boolean hasNoTattoosOrPiercings) {
        this.hasNoTattoosOrPiercings = hasNoTattoosOrPiercings;
    }

    public boolean hasNoRecentProcedures() {
        return hasNoRecentProcedures;
    }

    public void setHasNoRecentProcedures(boolean hasNoRecentProcedures) {
        this.hasNoRecentProcedures = hasNoRecentProcedures;
    }

    public boolean hasNoTravelToRiskAreas() {
        return hasNoTravelToRiskAreas;
    }

    public void setHasNoTravelToRiskAreas(boolean hasNoTravelToRiskAreas) {
        this.hasNoTravelToRiskAreas = hasNoTravelToRiskAreas;
    }

    public boolean hasNoRiskBehavior() {
        return hasNoRiskBehavior;
    }

    public void setHasNoRiskBehavior(boolean hasNoRiskBehavior) {
        this.hasNoRiskBehavior = hasNoRiskBehavior;
    }

    public boolean hasNoHIVOrDrugUse() {
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

    public enum ApplicationStatus {
        PENDING, APPROVED, REJECTED
    }
}
