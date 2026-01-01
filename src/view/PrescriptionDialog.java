package view;

import controller.MainController;
import model.Prescription;
import javax.swing.*;
import java.awt.*;

/**
 * Dialog for adding prescriptions
 */
public class PrescriptionDialog extends JDialog {

    private MainController controller;
    private Prescription editingPrescription;
    private boolean confirmed = false;

    private JTextField idField;
    private JComboBox<String> patientCombo;
    private JTextField medicationField;
    private JTextField dosageField;
    private JTextField frequencyField;
    private JTextField durationField;
    private JTextField quantityField;
    private JTextField instructionsField;
    private JTextField pharmacyField;

    public PrescriptionDialog(Frame parent, MainController controller) {
        super(parent, "Add Prescription", true);
        this.controller = controller;
        this.editingPrescription = null;
        initializeUI();
    }

    public PrescriptionDialog(Frame parent, MainController controller, Prescription p) {
        super(parent, "Edit Prescription", true);
        this.controller = controller;
        this.editingPrescription = p;
        initializeUI();
        populateFields();
    }

    private void initializeUI() {
        setSize(400, 400);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(9, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        idField = new JTextField(generateNewId());
        idField.setEditable(false);

        // Patient combo
        patientCombo = new JComboBox<>();
        for (var p : controller.getPatients()) {
            patientCombo.addItem(p.getPatientId() + " - " + p.getFullName());
        }

        medicationField = new JTextField();
        dosageField = new JTextField();
        frequencyField = new JTextField();
        durationField = new JTextField("28");
        quantityField = new JTextField();
        instructionsField = new JTextField();
        pharmacyField = new JTextField();

        formPanel.add(new JLabel("Prescription ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Patient:"));
        formPanel.add(patientCombo);
        formPanel.add(new JLabel("Medication:"));
        formPanel.add(medicationField);
        formPanel.add(new JLabel("Dosage:"));
        formPanel.add(dosageField);
        formPanel.add(new JLabel("Frequency:"));
        formPanel.add(frequencyField);
        formPanel.add(new JLabel("Duration (days):"));
        formPanel.add(durationField);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityField);
        formPanel.add(new JLabel("Instructions:"));
        formPanel.add(instructionsField);
        formPanel.add(new JLabel("Pharmacy:"));
        formPanel.add(pharmacyField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        saveBtn.addActionListener(e -> save());
        cancelBtn.addActionListener(e -> dispose());

        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void populateFields() {
        if (editingPrescription != null) {
            idField.setText(editingPrescription.getPrescriptionId());
            medicationField.setText(editingPrescription.getMedicationName());
            dosageField.setText(editingPrescription.getDosage());
            frequencyField.setText(editingPrescription.getFrequency());
            durationField.setText(String.valueOf(editingPrescription.getDurationDays()));
            quantityField.setText(editingPrescription.getQuantity());
            instructionsField.setText(editingPrescription.getInstructions());
            pharmacyField.setText(editingPrescription.getPharmacyName());
        }
    }

    private String generateNewId() {
        int maxId = 0;
        for (Prescription p : controller.getPrescriptions()) {
            String id = p.getPrescriptionId();
            if (id.startsWith("RX")) {
                int num = Integer.parseInt(id.substring(2));
                if (num > maxId) maxId = num;
            }
        }
        return String.format("RX%03d", maxId + 1);
    }

    private void save() {
        String patientId = ((String) patientCombo.getSelectedItem())
            .split(" - ")[0];
        String today = java.time.LocalDate.now().toString();

        if (editingPrescription != null) {
            // Edit mode
            editingPrescription.setMedicationName(medicationField.getText());
            editingPrescription.setDosage(dosageField.getText());
            editingPrescription.setFrequency(frequencyField.getText());
            editingPrescription.setDurationDays(Integer.parseInt(durationField.getText()));
            editingPrescription.setQuantity(quantityField.getText());
            editingPrescription.setInstructions(instructionsField.getText());
            editingPrescription.setPharmacyName(pharmacyField.getText());
        } else {
            // Add mode
            Prescription p = new Prescription(
                idField.getText(), patientId, "C001", "", today,
                medicationField.getText(), dosageField.getText(),
                frequencyField.getText(), Integer.parseInt(durationField.getText()),
                quantityField.getText(), instructionsField.getText(),
                pharmacyField.getText(), "Issued", today, ""
            );
            controller.getPrescriptions().add(p);
        }
        confirmed = true;
        dispose();
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
