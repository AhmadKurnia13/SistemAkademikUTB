package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ChangePasswordForm extends JInternalFrame {
    private JTextField txtUser;
    private JPasswordField txtPassLama, txtPassBaru;

    public ChangePasswordForm() {
        super("Ganti Password", true, true, true, true);
        setSize(500, 400);
        setLayout(new BorderLayout());
        getContentPane().setBackground(StyleManager.BG_PRIMARY);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(StyleManager.BG_CARD);
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(StyleManager.createLabel("Username"), gbc);
        gbc.gridx = 1;
        txtUser = StyleManager.createTextField();
        formPanel.add(txtUser, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(StyleManager.createLabel("Password Lama"), gbc);
        gbc.gridx = 1;
        txtPassLama = StyleManager.createPasswordField();
        formPanel.add(txtPassLama, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(StyleManager.createLabel("Password Baru"), gbc);
        gbc.gridx = 1;
        txtPassBaru = StyleManager.createPasswordField();
        formPanel.add(txtPassBaru, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(StyleManager.BG_CARD);
        JButton btnUpdate = StyleManager.createFlatBtn("Update Password", StyleManager.BTN_INFO);
        btnPanel.add(btnUpdate);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        btnUpdate.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                String sqlCek = "SELECT * FROM user WHERE id_user=? AND password=?";
                PreparedStatement pstCek = conn.prepareStatement(sqlCek);
                pstCek.setString(1, txtUser.getText());
                pstCek.setString(2, new String(txtPassLama.getPassword()));
                ResultSet rs = pstCek.executeQuery();
                
                if(rs.next()){
                    String sqlUpdate = "UPDATE user SET password=? WHERE id_user=?";
                    PreparedStatement pstUpdate = conn.prepareStatement(sqlUpdate);
                    pstUpdate.setString(1, new String(txtPassBaru.getPassword()));
                    pstUpdate.setString(2, txtUser.getText());
                    pstUpdate.execute();
                    JOptionPane.showMessageDialog(this, "Password Berhasil Diubah!");
                } else {
                    JOptionPane.showMessageDialog(this, "Password Lama Salah!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
    }
}
