/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Yusuf Fahruddin
 */
public class Koneksi {
    private Connection mConnection;
    private String PREFIX_JDBC = "jdbc:sqlserver://";
    private String DB_URL;
    private String HOST = "10.1.1.2";
    private String PORT = "1433";
    private String DB_NAME = "tes5";
    private String DB_USERNAME = "sa";
    private String DB_PASSWORD = "Shield@1945";
    
    public Connection Koneksi() {
        // DB_URL = PREFIX_JDBC + HOST + ":" + PORT + ";databaseName=" + DB_NAME;
        DB_URL = "jdbc:sqlserver://10.1.1.2:1433;databaseName=tes5";
               
        if (mConnection == null) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                mConnection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                mConnection.setAutoCommit(false);
            } catch (ClassNotFoundException cne) {
                JOptionPane.showMessageDialog(null, "Driver tidak ditemukan");
            } catch (SQLException ex) {
                Logger.getLogger(Koneksi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return mConnection;
    }
    
}
