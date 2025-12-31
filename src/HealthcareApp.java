import controller.MainController;
import view.MainFrame;
import javax.swing.*;

/**
 * Main application entry point
 * NHS Healthcare Management System
 */
public class HealthcareApp {

    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Get data path
        String dataPath = System.getProperty("user.dir");

        // Create controller
        MainController controller = new MainController(dataPath);

        // Load data
        controller.loadAllData();

        // Create and show GUI
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(controller);
            frame.refreshAllPanels();
            frame.setVisible(true);
        });
    }
}
