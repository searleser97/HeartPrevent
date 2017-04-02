/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilajasperjrxml;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

/**
 *
 * @author vaioubuntu
 */
public class CompilaJasperjrxml {

    /**
     * @param args the command line arguments
     * @throws net.sf.jasperreports.engine.JRException
     */
    public static void main(String[] args) throws JRException {
        for(int i=0;i<4;i++)
        {
            System.out.println(i);
               JasperCompileManager.compileReportToFile(
                "src/reportesjrxml/Reporte"+i+".jrxml",
                "src/reportesjrxml/Reporte"+i+".jasper"); 
        }
    }
}
