/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartPrevent;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author ACER-PC
 */
public class Alerts {

    public void Alerts(String tit, String tit2, String text, String imagen, int tipo) {
        // todos tiene logo
        String logo = "/img/logo.png";
        if (tipo == 0) {
//            error, sin imagen y espera
            Alert alert = new Alert(Alert.AlertType.ERROR);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource(logo).toString()));
            alert.setTitle(tit);
            alert.setHeaderText(tit2);
            alert.setContentText(text);
            alert.showAndWait();

        } else if (tipo == 1) {
//            error, imagen y espera
            Alert alert = new Alert(Alert.AlertType.ERROR);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource(logo).toString()));
            alert.setTitle(tit);
            alert.setHeaderText(tit2);
            alert.setContentText(text);
            alert.setGraphic(new ImageView(this.getClass().getResource(imagen).toString()));
            alert.showAndWait();
        } else if (tipo == 2) {
// informar, sin imagen, sin esperar
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(tit);
            alert.setHeaderText(tit2);
            alert.setContentText(text);
            alert.show();
        } else if (tipo == 3) {
//            informar, imagen, esperar
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource(logo).toString()));
            alert.setTitle(tit);
            alert.setHeaderText(tit2);
            alert.setContentText(text);
            alert.setGraphic(new ImageView(this.getClass().getResource(imagen).toString()));
            alert.showAndWait();

        } else if (tipo == 4) {
//            informar, imagen, sin esperar
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource(logo).toString()));
            alert.setTitle(tit);
            alert.setHeaderText(tit2);
            alert.setContentText(text);
            alert.setGraphic(new ImageView(this.getClass().getResource(imagen).toString()));
            alert.show();
        }

//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//        stage.getIcons().add(new Image(this.getClass().getResource("/img/logo.png").toString()));
//        alert.setTitle("Heart Cloud");
//        alert.setHeaderText("No hay Base");
//        alert.setContentText("No se ha podido de conectar.");
//        alert.setGraphic(new ImageView(this.getClass().getResource("/img/wait.png").toString()));
//        alert.showAndWait();
    }
}
