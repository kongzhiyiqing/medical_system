package view;

import controller.MainController;
import model.Prescription;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for displaying and managing prescriptions
 */
public class PrescriptionPanel extends JPanel {

    private MainController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    public PrescriptionPanel(MainController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Patient", "Medication",
            "Dosage", "Frequency", "Status", "Pharmacy"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        JButton exportBtn = new JButton("Export to File");

        addBtn.addActionListener(e -> addPrescription());
        editBtn.addActionListener(e -> editPrescription());
        deleteBtn.addActionListener(e -> deletePrescription());
        exportBtn.addActionListener(e -> exportToFile());

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(exportBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        List<Prescription> prescriptions = controller.getPrescriptions();
        for (Prescription p : prescriptions) {
            tableModel.addRow(new Object[]{
                p.getPrescriptionId(),
                p.getPatientId(),
                p.getMedicationName(),
                p.getDosage(),
                p.getFrequency(),
                p.getStatus(),
                p.getPharmacyName()
            });
        }
    }

    private void addPrescription() {
        PrescriptionDialog dialog = new PrescriptionDialog(
            (Frame) SwingUtilities.getWindowAncestor(this),
            controller);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            refreshData();
        }
    }

    private void exportToFile() {
        controller.getDataManager().savePrescriptions(
            controller.getPrescriptions(),
            "prescriptions_output.csv");
        JOptionPane.showMessageDialog(this,
            "Prescriptions exported to prescriptions_output.csv");
    }

    private void editPrescription() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a prescription");
            return;
        }
        String id = (String) tableModel.getValueAt(row, 0);
        Prescription p = null;
        for (Prescription presc : controller.getPrescriptions()) {
            if (presc.getPrescriptionId().equals(id)) {
                p = presc;
                break;
            }
        }
        if (p != null) {
            PrescriptionDialog dialog = new PrescriptionDialog(
                (Frame) SwingUtilities.getWindowAncestor(this), controller, p);
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                refreshData();
            }
        }
    }

    private void deletePrescription() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a prescription");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete this prescription?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String id = (String) tableModel.getValueAt(row, 0);
            controller.getPrescriptions().removeIf(p -> p.getPrescriptionId().equals(id));
            refreshData();
        }
    }
}
