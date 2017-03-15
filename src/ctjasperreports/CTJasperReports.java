/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctjasperreports;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
//import java.io.FileInputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
//import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

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
        String db = null, pass = null, url = null, user = null, dirRep = null, driver = null, pdfPath = null, repName = null;
        Boolean repParams = null;
        if (args.length > 1) {
            System.out.println("Hay demasiados parámetros. Debe escribir: CTJasperReports \"path\\archivodeconfiguracion.xml\"");
        } else if (args.length == 0) {
            System.out.println("Por favor ingrese como parametro la ruta de un archivo de configuración *.xml");
            System.out.println(args[0]);
        } else {
// <editor-fold defaultstate="collapsed" desc="Carga Propiedades desde XML">
//<editor-fold defaultstate="collapsed" desc="OLD Carga Propiedades">
//            try (FileInputStream filImputStream = new FileInputStream(args[0])) {
//                appConf.loadFromXML(filImputStream);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
// </editor-fold>
            try {
                File fXmlFile = new File(args[0]);
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fXmlFile);
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("entry");
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        switch (eElement.getAttribute("key")) {
                            case "dbName":
                                db = eElement.getTextContent();
                            case "dbPass":
                                pass = eElement.getTextContent();
                                break;
                            case "dbUrl":
                                url = eElement.getTextContent();
                                break;
                            case "dbUser":
                                user = eElement.getTextContent();
                            case "dirRep":
                                dirRep = eElement.getTextContent();
                                break;
                            case "driver":
                                driver = eElement.getTextContent();
                                break;
                            case "pdfPath":
                                pdfPath = eElement.getTextContent();
                                break;
                            case "repName":
                                repName = eElement.getTextContent();
                                break;                                
                            case "repParams":
                                repParams = Boolean.parseBoolean(eElement.getTextContent());
                            default:
                                break;
                        }                      
                    }
                }
            } catch (IOException | ParserConfigurationException | DOMException | SAXException e) {
                e.printStackTrace();
            }
// <editor-fold defaultstate="collapsed" desc="OLD Asiganación Propiedades">
            //String dirRep = appConf.getProperty("dirRep");
            //String repName = appConf.getProperty("repName");
            //String pdfPath = appConf.getProperty("pdfPath");
            //String driver = appConf.getProperty("driver");
            //String url = appConf.getProperty("dbUrl");
            //String db = appConf.getProperty("dbName");
            //String user = appConf.getProperty("dbUser");
            //String pass = appConf.getProperty("dbPass");
            //Boolean repParams = Boolean.parseBoolean(appConf.getProperty("repParams"));
// </editor-fold>            
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Conecta a la base de datos">
            conectToDatabase(driver, db, url, user, pass);
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="construye el reporte">            
            if (repParams) {//si viene con parametros
                File xmlFile = new File(args[0]);
                makeReport(dirRep + repName + ".jasper", pdfPath, xmlFile);
            }
            else{//si sin parametros
                makeReport(dirRep + repName + ".jasper", pdfPath);                
            }     
// </editor-fold>
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
