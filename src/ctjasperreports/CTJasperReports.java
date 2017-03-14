/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctjasperreports;

import java.io.File;
import java.util.Properties;
import java.io.FileInputStream;

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
        // TODO code application logic here        
        if (args.length > 1) {
            System.out.println("Hay demasiados parámetros. Debe escribir: CTJasperReports \"path\\archivodeconfiguracion.xml\"");
        } else if (args.length == 0) {
            System.out.println("Por favor ingrese como parametro la ruta de un archivo de configuración *.xml");
            System.out.println(args[0]);
        } else {
            System.out.println(args[0]);
            try (FileInputStream filImputStream = new FileInputStream(args[0])) {
                appConf.loadFromXML(filImputStream);
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
            boolean repParams = Boolean.parseBoolean(appConf.getProperty("repParams"));
            
            conectToDatabase(driver, db, url, user, pass);
            
            if (repParams) {
                makeReport(dirRep + repName + ".jasper", pdfPath);
            }
            else{
                File xmlFile = new File(args[0]);
            makeReport(dirRep + repName + ".jasper", pdfPath, xmlFile);
            }
            
        }
    }

    private static void conectToDatabase(String driver, String db, String url, String user, String pass) {

        con = new ConnectionDB(driver, db, url, user, pass);
        con.connect();
    }

    private static void makeReport(String repPath, String pdfPath) {
        AbstractJasperReports.createReport(con.getConn(), repPath);
        AbstractJasperReports.exportToPdf(pdfPath);
    }
    
    private static void makeReport(String repPath, String pdfPath, File fXmlFile) {
        AbstractJasperReports.createReport(con.getConn(), repPath, fXmlFile);
        AbstractJasperReports.exportToPdf(pdfPath);
    }
}
