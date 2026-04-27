package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FormNilai extends JPanel {

    private JTextField tfNim, tfKodeMk;
    private JComboBox<String> cbNilai;
    private JTable table;
    private DefaultTableModel model;

    public FormNilai() {
        setLayout(new BorderLayout(0, 20));
        setBackground(StyleManager.BG_PRIMARY);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("Form Nilai Akademik");
        title.setFont(StyleManager.F_TITLE);
        title.setForeground(StyleManager.TEXT_PRIMARY);

        // TOP: Form Inputs
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(StyleManager.BG_CARD);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(StyleManager.BORDER),
                new EmptyBorder(25, 25, 25, 25)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(StyleManager.createLabel("NIM Mahasiswa"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        tfNim = StyleManager.createTextField();
        formPanel.add(tfNim, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Kode Mata Kuliah"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        tfKodeMk = StyleManager.createTextField();
        formPanel.add(tfKodeMk, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Nilai Huruf"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        cbNilai = new JComboBox<>(new String[]{"A", "B", "C", "D", "E"});
        cbNilai.setFont(StyleManager.F_BODY);
        cbNilai.setBackground(StyleManager.BG_PRIMARY);
        cbNilai.setForeground(StyleManager.TEXT_PRIMARY);
        cbNilai.setBorder(BorderFactory.createLineBorder(StyleManager.BORDER));
        formPanel.add(cbNilai, gbc);

        // MIDDLE: Action Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        btnPanel.setBackground(StyleManager.BG_CARD);
        JButton btnClear = StyleManager.createFlatBtn("Clear", new Color(0x64748B));
        JButton btnCreate = StyleManager.createFlatBtn("Create", StyleManager.BTN_SUCCESS);
        JButton btnUpdate = StyleManager.createFlatBtn("Update", StyleManager.BTN_INFO);
        JButton btnDelete = StyleManager.createFlatBtn("Delete", StyleManager.BTN_DANGER);
        btnPanel.add(btnClear); btnPanel.add(btnCreate); btnPanel.add(btnUpdate); btnPanel.add(btnDelete);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.weightx = 1.0;
        formPanel.add(btnPanel, gbc);

        // BOTTOM: Table
        model = new DefaultTableModel(new String[]{"NIM", "Kode MK", "Nilai Huruf"}, 0);
        table = new JTable(model);
        StyleManager.styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(StyleManager.BORDER));
        scroll.getViewport().setBackground(StyleManager.BG_CARD);

        JPanel topHalf = new JPanel(new BorderLayout(0, 20));
        topHalf.setBackground(StyleManager.BG_PRIMARY);
        topHalf.add(title, BorderLayout.NORTH);
        topHalf.add(formPanel, BorderLayout.CENTER);

        add(topHalf, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // ACTION LISTENERS (DATABASE CRUD)
        btnCreate.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("INSERT INTO nilai (nim, kode_mk, nilai_huruf) VALUES (?, ?, ?)");
                pst.setString(1, tfNim.getText());
                pst.setString(2, tfKodeMk.getText());
                pst.setString(3, cbNilai.getSelectedItem().toString());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Nilai Berhasil Disimpan");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("UPDATE nilai SET nilai_huruf=? WHERE nim=? AND kode_mk=?");
                pst.setString(1, cbNilai.getSelectedItem().toString());
                pst.setString(2, tfNim.getText());
                pst.setString(3, tfKodeMk.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Nilai Berhasil Diubah");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("DELETE FROM nilai WHERE nim=? AND kode_mk=?");
                pst.setString(1, tfNim.getText());
                pst.setString(2, tfKodeMk.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Nilai Dihapus");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnClear.addActionListener(e -> {
            tfNim.setText(""); tfKodeMk.setText(""); cbNilai.setSelectedIndex(0);
        });

        table.getSelectionModel().addListSelectionListener(event -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                tfNim.setText(model.getValueAt(row, 0).toString());
                tfKodeMk.setText(model.getValueAt(row, 1).toString());
                cbNilai.setSelectedItem(model.getValueAt(row, 2).toString());
            }
        });

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            Connection conn = Koneksi.configDB();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM nilai");
            while(rs.next()) {
                model.addRow(new Object[]{rs.getString("nim"), rs.getString("kode_mk"), rs.getString("nilai_huruf")});
            }
        } catch (Exception e) {}
    }
}
