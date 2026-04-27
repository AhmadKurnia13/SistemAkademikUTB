package Akademik;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormLogin extends JFrame {

    public FormLogin() {
        setTitle("SIAKAD UTB - Login");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(StyleManager.BG_PRIMARY);

        // LEFT: Branding
        JPanel left = new JPanel(new GridBagLayout());
        left.setBackground(StyleManager.ACCENT);
        left.setPreferredSize(new Dimension(400, 0));

        ImageIcon logo = StyleManager.getScaledIcon("/img/logo_utb.png", 200, 200);
        JLabel lblLogo = new JLabel();
        if (logo != null) lblLogo.setIcon(logo);
        else {
            lblLogo.setText("LOGO UTB");
            lblLogo.setFont(new Font("SansSerif", Font.BOLD, 36));
            lblLogo.setForeground(Color.WHITE);
        }

        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.gridx = 0; gbcLeft.gridy = 0;
        gbcLeft.insets = new Insets(0, 0, 20, 0);
        left.add(lblLogo, gbcLeft);

        gbcLeft.gridy++;
        JLabel brandTitle = new JLabel("AKADEMIK UTB");
        brandTitle.setFont(StyleManager.F_TITLE);
        brandTitle.setForeground(Color.WHITE);
        left.add(brandTitle, gbcLeft);

        // RIGHT: Login Form
        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(StyleManager.BG_PRIMARY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 30, 10, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        JLabel title = new JLabel("Selamat Datang!");
        title.setFont(StyleManager.F_TITLE);
        title.setForeground(StyleManager.TEXT_PRIMARY);
        right.add(title, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 30, 30, 30);
        JLabel sub = new JLabel("Masuk ke dashboard akademik");
        sub.setFont(StyleManager.F_BODY);
        sub.setForeground(StyleManager.TEXT_SECONDARY);
        right.add(sub, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 30, 5, 30);
        right.add(StyleManager.createLabel("Username"), gbc);

        gbc.gridy++;
        JTextField tfUser = StyleManager.createTextField();
        tfUser.setPreferredSize(new Dimension(300, 45));
        right.add(tfUser, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(15, 30, 5, 30);
        right.add(StyleManager.createLabel("Password"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 30, 30, 30);
        JPasswordField pfPass = StyleManager.createPasswordField();
        pfPass.setPreferredSize(new Dimension(300, 45));
        right.add(pfPass, gbc);

        gbc.gridy++;
        JButton btnLogin = StyleManager.createFlatBtn("Masuk ke Dashboard", StyleManager.ACCENT);
        btnLogin.setPreferredSize(new Dimension(300, 48));
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    java.sql.Connection conn = Koneksi.configDB();
                    String sql = "SELECT * FROM user WHERE id_user=? AND password=?";
                    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setString(1, tfUser.getText());
                    pst.setString(2, new String(pfPass.getPassword()));
                    java.sql.ResultSet rs = pst.executeQuery();
                    
                    if (rs.next()) {
                        Session.setIdUser(rs.getString("id_user"));
                        Session.setLevel(rs.getString("level"));
                        new MenuUtama().setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Username atau Password Salah!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());
                }
            }
        });
        right.add(btnLogin, gbc);

        panel.add(left, BorderLayout.WEST);
        panel.add(right, BorderLayout.CENTER);
        
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(StyleManager.createFooter(), BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormLogin().setVisible(true));
    }
}
