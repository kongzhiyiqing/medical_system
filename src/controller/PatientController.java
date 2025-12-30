package controller;

import model.Patient;
import java.util.List;

/**
 * Controller for Patient operations
 */
public class PatientController {

    private List<Patient> patients;

    public PatientController(List<Patient> patients) {
        this.patients = patients;
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public boolean removePatient(String patientId) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getPatientId().equals(patientId)) {
                patients.remove(i);
                return true;
            }
        }
        return false;
    }

    public Patient getPatientById(String patientId) {
        for (Patient p : patients) {
            if (p.getPatientId().equals(patientId)) {
                return p;
            }
        }
        return null;
    }

    public String generateNewPatientId() {
        int maxId = 0;
        for (Patient p : patients) {
            String id = p.getPatientId();
            if (id.startsWith("P")) {
                int num = Integer.parseInt(id.substring(1));
                if (num > maxId) maxId = num;
            }
        }
        return String.format("P%03d", maxId + 1);
    }

    public List<Patient> getPatients() {
        return patients;
    }
}
