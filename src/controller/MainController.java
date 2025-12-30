package controller;

import model.*;
import util.DataManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Main Controller class - coordinates between Model and View
 */
public class MainController {

    private DataManager dataManager;
    private List<Patient> patients;
    private List<Clinician> clinicians;
    private List<Appointment> appointments;
    private List<Prescription> prescriptions;
    private ReferralManager referralManager;

    public MainController(String dataPath) {
        this.dataManager = new DataManager(dataPath);
        this.patients = new ArrayList<>();
        this.clinicians = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
        this.referralManager = ReferralManager.getInstance();
    }

    /**
     * Load all data from CSV files
     */
    public void loadAllData() {
        patients = dataManager.loadPatients();
        clinicians = dataManager.loadClinicians();
        appointments = dataManager.loadAppointments();
        prescriptions = dataManager.loadPrescriptions();

        // Load referrals into singleton manager
        referralManager.clearAll();
        List<Referral> referrals = dataManager.loadReferrals();
        for (Referral r : referrals) {
            referralManager.addReferral(r);
        }
    }

    // Getters
    public List<Patient> getPatients() { return patients; }
    public List<Clinician> getClinicians() { return clinicians; }
    public List<Appointment> getAppointments() { return appointments; }
    public List<Prescription> getPrescriptions() { return prescriptions; }
    public ReferralManager getReferralManager() { return referralManager; }
    public DataManager getDataManager() { return dataManager; }
}
