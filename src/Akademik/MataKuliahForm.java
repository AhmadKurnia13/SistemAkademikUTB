package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MataKuliahForm extends JInternalFrame {
    private JTextField txtKodeMk, txtNamaMk, txtSemester;
    private JComboBox<String> cbSks;
    private JTable table;
    private DefaultTableModel model;

    public MataKuliahForm() {
        super("Data Mata Kuliah", true, true, true, true);
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
        formPanel.add(StyleManager.createLabel("Kode MK"), gbc);
        gbc.gridx = 1;
        txtKodeMk = StyleManager.createTextField();
        formPanel.add(txtKodeMk, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(StyleManager.createLabel("Nama Mata Kuliah"), gbc);
        gbc.gridx = 1;
        txtNamaMk = StyleManager.createTextField();
        formPanel.add(txtNamaMk, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(StyleManager.createLabel("SKS"), gbc);
        gbc.gridx = 1;
        cbSks = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6"});
        cbSks.setBackground(StyleManager.BG_PRIMARY);
        cbSks.setForeground(StyleManager.TEXT_PRIMARY);
        formPanel.add(cbSks, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(StyleManager.createLabel("Semester"), gbc);
        gbc.gridx = 1;
        txtSemester = StyleManager.createTextField();
        formPanel.add(txtSemester, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(StyleManager.BG_CARD);
        JButton btnSimpan = StyleManager.createFlatBtn("Simpan", StyleManager.BTN_SUCCESS);
        JButton btnUbah = StyleManager.createFlatBtn("Ubah", StyleManager.BTN_INFO);
        JButton btnHapus = StyleManager.createFlatBtn("Hapus", StyleManager.BTN_DANGER);
        JButton btnClear = StyleManager.createFlatBtn("Clear", Color.GRAY);
        btnPanel.add(btnClear); btnPanel.add(btnSimpan); btnPanel.add(btnUbah); btnPanel.add(btnHapus);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        model = new DefaultTableModel(new String[]{"Kode MK", "Nama MK", "SKS", "Semester"}, 0);
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
                PreparedStatement pst = conn.prepareStatement("INSERT INTO matakuliah (kode_mk, nama_mk, sks, semester) VALUES (?, ?, ?, ?)");
                pst.setString(1, txtKodeMk.getText());
                pst.setString(2, txtNamaMk.getText());
                pst.setString(3, cbSks.getSelectedItem().toString());
                pst.setString(4, txtSemester.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Mata Kuliah Tersimpan");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnUbah.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("UPDATE matakuliah SET nama_mk=?, sks=?, semester=? WHERE kode_mk=?");
                pst.setString(1, txtNamaMk.getText());
                pst.setString(2, cbSks.getSelectedItem().toString());
                pst.setString(3, txtSemester.getText());
                pst.setString(4, txtKodeMk.getText());
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
                PreparedStatement pst = conn.prepareStatement("DELETE FROM matakuliah WHERE kode_mk=?");
                pst.setString(1, txtKodeMk.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Dihapus");
                loadData();
            } catch (Exception ex) {}
        });
        
        btnClear.addActionListener(e -> {
            txtKodeMk.setText(""); txtNamaMk.setText(""); txtSemester.setText("");
        });

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            Connection conn = Koneksi.configDB();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM matakuliah");
            while(rs.next()) {
                model.addRow(new Object[]{rs.getString("kode_mk"), rs.getString("nama_mk"), rs.getString("sks"), rs.getString("semester")});
            }
        } catch (Exception e) {}
    }
}
