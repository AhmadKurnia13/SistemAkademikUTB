package Akademik;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class SistemAkademikUTB {
    public static void main(String[] args) {
        // Set cross-platform look and feel so the flat design looks the same everywhere
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Jalankan FormLogin sebagai titik awal aplikasi
        SwingUtilities.invokeLater(() -> {
            FormLogin loginPage = new FormLogin();
            loginPage.setVisible(true);
        });
    }
}
