package util;

import model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for loading and saving CSV data files
 */
public class DataManager {

    private String dataPath;

    public DataManager(String dataPath) {
        this.dataPath = dataPath;
    }

    /**
     * Load patients from CSV file
     */
    public List<Patient> loadPatients() {
        List<Patient> patients = new ArrayList<>();
        String filePath = dataPath + "/patients.csv";

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] values = parseCsvLine(line);
                if (values.length >= 14) {
                    Patient p = new Patient(
                        values[0], values[1], values[2], values[3],
                        values[4], values[5], values[6], values[7],
                        values[8], values[9], values[10], values[11],
                        values[12], values[13]
                    );
                    patients.add(p);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading patients: " + e.getMessage());
        }
        return patients;
    }

    /**
     * Load clinicians from CSV file
     */
    public List<Clinician> loadClinicians() {
        List<Clinician> clinicians = new ArrayList<>();
        String filePath = dataPath + "/clinicians.csv";

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] values = parseCsvLine(line);
                if (values.length >= 12) {
                    Clinician c = new Clinician(
                        values[0], values[1], values[2], values[3],
                        values[4], values[5], values[6], values[7],
                        values[8], values[9], values[10], values[11]
                    );
                    clinicians.add(c);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading clinicians: " + e.getMessage());
        }
        return clinicians;
    }

    /**
     * Parse CSV line handling quoted fields
     */
    private String[] parseCsvLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString().trim());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result.add(current.toString().trim());
        return result.toArray(new String[0]);
    }

    /**
     * Load appointments from CSV file
     */
    public List<Appointment> loadAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String filePath = dataPath + "/appointments.csv";

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; }
                String[] v = parseCsvLine(line);
                if (v.length >= 13) {
                    Appointment a = new Appointment(
                        v[0], v[1], v[2], v[3], v[4], v[5],
                        Integer.parseInt(v[6]), v[7], v[8], v[9],
                        v[10], v[11], v[12]
                    );
                    appointments.add(a);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading appointments: " + e.getMessage());
        }
        return appointments;
    }

    /**
     * Load prescriptions from CSV file
     */
    public List<Prescription> loadPrescriptions() {
        List<Prescription> prescriptions = new ArrayList<>();
        String filePath = dataPath + "/prescriptions.csv";

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; }
                String[] v = parseCsvLine(line);
                if (v.length >= 15) {
                    Prescription p = new Prescription(
                        v[0], v[1], v[2], v[3], v[4], v[5], v[6], v[7],
                        Integer.parseInt(v[8]), v[9], v[10], v[11],
                        v[12], v[13], v[14]
                    );
                    prescriptions.add(p);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading prescriptions: " + e.getMessage());
        }
        return prescriptions;
    }

    /**
     * Load referrals from CSV file
     */
    public List<Referral> loadReferrals() {
        List<Referral> referrals = new ArrayList<>();
        String filePath = dataPath + "/referrals.csv";

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; }
                String[] v = parseCsvLine(line);
                if (v.length >= 16) {
                    Referral r = new Referral();
                    r.setReferralId(v[0]);
                    r.setPatientId(v[1]);
                    r.setReferringClinicianId(v[2]);
                    r.setReferredToClinicianId(v[3]);
                    r.setReferringFacilityId(v[4]);
                    r.setReferredToFacilityId(v[5]);
                    r.setReferralDate(v[6]);
                    r.setUrgencyLevel(v[7]);
                    r.setReferralReason(v[8]);
                    r.setClinicalSummary(v[9]);
                    r.setRequestedInvestigations(v[10]);
                    r.setStatus(v[11]);
                    r.setAppointmentId(v[12]);
                    r.setNotes(v[13]);
                    r.setCreatedDate(v[14]);
                    r.setLastUpdated(v[15]);
                    referrals.add(r);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading referrals: " + e.getMessage());
        }
        return referrals;
    }

    /**
     * Save patients to CSV file
     */
    public void savePatients(List<Patient> patients, String filename) {
        String filePath = dataPath + "/" + filename;
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), "UTF-8"))) {
            pw.println("patient_id,first_name,last_name,date_of_birth,nhs_number," +
                "gender,phone_number,email,address,postcode,emergency_contact_name," +
                "emergency_contact_phone,registration_date,gp_surgery_id");
            for (Patient p : patients) {
                pw.println(p.toCsvString());
            }
        } catch (IOException e) {
            System.err.println("Error saving patients: " + e.getMessage());
        }
    }

    /**
     * Save prescriptions to CSV file
     */
    public void savePrescriptions(List<Prescription> prescriptions, String filename) {
        String filePath = dataPath + "/" + filename;
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), "UTF-8"))) {
            pw.println("prescription_id,patient_id,clinician_id,appointment_id," +
                "prescription_date,medication_name,dosage,frequency,duration_days," +
                "quantity,instructions,pharmacy_name,status,issue_date,collection_date");
            for (Prescription p : prescriptions) {
                pw.println(p.toCsvString());
            }
        } catch (IOException e) {
            System.err.println("Error saving prescriptions: " + e.getMessage());
        }
    }

    /**
     * Save referrals to CSV file
     */
    public void saveReferrals(List<Referral> referrals, String filename) {
        String filePath = dataPath + "/" + filename;
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), "UTF-8"))) {
            pw.println("referral_id,patient_id,referring_clinician_id," +
                "referred_to_clinician_id,referring_facility_id,referred_to_facility_id," +
                "referral_date,urgency_level,referral_reason,clinical_summary," +
                "requested_investigations,status,appointment_id,notes,created_date,last_updated");
            for (Referral r : referrals) {
                pw.println(r.toCsvString());
            }
        } catch (IOException e) {
            System.err.println("Error saving referrals: " + e.getMessage());
        }
    }
}
