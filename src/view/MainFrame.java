package view;

import controller.MainController;
import javax.swing.*;
import java.awt.*;

/**
 * Main application window - View layer
 */
public class MainFrame extends JFrame {

    private MainController controller;
    private JTabbedPane tabbedPane;
    private PatientPanel patientPanel;
    private ClinicianPanel clinicianPanel;
    private AppointmentPanel appointmentPanel;
    private PrescriptionPanel prescriptionPanel;
    private ReferralPanel referralPanel;

    public MainFrame(MainController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("NHS Healthcare Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Create tabbed pane
        tabbedPane = new JTabbedPane();

        // Initialize panels
        patientPanel = new PatientPanel(controller);
        clinicianPanel = new ClinicianPanel(controller);
        appointmentPanel = new AppointmentPanel(controller);
        prescriptionPanel = new PrescriptionPanel(controller);
        referralPanel = new ReferralPanel(controller);

        // Add tabs
        tabbedPane.addTab("Patients", patientPanel);
        tabbedPane.addTab("Clinicians", clinicianPanel);
        tabbedPane.addTab("Appointments", appointmentPanel);
        tabbedPane.addTab("Prescriptions", prescriptionPanel);
        tabbedPane.addTab("Referrals", referralPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Create menu bar
        createMenuBar();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadItem = new JMenuItem("Load Data");
        JMenuItem exitItem = new JMenuItem("Exit");

        loadItem.addActionListener(e -> loadData());
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private void loadData() {
        controller.loadAllData();
        refreshAllPanels();
        JOptionPane.showMessageDialog(this,
            "Data loaded successfully!",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);
    }

    public void refreshAllPanels() {
        patientPanel.refreshData();
        clinicianPanel.refreshData();
        appointmentPanel.refreshData();
        prescriptionPanel.refreshData();
        referralPanel.refreshData();
    }
}
