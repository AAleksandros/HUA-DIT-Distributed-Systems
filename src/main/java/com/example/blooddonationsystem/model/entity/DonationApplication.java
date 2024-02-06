package com.example.blooddonationsystem.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "donation_application")
public class DonationApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id", nullable = false)
    private Citizen citizen;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ApplicationStatus status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    @Column(name = "approval_date")
    private LocalDateTime approvalDate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "processed_by_secretary_id")
    private Secretary processedBy;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

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

    public DonationApplication(Citizen citizen, ApplicationStatus status, boolean isFreeOfInfections, boolean hasNoTattoosOrPiercings, boolean hasNoRecentProcedures, boolean hasNoTravelToRiskAreas, boolean hasNoRiskBehavior, boolean hasNoHIVOrDrugUse, boolean isNotRecentlyPregnant, boolean isNotBreastfeeding, boolean hasAIDS, String rejectionReason) {
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
        this.hasAIDS = hasAIDS;
        this.rejectionReason = rejectionReason;
        this.createdAt = LocalDateTime.now(); // Initialize createdAt to the current time
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }



    // Getters and setters for all fields
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


    public LocalDateTime getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDateTime approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Secretary getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Secretary processedBy) {
        this.processedBy = processedBy;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
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

    public boolean hasAIDS() {
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
