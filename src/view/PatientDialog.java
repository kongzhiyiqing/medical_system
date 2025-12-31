package view;

import controller.MainController;
import model.Patient;
import javax.swing.*;
import java.awt.*;

/**
 * Dialog for adding/editing patients
 */
public class PatientDialog extends JDialog {

    private MainController controller;
    private Patient patient;
    private boolean confirmed = false;

    private JTextField idField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dobField;
    private JTextField nhsField;
    private JComboBox<String> genderCombo;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField postcodeField;

    public PatientDialog(Frame parent, String title,
                         Patient patient, MainController controller) {
        super(parent, title, true);
        this.controller = controller;
        this.patient = patient;
        initializeUI();
        if (patient != null) {
            populateFields();
        }
    }

    private void initializeUI() {
        setSize(400, 450);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(10, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        idField = new JTextField();
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        dobField = new JTextField();
        nhsField = new JTextField();
        genderCombo = new JComboBox<>(new String[]{"M", "F"});
        phoneField = new JTextField();
        emailField = new JTextField();
        addressField = new JTextField();
        postcodeField = new JTextField();

        // Generate new ID if adding
        if (patient == null) {
            idField.setText(generateNewId());
        }
        idField.setEditable(false);

        formPanel.add(new JLabel("Patient ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Date of Birth:"));
        formPanel.add(dobField);
        formPanel.add(new JLabel("NHS Number:"));
        formPanel.add(nhsField);
        formPanel.add(new JLabel("Gender:"));
        formPanel.add(genderCombo);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressField);
        formPanel.add(new JLabel("Postcode:"));
        formPanel.add(postcodeField);

        // Button panel
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
        idField.setText(patient.getPatientId());
        firstNameField.setText(patient.getFirstName());
        lastNameField.setText(patient.getLastName());
        dobField.setText(patient.getDateOfBirth());
        nhsField.setText(patient.getNhsNumber());
        genderCombo.setSelectedItem(patient.getGender());
        phoneField.setText(patient.getPhoneNumber());
        emailField.setText(patient.getEmail());
        addressField.setText(patient.getAddress());
        postcodeField.setText(patient.getPostcode());
    }

    private String generateNewId() {
        int maxId = 0;
        for (Patient p : controller.getPatients()) {
            String id = p.getPatientId();
            if (id.startsWith("P")) {
                int num = Integer.parseInt(id.substring(1));
                if (num > maxId) maxId = num;
            }
        }
        return String.format("P%03d", maxId + 1);
    }

    private void save() {
        if (patient == null) {
            patient = new Patient();
            controller.getPatients().add(patient);
        }
        patient.setPatientId(idField.getText());
        patient.setFirstName(firstNameField.getText());
        patient.setLastName(lastNameField.getText());
        patient.setDateOfBirth(dobField.getText());
        patient.setNhsNumber(nhsField.getText());
        patient.setGender((String) genderCombo.getSelectedItem());
        patient.setPhoneNumber(phoneField.getText());
        patient.setEmail(emailField.getText());
        patient.setAddress(addressField.getText());
        patient.setPostcode(postcodeField.getText());
        patient.setRegistrationDate(java.time.LocalDate.now().toString());
        patient.setGpSurgeryId("S001");

        confirmed = true;
        dispose();
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
