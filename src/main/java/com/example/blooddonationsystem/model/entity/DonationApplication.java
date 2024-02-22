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
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;



    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "processed_by_secretary_id")
    private Secretary processedBy;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    // Health History Attributes
    @NotNull(message = "This field is required")
    private boolean isFreeOfInfections;

    @NotNull(message = "This field is required")
    private boolean hasTattoosOrPiercings;

    @NotNull(message = "This field is required")
    private boolean hasRecentProcedures;

    @NotNull(message = "This field is required")
    private boolean hasTravelToRiskAreas;

    @NotNull(message = "This field is required")
    private boolean hasRiskBehavior;

    @NotNull(message = "This field is required")
    private boolean isRecentlyPregnant;

    @NotNull(message = "This field is required")
    private boolean isBreastfeeding;
    @NotNull(message = "This field is required")
    private boolean hasDrugUse;

    @NotNull(message = "This field is required")
    private boolean hasAIDS;

    @Column(name = "rejection_reason")
    private String rejectionReason;



    public DonationApplication() {
    }



    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }




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

    public void setHasDrugUse(boolean hasNoDrugUse) {
        this.hasDrugUse = hasNoDrugUse;
    }
    public boolean isHasDrugUse(){
        return hasDrugUse;
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
        return hasTattoosOrPiercings;
    }

    public void setHasTattoosOrPiercings(boolean hasNoTattoosOrPiercings) {
        this.hasTattoosOrPiercings = hasNoTattoosOrPiercings;
    }

    public boolean hasNoRecentProcedures() {
        return hasRecentProcedures;
    }

    public void setHasRecentProcedures(boolean hasNoRecentProcedures) {
        this.hasRecentProcedures = hasNoRecentProcedures;
    }

    public boolean hasNoTravelToRiskAreas() {
        return hasTravelToRiskAreas;
    }

    public void setHasTravelToRiskAreas(boolean hasNoTravelToRiskAreas) {
        this.hasTravelToRiskAreas = hasNoTravelToRiskAreas;
    }

    public boolean hasNoRiskBehavior() {
        return hasRiskBehavior;
    }

    public void setHasRiskBehavior(boolean hasNoRiskBehavior) {
        this.hasRiskBehavior = hasNoRiskBehavior;
    }


    public boolean isRecentlyPregnant() {
        return isRecentlyPregnant;
    }

    public void setRecentlyPregnant(boolean recentlyPregnant) {
        isRecentlyPregnant = recentlyPregnant;
    }

    public boolean isBreastfeeding() {
        return isBreastfeeding;
    }

    public void setBreastfeeding(boolean breastfeeding) {
        isBreastfeeding = breastfeeding;
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
