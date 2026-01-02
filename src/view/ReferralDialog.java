package view;

import controller.MainController;
import model.Referral;
import model.ReferralManager;
import javax.swing.*;
import java.awt.*;

/**
 * Dialog for adding referrals
 */
public class ReferralDialog extends JDialog {

    private MainController controller;
    private Referral editingReferral;
    private boolean confirmed = false;

    private JTextField idField;
    private JComboBox<String> patientCombo;
    private JComboBox<String> referringClinicianCombo;
    private JComboBox<String> referredToClinicianCombo;
    private JComboBox<String> urgencyCombo;
    private JTextField reasonField;
    private JTextArea summaryArea;
    private JTextField investigationsField;

    public ReferralDialog(Frame parent, MainController controller) {
        super(parent, "Add Referral", true);
        this.controller = controller;
        this.editingReferral = null;
        initializeUI();
    }

    public ReferralDialog(Frame parent, MainController controller, Referral r) {
        super(parent, "Edit Referral", true);
        this.controller = controller;
        this.editingReferral = r;
        initializeUI();
        populateFields();
    }

    private void initializeUI() {
        setSize(500, 500);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        idField = new JTextField(generateNewId());
        idField.setEditable(false);

        patientCombo = new JComboBox<>();
        for (var p : controller.getPatients()) {
            patientCombo.addItem(p.getPatientId() + " - " + p.getFullName());
        }

        // Referring clinician combo
        referringClinicianCombo = new JComboBox<>();
        for (var c : controller.getClinicians()) {
            referringClinicianCombo.addItem(c.getClinicianId() + " - " + c.getFullName());
        }

        // Referred to clinician combo
        referredToClinicianCombo = new JComboBox<>();
        for (var c : controller.getClinicians()) {
            referredToClinicianCombo.addItem(c.getClinicianId() + " - " + c.getFullName());
        }

        urgencyCombo = new JComboBox<>(new String[]{
            "Routine", "Urgent", "Non-urgent"});
        reasonField = new JTextField();
        summaryArea = new JTextArea(3, 20);
        investigationsField = new JTextField();

        // Row 0
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Referral ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(idField, gbc);

        // Row 1
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Patient:"), gbc);
        gbc.gridx = 1;
        formPanel.add(patientCombo, gbc);

        // Row 2 - Referring Clinician
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Referring Clinician:"), gbc);
        gbc.gridx = 1;
        formPanel.add(referringClinicianCombo, gbc);

        // Row 3 - Referred To Clinician
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Referred To:"), gbc);
        gbc.gridx = 1;
        formPanel.add(referredToClinicianCombo, gbc);

        // Row 4
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Urgency:"), gbc);
        gbc.gridx = 1;
        formPanel.add(urgencyCombo, gbc);

        // Row 5
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Reason:"), gbc);
        gbc.gridx = 1;
        formPanel.add(reasonField, gbc);

        // Row 6
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Clinical Summary:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(summaryArea), gbc);

        // Row 7
        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(new JLabel("Investigations:"), gbc);
        gbc.gridx = 1;
        formPanel.add(investigationsField, gbc);

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
        if (editingReferral != null) {
            idField.setText(editingReferral.getReferralId());
            urgencyCombo.setSelectedItem(editingReferral.getUrgencyLevel());
            reasonField.setText(editingReferral.getReferralReason());
            summaryArea.setText(editingReferral.getClinicalSummary());
            investigationsField.setText(editingReferral.getRequestedInvestigations());
        }
    }

    private String generateNewId() {
        int maxId = 0;
        ReferralManager rm = ReferralManager.getInstance();
        for (Referral r : rm.getReferralQueue()) {
            String id = r.getReferralId();
            if (id.startsWith("R")) {
                int num = Integer.parseInt(id.substring(1));
                if (num > maxId) maxId = num;
            }
        }
        return String.format("R%03d", maxId + 1);
    }

    private void save() {
        String patientId = ((String) patientCombo.getSelectedItem())
            .split(" - ")[0];
        String referringId = ((String) referringClinicianCombo.getSelectedItem())
            .split(" - ")[0];
        String referredToId = ((String) referredToClinicianCombo.getSelectedItem())
            .split(" - ")[0];
        String today = java.time.LocalDate.now().toString();

        if (editingReferral != null) {
            // Edit mode
            editingReferral.setReferringClinicianId(referringId);
            editingReferral.setReferredToClinicianId(referredToId);
            editingReferral.setUrgencyLevel((String) urgencyCombo.getSelectedItem());
            editingReferral.setReferralReason(reasonField.getText());
            editingReferral.setClinicalSummary(summaryArea.getText());
            editingReferral.setRequestedInvestigations(investigationsField.getText());
            editingReferral.setLastUpdated(today);
        } else {
            // Add mode
            Referral r = new Referral();
            r.setReferralId(idField.getText());
            r.setPatientId(patientId);
            r.setReferringClinicianId(referringId);
            r.setReferredToClinicianId(referredToId);
            r.setReferringFacilityId("S001");
            r.setReferredToFacilityId("H001");
            r.setReferralDate(today);
            r.setUrgencyLevel((String) urgencyCombo.getSelectedItem());
            r.setReferralReason(reasonField.getText());
            r.setClinicalSummary(summaryArea.getText());
            r.setRequestedInvestigations(investigationsField.getText());
            r.setStatus("New");
            r.setNotes("");
            r.setCreatedDate(today);
            r.setLastUpdated(today);
            ReferralManager.getInstance().addReferral(r);
        }
        confirmed = true;
        dispose();
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
