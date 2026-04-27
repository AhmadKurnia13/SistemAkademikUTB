package Akademik;

/**
 *
 * @author Ahmad Kurnia
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class FormSoal1 extends javax.swing.JFrame {

    String[] arrNim = new String[5];
    double[] arrUts = new double[5];   
    double[] arrTugas = new double[5]; 
    double[] arrUas = new double[5];   
    double[] arrAbsen = new double[5]; 
    double[] arrAkhir = new double[5];
    int jumlahData = 0;
    
    public FormSoal1() {
        initComponents();
        setTitle("Input Nilai");
        setSize(820, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();
        btnInput = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        txtAbsen = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUas = new javax.swing.JTextField();
        txtTugas = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNilaiAkhir = new javax.swing.JTextField();
        txtNilaiHuruf = new javax.swing.JTextField();
        btnSimpanDB = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtNim = new javax.swing.JTextField();
        txtUts = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNilai = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Form input Nilai", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        btnClose.setBackground(new java.awt.Color(200, 50, 50));
        btnClose.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClose.setForeground(new java.awt.Color(255, 255, 255));
        btnClose.setText("Close");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnInput.setBackground(new java.awt.Color(0, 153, 255));
        btnInput.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnInput.setForeground(new java.awt.Color(255, 255, 255));
        btnInput.setText("Input");
        btnInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInputActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(153, 153, 153));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        txtAbsen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAbsenActionPerformed(evt);
            }
        });

        jLabel6.setText("Nilai Absensi (bobot 15%) :");

        jLabel5.setText("Nilai UAS (bobot 30%) :");

        txtTugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTugasActionPerformed(evt);
            }
        });

        jLabel4.setText("Nilai Tugas (bobot 25%):");

        jLabel3.setText("Nilai UTS (bobot 30%) :");

        jLabel2.setText("NIM :");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Masukkan jumlah data maksimal 5");

        jLabel7.setText("Nilai Akhir (otomatis) :");

        jLabel8.setText("Nilai Huruf :");

        txtNilaiAkhir.setEditable(false);

        txtNilaiHuruf.setEditable(false);

        btnSimpanDB.setBackground(new java.awt.Color(40, 160, 70));
        btnSimpanDB.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSimpanDB.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpanDB.setText("Simpan ke DB");
        btnSimpanDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanDBActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        jLabel9.setText("bobot penilaian = (UTSx30%) + (Tugasx25%) + (UASx30%) + (Absensix15%) A>80,B>70,C>60,D>50,E<50");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtUts, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                                .addComponent(txtNim)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(22, 22, 22)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtUas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                                        .addComponent(txtAbsen, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtTugas)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNilaiAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtNilaiHuruf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(25, 25, 25)
                                    .addComponent(btnInput)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnClear)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnSimpanDB)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnClose))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(248, 248, 248))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtUts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtUas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtAbsen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNilaiAkhir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtNilaiHuruf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInput)
                    .addComponent(btnClear)
                    .addComponent(btnSimpanDB)
                    .addComponent(btnClose))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Data Yang Sudah Di Input"));

        tblNilai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "NIM", "Nilai UTS", "Nilai Tugas", "Nilai UAS", "Nilai Absensi", "Nilai Akhir", "Grade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblNilai);
        if (tblNilai.getColumnModel().getColumnCount() > 0) {
            tblNilai.getColumnModel().getColumn(0).setResizable(false);
            tblNilai.getColumnModel().getColumn(1).setResizable(false);
            tblNilai.getColumnModel().getColumn(2).setResizable(false);
            tblNilai.getColumnModel().getColumn(3).setResizable(false);
            tblNilai.getColumnModel().getColumn(4).setResizable(false);
            tblNilai.getColumnModel().getColumn(5).setResizable(false);
            tblNilai.getColumnModel().getColumn(6).setResizable(false);
            tblNilai.getColumnModel().getColumn(7).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTugasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTugasActionPerformed

    private void txtAbsenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAbsenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAbsenActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        txtNim.setText("");
        txtUts.setText("");
        txtTugas.setText("");
        txtUas.setText("");
        txtAbsen.setText("");
        txtNim.requestFocus();
        txtNilaiAkhir.setText("");
        txtNilaiHuruf.setText("");
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInputActionPerformed
        if (jumlahData >= 5) {
        JOptionPane.showMessageDialog(this, "Data sudah mencapai batas maksimal (5)!");
        return;
    }

    try {
        String nim = txtNim.getText();
        double uts = Double.parseDouble(txtUts.getText());
        double tugas = Double.parseDouble(txtTugas.getText());
        double uas = Double.parseDouble(txtUas.getText());
        double absen = Double.parseDouble(txtAbsen.getText());
        if (uts > 100 || tugas > 100 || uas > 100 || absen > 100) {
            JOptionPane.showMessageDialog(this, "Nilai tidak boleh lebih dari 100!");
            return;
        }
        double akhir = (uts * 0.3) + (tugas * 0.25) + (uas * 0.3) + (absen * 0.15);
        String grade = (akhir >= 80) ? "A" : (akhir >= 70) ? "B" : (akhir >= 60) ? "C" : (akhir >= 50) ? "D" : "E";
        long akhirBulat = Math.round(akhir); 
        txtNilaiAkhir.setText(String.valueOf(akhirBulat));
        txtNilaiHuruf.setText(grade);
        arrNim[jumlahData] = nim;
        arrAkhir[jumlahData] = (double) akhirBulat; 
        DefaultTableModel model = (DefaultTableModel) tblNilai.getModel();
        if (jumlahData == 0) {
            model.setRowCount(0);
        }

        model.addRow(new Object[]{
            (jumlahData + 1), 
            nim, 
            uts, 
            tugas, 
            uas, 
            absen, 
            akhirBulat,
            grade
        });
        jumlahData++;
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Input harus berupa angka!");
    }
    }//GEN-LAST:event_btnInputActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnSimpanDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanDBActionPerformed
        if (jumlahData == 0) {
        JOptionPane.showMessageDialog(this, "Array masih kosong! Input data dulu.");
        return;
    }

    try {
        Connection conn = Koneksi.configDB();
        int sukses = 0;
        for (int i = 0; i < jumlahData; i++) {
            String sqlCekKrs = "SELECT id_krs FROM krs WHERE nim = ? LIMIT 1";
            PreparedStatement pstKrs = conn.prepareStatement(sqlCekKrs);
            pstKrs.setString(1, arrNim[i]);
            ResultSet rsKrs = pstKrs.executeQuery();

            if (rsKrs.next()) {
                int idKrsTerdeteksi = rsKrs.getInt("id_krs");
                String sqlInsert = "INSERT INTO nilai (id_krs, nilai_uts, nilai_tugas, nilai_uas, nilai_absen, nilai_akhir, nilai_huruf) "
                                 + "VALUES (?, ?, ?, ?, ?, ?, ?)";
                
                PreparedStatement pstInsert = conn.prepareStatement(sqlInsert);
                pstInsert.setInt(1, idKrsTerdeteksi);
                pstInsert.setDouble(2, arrUts[i]);
                pstInsert.setDouble(3, arrTugas[i]);
                pstInsert.setDouble(4, arrUas[i]);   
                pstInsert.setDouble(5, arrAbsen[i]); 
                pstInsert.setDouble(6, arrAkhir[i]);
                double n = arrAkhir[i];
                String grade = (n >= 80) ? "A" : (n >= 70) ? "B" : (n >= 60) ? "C" : (n >= 50) ? "D" : "E";
                pstInsert.setString(7, grade);

                pstInsert.execute();
                sukses++;
            }
        } 
        JOptionPane.showMessageDialog(this, sukses + " Data Berhasil Disimpan ke Database!");
        jumlahData = 0;
        DefaultTableModel model = (DefaultTableModel) tblNilai.getModel();
        model.setRowCount(0);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Kesalahan Database: " + e.getMessage());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Kesalahan Umum: " + e.getMessage());
    }
    }//GEN-LAST:event_btnSimpanDBActionPerformed

    /**
     * @param args the command l419ine arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormSoal1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormSoal1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormSoal1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormSoal1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormSoal1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnInput;
    private javax.swing.JButton btnSimpanDB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblNilai;
    private javax.swing.JTextField txtAbsen;
    private javax.swing.JTextField txtNilaiAkhir;
    private javax.swing.JTextField txtNilaiHuruf;
    private javax.swing.JTextField txtNim;
    private javax.swing.JTextField txtTugas;
    private javax.swing.JTextField txtUas;
    private javax.swing.JTextField txtUts;
    // End of variables declaration//GEN-END:variables
}
