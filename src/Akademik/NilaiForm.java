package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NilaiForm extends JInternalFrame {
    private JTextField txtNim, txtKodeMk;
    private JComboBox<String> cbNilai;
    private JTable table;
    private DefaultTableModel model;

    public NilaiForm() {
        super("Form Nilai Akademik", true, true, true, true);
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
        formPanel.add(StyleManager.createLabel("Kode MK"), gbc);
        gbc.gridx = 1;
        txtKodeMk = StyleManager.createTextField();
        formPanel.add(txtKodeMk, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(StyleManager.createLabel("Nilai"), gbc);
        gbc.gridx = 1;
        cbNilai = new JComboBox<>(new String[]{"A", "B", "C", "D", "E"});
        cbNilai.setBackground(StyleManager.BG_PRIMARY);
        cbNilai.setForeground(StyleManager.TEXT_PRIMARY);
        formPanel.add(cbNilai, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(StyleManager.BG_CARD);
        JButton btnSimpan = StyleManager.createFlatBtn("Simpan", StyleManager.BTN_SUCCESS);
        JButton btnUbah = StyleManager.createFlatBtn("Ubah", StyleManager.BTN_INFO);
        JButton btnHapus = StyleManager.createFlatBtn("Hapus", StyleManager.BTN_DANGER);
        JButton btnClear = StyleManager.createFlatBtn("Clear", Color.GRAY);
        btnPanel.add(btnClear); btnPanel.add(btnSimpan); btnPanel.add(btnUbah); btnPanel.add(btnHapus);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        model = new DefaultTableModel(new String[]{"NIM", "Kode MK", "Nilai Huruf"}, 0);
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
                PreparedStatement pst = conn.prepareStatement("INSERT INTO nilai (nim, kode_mk, nilai_huruf) VALUES (?, ?, ?)");
                pst.setString(1, txtNim.getText());
                pst.setString(2, txtKodeMk.getText());
                pst.setString(3, cbNilai.getSelectedItem().toString());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Tersimpan");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        
        btnUbah.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("UPDATE nilai SET nilai_huruf=? WHERE nim=? AND kode_mk=?");
                pst.setString(1, cbNilai.getSelectedItem().toString());
                pst.setString(2, txtNim.getText());
                pst.setString(3, txtKodeMk.getText());
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
                PreparedStatement pst = conn.prepareStatement("DELETE FROM nilai WHERE nim=? AND kode_mk=?");
                pst.setString(1, txtNim.getText());
                pst.setString(2, txtKodeMk.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Dihapus");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnClear.addActionListener(e -> {
            txtNim.setText(""); txtKodeMk.setText(""); cbNilai.setSelectedIndex(0);
        });

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            Connection conn = Koneksi.configDB();
            ResultSet rs = conn.createStatement().executeQuery("SELECT nim, kode_mk, nilai_huruf FROM nilai");
            while(rs.next()) {
                model.addRow(new Object[]{rs.getString("nim"), rs.getString("kode_mk"), rs.getString("nilai_huruf")});
            }
        } catch (Exception e) {}
    }
}
