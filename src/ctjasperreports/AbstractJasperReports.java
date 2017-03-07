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
import java.util.Map;

/**
 *
 * @author desarrollo1
 */
public abstract class AbstractJasperReports {

    private static JasperReport report;
    private static JasperPrint reportFilled;
    private static JasperViewer viewer;

    public static void createReport(Connection conn, String path) {
        try {
            report = (JasperReport) JRLoader.loadObjectFromFile(path);
            reportFilled = JasperFillManager.fillReport(report, null, conn);
        }
        catch (JRException e) {
            e.printStackTrace();
        }
    }
    
    public static void createReport(Connection conn, String path, Map parameters) {
        try {
            report = (JasperReport) JRLoader.loadObjectFromFile(path);
            reportFilled = JasperFillManager.fillReport(report, parameters, conn);
        }
        catch (JRException e) {
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
