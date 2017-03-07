/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctjasperreports;

/**
 *
 * @author Aldo Fernando Vilardy Roa
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private String driver, db, url, user, pass;
    private Connection conn;

    public ConnectionDB(String driver, String db, String url, String user, String pass) {
        this.driver = driver;
        this.db = db;
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

        public Connection getConn() {
        return conn;
    }

    public void connect() {
        try {
            Class.forName(this.driver);
            conn = DriverManager.getConnection(this.url +";databaseName="+ this.db, user, pass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            conn.close();
            conn = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getStateConnection() {
        try {
            if (!this.conn.isClosed()) {
                javax.swing.JOptionPane.showMessageDialog(null, "conectado");
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "desconectado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
