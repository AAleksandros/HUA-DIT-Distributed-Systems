package com.example.blooddonationsystem.model.payload.request;

public class DonationApplication {
    private boolean hasTattoosOrPiercings;
    private boolean hasRecentProcedures;
    private boolean hasTravelToRiskAreas;
    private boolean hasRiskBehavior;
    private boolean hasDrugUse;
    private boolean hasAIDS;
    private boolean freeOfInfections;
    private boolean RecentlyPregnant;
    private boolean Breastfeeding;

    public boolean isHasTattoosOrPiercings() {
        return hasTattoosOrPiercings;
    }

    public void setHasTattoosOrPiercings(boolean hasTattoosOrPiercings) {
        this.hasTattoosOrPiercings = hasTattoosOrPiercings;
    }

    public boolean isHasRecentProcedures() {
        return hasRecentProcedures;
    }

    public void setHasRecentProcedures(boolean hasRecentProcedures) {
        this.hasRecentProcedures = hasRecentProcedures;
    }

    public boolean isHasTravelToRiskAreas() {
        return hasTravelToRiskAreas;
    }

    public void setHasTravelToRiskAreas(boolean hasTravelToRiskAreas) {
        this.hasTravelToRiskAreas = hasTravelToRiskAreas;
    }

    public boolean isHasRiskBehavior() {
        return hasRiskBehavior;
    }

    public void setHasRiskBehavior(boolean hasRiskBehavior) {
        this.hasRiskBehavior = hasRiskBehavior;
    }

    public boolean isHasAIDS() {
        return hasAIDS;
    }

    public void setHasAIDS(boolean hasAIDS) {
        this.hasAIDS = hasAIDS;
    }
    public void setHasDrugUse(boolean hasDrugUse) {
        this.hasDrugUse = hasDrugUse;
    }
    public boolean isHasDrugUse(){
        return hasDrugUse;
    }
    public boolean isFreeOfInfections() {
        return freeOfInfections;
    }

    public void setFreeOfInfections(boolean freeOfInfections) {
        this.freeOfInfections = freeOfInfections;
    }

    public boolean isRecentlyPregnant() {
        return RecentlyPregnant;
    }

    public void setRecentlyPregnant(boolean recentlyPregnant) {
        this.RecentlyPregnant = recentlyPregnant;
    }

    public boolean isBreastfeeding() {
        return Breastfeeding;
    }

    public void setBreastfeeding(boolean breastfeeding) {
        this.Breastfeeding = breastfeeding;
    }


    public DonationApplication(boolean hasTattoosOrPiercings, boolean hasRecentProcedures, boolean hasTravelToRiskAreas, boolean hasRiskBehavior, boolean hasDrugUse, boolean hasAIDS, boolean freeOfInfections, boolean RecentlyPregnant, boolean Breastfeeding) {
        this.hasTattoosOrPiercings = hasTattoosOrPiercings;
        this.hasRecentProcedures = hasRecentProcedures;
        this.hasTravelToRiskAreas = hasTravelToRiskAreas;
        this.hasRiskBehavior = hasRiskBehavior;
        this.hasDrugUse = hasDrugUse;
        this.hasAIDS = hasAIDS;
        this.freeOfInfections = freeOfInfections;
        this.RecentlyPregnant = RecentlyPregnant;
        this.Breastfeeding = Breastfeeding;

    }

    public DonationApplication() {
    }

    @Override

    public String toString() {
        return "DonationApplicationDTO{" +
                "hasNoTattoosOrPiercings=" + hasTattoosOrPiercings +
                ", hasNoRecentProcedures=" + hasRecentProcedures +
                ", hasNoTravelToRiskAreas=" + hasTravelToRiskAreas +
                ", hasNoRiskBehavior=" + hasRiskBehavior +
                ", hasNoDrugUse=" + hasDrugUse +
                ", hasAIDS=" + hasAIDS +
                ", freeOfInfections=" + freeOfInfections +
                ", notRecentlyPregnant=" + RecentlyPregnant +
                ", notBreastfeeding=" + Breastfeeding +
                '}';
    }











}
