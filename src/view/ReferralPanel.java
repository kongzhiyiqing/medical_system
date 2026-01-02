package view;

import controller.MainController;
import model.Referral;
import model.ReferralManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for displaying and managing referrals
 */
public class ReferralPanel extends JPanel {

    private MainController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    public ReferralPanel(MainController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Patient", "Urgency",
            "Reason", "Status", "Date"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add Referral");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        JButton exportBtn = new JButton("Export to File");

        addBtn.addActionListener(e -> addReferral());
        editBtn.addActionListener(e -> editReferral());
        deleteBtn.addActionListener(e -> deleteReferral());
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
        ReferralManager rm = controller.getReferralManager();
        List<Referral> referrals = rm.getReferralQueue();
        for (Referral r : referrals) {
            tableModel.addRow(new Object[]{
                r.getReferralId(),
                r.getPatientId(),
                r.getUrgencyLevel(),
                r.getReferralReason(),
                r.getStatus(),
                r.getReferralDate()
            });
        }
    }

    private void addReferral() {
        ReferralDialog dialog = new ReferralDialog(
            (Frame) SwingUtilities.getWindowAncestor(this),
            controller);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            refreshData();
        }
    }

    private void exportToFile() {
        List<Referral> referrals = controller.getReferralManager()
            .getReferralQueue();
        controller.getDataManager().saveReferrals(
            referrals, "referrals_output.csv");
        JOptionPane.showMessageDialog(this,
            "Referrals exported to referrals_output.csv");
    }

    private void editReferral() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a referral");
            return;
        }
        String id = (String) tableModel.getValueAt(row, 0);
        Referral r = controller.getReferralManager().getReferralById(id);
        if (r != null) {
            ReferralDialog dialog = new ReferralDialog(
                (Frame) SwingUtilities.getWindowAncestor(this), controller, r);
            dialog.setVisible(true);
            if (dialog.isConfirmed()) {
                refreshData();
            }
        }
    }

    private void deleteReferral() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a referral");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete this referral?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String id = (String) tableModel.getValueAt(row, 0);
            controller.getReferralManager().removeReferral(id);
            refreshData();
        }
    }
}
