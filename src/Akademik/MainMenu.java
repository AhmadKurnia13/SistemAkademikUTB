package Akademik;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenu extends JFrame {
    private JDesktopPane desktopPane;

    public MainMenu() {
        setTitle("Sistem Akademik UTB - JDesktopPane Version");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktopPane = new JDesktopPane();
        desktopPane.setBackground(StyleManager.BG_PRIMARY);
        setContentPane(desktopPane);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(StyleManager.BG_CARD);
        menuBar.setForeground(StyleManager.TEXT_PRIMARY);

        // File Master
        JMenu menuMaster = new JMenu("File Master");
        JMenuItem itemUser = new JMenuItem("User");
        JMenuItem itemMahasiswa = new JMenuItem("Mahasiswa");
        JMenuItem itemMataKuliah = new JMenuItem("Mata Kuliah");
        JMenuItem itemDosen = new JMenuItem("Dosen");
        menuMaster.add(itemUser);
        menuMaster.add(itemMahasiswa);
        menuMaster.add(itemMataKuliah);
        menuMaster.add(itemDosen);

        // Transaction
        JMenu menuTransaction = new JMenu("Transaction");
        JMenuItem itemKRS = new JMenuItem("KRS");
        JMenuItem itemNilai = new JMenuItem("Nilai");
        menuTransaction.add(itemKRS);
        menuTransaction.add(itemNilai);

        // Setting
        JMenu menuSetting = new JMenu("Setting");
        JMenuItem itemAddUser = new JMenuItem("Add User");
        JMenuItem itemChangePass = new JMenuItem("Change Password");
        menuSetting.add(itemAddUser);
        menuSetting.add(itemChangePass);

        menuBar.add(menuMaster);
        menuBar.add(menuTransaction);
        menuBar.add(menuSetting);
        setJMenuBar(menuBar);

        // Action Listeners
        itemMahasiswa.addActionListener(e -> openForm(new MahasiswaForm()));
        itemMataKuliah.addActionListener(e -> openForm(new MataKuliahForm()));
        itemDosen.addActionListener(e -> openForm(new DosenForm()));
        itemNilai.addActionListener(e -> openForm(new NilaiForm()));
        itemKRS.addActionListener(e -> openForm(new KRSForm()));
        itemChangePass.addActionListener(e -> openForm(new ChangePasswordForm()));
    }

    private void openForm(JInternalFrame form) {
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame.getClass().equals(form.getClass())) {
                frame.toFront();
                try {
                    frame.setSelected(true);
                } catch (Exception ex) {}
                return;
            }
        }
        desktopPane.add(form);
        form.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }
}
