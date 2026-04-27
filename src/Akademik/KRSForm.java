package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class KRSForm extends JInternalFrame {
    private JTextField txtNim, txtKodeMk, txtSemester;
    private JTable table;
    private DefaultTableModel model;

    public KRSForm() {
        super("Form KRS", true, true, true, true);
        setSize(800, 500);
        setLayout(new BorderLayout(0, 10));
        getContentPane().setBackground(StyleManager.BG_PRIMARY);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(StyleManager.BG_CARD);
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(StyleManager.createLabel("NIM Mahasiswa"), gbc);
        gbc.gridx = 1;
        txtNim = StyleManager.createTextField();
        formPanel.add(txtNim, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(StyleManager.createLabel("Kode MK"), gbc);
        gbc.gridx = 1;
        txtKodeMk = StyleManager.createTextField();
        formPanel.add(txtKodeMk, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(StyleManager.createLabel("Semester"), gbc);
        gbc.gridx = 1;
        txtSemester = StyleManager.createTextField();
        formPanel.add(txtSemester, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(StyleManager.BG_CARD);
        JButton btnTambah = StyleManager.createFlatBtn("Tambah ke KRS", StyleManager.BTN_SUCCESS);
        JButton btnHapus = StyleManager.createFlatBtn("Hapus dari KRS", StyleManager.BTN_DANGER);
        btnPanel.add(btnTambah); btnPanel.add(btnHapus);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        model = new DefaultTableModel(new String[]{"NIM", "Kode MK", "Semester"}, 0);
        table = new JTable(model);
        StyleManager.styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(StyleManager.BG_CARD);

        add(formPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // CRUD LOGIC
        btnTambah.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("INSERT INTO krs (nim, kode_mk, semester) VALUES (?, ?, ?)");
                pst.setString(1, txtNim.getText());
                pst.setString(2, txtKodeMk.getText());
                pst.setString(3, txtSemester.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data KRS Tersimpan");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnHapus.addActionListener(e -> {
            try {
                Connection conn = Koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement("DELETE FROM krs WHERE nim=? AND kode_mk=?");
                pst.setString(1, txtNim.getText());
                pst.setString(2, txtKodeMk.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data KRS Dihapus");
                loadData();
            } catch (Exception ex) {}
        });

        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        try {
            Connection conn = Koneksi.configDB();
            ResultSet rs = conn.createStatement().executeQuery("SELECT nim, kode_mk, semester FROM krs");
            while(rs.next()) {
                model.addRow(new Object[]{rs.getString("nim"), rs.getString("kode_mk"), rs.getString("semester")});
            }
        } catch (Exception e) {}
    }
}
