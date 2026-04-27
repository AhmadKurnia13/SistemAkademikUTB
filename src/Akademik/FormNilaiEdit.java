package Akademik;

/**
 *
 * @author Ahmad Kurnia
 */

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class FormNilaiEdit extends javax.swing.JFrame {

    private JTextField txtUts, txtTugas, txtUas, txtAbsen, txtAkhir, txtHuruf;
    private JButton    btnUbah, btnHapus, btnClear, btnTutup;
    private JTable     tblNilai;
    private DefaultTableModel model;
    private int selectedIdNilai = -1;

    private static final double BOBOT_UTS   = 0.30;
    private static final double BOBOT_TUGAS = 0.25;
    private static final double BOBOT_UAS   = 0.35;
    private static final double BOBOT_ABSEN = 0.10;

    public FormNilaiEdit() {
        initComponents();
        tampilData();
    }

    private void initComponents() {
        setTitle("Edit / Hapus Nilai");
        setSize(900, 540);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel pnlMain = new JPanel(new BorderLayout(8, 8));
        pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createTitledBorder(
            "Edit Nilai — Klik baris tabel untuk memilih data"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 8, 4, 8);
        g.fill   = GridBagConstraints.HORIZONTAL;

        txtUts   = field(); txtTugas = field();
        txtUas   = field(); txtAbsen = field();
        txtAkhir = field(); txtAkhir.setEditable(false);
        txtAkhir.setBackground(new Color(235, 248, 255));
        txtHuruf = field(); txtHuruf.setEditable(false);
        txtHuruf.setBackground(new Color(235, 255, 235));
        txtHuruf.setFont(new Font("Segoe UI", Font.BOLD, 14));

        KeyAdapter preview = new KeyAdapter() {
            public void keyReleased(KeyEvent e) { previewNilai(); }
        };
        txtUts.addKeyListener(preview); txtTugas.addKeyListener(preview);
        txtUas.addKeyListener(preview); txtAbsen.addKeyListener(preview);

        g.gridwidth = 1; g.weightx = 0;
        int row = 0;
        addBaris2(pnlForm, g, row++, "Nilai UTS (30%) :", txtUts,   "Nilai Tugas (25%) :", txtTugas);
        addBaris2(pnlForm, g, row++, "Nilai UAS (30%) :", txtUas,   "Nilai Absensi (15%):", txtAbsen);
        addBaris2(pnlForm, g, row++, "Nilai Akhir :",     txtAkhir, "Nilai Huruf :",       txtHuruf);

        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
        btnUbah  = btn("Ubah",  new Color(200, 130, 0));
        btnHapus = btn("Hapus", new Color(180, 40, 40));
        btnClear = btn("Clear", new Color(100, 100, 100));
        btnTutup = btn("Tutup", new Color(80, 80, 80));
        pnlBtn.add(btnUbah); pnlBtn.add(btnHapus);
        pnlBtn.add(btnClear); pnlBtn.add(btnTutup);
        g.gridx = 0; g.gridy = row; g.gridwidth = 4;
        pnlForm.add(pnlBtn, g);

        String[] kolom = {"ID Nilai","ID KRS","NIM","Nama Mahasiswa","Mata Kuliah",
                          "UTS","Tugas","UAS","Absensi","Akhir","Huruf"};
        model = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblNilai = new JTable(model);
        tblNilai.setRowHeight(26);
        tblNilai.getTableHeader().setBackground(new Color(30, 60, 114));
        tblNilai.getTableHeader().setForeground(Color.WHITE);
        tblNilai.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        tblNilai.getColumnModel().getColumn(0).setMinWidth(0); tblNilai.getColumnModel().getColumn(0).setMaxWidth(0);
        tblNilai.getColumnModel().getColumn(1).setMinWidth(0); tblNilai.getColumnModel().getColumn(1).setMaxWidth(0);

        tblNilai.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { isiBarisTerpilih(); }
        });

        pnlMain.add(pnlForm, BorderLayout.NORTH);
        pnlMain.add(new JScrollPane(tblNilai), BorderLayout.CENTER);
        add(pnlMain);

        btnUbah.addActionListener(e  -> doUbah());
        btnHapus.addActionListener(e -> doHapus());
        btnClear.addActionListener(e -> kosongkanForm());
        btnTutup.addActionListener(e -> dispose());
    }

    private void tampilData() {
       model.setRowCount(0); 
        try {
            Connection conn = Koneksi.configDB();
            if (conn == null) return;
            
            String sql = "SELECT n.id_nilai, n.id_krs, k.nim, m.nama, mk.nama_mk, "
                       + "n.nilai_uts, n.nilai_tugas, n.nilai_uas, n.nilai_absen, n.nilai_akhir, n.nilai_huruf "
                       + "FROM nilai n "
                       + "JOIN krs k ON n.id_krs = k.id_krs "
                       + "JOIN mahasiswa m ON k.nim = m.nim "
                       + "JOIN matakuliah mk ON k.kode_mk = mk.kode_mk "
                       + "ORDER BY k.nim ASC";

            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String uts = formatNilai(rs.getDouble("nilai_uts"));
                String tugas = formatNilai(rs.getDouble("nilai_tugas"));
                String uas = formatNilai(rs.getDouble("nilai_uas"));
                String absen = formatNilai(rs.getDouble("nilai_absen"));
                String akhir = formatNilai(rs.getDouble("nilai_akhir"));

                model.addRow(new Object[]{
                    rs.getInt("id_nilai"),
                    rs.getInt("id_krs"),
                    rs.getString("nim"),           
                    rs.getString("nama"),          
                    rs.getString("nama_mk"),       
                    uts,
                    tugas,
                    uas,
                    absen,
                    akhir,
                    rs.getString("nilai_huruf")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal Tarik Data: " + e.getMessage());
        }
    }
    private String formatNilai(double nilai) {
        if (nilai == (long) nilai) {
            return String.format("%d", (long) nilai);
        } else {
            return String.format("%s", nilai);
        }
    }

    private void isiBarisTerpilih() {
        int baris = tblNilai.getSelectedRow();
        if (baris < 0) return;
        selectedIdNilai = Integer.parseInt(model.getValueAt(baris, 0).toString());
        txtUts.setText(model.getValueAt(baris, 5).toString());
        txtTugas.setText(model.getValueAt(baris, 6).toString());
        txtUas.setText(model.getValueAt(baris, 7).toString());
        txtAbsen.setText(model.getValueAt(baris, 8).toString());
        txtAkhir.setText(model.getValueAt(baris, 9).toString());
        txtHuruf.setText(model.getValueAt(baris, 10).toString());
    }

    private void previewNilai() {
        try {
            double uts = parseD(txtUts.getText());
            double tugas = parseD(txtTugas.getText());
            double uas = parseD(txtUas.getText());
            double absen = parseD(txtAbsen.getText());
            double total = (uts * BOBOT_UTS) + (tugas * BOBOT_TUGAS) + (uas * BOBOT_UAS) + (absen * BOBOT_ABSEN);
            total = Math.round(total * 100.0) / 100.0;
            txtAkhir.setText(String.format("%.2f", total));
            txtHuruf.setText(toHuruf(total));
        } catch (NumberFormatException ignored) {}
    }

    private void doUbah() {
        if (selectedIdNilai < 0) { JOptionPane.showMessageDialog(this, "Pilih baris nilai dari tabel!"); return; }
        try {
            double uts=parseD(txtUts.getText()), tugas=parseD(txtTugas.getText());
            double uas=parseD(txtUas.getText()), absen=parseD(txtAbsen.getText());
            if (!valid(uts)||!valid(tugas)||!valid(uas)||!valid(absen)) {
                JOptionPane.showMessageDialog(this, "Nilai harus antara 0-100!"); return;
            }
            double akhir = (uts * BOBOT_UTS) + (tugas * BOBOT_TUGAS) + (uas * BOBOT_UAS) + (absen * BOBOT_ABSEN);
            akhir = Math.round(akhir * 100.0) / 100.0;

            Connection conn = Koneksi.configDB();
            String sql = "UPDATE nilai SET nilai_uts=?,nilai_tugas=?,nilai_uas=?,nilai_absen=?,nilai_akhir=?,nilai_huruf=? WHERE id_nilai=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDouble(1,uts); ps.setDouble(2,tugas); ps.setDouble(3,uas);
                ps.setDouble(4,absen); ps.setDouble(5,akhir);
                ps.setString(6,toHuruf(akhir)); ps.setInt(7,selectedIdNilai);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Nilai berhasil diubah!");
                tampilData(); kosongkanForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal Ubah: " + e.getMessage());
        }
    }

    private void doHapus() {
        if (selectedIdNilai < 0) { JOptionPane.showMessageDialog(this, "Pilih baris nilai dari tabel!"); return; }
        if (JOptionPane.showConfirmDialog(this,"Yakin hapus data nilai ini?","Konfirmasi",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try {
            Connection conn = Koneksi.configDB();
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM nilai WHERE id_nilai=?")) {
                ps.setInt(1, selectedIdNilai);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Nilai berhasil dihapus!");
                tampilData(); kosongkanForm();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Hapus Gagal: " + e.getMessage());
        }
    }

    private void kosongkanForm() {
        txtUts.setText(""); 
        txtTugas.setText(""); 
        txtUas.setText(""); 
        txtAbsen.setText("");
        txtAkhir.setText(""); 
        txtHuruf.setText("");
        selectedIdNilai = -1; 
        tblNilai.clearSelection();
    }

    private void addBaris2(JPanel p, GridBagConstraints g, int row, String l1, JTextField f1, String l2, JTextField f2) {
        g.gridwidth = 1; g.weightx = 0; g.gridx = 0; g.gridy = row; p.add(new JLabel(l1), g);
        g.gridx = 1; g.weightx = 0.4; p.add(f1, g);
        g.gridx = 2; g.weightx = 0;   p.add(new JLabel(l2), g);
        g.gridx = 3; g.weightx = 0.4; p.add(f2, g);
    }

    private String toHuruf(double a) {
        if(a>=80) return "A"; if(a>=70) return "B";
        if(a>=60) return "C"; if(a>=50) return "D"; return "E";
    }

    private JTextField field() { JTextField tf = new JTextField(12); return tf; }
    
    private JButton btn(String t, Color bg) {
        JButton b = new JButton(t); b.setBackground(bg); b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI",Font.BOLD,11)); b.setPreferredSize(new Dimension(90,30));
        b.setBorderPainted(false); return b;
    }

    private double parseD(String s) { return s.isEmpty() ? 0 : Double.parseDouble(s.trim()); }
    private boolean valid(double v) { return v>=0 && v<=100; }
}