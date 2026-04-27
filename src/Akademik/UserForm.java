package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserForm extends JInternalFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JComboBox<String> cbLevel;
    private JTable table;
    private DefaultTableModel model;

    public UserForm() {
        super("Data User", true, true, true, true);
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
        formPanel.add(StyleManager.createLabel("Username (ID User)"), gbc);
        gbc.gridx = 1;
        txtUser = StyleManager.createTextField();
        formPanel.add(txtUser, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(StyleManager.createLabel("Password"), gbc);
        gbc.gridx = 1;
        txtPass = StyleManager.createPasswordField();
        formPanel.add(txtPass, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(StyleManager.createLabel("Level"), gbc);
        gbc.gridx = 1;
        cbLevel = new JComboBox<>(new String[]{"admin", "operator"});
        cbLevel.setBackground(StyleManager.BG_PRIMARY);
        cbLevel.setForeground(StyleManager.TEXT_PRIMARY);
        formPanel.add(cbLevel, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(StyleManager.BG_CARD);
        JButton btnSimpan = StyleManager.createFlatBtn("Simpan", StyleManager.BTN_SUCCESS);
        JButton btnUbah = StyleManager.createFlatBtn("Ubah", StyleManager.BTN_INFO);
        JButton btnHapus = StyleManager.createFlatBtn("Hapus", StyleManager.BTN_DANGER);
        JButton btnClear = StyleManager.createFlatBtn("Clear", Color.GRAY);
        btnPanel.add(btnClear); btnPanel.add(btnSimpan); btnPanel.add(btnUbah); btnPanel.add(btnHapus);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        model = new DefaultTableModel(new String[]{"ID User", "Password", "Level"}, 0);
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
                PreparedStatement pst = conn.prepareStatement("INSERT INTO user (id_user, password, level) VALUES (?, ?, ?)");
                pst.setString(1, txtUser.getText());
                pst.setString(2, new String(txtPass.getPassword()));
                pst.setString(3, cbLevel.getSelectedItem().toString());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data User Tersimpan");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnUbah.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("UPDATE user SET password=?, level=? WHERE id_user=?");
                pst.setString(1, new String(txtPass.getPassword()));
                pst.setString(2, cbLevel.getSelectedItem().toString());
                pst.setString(3, txtUser.getText());
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
                PreparedStatement pst = conn.prepareStatement("DELETE FROM user WHERE id_user=?");
                pst.setString(1, txtUser.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Dihapus");
                loadData();
            } catch (Exception ex) {}
        });

        btnClear.addActionListener(e -> {
            txtUser.setText(""); txtPass.setText(""); cbLevel.setSelectedIndex(0);
        });

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            Connection conn = Koneksi.configDB();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM user");
            while(rs.next()) {
                model.addRow(new Object[]{rs.getString("id_user"), "********", rs.getString("level")});
            }
        } catch (Exception e) {}
    }
}
