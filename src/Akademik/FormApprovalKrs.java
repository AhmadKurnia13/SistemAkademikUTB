package Akademik;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FormApprovalKrs extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private String selectedNim = "";
    private String selectedKodeMk = "";

    public FormApprovalKrs() {
        setLayout(new BorderLayout(0, 20));
        setBackground(StyleManager.BG_PRIMARY);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        // Attempt to create the column if it doesn't exist
        try {
            Connection conn = Koneksi.configDB();
            conn.createStatement().execute("ALTER TABLE krs ADD COLUMN status_approval VARCHAR(20) DEFAULT 'Pending'");
        } catch (Exception e) {
            // Column likely already exists, ignore
        }

        JLabel title = new JLabel("menu-approval : Persetujuan KRS (Admin)");
        title.setFont(StyleManager.F_TITLE);
        title.setForeground(StyleManager.TEXT_PRIMARY);

        // TOP: Action Buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        actionPanel.setBackground(StyleManager.BG_PRIMARY);
        
        JButton btnApprove = StyleManager.createFlatBtn("Setujui KRS", StyleManager.BTN_SUCCESS);
        JButton btnReject = StyleManager.createFlatBtn("Tolak KRS", StyleManager.BTN_DANGER);
        JButton btnRefresh = StyleManager.createFlatBtn("Refresh", StyleManager.BTN_INFO);
        
        actionPanel.add(btnApprove);
        actionPanel.add(btnReject);
        actionPanel.add(btnRefresh);

        // MIDDLE: Table
        model = new DefaultTableModel(new String[]{"NIM", "Kode MK", "Semester Aktif", "Status Persetujuan"}, 0);
        table = new JTable(model);
        StyleManager.styleTable(table);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(StyleManager.BORDER));
        scroll.getViewport().setBackground(StyleManager.BG_CARD);

        JPanel topHalf = new JPanel(new BorderLayout(0, 20));
        topHalf.setBackground(StyleManager.BG_PRIMARY);
        topHalf.add(title, BorderLayout.NORTH);
        topHalf.add(actionPanel, BorderLayout.CENTER);

        add(topHalf, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // Actions
        table.getSelectionModel().addListSelectionListener(event -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                selectedNim = model.getValueAt(row, 0).toString();
                selectedKodeMk = model.getValueAt(row, 1).toString();
            }
        });

        btnApprove.addActionListener(e -> updateStatus("Disetujui"));
        btnReject.addActionListener(e -> updateStatus("Ditolak"));
        btnRefresh.addActionListener(e -> loadData());

        loadData();
    }

    private void updateStatus(String status) {
        if (selectedNim.isEmpty() || selectedKodeMk.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data KRS terlebih dahulu dari tabel!");
            return;
        }
        try {
            Connection conn = Koneksi.configDB();
            PreparedStatement pst = conn.prepareStatement("UPDATE krs SET status_approval=? WHERE nim=? AND kode_mk=?");
            pst.setString(1, status);
            pst.setString(2, selectedNim);
            pst.setString(3, selectedKodeMk);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Status KRS berhasil diubah menjadi: " + status);
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error update status: " + ex.getMessage());
        }
    }

    private void loadData() {
        model.setRowCount(0);
        selectedNim = "";
        selectedKodeMk = "";
        try {
            Connection conn = Koneksi.configDB();
            ResultSet rs = conn.createStatement().executeQuery("SELECT nim, kode_mk, semester_aktif, status_approval FROM krs");
            while(rs.next()) {
                String status = rs.getString("status_approval");
                if (status == null) status = "Pending";
                model.addRow(new Object[]{rs.getString("nim"), rs.getString("kode_mk"), rs.getString("semester_aktif"), status});
            }
        } catch (Exception e) {}
    }
}
