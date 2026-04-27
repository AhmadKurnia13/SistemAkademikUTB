package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuUtama extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;

    public MenuUtama() {
        setTitle("AKADEMIK UTB - Main Menu");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLayout(new BorderLayout());
        
        // SIDEBAR
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(0x151521)); // Darker shade for sidebar
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setBorder(new EmptyBorder(30, 0, 30, 0));

        JLabel title = new JLabel("AKADEMIK UTB");
        title.setFont(StyleManager.F_TITLE);
        title.setForeground(StyleManager.ACCENT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(title);
        sidebar.add(Box.createVerticalStrut(40));

        // CENTER CARDS
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(StyleManager.BG_PRIMARY);

        // Add Forms
        cardPanel.add(createDashboard(), "Dashboard");
        cardPanel.add(new FormMahasiswa(), "Mahasiswa");
        cardPanel.add(new FormDosen(), "Dosen");
        cardPanel.add(new FormMatakuliah(), "Mata Kuliah");
        cardPanel.add(new FormKrs(), "KRS");
        cardPanel.add(new FormNilai(), "Nilai");
        cardPanel.add(new FormDataUser(), "Data User");
        cardPanel.add(new FormApprovalKrs(), "Approval KRS");
        cardPanel.add(new FormPengaturanPengguna(), "Pengaturan");

        // NAVIGATION BUTTONS
        java.util.List<String> navList = new java.util.ArrayList<>();
        navList.add("Dashboard");

        String level = Session.getLevel();
        if ("admin".equalsIgnoreCase(level)) {
            navList.add("Mahasiswa");
            navList.add("Dosen");
            navList.add("Mata Kuliah");
            navList.add("Pengaturan");
        } else if ("operator".equalsIgnoreCase(level)) {
            navList.add("KRS");
            navList.add("Nilai");
            navList.add("Pengaturan");
        } else {
            // Default fallback if level is empty
            navList.add("Pengaturan");
        }

        for (String nav : navList) {
            JButton btn = createNavBtn(nav);
            btn.addActionListener(e -> cardLayout.show(cardPanel, nav));
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(5));
        }

        sidebar.add(Box.createVerticalGlue());
        JButton btnLogout = createNavBtn("Logout");
        btnLogout.setForeground(StyleManager.BTN_DANGER);
        btnLogout.addActionListener(e -> {
            Session.clear();
            new FormLogin().setVisible(true);
            dispose();
        });
        sidebar.add(btnLogout);

        add(sidebar, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);
        add(StyleManager.createFooter(), BorderLayout.SOUTH);
    }

    private JButton createNavBtn(String text) {
        JButton btn = new JButton("   " + text);
        btn.setFont(StyleManager.F_BODY);
        btn.setForeground(StyleManager.TEXT_SECONDARY);
        btn.setBackground(new Color(0x151521));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(StyleManager.BG_CARD);
                if (!text.equals("Logout")) btn.setForeground(StyleManager.TEXT_PRIMARY);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(0x151521));
                if (!text.equals("Logout")) btn.setForeground(StyleManager.TEXT_SECONDARY);
            }
        });
        return btn;
    }

    private JPanel createDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(StyleManager.BG_PRIMARY);
        panel.setBorder(new EmptyBorder(40, 50, 40, 50));

        JLabel welcome = new JLabel("Dashboard Overview");
        welcome.setFont(StyleManager.F_TITLE);
        welcome.setForeground(StyleManager.TEXT_PRIMARY);
        panel.add(welcome, BorderLayout.NORTH);

        JPanel cardsPanel = new JPanel(new GridLayout(2, 2, 25, 25));
        cardsPanel.setBackground(StyleManager.BG_PRIMARY);
        cardsPanel.setBorder(new EmptyBorder(40, 0, 0, 0));

        cardsPanel.add(createStatCard("Total Mahasiswa", "21", StyleManager.ACCENT));
        cardsPanel.add(createStatCard("Sudah KRS", "1", StyleManager.BTN_SUCCESS));
        cardsPanel.add(createStatCard("Belum KRS", "20", StyleManager.BTN_WARNING));
        cardsPanel.add(createStatCard("Avg Nilai", "4.00", StyleManager.BTN_INFO));

        JPanel centerAlign = new JPanel(new BorderLayout());
        centerAlign.setBackground(StyleManager.BG_PRIMARY);
        centerAlign.add(cardsPanel, BorderLayout.NORTH);

        panel.add(centerAlign, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createStatCard(String title, String value, Color accent) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(StyleManager.BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(StyleManager.BORDER, 1),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 4, 0, 0, accent),
                        new EmptyBorder(25, 25, 25, 25)
                )
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(StyleManager.F_HEADER);
        lblTitle.setForeground(StyleManager.TEXT_SECONDARY);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("SansSerif", Font.BOLD, 42));
        lblValue.setForeground(StyleManager.TEXT_PRIMARY);

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        return card;
    }

    private JPanel createDummyPanel(String name) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(StyleManager.BG_PRIMARY);
        JLabel lbl = new JLabel(name + " Panel (Under Construction)");
        lbl.setFont(StyleManager.F_TITLE);
        lbl.setForeground(StyleManager.TEXT_SECONDARY);
        panel.add(lbl);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuUtama().setVisible(true));
    }
}
