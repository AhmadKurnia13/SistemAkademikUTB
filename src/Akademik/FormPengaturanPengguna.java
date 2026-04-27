package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FormPengaturanPengguna extends JPanel {

    private JTextField tfNewUserId, tfNewUserPass, tfTargetUserId;
    private JComboBox<String> cbTargetUserId, cbLevelAkses;
    private JPasswordField pfNewPass;

    public FormPengaturanPengguna() {
        setLayout(new BorderLayout(20, 20));
        setBackground(StyleManager.BG_PRIMARY);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("Pengaturan Pengguna (Ganti Password & Tambah User)");
        title.setFont(StyleManager.F_TITLE);
        title.setForeground(StyleManager.TEXT_PRIMARY);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        mainPanel.setBackground(StyleManager.BG_PRIMARY);

        // LEFT: Change Password
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(StyleManager.BG_CARD);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(StyleManager.BORDER),
                new EmptyBorder(25, 25, 25, 25)
        ));

        JLabel lblChangePass = new JLabel("Ganti Password User");
        lblChangePass.setFont(StyleManager.F_HEADER);
        lblChangePass.setForeground(StyleManager.ACCENT);
        leftPanel.add(lblChangePass, BorderLayout.NORTH);

        JPanel formChangePass = new JPanel(new GridBagLayout());
        formChangePass.setBackground(StyleManager.BG_CARD);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        gbc.gridx = 0; gbc.gridy = 0;
        formChangePass.add(StyleManager.createLabel("Pilih ID User"), gbc);
        gbc.gridy = 1;
        
        // We will use a JComboBox loaded from DB for Target User
        cbTargetUserId = new JComboBox<>();
        cbTargetUserId.setFont(StyleManager.F_BODY);
        cbTargetUserId.setBackground(StyleManager.BG_PRIMARY);
        cbTargetUserId.setForeground(StyleManager.TEXT_PRIMARY);
        cbTargetUserId.setBorder(BorderFactory.createLineBorder(StyleManager.BORDER));
        loadUsersToCombo();
        formChangePass.add(cbTargetUserId, gbc);
        
        gbc.gridy = 2;
        formChangePass.add(StyleManager.createLabel("Password Baru"), gbc);
        gbc.gridy = 3;
        pfNewPass = StyleManager.createPasswordField();
        formChangePass.add(pfNewPass, gbc);

        gbc.gridy = 4;
        JButton btnChangePass = StyleManager.createFlatBtn("Update Password", StyleManager.BTN_INFO);
        formChangePass.add(btnChangePass, gbc);
        
        leftPanel.add(formChangePass, BorderLayout.CENTER);

        // RIGHT: Add User
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(StyleManager.BG_CARD);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(StyleManager.BORDER),
                new EmptyBorder(25, 25, 25, 25)
        ));

        JLabel lblAddUser = new JLabel("Tambah User Baru");
        lblAddUser.setFont(StyleManager.F_HEADER);
        lblAddUser.setForeground(StyleManager.BTN_SUCCESS);
        rightPanel.add(lblAddUser, BorderLayout.NORTH);

        JPanel formAddUser = new JPanel(new GridBagLayout());
        formAddUser.setBackground(StyleManager.BG_CARD);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formAddUser.add(StyleManager.createLabel("ID User Baru"), gbc);
        gbc.gridy = 1;
        tfNewUserId = StyleManager.createTextField();
        formAddUser.add(tfNewUserId, gbc);

        gbc.gridy = 2;
        formAddUser.add(StyleManager.createLabel("Password Baru"), gbc);
        gbc.gridy = 3;
        tfNewUserPass = StyleManager.createTextField();
        formAddUser.add(tfNewUserPass, gbc);
        
        gbc.gridy = 4;
        formAddUser.add(StyleManager.createLabel("Level Akses"), gbc);
        gbc.gridy = 5;
        cbLevelAkses = new JComboBox<>(new String[]{"admin", "operator"});
        cbLevelAkses.setFont(StyleManager.F_BODY);
        cbLevelAkses.setBackground(StyleManager.BG_PRIMARY);
        cbLevelAkses.setForeground(StyleManager.TEXT_PRIMARY);
        cbLevelAkses.setBorder(BorderFactory.createLineBorder(StyleManager.BORDER));
        formAddUser.add(cbLevelAkses, gbc);

        gbc.gridy = 6;
        JButton btnAddUser = StyleManager.createFlatBtn("Simpan User Baru", StyleManager.BTN_SUCCESS);
        formAddUser.add(btnAddUser, gbc);

        rightPanel.add(formAddUser, BorderLayout.CENTER);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(title, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // Actions
        btnChangePass.addActionListener(e -> {
            if (cbTargetUserId.getSelectedItem() == null) return;
            String targetUser = cbTargetUserId.getSelectedItem().toString();
            String newP = new String(pfNewPass.getPassword());
            
            if (newP.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password baru tidak boleh kosong!");
                return;
            }
            
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement update = conn.prepareStatement("UPDATE user SET password=? WHERE id_user=?");
                update.setString(1, newP);
                update.setString(2, targetUser);
                update.executeUpdate();
                JOptionPane.showMessageDialog(this, "Password user " + targetUser + " berhasil diubah!");
                pfNewPass.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnAddUser.addActionListener(e -> {
            try {
                String id = tfNewUserId.getText();
                String pass = tfNewUserPass.getText();
                String lvl = cbLevelAkses.getSelectedItem().toString();
                
                if (id.trim().isEmpty() || pass.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "ID dan Password tidak boleh kosong!");
                    return;
                }
                
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("INSERT INTO user (id_user, password, level) VALUES (?, ?, ?)");
                pst.setString(1, id);
                pst.setString(2, pass);
                pst.setString(3, lvl);
                pst.executeUpdate();
                
                JOptionPane.showMessageDialog(this, "User " + lvl + " Baru Berhasil Ditambahkan!");
                tfNewUserId.setText("");
                tfNewUserPass.setText("");
                
                loadUsersToCombo(); // Refresh dropdown
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan user (ID mungkin sudah ada): " + ex.getMessage());
            }
        });
    }
    
    private void loadUsersToCombo() {
        try {
            cbTargetUserId.removeAllItems();
            Connection conn = Koneksi.configDB();
            ResultSet rs = conn.createStatement().executeQuery("SELECT id_user FROM user");
            while(rs.next()) {
                cbTargetUserId.addItem(rs.getString("id_user"));
            }
        } catch(Exception e) {
            // ignore
        }
    }
}
