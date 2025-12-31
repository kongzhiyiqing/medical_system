package view;

import controller.MainController;
import model.Patient;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for displaying and managing patients
 */
public class PatientPanel extends JPanel {

    private MainController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    public PatientPanel(MainController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Create table
        String[] columns = {"ID", "Name", "DOB", "NHS Number",
            "Gender", "Phone", "Email"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        addBtn.addActionListener(e -> addPatient());
        editBtn.addActionListener(e -> editPatient());
        deleteBtn.addActionListener(e -> deletePatient());

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        List<Patient> patients = controller.getPatients();
        for (Patient p : patients) {
            tableModel.addRow(new Object[]{
                p.getPatientId(),
                p.getFullName(),
                p.getDateOfBirth(),
                p.getNhsNumber(),
                p.getGender(),
                p.getPhoneNumber(),
                p.getEmail()
            });
        }
    }

    private void addPatient() {
        PatientDialog dialog = new PatientDialog(
            (Frame) SwingUtilities.getWindowAncestor(this),
            "Add Patient", null, controller);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            refreshData();
        }
    }

    private void editPatient() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a patient");
            return;
        }
        String id = (String) tableModel.getValueAt(row, 0);
        Patient p = null;
        for (Patient patient : controller.getPatients()) {
            if (patient.getPatientId().equals(id)) {
                p = patient;
                break;
            }
        }
        if (p != null) {
            PatientDialog dialog = new PatientDialog(
                (Frame) SwingUtilities.getWindowAncestor(this),
                "Edit Patient", p, controller);
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                refreshData();
            }
        }
    }

    private void deletePatient() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                "Please select a patient");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete this patient?", "Confirm",
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String id = (String) tableModel.getValueAt(row, 0);
            controller.getPatients().removeIf(
                p -> p.getPatientId().equals(id));
            refreshData();
        }
    }
}
