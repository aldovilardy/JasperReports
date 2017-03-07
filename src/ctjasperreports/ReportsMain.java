/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctjasperreports;

import java.awt.EventQueue;

/**
 *
 * @author desarrollo1
 */
public class ReportsMain {

    private static ConnectionDB con;

    public static void main(String[] args) {
        // TODO code application logic here
        conectToDatabase();
        openReportFrame();
    }

    private static void conectToDatabase() {
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://localhost:1433";
        String db = "JasperTest";

        con = new ConnectionDB(driver, db, url, "sa", "calltech");
        con.connect();
    }

    private static void openReportFrame() {

        java.awt.EventQueue.invokeLater(() -> {
            try {
                new ReportJFrame(con.getConn()).setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
