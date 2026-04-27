package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FormKrs extends JPanel {
    public FormKrs() {
        setLayout(new BorderLayout(0, 20));
        setBackground(StyleManager.BG_PRIMARY);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("Transaksi KRS");
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
        formPanel.add(StyleManager.createLabel("Nama Mahasiswa"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        JTextField tfNama = StyleManager.createTextField();
        tfNama.setText("Ahmad Kurnia"); // Auto-fill mock
        tfNama.setEditable(false);
        formPanel.add(tfNama, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Kode MK"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        JComboBox<String> cbKodeMk = new JComboBox<>(new String[]{"TIF2401", "TIF2402", "TIF2403"});
        cbKodeMk.setFont(StyleManager.F_BODY);
        cbKodeMk.setBackground(StyleManager.BG_PRIMARY);
        cbKodeMk.setForeground(StyleManager.TEXT_PRIMARY);
        cbKodeMk.setBorder(BorderFactory.createLineBorder(StyleManager.BORDER));
        formPanel.add(cbKodeMk, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Mata Kuliah"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        JTextField tfMk = StyleManager.createTextField();
        tfMk.setText("Pemrograman Berorientasi Objek II"); // Auto-fill mock
        tfMk.setEditable(false);
        formPanel.add(tfMk, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        btnPanel.setBackground(StyleManager.BG_CARD);
        btnPanel.add(StyleManager.createFlatBtn("Hapus dari KRS", StyleManager.BTN_DANGER));
        btnPanel.add(StyleManager.createFlatBtn("Tambah ke KRS", StyleManager.ACCENT));

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.weightx = 1.0;
        formPanel.add(btnPanel, gbc);

        String[] cols = {"NIM", "Nama Mahasiswa", "Kode MK", "Nama Mata Kuliah", "SKS"};
        Object[][] data = {
                {"24552011297", "Ahmad Kurnia", "TIF2401", "Pemrograman Berorientasi Objek II", "3"},
                {"24552011297", "Ahmad Kurnia", "TIF2402", "Basis Data Lanjut", "3"}
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
