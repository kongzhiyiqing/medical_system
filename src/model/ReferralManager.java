package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton pattern implementation for Referral Management
 * Manages referral queue, email communication, and EHR updates
 */
public class ReferralManager {

    // Singleton instance
    private static ReferralManager instance;

    // Referral queue
    private List<Referral> referralQueue;

    // Email log for audit trail
    private List<String> emailLog;

    // Private constructor for Singleton pattern
    private ReferralManager() {
        this.referralQueue = new ArrayList<>();
        this.emailLog = new ArrayList<>();
    }

    /**
     * Get the singleton instance of ReferralManager
     * Thread-safe implementation
     */
    public static synchronized ReferralManager getInstance() {
        if (instance == null) {
            instance = new ReferralManager();
        }
        return instance;
    }

    /**
     * Add a referral to the queue
     */
    public void addReferral(Referral referral) {
        referralQueue.add(referral);
        logEmail("New referral added: " + referral.getReferralId());
    }

    /**
     * Remove a referral from the queue
     */
    public boolean removeReferral(String referralId) {
        for (int i = 0; i < referralQueue.size(); i++) {
            if (referralQueue.get(i).getReferralId().equals(referralId)) {
                referralQueue.remove(i);
                logEmail("Referral removed: " + referralId);
                return true;
            }
        }
        return false;
    }

    /**
     * Get all referrals in the queue
     */
    public List<Referral> getReferralQueue() {
        return new ArrayList<>(referralQueue);
    }

    /**
     * Get referral by ID
     */
    public Referral getReferralById(String referralId) {
        for (Referral r : referralQueue) {
            if (r.getReferralId().equals(referralId)) {
                return r;
            }
        }
        return null;
    }

    /**
     * Update referral status
     */
    public void updateReferralStatus(String referralId, String newStatus) {
        Referral referral = getReferralById(referralId);
        if (referral != null) {
            referral.setStatus(newStatus);
            logEmail("Referral " + referralId + " status updated to: " + newStatus);
        }
    }

    /**
     * Log email communication for audit trail
     */
    private void logEmail(String message) {
        String timestamp = java.time.LocalDateTime.now().toString();
        emailLog.add("[" + timestamp + "] " + message);
    }

    /**
     * Get email log
     */
    public List<String> getEmailLog() {
        return new ArrayList<>(emailLog);
    }

    /**
     * Get referrals by patient ID
     */
    public List<Referral> getReferralsByPatient(String patientId) {
        List<Referral> result = new ArrayList<>();
        for (Referral r : referralQueue) {
            if (r.getPatientId().equals(patientId)) {
                result.add(r);
            }
        }
        return result;
    }

    /**
     * Get referrals by urgency level
     */
    public List<Referral> getReferralsByUrgency(String urgency) {
        List<Referral> result = new ArrayList<>();
        for (Referral r : referralQueue) {
            if (r.getUrgencyLevel().equals(urgency)) {
                result.add(r);
            }
        }
        return result;
    }

    /**
     * Clear all referrals (for reloading data)
     */
    public void clearAll() {
        referralQueue.clear();
    }

    /**
     * Get queue size
     */
    public int getQueueSize() {
        return referralQueue.size();
    }
}
