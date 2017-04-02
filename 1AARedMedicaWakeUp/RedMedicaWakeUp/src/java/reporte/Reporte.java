/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporte;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Silvester
 */
@WebListener
public class Reporte implements ServletContextListener {

    Calendar calendar = Calendar.getInstance();
    private ScheduledExecutorService scheduler;
    Config.Defaults def = new Config.Defaults();
    final private String rutaPDFS = def.pdfPath;
    //db.Condb con;

    @Override
    public void contextInitialized(ServletContextEvent event) {
//        scheduler = Executors.newSingleThreadScheduledExecutor();
//      scheduler.scheduleAtFixedRate(new GenerateReport(), 0, 1, TimeUnit.MINUTES);
        //scheduler.scheduleAtFixedRate(new Notificationsmedicine(), 0, 1, TimeUnit.SECONDS);

    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

    int count = 0;

    public class Notificationsmedicine implements Runnable {

        @Override
        public void run() {
            try {
                db.Condb con = new db.Condb();
                ResultSet rs = con.consulta("call notificacionesmedi('0','0')");

                while (rs.next()) {
                    System.out.println("entro");
                    System.out.println("regId: " + rs.getString("gcmid"));
                    String GOOGLE_SERVER_KEY = "AIzaSyBLDvZjkAUsV0_qxIDC4OuLz2-fGzXdiCc";
                    String MESSAGE_KEY = "message";
                    String userMessage = "Es momento de tomar el medicamento: " + rs.getString("medicamento");
                    Sender sender = new Sender(GOOGLE_SERVER_KEY);
                    Message message = new Message.Builder().timeToLive(30)
                            .delayWhileIdle(true).addData(MESSAGE_KEY, userMessage).build();
                    Result result = sender.send(message, rs.getString("gcmid"), 1);

                    System.out.println(result);
                    con.consulta("call notificacionesmedi('1','" + rs.getString("idrelusumedicamentos") + "')");

//                                count++;
//                                if(count==2){
//                                con.consulta("call notificacionesmedi('1','"+rs.getString("idrelusumedicamentos")+"')"); 
//                                count=0;
//                                }
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void generateReport(String usrId) {
        Thread td = new Thread(() -> {
            System.out.println("generaReporte()");
            try {
                String tipoReporte = "";

                int mes = (calendar.get(Calendar.MONTH));
                int annio = (calendar.get(Calendar.YEAR));
                String dia = "" + calendar.get(Calendar.DAY_OF_MONTH);

                db.Condb con = new db.Condb();
                con.conectar();
                ResultSet r = con.consulta("call datospersonapdf('" + usrId + "');");

                if (r.next()) {
                    for (int i = r.getInt("cuantosfaltan"); i > 0; i--) {
                        String mesReal = String.valueOf(mes + 1);
                        if (mesReal.length() == 1) {
                            mesReal = "0" + mesReal;
                        }
                        if (dia.length() == 1) {
                            dia = "0" + dia;
                        }
                        String monthname = getMonthName(mes);

                        System.out.println("Processing " + i + "...");
                        ResultSet r2 = con.consulta("call tipohistorial('" + r.getString("correo") + "');");
                        if (r2.next()) {
                            switch (r2.getInt("Msg")) {
                                case 0:
                                    tipoReporte = "0";
                                    break;
                                case 1:
                                    tipoReporte = "1";
                                    break;
                                case 2:
                                    tipoReporte = "2";
                                    break;
                                case 3:
                                    tipoReporte = "3";
                                    break;
                                default:
                                    break;
                            }
                        }
                        String ReportLocation = rutaPDFS + "Reporte" + tipoReporte + ".jasper";
                        Map<String, Object> parameters = new HashMap<>();

                        parameters.put("Usuario", r.getString("correo"));
                        parameters.put("rutaimg", rutaPDFS);
                        JasperReport jr = (JasperReport) JRLoader.loadObject(new File(ReportLocation));

                        JasperPrint print = JasperFillManager.fillReport(jr, parameters, con.getConexion());

                        byte[] pdfbytes = JasperExportManager.exportReportToPdf(print);
                        PreparedStatement stmt = con.getConexion().prepareStatement("call altapdf(?,?,?,1,?);");
                        try (InputStream byteis = new ByteArrayInputStream(pdfbytes)) {
                            stmt.setBinaryStream(1, byteis);
                            stmt.setString(2,
                                    monthname + "_" + annio + "_" + r.getString("Nombre").toLowerCase() + "_" + r.getString("apellido").toLowerCase() + ".pdf");
                            stmt.setString(3, r.getString("correo"));
                            stmt.setString(4, annio + "-" + mesReal + "-" + dia + " 00:00:00");
                            stmt.execute();
                        }

                        if (mes == 0) {
                            mes = 12;
                            annio = annio - 1;
                        }
                        mes--;
                    }
                }
                con.cierraConexion();
                System.out.println("Done.");
            } catch (JRException | SQLException | IOException ex) {
                System.out.println("Reporte.java: " + ex.getMessage());
            }
        });
        td.start();
    }

    public String getMonthName(int monthNumber) {
        String monthname = "";
        switch (monthNumber) {
            case 0: {
                monthname = "Enero";
                break;
            }
            case 1: {
                monthname = "Febrero";
                break;
            }
            case 2: {
                monthname = "Marzo";
                break;
            }
            case 3: {
                monthname = "Abril";
                break;
            }
            case 4: {
                monthname = "Mayo";
                break;
            }
            case 5: {
                monthname = "Junio";
                break;
            }
            case 6: {
                monthname = "Julio";
                break;
            }
            case 7: {
                monthname = "Agosto";
                break;
            }
            case 8: {
                monthname = "Septiembre";
                break;
            }
            case 9: {
                monthname = "Octubre";
                break;
            }
            case 10: {
                monthname = "Noviembre";
                break;
            }
            case 11: {
                monthname = "Diciembre";
                break;
            }
        }
        return monthname;
    }

}
