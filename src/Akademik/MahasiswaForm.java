package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MahasiswaForm extends JInternalFrame {
    private JTextField txtNim, txtNama;
    private JTable table;
    private DefaultTableModel model;

    public MahasiswaForm() {
        super("Data Mahasiswa", true, true, true, true);
        setSize(800, 500);
        setLayout(new BorderLayout(0, 10));
        getContentPane().setBackground(StyleManager.BG_PRIMARY);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(StyleManager.BG_CARD);
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(StyleManager.createLabel("NIM"), gbc);
        gbc.gridx = 1;
        txtNim = StyleManager.createTextField();
        formPanel.add(txtNim, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(StyleManager.createLabel("Nama"), gbc);
        gbc.gridx = 1;
        txtNama = StyleManager.createTextField();
        formPanel.add(txtNama, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(StyleManager.BG_CARD);
        JButton btnSimpan = StyleManager.createFlatBtn("Simpan", StyleManager.BTN_SUCCESS);
        JButton btnUbah = StyleManager.createFlatBtn("Ubah", StyleManager.BTN_INFO);
        JButton btnHapus = StyleManager.createFlatBtn("Hapus", StyleManager.BTN_DANGER);
        btnPanel.add(btnSimpan); btnPanel.add(btnUbah); btnPanel.add(btnHapus);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        model = new DefaultTableModel(new String[]{"NIM", "Nama"}, 0);
        table = new JTable(model);
        StyleManager.styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(StyleManager.BG_CARD);

        add(formPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // CRUD LOGIC
        btnSimpan.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("INSERT INTO data_user (id_user, password, akses) VALUES (?, ?, 'mahasiswa')");
                pst.setString(1, txtNim.getText());
                pst.setString(2, txtNama.getText()); // Simplified logic for demo
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Mahasiswa Tersimpan");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnHapus.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("DELETE FROM data_user WHERE id_user=?");
                pst.setString(1, txtNim.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Dihapus");
                loadData();
            } catch (Exception ex) {}
        });

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            Connection conn = Koneksi.configDB();
            ResultSet rs = conn.createStatement().executeQuery("SELECT id_user, password FROM data_user WHERE akses='mahasiswa'");
            while(rs.next()) {
                model.addRow(new Object[]{rs.getString("id_user"), rs.getString("password")});
            }
        } catch (Exception e) {}
    }
}
