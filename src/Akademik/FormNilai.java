package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormNilai extends JPanel {
    public FormNilai() {
        setLayout(new BorderLayout(0, 20));
        setBackground(StyleManager.BG_PRIMARY);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("Input Nilai Akademik");
        title.setFont(StyleManager.F_TITLE);
        title.setForeground(StyleManager.TEXT_PRIMARY);

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
        formPanel.add(StyleManager.createLabel("NIM Mahasiswa"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(StyleManager.createTextField(), gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Kode MK"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(StyleManager.createTextField(), gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Nilai Huruf"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        
        // CRITICAL REQUIREMENT: Nilai Huruf MUST be a JComboBox containing ONLY: A, B, C, D, E.
        JComboBox<String> cbNilai = new JComboBox<>(new String[]{"A", "B", "C", "D", "E"});
        cbNilai.setFont(StyleManager.F_BODY);
        cbNilai.setBackground(StyleManager.BG_PRIMARY);
        cbNilai.setForeground(StyleManager.TEXT_PRIMARY);
        cbNilai.setBorder(BorderFactory.createLineBorder(StyleManager.BORDER));
        formPanel.add(cbNilai, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        btnPanel.setBackground(StyleManager.BG_CARD);
        btnPanel.add(StyleManager.createFlatBtn("Update Nilai", StyleManager.BTN_INFO));
        btnPanel.add(StyleManager.createFlatBtn("Input Nilai", StyleManager.ACCENT));

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.weightx = 1.0;
        formPanel.add(btnPanel, gbc);

        String[] cols = {"NIM", "Kode MK", "Nilai Huruf"};
        Object[][] data = {
                {"24552011297", "TIF2401", "A"},
                {"24552011297", "TIF2402", "B"}
        };
        JTable table = new JTable(new DefaultTableModel(data, cols));
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
    }
}
