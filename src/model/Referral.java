package model;

/**
 * Referral model class representing patient referrals between care providers
 */
public class Referral {
    private String referralId;
    private String patientId;
    private String referringClinicianId;
    private String referredToClinicianId;
    private String referringFacilityId;
    private String referredToFacilityId;
    private String referralDate;
    private String urgencyLevel;
    private String referralReason;
    private String clinicalSummary;
    private String requestedInvestigations;
    private String status;
    private String appointmentId;
    private String notes;
    private String createdDate;
    private String lastUpdated;

    public Referral() {}

    // Getters and Setters
    public String getReferralId() { return referralId; }
    public void setReferralId(String referralId) { this.referralId = referralId; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getReferringClinicianId() { return referringClinicianId; }
    public void setReferringClinicianId(String id) { this.referringClinicianId = id; }

    public String getReferredToClinicianId() { return referredToClinicianId; }
    public void setReferredToClinicianId(String id) { this.referredToClinicianId = id; }

    public String getReferringFacilityId() { return referringFacilityId; }
    public void setReferringFacilityId(String id) { this.referringFacilityId = id; }

    public String getReferredToFacilityId() { return referredToFacilityId; }
    public void setReferredToFacilityId(String id) { this.referredToFacilityId = id; }

    public String getReferralDate() { return referralDate; }
    public void setReferralDate(String referralDate) { this.referralDate = referralDate; }

    public String getUrgencyLevel() { return urgencyLevel; }
    public void setUrgencyLevel(String urgencyLevel) { this.urgencyLevel = urgencyLevel; }

    public String getReferralReason() { return referralReason; }
    public void setReferralReason(String referralReason) { this.referralReason = referralReason; }

    public String getClinicalSummary() { return clinicalSummary; }
    public void setClinicalSummary(String clinicalSummary) { this.clinicalSummary = clinicalSummary; }

    public String getRequestedInvestigations() { return requestedInvestigations; }
    public void setRequestedInvestigations(String inv) { this.requestedInvestigations = inv; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAppointmentId() { return appointmentId; }
    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getCreatedDate() { return createdDate; }
    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }

    public String getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }

    @Override
    public String toString() {
        return referralId + " - " + referralReason + " (" + urgencyLevel + ")";
    }

    public String toCsvString() {
        return String.join(",",
            referralId, patientId, referringClinicianId, referredToClinicianId,
            referringFacilityId, referredToFacilityId, referralDate, urgencyLevel,
            referralReason, "\"" + clinicalSummary + "\"", requestedInvestigations,
            status, appointmentId != null ? appointmentId : "",
            "\"" + notes + "\"", createdDate, lastUpdated);
    }
}
