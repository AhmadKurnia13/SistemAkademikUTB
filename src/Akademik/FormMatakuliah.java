package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FormMatakuliah extends JPanel {

    private JTextField tfKodeMk, tfNamaMk, tfSemester;
    private JComboBox<String> cbSks;
    private JTable table;
    private DefaultTableModel model;

    public FormMatakuliah() {
        setLayout(new BorderLayout(0, 20));
        setBackground(StyleManager.BG_PRIMARY);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("Data Mata Kuliah");
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
        formPanel.add(StyleManager.createLabel("Kode MK"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        tfKodeMk = StyleManager.createTextField();
        formPanel.add(tfKodeMk, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Nama Mata Kuliah"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        tfNamaMk = StyleManager.createTextField();
        formPanel.add(tfNamaMk, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("SKS"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        cbSks = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6"});
        cbSks.setFont(StyleManager.F_BODY);
        cbSks.setBackground(StyleManager.BG_PRIMARY);
        cbSks.setForeground(StyleManager.TEXT_PRIMARY);
        cbSks.setBorder(BorderFactory.createLineBorder(StyleManager.BORDER));
        formPanel.add(cbSks, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Semester"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        tfSemester = StyleManager.createTextField();
        formPanel.add(tfSemester, gbc);

        // MIDDLE: Action Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        btnPanel.setBackground(StyleManager.BG_CARD);
        JButton btnClear = StyleManager.createFlatBtn("Clear", new Color(0x64748B));
        JButton btnCreate = StyleManager.createFlatBtn("Create", StyleManager.BTN_SUCCESS);
        JButton btnUpdate = StyleManager.createFlatBtn("Update", StyleManager.BTN_INFO);
        JButton btnDelete = StyleManager.createFlatBtn("Delete", StyleManager.BTN_DANGER);
        btnPanel.add(btnClear); btnPanel.add(btnCreate); btnPanel.add(btnUpdate); btnPanel.add(btnDelete);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.weightx = 1.0;
        formPanel.add(btnPanel, gbc);

        // BOTTOM: Table
        model = new DefaultTableModel(new String[]{"Kode MK", "Nama MK", "SKS", "Semester"}, 0);
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
                PreparedStatement pst = conn.prepareStatement("INSERT INTO matakuliah (kode_mk, nama_mk, sks, semester) VALUES (?, ?, ?, ?)");
                pst.setString(1, tfKodeMk.getText());
                pst.setString(2, tfNamaMk.getText());
                pst.setString(3, cbSks.getSelectedItem().toString());
                pst.setString(4, tfSemester.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Mata Kuliah Berhasil Disimpan");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("UPDATE matakuliah SET nama_mk=?, sks=?, semester=? WHERE kode_mk=?");
                pst.setString(1, tfNamaMk.getText());
                pst.setString(2, cbSks.getSelectedItem().toString());
                pst.setString(3, tfSemester.getText());
                pst.setString(4, tfKodeMk.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Mata Kuliah Berhasil Diubah");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("DELETE FROM matakuliah WHERE kode_mk=?");
                pst.setString(1, tfKodeMk.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Mata Kuliah Dihapus");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnClear.addActionListener(e -> {
            tfKodeMk.setText(""); tfNamaMk.setText(""); tfSemester.setText("");
        });
        
        table.getSelectionModel().addListSelectionListener(event -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                tfKodeMk.setText(model.getValueAt(row, 0).toString());
                tfNamaMk.setText(model.getValueAt(row, 1).toString());
                cbSks.setSelectedItem(model.getValueAt(row, 2).toString());
                tfSemester.setText(model.getValueAt(row, 3).toString());
            }
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
