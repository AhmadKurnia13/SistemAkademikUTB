package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DosenForm extends JInternalFrame {
    private JTextField txtNidn, txtNama, txtHp;
    private JTable table;
    private DefaultTableModel model;

    public DosenForm() {
        super("Data Dosen", true, true, true, true);
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
        formPanel.add(StyleManager.createLabel("NIDN"), gbc);
        gbc.gridx = 1;
        txtNidn = StyleManager.createTextField();
        formPanel.add(txtNidn, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(StyleManager.createLabel("Nama Dosen"), gbc);
        gbc.gridx = 1;
        txtNama = StyleManager.createTextField();
        formPanel.add(txtNama, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(StyleManager.createLabel("No HP"), gbc);
        gbc.gridx = 1;
        txtHp = StyleManager.createTextField();
        formPanel.add(txtHp, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(StyleManager.BG_CARD);
        JButton btnSimpan = StyleManager.createFlatBtn("Simpan", StyleManager.BTN_SUCCESS);
        JButton btnUbah = StyleManager.createFlatBtn("Ubah", StyleManager.BTN_INFO);
        JButton btnHapus = StyleManager.createFlatBtn("Hapus", StyleManager.BTN_DANGER);
        JButton btnClear = StyleManager.createFlatBtn("Clear", Color.GRAY);
        btnPanel.add(btnClear); btnPanel.add(btnSimpan); btnPanel.add(btnUbah); btnPanel.add(btnHapus);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        model = new DefaultTableModel(new String[]{"NIDN", "Nama Dosen", "No HP"}, 0);
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
                PreparedStatement pst = conn.prepareStatement("INSERT INTO dosen (nidn, nama_dosen, no_hp) VALUES (?, ?, ?)");
                pst.setString(1, txtNidn.getText());
                pst.setString(2, txtNama.getText());
                pst.setString(3, txtHp.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Dosen Tersimpan");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnUbah.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("UPDATE dosen SET nama_dosen=?, no_hp=? WHERE nidn=?");
                pst.setString(1, txtNama.getText());
                pst.setString(2, txtHp.getText());
                pst.setString(3, txtNidn.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Diubah");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnHapus.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("DELETE FROM dosen WHERE nidn=?");
                pst.setString(1, txtNidn.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Dihapus");
                loadData();
            } catch (Exception ex) {}
        });
        
        btnClear.addActionListener(e -> {
            txtNidn.setText(""); txtNama.setText(""); txtHp.setText("");
        });

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            Connection conn = Koneksi.configDB();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM dosen");
            while(rs.next()) {
                model.addRow(new Object[]{rs.getString("nidn"), rs.getString("nama_dosen"), rs.getString("no_hp")});
            }
        } catch (Exception e) {}
    }
}
