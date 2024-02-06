package com.example.blooddonationsystem.model.payload.request;

public class DonationApplicationDTO {
    private boolean hasNoTattoosOrPiercings;
    private boolean hasNoRecentProcedures;
    private boolean hasNoTravelToRiskAreas;
    private boolean hasNoRiskBehavior;
    private boolean hasNoHIVOrDrugUse;
    private boolean hasAIDS;
    private boolean freeOfInfections;
    private boolean notRecentlyPregnant;
    private boolean notBreastfeeding;

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

    public boolean isHasAIDS() {
        return hasAIDS;
    }

    public void setHasAIDS(boolean hasAIDS) {
        this.hasAIDS = hasAIDS;
    }

    public boolean isFreeOfInfections() {
        return freeOfInfections;
    }

    public void setFreeOfInfections(boolean freeOfInfections) {
        this.freeOfInfections = freeOfInfections;
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

public DonationApplicationDTO(boolean hasNoTattoosOrPiercings, boolean hasNoRecentProcedures, boolean hasNoTravelToRiskAreas, boolean hasNoRiskBehavior, boolean hasNoHIVOrDrugUse, boolean hasAIDS, boolean freeOfInfections, boolean notRecentlyPregnant, boolean notBreastfeeding) {
        this.hasNoTattoosOrPiercings = hasNoTattoosOrPiercings;
        this.hasNoRecentProcedures = hasNoRecentProcedures;
        this.hasNoTravelToRiskAreas = hasNoTravelToRiskAreas;
        this.hasNoRiskBehavior = hasNoRiskBehavior;
        this.hasNoHIVOrDrugUse = hasNoHIVOrDrugUse;
        this.hasAIDS = hasAIDS;
        this.freeOfInfections = freeOfInfections;
        this.notRecentlyPregnant = notRecentlyPregnant;
        this.notBreastfeeding = notBreastfeeding;
    }

    public DonationApplicationDTO() {
    }

    @Override

    public String toString() {
        return "DonationApplicationDTO{" +
                "hasNoTattoosOrPiercings=" + hasNoTattoosOrPiercings +
                ", hasNoRecentProcedures=" + hasNoRecentProcedures +
                ", hasNoTravelToRiskAreas=" + hasNoTravelToRiskAreas +
                ", hasNoRiskBehavior=" + hasNoRiskBehavior +
                ", hasNoHIVOrDrugUse=" + hasNoHIVOrDrugUse +
                ", hasAIDS=" + hasAIDS +
                ", freeOfInfections=" + freeOfInfections +
                ", notRecentlyPregnant=" + notRecentlyPregnant +
                ", notBreastfeeding=" + notBreastfeeding +
                '}';
    }











}
