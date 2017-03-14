/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctjasperreports;

import java.sql.Connection;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;
import java.text.DateFormat;

/**
 *
 * @author Aldo Fernando Vilardy Roa
 */
public abstract class AbstractJasperReports {

    private static JasperReport report;
    private static JasperPrint reportFilled;
    private static JasperViewer viewer;

    public static void createReport(Connection conn, String path) {
        try {
            report = (JasperReport) JRLoader.loadObjectFromFile(path);
            reportFilled = JasperFillManager.fillReport(report, null, conn);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static void createReport(Connection conn, String path, Map parameters) {
        try {
            report = (JasperReport) JRLoader.loadObjectFromFile(path);
            reportFilled = JasperFillManager.fillReport(report, parameters, conn);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static void createReport(Connection conn, String path, File xmlFile) {
        Map<String, Object> parameters = new HashMap();
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("parameter");
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.println("Parametro : " + eElement.getAttribute("name"));
                    System.out.println("Tipo de dato : " + eElement.getElementsByTagName("type").item(0).getTextContent());
                    System.out.println("valor : " + eElement.getElementsByTagName("value").item(0).getTextContent());
                    switch (eElement.getElementsByTagName("type").item(0).getTextContent()) {
                        case "java.lang.Boolean":
                            parameters.put(eElement.getAttribute("name"),
                                    Boolean.valueOf(eElement.getElementsByTagName("value").item(0).getTextContent()));
                            break;
                        case "java.lang.Double":
                            parameters.put(eElement.getAttribute("name"),
                                    Double.parseDouble(eElement.getElementsByTagName("value").item(0).getTextContent()));
                            break;
                        case "java.lang.Foat":
                            parameters.put(eElement.getAttribute("name"),
                                    Float.parseFloat(eElement.getElementsByTagName("value").item(0).getTextContent()));
                            break;
                        case "java.lang.Integer":
                            parameters.put(eElement.getAttribute("name"),
                                    Integer.parseInt(eElement.getElementsByTagName("value").item(0).getTextContent()));
                            break;
                        case "java.lang.Long":
                            parameters.put(eElement.getAttribute("name"),
                                    Long.parseLong(eElement.getElementsByTagName("value").item(0).getTextContent()));
                            break;
                        case "java.lang.Short":
                            parameters.put(eElement.getAttribute("name"),
                                    Short.parseShort(eElement.getElementsByTagName("value").item(0).getTextContent()));
                            break;
                        case "java.math.BigDecimal":
                            parameters.put(eElement.getAttribute("name"),
                                    new BigDecimal(eElement.getElementsByTagName("value").item(0).getTextContent()));
                            break;
                        case "java.sql.Date":
                            parameters.put(eElement.getAttribute("name"),
                                    Date.valueOf(eElement.getElementsByTagName("value").item(0).getTextContent()));
                            break;
                        case "java.sql.Time":
                            parameters.put(eElement.getAttribute("name"),
                                    Time.valueOf(eElement.getElementsByTagName("value").item(0).getTextContent()));
                            break;
                        case "java.sql.Timestamp":
                            parameters.put(eElement.getAttribute("name"),
                                    Timestamp.valueOf(eElement.getElementsByTagName("value").item(0).getTextContent()));
                            break;
                        case "java.util.Date":
                            parameters.put(eElement.getAttribute("name"),
                                    DateFormat.getDateInstance().parse(eElement.getElementsByTagName("value").item(0).getTextContent()));
                            break;
                        default:
                            parameters.put(eElement.getAttribute("name"),
                                    eElement.getElementsByTagName("value").item(0).getTextContent());
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            report = (JasperReport) JRLoader.loadObjectFromFile(path);
            reportFilled = JasperFillManager.fillReport(report, parameters, conn);
        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    public static void showViewer() {
        viewer = new JasperViewer(reportFilled);
        viewer.setVisible(true);
    }

    public static void exportToPdf(String path) {
        try {
            JasperExportManager.exportReportToPdfFile(reportFilled, path);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
