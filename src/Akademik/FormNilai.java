package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FormNilai extends JPanel {

    private JTextField tfNim, tfKodeMk;
    private JTextField tfAbsensi, tfTugas, tfQuiz, tfUts, tfUas;
    private JTable table;
    private DefaultTableModel model;

    public FormNilai() {
        setLayout(new BorderLayout(0, 20));
        setBackground(StyleManager.BG_PRIMARY);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        initDatabaseSchema();

        JLabel title = new JLabel("Pengelolaan Nilai Komprehensif");
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
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Row 0
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(StyleManager.createLabel("NIM Mahasiswa"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        tfNim = StyleManager.createTextField();
        formPanel.add(tfNim, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Absensi (10%)"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        tfAbsensi = StyleManager.createTextField();
        tfAbsensi.setText("0");
        formPanel.add(tfAbsensi, gbc);

        // Row 1
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Kode Mata Kuliah"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        tfKodeMk = StyleManager.createTextField();
        formPanel.add(tfKodeMk, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Tugas (20%)"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        tfTugas = StyleManager.createTextField();
        tfTugas.setText("0");
        formPanel.add(tfTugas, gbc);

        // Row 2
        gbc.gridx = 2; gbc.gridy = 2; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("Quiz (10%)"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        tfQuiz = StyleManager.createTextField();
        tfQuiz.setText("0");
        formPanel.add(tfQuiz, gbc);

        // Row 3
        gbc.gridx = 2; gbc.gridy = 3; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("UTS (30%)"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        tfUts = StyleManager.createTextField();
        tfUts.setText("0");
        formPanel.add(tfUts, gbc);
        
        // Row 4
        gbc.gridx = 2; gbc.gridy = 4; gbc.weightx = 0;
        formPanel.add(StyleManager.createLabel("UAS (30%)"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0;
        tfUas = StyleManager.createTextField();
        tfUas.setText("0");
        formPanel.add(tfUas, gbc);

        // MIDDLE: Action Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        btnPanel.setBackground(StyleManager.BG_CARD);
        JButton btnClear = StyleManager.createFlatBtn("Clear", new Color(0x64748B));
        JButton btnCreate = StyleManager.createFlatBtn("Simpan Nilai", StyleManager.BTN_SUCCESS);
        JButton btnUpdate = StyleManager.createFlatBtn("Update", StyleManager.BTN_INFO);
        JButton btnDelete = StyleManager.createFlatBtn("Delete", StyleManager.BTN_DANGER);
        btnPanel.add(btnClear); btnPanel.add(btnCreate); btnPanel.add(btnUpdate); btnPanel.add(btnDelete);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 4; gbc.weightx = 1.0;
        formPanel.add(btnPanel, gbc);

        // BOTTOM: Table
        model = new DefaultTableModel(new String[]{"NIM", "Kode MK", "Absensi", "Tugas", "Quiz", "UTS", "UAS", "Akhir", "Grade", "Status"}, 0);
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
                double[] vals = parseAndCalculate();
                if (vals == null) return;
                double abs = vals[0], tug = vals[1], qz = vals[2], uts = vals[3], uas = vals[4], akhir = vals[5];
                String grade = determineGrade(akhir);
                String status = determineStatus(grade);

                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("INSERT INTO nilai (nim, kode_mk, absensi, tugas, quiz, uts, uas, nilai_akhir, grade, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                pst.setString(1, tfNim.getText());
                pst.setString(2, tfKodeMk.getText());
                pst.setDouble(3, abs);
                pst.setDouble(4, tug);
                pst.setDouble(5, qz);
                pst.setDouble(6, uts);
                pst.setDouble(7, uas);
                pst.setDouble(8, akhir);
                pst.setString(9, grade);
                pst.setString(10, status);
                
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Nilai Berhasil Disimpan\nGrade: " + grade + "\nStatus: " + status);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error Insert: " + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                double[] vals = parseAndCalculate();
                if (vals == null) return;
                double abs = vals[0], tug = vals[1], qz = vals[2], uts = vals[3], uas = vals[4], akhir = vals[5];
                String grade = determineGrade(akhir);
                String status = determineStatus(grade);

                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("UPDATE nilai SET absensi=?, tugas=?, quiz=?, uts=?, uas=?, nilai_akhir=?, grade=?, status=? WHERE nim=? AND kode_mk=?");
                pst.setDouble(1, abs);
                pst.setDouble(2, tug);
                pst.setDouble(3, qz);
                pst.setDouble(4, uts);
                pst.setDouble(5, uas);
                pst.setDouble(6, akhir);
                pst.setString(7, grade);
                pst.setString(8, status);
                pst.setString(9, tfNim.getText());
                pst.setString(10, tfKodeMk.getText());
                
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Nilai Berhasil Diubah\nGrade: " + grade + "\nStatus: " + status);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error Update: " + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("DELETE FROM nilai WHERE nim=? AND kode_mk=?");
                pst.setString(1, tfNim.getText());
                pst.setString(2, tfKodeMk.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data Nilai Dihapus");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error Delete: " + ex.getMessage());
            }
        });

        btnClear.addActionListener(e -> {
            tfNim.setText(""); tfKodeMk.setText("");
            tfAbsensi.setText("0"); tfTugas.setText("0"); tfQuiz.setText("0"); tfUts.setText("0"); tfUas.setText("0");
        });

        table.getSelectionModel().addListSelectionListener(event -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                tfNim.setText(model.getValueAt(row, 0).toString());
                tfKodeMk.setText(model.getValueAt(row, 1).toString());
                tfAbsensi.setText(model.getValueAt(row, 2).toString());
                tfTugas.setText(model.getValueAt(row, 3).toString());
                tfQuiz.setText(model.getValueAt(row, 4).toString());
                tfUts.setText(model.getValueAt(row, 5).toString());
                tfUas.setText(model.getValueAt(row, 6).toString());
            }
        });

        loadData();
    }
    
    private double[] parseAndCalculate() {
        try {
            double abs = Double.parseDouble(tfAbsensi.getText());
            double tug = Double.parseDouble(tfTugas.getText());
            double qz = Double.parseDouble(tfQuiz.getText());
            double uts = Double.parseDouble(tfUts.getText());
            double uas = Double.parseDouble(tfUas.getText());
            
            double akhir = (abs * 0.1) + (tug * 0.2) + (qz * 0.1) + (uts * 0.3) + (uas * 0.3);
            return new double[]{abs, tug, qz, uts, uas, akhir};
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Input komponen nilai harus berupa angka!");
            return null;
        }
    }
    
    private String determineGrade(double akhir) {
        if (akhir >= 85) return "A";
        if (akhir >= 75) return "B";
        if (akhir >= 65) return "C";
        if (akhir >= 50) return "D";
        return "E";
    }
    
    private String determineStatus(String grade) {
        if (grade.equals("A") || grade.equals("B") || grade.equals("C")) {
            return "Lulus";
        }
        return "Tidak Lulus";
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            Connection conn = Koneksi.configDB();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM nilai");
            while(rs.next()) {
                // Handle potential nulls if records were created before schema change
                String abs = rs.getString("absensi") != null ? rs.getString("absensi") : "0";
                String tug = rs.getString("tugas") != null ? rs.getString("tugas") : "0";
                String qz = rs.getString("quiz") != null ? rs.getString("quiz") : "0";
                String uts = rs.getString("uts") != null ? rs.getString("uts") : "0";
                String uas = rs.getString("uas") != null ? rs.getString("uas") : "0";
                String akhir = rs.getString("nilai_akhir") != null ? rs.getString("nilai_akhir") : "0";
                String gr = rs.getString("grade") != null ? rs.getString("grade") : "-";
                String st = rs.getString("status") != null ? rs.getString("status") : "-";
                
                model.addRow(new Object[]{rs.getString("nim"), rs.getString("kode_mk"), abs, tug, qz, uts, uas, akhir, gr, st});
            }
        } catch (Exception e) {}
    }
    
    private void initDatabaseSchema() {
        String[] columns = {
            "ADD COLUMN absensi DOUBLE DEFAULT 0",
            "ADD COLUMN tugas DOUBLE DEFAULT 0",
            "ADD COLUMN quiz DOUBLE DEFAULT 0",
            "ADD COLUMN uts DOUBLE DEFAULT 0",
            "ADD COLUMN uas DOUBLE DEFAULT 0",
            "ADD COLUMN nilai_akhir DOUBLE DEFAULT 0",
            "ADD COLUMN grade VARCHAR(2) DEFAULT 'E'",
            "ADD COLUMN status VARCHAR(20) DEFAULT 'Tidak Lulus'"
        };
        try {
            Connection conn = Koneksi.configDB();
            for (String col : columns) {
                try {
                    conn.createStatement().execute("ALTER TABLE nilai " + col);
                } catch (Exception ignored) { }
            }
        } catch (Exception e) {}
    }
}
