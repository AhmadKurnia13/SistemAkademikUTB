package Akademik;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Ahmad Kurnia
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Koneksi {

    private static Connection mysqlconfig;

    public static Connection configDB() throws SQLException {
        try {

            String url = "jdbc:mysql://localhost:3306/db_uts_pbo";
            String user = "root";
            String pass = "";

            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            mysqlconfig = DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            System.err.println("Koneksi gagal: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Koneksi Database Gagal!\nPastikan XAMPP (MySQL) sudah menyala.\nError: " + e.getMessage());
        }
        return mysqlconfig;
    }
}
