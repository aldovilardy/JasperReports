/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctjasperreports;

import java.util.Properties;
import java.io.FileReader;
import java.io.FileInputStream;
import java.util.Map;
import java.util.HashMap;
/**
 *
 * @author Aldo Fernando Vilardy Roa
 */
public class CTJasperReports {

    /**
     * @param args the command line arguments
     */
    private static ConnectionDB con;
    public static Properties appConf = new Properties();

    public static void main(String[] args) throws Exception {
        if (args.length > 1){
            System.out.println("Hay demasiados parámetros. Debe escribir: CTJasperReports path\\archivodeconfiguracion.xml");
        }
        // TODO code application logic here        
//        try (FileReader reader = new FileReader("D:\\Users\\desarrollo1\\Documents\\NetBeansProjects\\CTJasperReports\\src\\ctjasperreports\\App.config.properties")) {
//            //Properties appConf = new Properties();
//            appConf.load(reader);
//        } catch (Exception e) {
//            ;
//            e.printStackTrace();
//        }
        
        try (FileInputStream fil = new FileInputStream("D:\\Users\\desarrollo1\\Documents\\NetBeansProjects\\JasperReports\\src\\ctjasperreports\\App.config.properties.xml"))
        {
            appConf.loadFromXML(fil);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String dirRep = appConf.getProperty("dirRep");
        String repName = appConf.getProperty("repName");
        String pdfPath = appConf.getProperty("pdfPath");
        String driver = appConf.getProperty("driver");
        String url = appConf.getProperty("dbUrl");
        String db = appConf.getProperty("dbName");
        String user = appConf.getProperty("dbUser");
        String pass = appConf.getProperty("dbPass");
        
        Map<String, Object> parameters = new HashMap();

        conectToDatabase(driver, db, url, user, pass);        
        makeReport(dirRep + repName + ".jasper", pdfPath);
    }

    private static void conectToDatabase(String driver, String db, String url, String user, String pass) {

        con = new ConnectionDB(driver, db, url, user, pass);
        con.connect();
    }

    private static void makeReport(String repPath, String pdfPath) {
        AbstractJasperReports.createReport(con.getConn(), repPath);
        AbstractJasperReports.exportToPdf(pdfPath);
    }

}
