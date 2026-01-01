package view;

import controller.MainController;
import model.Appointment;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel for displaying appointments
 */
public class AppointmentPanel extends JPanel {

    private MainController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    public AppointmentPanel(MainController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Patient", "Clinician", "Date",
            "Time", "Type", "Status", "Reason"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        List<Appointment> appointments = controller.getAppointments();
        for (Appointment a : appointments) {
            tableModel.addRow(new Object[]{
                a.getAppointmentId(),
                a.getPatientId(),
                a.getClinicianId(),
                a.getAppointmentDate(),
                a.getAppointmentTime(),
                a.getAppointmentType(),
                a.getStatus(),
                a.getReasonForVisit()
            });
        }
    }
}
