package controller;

import model.Prescription;
import java.util.List;

/**
 * Controller for Prescription operations
 */
public class PrescriptionController {

    private List<Prescription> prescriptions;

    public PrescriptionController(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
    }

    public boolean removePrescription(String prescriptionId) {
        for (int i = 0; i < prescriptions.size(); i++) {
            if (prescriptions.get(i).getPrescriptionId().equals(prescriptionId)) {
                prescriptions.remove(i);
                return true;
            }
        }
        return false;
    }

    public Prescription getPrescriptionById(String prescriptionId) {
        for (Prescription p : prescriptions) {
            if (p.getPrescriptionId().equals(prescriptionId)) {
                return p;
            }
        }
        return null;
    }

    public String generateNewPrescriptionId() {
        int maxId = 0;
        for (Prescription p : prescriptions) {
            String id = p.getPrescriptionId();
            if (id.startsWith("RX")) {
                int num = Integer.parseInt(id.substring(2));
                if (num > maxId) maxId = num;
            }
        }
        return String.format("RX%03d", maxId + 1);
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }
}
