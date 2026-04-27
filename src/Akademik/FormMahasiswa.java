package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormMahasiswa extends JFrame {

    public FormMahasiswa() {
        setTitle("SIAKAD UTB - Data Mahasiswa");
        setSize(900, 600);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(StyleManager.BG_PRIMARY);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));

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
        JTextField tfNim = StyleManager.createTextField();
        formPanel.add(tfNim, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Nama"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        JTextField tfNama = StyleManager.createTextField();
        formPanel.add(tfNama, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Jurusan"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        JComboBox<String> cbJurusan = new JComboBox<>(new String[]{"Teknik Informatika", "Sistem Informasi", "Teknik Elektro"});
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

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.weightx = 1.0;
        formPanel.add(btnPanel, gbc);

        // BOTTOM: Table
        String[] cols = {"NIM", "Nama", "Jurusan"};
        Object[][] data = {
                {"24552011297", "Ahmad Kurnia", "Teknik Informatika"},
                {"24552011298", "Budi Santoso", "Sistem Informasi"},
                {"24552011299", "Citra Kirana", "Teknik Elektro"}
        };
        DefaultTableModel model = new DefaultTableModel(data, cols);
        JTable table = new JTable(model);
        StyleManager.styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(StyleManager.BORDER));
        scroll.getViewport().setBackground(StyleManager.BG_CARD);

        JPanel topHalf = new JPanel(new BorderLayout(0, 20));
        topHalf.setBackground(StyleManager.BG_PRIMARY);
        topHalf.add(title, BorderLayout.NORTH);
        topHalf.add(formPanel, BorderLayout.CENTER);

        panel.add(topHalf, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormMahasiswa().setVisible(true));
    }
}
