package controller;

import model.Referral;
import model.ReferralManager;
import java.util.List;

/**
 * Controller for Referral operations
 */
public class ReferralController {

    private ReferralManager referralManager;

    public ReferralController() {
        this.referralManager = ReferralManager.getInstance();
    }

    public void addReferral(Referral referral) {
        referralManager.addReferral(referral);
    }

    public boolean removeReferral(String referralId) {
        return referralManager.removeReferral(referralId);
    }

    public Referral getReferralById(String referralId) {
        return referralManager.getReferralById(referralId);
    }

    public List<Referral> getAllReferrals() {
        return referralManager.getReferralQueue();
    }

    public String generateNewReferralId() {
        int maxId = 0;
        for (Referral r : referralManager.getReferralQueue()) {
            String id = r.getReferralId();
            if (id.startsWith("R")) {
                int num = Integer.parseInt(id.substring(1));
                if (num > maxId) maxId = num;
            }
        }
        return String.format("R%03d", maxId + 1);
    }
}
