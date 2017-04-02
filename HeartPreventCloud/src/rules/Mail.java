package rules;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

    public void Mail(String corUsuario) throws SQLException, MessagingException, UnknownHostException {

        try {
            db.cDatos con = new db.cDatos();
            con.conectar();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();
            String Fecha = dateFormat.format(date); //2014/08/06 15:59:48
            ResultSet rs = con.consulta("call validaMail('" + corUsuario + "')");
            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("/img/logo.png").toString()));
                alert.setTitle("Pass recovery");
                alert.setHeaderText("Correo enviado.");
                alert.setContentText("Revisa Tu Bandeja de Entrada.");
                alert.setGraphic(new ImageView(this.getClass().getResource("/img/check.png").toString()));

                final String username = "heartpreventwakeup@outlook.com";
                final String password = "123456789AB";

                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp-mail.outlook.com");
                props.put("mail.smtp.port", "587");
                Session session = Session.getInstance(props,
                        new javax.mail.Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });
                Seguridad crip = new Seguridad();
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("heartpreventwakeup@outlook.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(crip.decriptaAES(corUsuario)));
                String myIp = Inet4Address.getLocalHost().getHostAddress();
                message.setSubject("Recuperacion de Contraseña HeartPrevent Cloud");
                message.setText("Para continuar con el proceso de renovación de su contraseña de click en el siguiente enlace:"
                        + "\n\nhttp://" + myIp + ":8080/HPNet/sections/passrecovery.jsp?m=" + rs.getString("contraseña") + rs.getString("correo") + ""
                        + "\n\n" + Fecha);

                Transport.send(message);
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("/img/logo.png").toString()));
                alert.setTitle("Pass recovery");
                alert.setHeaderText("Correo Invalido.");
                alert.setContentText("Lo sentimos ese correo no ha sido registrado.");
                alert.setGraphic(new ImageView(this.getClass().getResource("/img/warn.png").toString()));
                alert.show();
            }
        } catch (MessagingException e) {
            System.out.println("Msgex");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/img/logo.png").toString()));
            alert.setTitle("Pass recovery");
            alert.setHeaderText("La conexion se ha perdido.");
            alert.setContentText("No esta conectado a una red.");
            alert.setGraphic(new ImageView(this.getClass().getResource("/img/warn.png").toString()));
            alert.show();
        } catch (java.lang.NullPointerException e) {
            System.out.println("nulo");
        } catch (SQLException e) {
            System.out.println("SQLex");
        }
    }
}
