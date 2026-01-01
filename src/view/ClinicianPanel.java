package view;

import controller.MainController;
import model.Clinician;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for displaying clinicians
 */
public class ClinicianPanel extends JPanel {

    private MainController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    public ClinicianPanel(MainController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Name", "Title", "Speciality",
            "Phone", "Email", "Workplace"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        List<Clinician> clinicians = controller.getClinicians();
        for (Clinician c : clinicians) {
            tableModel.addRow(new Object[]{
                c.getClinicianId(),
                c.getFullName(),
                c.getTitle(),
                c.getSpeciality(),
                c.getPhoneNumber(),
                c.getEmail(),
                c.getWorkplaceType()
            });
        }
    }
}
