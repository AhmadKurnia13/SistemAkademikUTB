package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FormDataUser extends JPanel {

    private JTextField tfIdUser, tfPassword;
    private JComboBox<String> cbLevel;
    private JTable table;
    private DefaultTableModel model;

    public FormDataUser() {
        setLayout(new BorderLayout(0, 20));
        setBackground(StyleManager.BG_PRIMARY);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("Data User");
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
        formPanel.add(StyleManager.createLabel("ID User"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        tfIdUser = StyleManager.createTextField();
        formPanel.add(tfIdUser, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Password"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        tfPassword = StyleManager.createTextField();
        formPanel.add(tfPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Level Akses"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        cbLevel = new JComboBox<>(new String[]{"admin", "operator"});
        cbLevel.setFont(StyleManager.F_BODY);
        cbLevel.setBackground(StyleManager.BG_PRIMARY);
        cbLevel.setForeground(StyleManager.TEXT_PRIMARY);
        cbLevel.setBorder(BorderFactory.createLineBorder(StyleManager.BORDER));
        formPanel.add(cbLevel, gbc);

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

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.weightx = 1.0;
        formPanel.add(btnPanel, gbc);

        // BOTTOM: Table
        model = new DefaultTableModel(new String[]{"ID User", "Password", "Level"}, 0);
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
                PreparedStatement pst = conn.prepareStatement("INSERT INTO user (id_user, password, level) VALUES (?, ?, ?)");
                pst.setString(1, tfIdUser.getText());
                pst.setString(2, tfPassword.getText());
                pst.setString(3, cbLevel.getSelectedItem().toString());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data User Berhasil Disimpan");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("UPDATE user SET password=?, level=? WHERE id_user=?");
                pst.setString(1, tfPassword.getText());
                pst.setString(2, cbLevel.getSelectedItem().toString());
                pst.setString(3, tfIdUser.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data User Berhasil Diubah");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("DELETE FROM user WHERE id_user=?");
                pst.setString(1, tfIdUser.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data User Dihapus");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnClear.addActionListener(e -> {
            tfIdUser.setText(""); tfPassword.setText(""); cbLevel.setSelectedIndex(0);
        });
        
        table.getSelectionModel().addListSelectionListener(event -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                tfIdUser.setText(model.getValueAt(row, 0).toString());
                tfPassword.setText(model.getValueAt(row, 1).toString());
                cbLevel.setSelectedItem(model.getValueAt(row, 2).toString());
            }
        });

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            Connection conn = Koneksi.configDB();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM user");
            while(rs.next()) {
                model.addRow(new Object[]{rs.getString("id_user"), rs.getString("password"), rs.getString("level")});
            }
        } catch (Exception e) {}
    }
}
