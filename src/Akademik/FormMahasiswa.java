package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FormMahasiswa extends JPanel {

    private JTextField tfNim, tfNama, tfAlamat;
    private JComboBox<String> cbJurusan;
    private JTable table;
    private DefaultTableModel model;

    public FormMahasiswa() {
        setLayout(new BorderLayout(0, 20));
        setBackground(StyleManager.BG_PRIMARY);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("Data Mahasiswa");
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
        formPanel.add(StyleManager.createLabel("NIM"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        tfNim = StyleManager.createTextField();
        formPanel.add(tfNim, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Nama"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        tfNama = StyleManager.createTextField();
        formPanel.add(tfNama, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Alamat"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        tfAlamat = StyleManager.createTextField();
        formPanel.add(tfAlamat, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Jurusan"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        cbJurusan = new JComboBox<>(new String[]{"Teknik Informatika", "Sistem Informasi", "Teknik Elektro"});
        cbJurusan.setFont(StyleManager.F_BODY);
        cbJurusan.setBackground(StyleManager.BG_PRIMARY);
        cbJurusan.setForeground(StyleManager.TEXT_PRIMARY);
        cbJurusan.setBorder(BorderFactory.createLineBorder(StyleManager.BORDER));
        formPanel.add(cbJurusan, gbc);

        // MIDDLE: Action Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        btnPanel.setBackground(StyleManager.BG_CARD);
        JButton btnClear = StyleManager.createFlatBtn("Clear", new Color(0x64748B));
        JButton btnCreate = StyleManager.createFlatBtn("Create", StyleManager.BTN_SUCCESS);
        JButton btnUpdate = StyleManager.createFlatBtn("Update", StyleManager.BTN_INFO);
        JButton btnDelete = StyleManager.createFlatBtn("Delete", StyleManager.BTN_DANGER);
        btnPanel.add(btnClear);
        btnPanel.add(btnCreate);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.weightx = 1.0;
        formPanel.add(btnPanel, gbc);

        // BOTTOM: Table
        model = new DefaultTableModel(new String[]{"NIM", "Nama", "Alamat", "Jurusan"}, 0);
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
                PreparedStatement pst = conn.prepareStatement("INSERT INTO mahasiswa (nim, nama, alamat, jurusan) VALUES (?, ?, ?, ?)");
                pst.setString(1, tfNim.getText());
                pst.setString(2, tfNama.getText());
                pst.setString(3, tfAlamat.getText());
                pst.setString(4, cbJurusan.getSelectedItem().toString());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Mahasiswa Berhasil Disimpan");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("UPDATE mahasiswa SET nama=?, alamat=?, jurusan=? WHERE nim=?");
                pst.setString(1, tfNama.getText());
                pst.setString(2, tfAlamat.getText());
                pst.setString(3, cbJurusan.getSelectedItem().toString());
                pst.setString(4, tfNim.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Mahasiswa Berhasil Diubah");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("DELETE FROM mahasiswa WHERE nim=?");
                pst.setString(1, tfNim.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Mahasiswa Dihapus");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnClear.addActionListener(e -> {
            tfNim.setText(""); tfNama.setText(""); tfAlamat.setText(""); cbJurusan.setSelectedIndex(0);
        });
        
        table.getSelectionModel().addListSelectionListener(event -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                tfNim.setText(model.getValueAt(row, 0).toString());
                tfNama.setText(model.getValueAt(row, 1).toString());
                tfAlamat.setText(model.getValueAt(row, 2).toString());
                cbJurusan.setSelectedItem(model.getValueAt(row, 3).toString());
            }
        });

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            Connection conn = Koneksi.configDB();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM mahasiswa");
            while(rs.next()) {
                model.addRow(new Object[]{rs.getString("nim"), rs.getString("nama"), rs.getString("alamat"), rs.getString("jurusan")});
            }
        } catch (Exception e) {}
    }
}
