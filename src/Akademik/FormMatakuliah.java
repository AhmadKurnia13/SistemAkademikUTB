package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormMatakuliah extends JPanel {
    public FormMatakuliah() {
        setLayout(new BorderLayout(0, 20));
        setBackground(StyleManager.BG_PRIMARY);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("Data Mata Kuliah");
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
        formPanel.add(StyleManager.createLabel("Kode MK"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(StyleManager.createTextField(), gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Nama Mata Kuliah"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(StyleManager.createTextField(), gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("SKS"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        JComboBox<String> cbSks = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        cbSks.setFont(StyleManager.F_BODY);
        cbSks.setBackground(StyleManager.BG_PRIMARY);
        cbSks.setForeground(StyleManager.TEXT_PRIMARY);
        cbSks.setBorder(BorderFactory.createLineBorder(StyleManager.BORDER));
        formPanel.add(cbSks, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Semester"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        formPanel.add(StyleManager.createTextField(), gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        btnPanel.setBackground(StyleManager.BG_CARD);
        btnPanel.add(StyleManager.createFlatBtn("Clear", new Color(0x64748B)));
        btnPanel.add(StyleManager.createFlatBtn("Simpan", StyleManager.BTN_SUCCESS));
        btnPanel.add(StyleManager.createFlatBtn("Update", StyleManager.BTN_INFO));
        btnPanel.add(StyleManager.createFlatBtn("Hapus", StyleManager.BTN_DANGER));

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.weightx = 1.0;
        formPanel.add(btnPanel, gbc);

        String[] cols = {"Kode MK", "Nama Mata Kuliah", "SKS", "Semester"};
        Object[][] data = {
                {"TIF2401", "Pemrograman Berorientasi Objek II", "3", "4"},
                {"TIF2402", "Basis Data Lanjut", "3", "4"}
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
