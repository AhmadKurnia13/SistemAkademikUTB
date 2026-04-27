package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FormDosen extends JPanel {

    private JTextField tfKodeDosen, tfNama, tfJabatan;
    private JTable table;
    private DefaultTableModel model;

    public FormDosen() {
        setLayout(new BorderLayout(0, 20));
        setBackground(StyleManager.BG_PRIMARY);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("Data Dosen");
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
        formPanel.add(StyleManager.createLabel("Kode Dosen"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        tfKodeDosen = StyleManager.createTextField();
        formPanel.add(tfKodeDosen, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Nama Dosen"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        tfNama = StyleManager.createTextField();
        formPanel.add(tfNama, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Jabatan"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        tfJabatan = StyleManager.createTextField();
        formPanel.add(tfJabatan, gbc);

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
        model = new DefaultTableModel(new String[]{"Kode Dosen", "Nama Dosen", "Jabatan"}, 0);
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
                PreparedStatement pst = conn.prepareStatement("INSERT INTO dosen (kode_dosen, nama_dosen, jabatan) VALUES (?, ?, ?)");
                pst.setString(1, tfKodeDosen.getText());
                pst.setString(2, tfNama.getText());
                pst.setString(3, tfJabatan.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Dosen Berhasil Disimpan");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("UPDATE dosen SET nama_dosen=?, jabatan=? WHERE kode_dosen=?");
                pst.setString(1, tfNama.getText());
                pst.setString(2, tfJabatan.getText());
                pst.setString(3, tfKodeDosen.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Dosen Berhasil Diubah");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("DELETE FROM dosen WHERE kode_dosen=?");
                pst.setString(1, tfKodeDosen.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Dosen Dihapus");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnClear.addActionListener(e -> {
            tfKodeDosen.setText(""); tfNama.setText(""); tfJabatan.setText("");
        });
        
        table.getSelectionModel().addListSelectionListener(event -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                tfKodeDosen.setText(model.getValueAt(row, 0).toString());
                tfNama.setText(model.getValueAt(row, 1).toString());
                tfJabatan.setText(model.getValueAt(row, 2).toString());
            }
        });

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            Connection conn = Koneksi.configDB();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM dosen");
            while(rs.next()) {
                model.addRow(new Object[]{rs.getString("kode_dosen"), rs.getString("nama_dosen"), rs.getString("jabatan")});
            }
        } catch (Exception e) {}
    }
}
