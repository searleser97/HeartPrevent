package heartPrevent;

import java.awt.Desktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import webservicepckg.FileNotFoundException_Exception;
import webservicepckg.IOException_Exception;
import webservicepckg.SQLException_Exception;
import webservicepckg.Webservicemethods;

public class MenuController implements Initializable {

    Calendar calendar = Calendar.getInstance();
    int mes;
    String annio;
    rules.Seguridad cifrado = new rules.Seguridad();
    String passCifrada = cifrado.encriptaSHA1(Usuarios.getInstance().getContrasenia().getText());
    String correoCifrado = cifrado.encriptaAES(Usuarios.getInstance().getCorreo().getText());

    public static int posicionArray = -1;
    ObservableList<Archivos> archivos = FXCollections.observableArrayList();
    ObservableList<Archivos> archivos2 = FXCollections.observableArrayList();
    IntegerProperty limit;

    static ResultSet r;
    db.cDatos bd = new db.cDatos();

    ImageView img = new ImageView("img/settings-icon2.png");
    ImageView img2 = new ImageView("img/syncicon.png");
    ImageView img3 = new ImageView("img/exit.png");
    ImageView img4 = new ImageView("img/close.png");
    ImageView img5 = new ImageView("img/wakeupinc.jpeg");
    ImageView img6 = new ImageView("img/explorer2.png");
    ImageView img7 = new ImageView("img/chat_messages.png");

    public static Stage ventanaNubeP;
    @FXML
    private MenuItem cerrarS;
    @FXML
    private MenuItem exit;
    @FXML
    private MenuItem conf;
    @FXML
    private MenuItem opcSync;
    @FXML
    public Label name;
    @FXML
    public Label enf;
    @FXML
    public ImageView imgUser;
    @FXML
    public Hyperlink linkEnf;
    @FXML
    public Label sexo;
    @FXML
    public Label correoL;
    @FXML
    private StackPane contentPane;
    private StackPane contentPaneArch;
    @FXML
    private Label textoSoltar;
    @FXML
    private TableView<Archivos> tablaNube;
    @FXML
    private TableColumn<Archivos, ImageView> type;
    @FXML
    private TableColumn<Archivos, String> nombreA;
    @FXML
    private TableColumn<Archivos, String> fechaA;
    @FXML
    private TableColumn<Archivos, Long> sizeA;
    @FXML
    private TableColumn<Archivos, String> linkVer;
    @FXML
    private MenuItem linkPagNosotros;
    @FXML
    private TextField buscarText;
    @FXML
    private TableView<Archivos> tablaNube1;
    @FXML
    private TableColumn<Archivos, ImageView> type1;
    @FXML
    private TableColumn<Archivos, String> nombreA1;
    @FXML
    private TableColumn<Archivos, String> fechaA1;
    @FXML
    private TableColumn<Archivos, Long> sizeA1;
    @FXML
    private TableColumn<Archivos, String> linkVer1;

    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();

    @FXML
    private LineChart<String, Number> graphDatos;
    @FXML
    private ChoiceBox<String> mesCB;
    @FXML
    private ChoiceBox<String> anoCB;
    @FXML
    private MenuItem verCar;
    @FXML
    private Pagination pages;
    int totalCuenta;
    @FXML
    private ListView<String> listpacientes;
    @FXML
    private MenuItem chat;

    Alerts alertVent = new Alerts();

    @FXML
    private LineChart<Number, Number> graficaAhorita;

    @FXML
    public void handleButtonAction1(ActionEvent event) throws IOException, Throwable {
        r = bd.consulta("call archivospersonat('" + correoCifrado + "');");
        ResultSet r2 = bd.consulta("call verajustes('" + correoCifrado + "');");
        ResultSet r3 = bd.consulta("call traepacientesarch('" + correoCifrado + "');");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/img/logo.png").toString()));
        alert.setTitle("Cloud Configuracion");
        alert.setHeaderText("Eliminando archivos...");
        alert.setContentText("Por favor espere.");
        alert.show();

        if (r2.next()) {
            String q = r2.getString("path");
            while (r3.next()) {
                File archUsuarios = new File(q + "/" + r3.getString("Nombre") + "/" + r3.getString("name"));
                archUsuarios.delete();
                if (r3.last()) {
                    File carUsuarios = new File(q + "/" + r3.getString("Nombre"));
                    carUsuarios.delete();
                }
            }
            while (r.next()) {
                File archivosNube = new File(q + "/" + r.getString("name"));
                if (archivosNube.exists()) {
                    archivosNube.delete();
                }
            }
            File sesionFile = new File("sitzung.dat");
            File carp = new File(q);
            //carp.delete();
            deleteAll(q);

            if (carp.list() != null || carp.exists()) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                Stage stage2 = (Stage) alert2.getDialogPane().getScene().getWindow();
                stage2.getIcons().add(new Image(this.getClass().getResource("/img/logo.png").toString()));
                alert2.setTitle("Cloud Sync");
                alert2.setHeaderText("Aviso");
                alert2.setContentText("Hay archivos todavia en la carpeta HeartCloud. Espere a que se borren.");
                alert2.setGraphic(new ImageView(this.getClass().getResource("/img/Recycle-Bin.png").toString()));
                alert2.show();
                String[] ficheros = carp.list();
                String nomA;
                for (String fichero : ficheros) {
                    nomA = fichero;
                    File noBorrado = new File(q + nomA);
                    noBorrado.delete();
                }
                alert2.close();
                if (carp.list() != null || carp.exists()) {
                    alertVent.Alerts("Cloud Sync", "Aviso",
                            "Imposible borrar todos los archivos.", "/img/Recycle-Bin.png", 3);
                }
            }
            sesionFile.delete();
            alert.close();
        }
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Log.fxml"));
        Scene escenaLogin = new Scene(root);
        heartPrevent.ventanaNubeLogin.getIcons().add(new Image("/img/logo.png"));
        heartPrevent.ventanaNubeLogin.setScene(escenaLogin);
        heartPrevent.ventanaNubeLogin.setTitle("HeartCloud");
        heartPrevent.ventanaNubeLogin.setResizable(false);
        heartPrevent.ventanaNubeLogin.show();
        LoginController.ventanaNubeLoad.close();
    }

    @FXML
    public void handleButtonAction2(ActionEvent event) throws IOException {
        Platform.exit();
    }

    @FXML
    public void handleButtonAction3(ActionEvent event) throws IOException, Throwable {
        ventanaNubeP = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Confi.fxml"));
        Scene escenaConf = new Scene(root);
        ventanaNubeP.getIcons().add(new Image("/img/logo.png"));
        ventanaNubeP.setScene(escenaConf);
        ventanaNubeP.setTitle("HeartCloud Configuracion");
        ventanaNubeP.setResizable(false);
        ventanaNubeP.show();
    }

    @FXML
    public void handleButtonAction4(ActionEvent event) throws IOException {
        ventanaNubeP = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Syncr.fxml"));
        Scene escenaSync = new Scene(root);
        ventanaNubeP.getIcons().add(new Image("/img/logo.png"));
        ventanaNubeP.setScene(escenaSync);
        ventanaNubeP.setTitle("HeartCloud_SyncConf");
        ventanaNubeP.setResizable(false);
        ventanaNubeP.show();
    }

    @FXML
    public void masEnfer() throws SQLException {
        r = bd.consulta("call datospersona('" + correoCifrado + "');");
        if (r.next()) {
            alertVent.Alerts("Alergias de " + r.getString("Nombre"), r.getString("Alergias"),
                    null, "/img/info.png", 1);
        }

    }

    public void datosUsuario() throws SQLException, IOException {
        r = bd.consulta("call datospersona('" + correoCifrado + "');");
        if (r.next()) {
            name.setText("Nombre: " + r.getString("Nombre") + " " + r.getString("apellido"));
            correoL.setText("Correo: " + Usuarios.getInstance().getCorreo().getText());
            if (r.getString("Genero").equalsIgnoreCase("m")) {
                sexo.setText("Genero: Masculino");
            } else {
                sexo.setText("Genero: Femenino");
            }
            if (r.getString("Alergias").length() < 12) {
                enf.setText("Alergias: " + r.getString("Alergias"));
            } else {
                linkEnf.setVisible(true);
            }
            Image image = new Image(bd.webdir + "UsrImagenes/" + r.getString("Imagen"));
            imgUser.setImage(image);
        }

    }

    public void upPDF(File pdf) throws IOException, Throwable {

        r = bd.consulta("call archivospersonat('" + correoCifrado + "');");
        while (r.next()) {
            totalCuenta += r.getBlob("File").length();
        }
        if (totalCuenta < (10485760)) {
            if (pdf.length() < (4194304)) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //get current date time with Date()
                Date date = new Date();
                String nompdf = pdf.getName();
                PreparedStatement stmt = bd.getConexion().prepareStatement("call altaPDF(?,?,?,0,?);");
                FileInputStream fis = new FileInputStream(pdf);
                stmt.setBinaryStream(1, fis);
                stmt.setString(2, nompdf);
                stmt.setString(3, correoCifrado);
                stmt.setString(4, dateFormat.format(date));
                stmt.execute();
                if (posicionArray == -1) {
                    posicionArray++;
                } else {
                    archivos.get(posicionArray);
                    posicionArray++;
                }
                fis.close();
                this.init();
                this.cargarPDFs();
//                alertVent.Alerts("HeartCloud", "Sincronizacion completa.",
//                        "Archivo Subido a la Base.", "/img/check.png", 4);
            } else {
                pdf.delete();
                alertVent.Alerts("HeartCloud", "Error al Subir Archivo",
                        "El tamaño del archivo debe ser menor a 4.5MB", "/img/document_error.png", 1);
            }
        } else {
            pdf.delete();
            alertVent.Alerts("HeartCloud", "Error al Subir Archivo",
                    "Ha llegado al Tamaño Máximo de su Cuenta.", "/img/document_error.png", 1);
        }
        totalCuenta = 0;
    }

    public void upPDF2(File pdf) throws IOException, Throwable {
        r = bd.consulta("call archivospersonat('" + correoCifrado + "');");
        while (r.next()) {
            totalCuenta += r.getBlob("File").length();
        }
        if (totalCuenta < (10485760)) {
            if (pdf.length() < (1000000)) {
                String nompdf = pdf.getName();
                PreparedStatement stmt = bd.getConexion().prepareStatement("call altaPDF(?,?,?,0);");
                FileInputStream fis = new FileInputStream(pdf);
                stmt.setBinaryStream(1, fis);
                stmt.setString(2, nompdf);
                stmt.setString(3, correoCifrado);
                stmt.execute();
                if (posicionArray == -1) {
                    posicionArray++;
                } else {
                    archivos.get(posicionArray);
                    posicionArray++;
                }
                fis.close();
                this.init();
                this.cargarPDFs();
            } else {
                pdf.delete();
                alertVent.Alerts("HeartCloud", "Error al Subir Archivo",
                        "Intente Otra Vez con un Archivo Menos Pesado.", "/img/document_error.png", 1);
            }
        }
        totalCuenta = 0;
    }

    private void inicializarTabla() throws SQLException {
        nombreA1.setCellValueFactory(new PropertyValueFactory<>("nombreA"));
        fechaA1.setCellValueFactory(new PropertyValueFactory<>("fechaA"));
        sizeA1.setCellValueFactory(new PropertyValueFactory<>("sizeA"));
        linkVer1.setCellValueFactory(new PropertyValueFactory<>("ver"));
        type1.setCellValueFactory(new PropertyValueFactory<>("typeA"));

        archivos2 = FXCollections.observableArrayList();
        //archivos2 = cargarArchivosBD2();
        tablaNube1.setItems(archivos2);
    }

    public ObservableList<Archivos> cargarArchivosBD() {
        ObservableList<Archivos> traer = FXCollections.observableArrayList();
        try {
            bd.conectar();
            r = bd.consulta("call archivosPersona('" + correoCifrado + "',0);");
            while (r.next()) {
                System.out.println("entra");
                traer.add(new Archivos(r.getString("name"), r.getString("fechaArch"), (r.getBlob("File").length()) / 1024, new Button("Ver"), null, "MisArchivos"));
            }
        } catch (SQLException e) {
        } catch (NullPointerException npe) {
            System.out.println("a:" + npe.getMessage());
            alertVent.Alerts("Conexion Fallo", "Error Base de Datos.",
                    "No se ha podido conectar.", null, 0);
        }
        return traer;
    }

//    public ObservableList<Archivos> cargarArchivosBD2() throws SQLException {
//        ObservableList<Archivos> traer = FXCollections.observableArrayList();
//        try {
//            bd.conectar();
//            r = bd.consulta("call archivosPersona('" + correoCifrado + "',1);");
//            while (r.next()) {
//                traer.add(new Archivos(r.getString("name"), r.getString("fechaArch"), (r.getBlob("File").length()) / 1024, new Button("Ver"), null,"archivos2"));
//            }
//        } catch (SQLException e) {
//        } catch (NullPointerException npe) {
//            System.out.println("b:"+npe.getMessage());
//            alertVent.Alerts("Conexion Fallo", "Error Base de Datos.",
//                    "No se ha podido conectar.", null, 0);
//        }
//        return traer;
//    }
    public void cargarPDFs() throws SQLException, FileNotFoundException, IOException {
        bd.conectar();
        r = bd.consulta("call archivospersonat('" + correoCifrado + "');");
        ResultSet r2 = bd.consulta("call verajustes('" + correoCifrado + "');");
        if (r2.next()) {
            String ruta1 = r2.getString("path") + "/MisArchivos";
            File carpSalida2 = new File(ruta1);
            if (!carpSalida2.exists()) {
                carpSalida2.mkdirs();
            }
            while (r.next()) {
                File theFile = new File(ruta1 + "/" + r.getString("name"));
                if (!theFile.exists()) {
                    FileOutputStream output = new FileOutputStream(theFile);
                    InputStream input = r.getBinaryStream("File");
                    byte[] buffer = new byte[1024];
                    while (input.read(buffer) > 0) {
                        output.write(buffer);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
            }
        }
    }

    public void elimPDF() throws SQLException {
//        bd.conectar();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/img/logo.png").toString()));
        alert.setTitle("Cloud Configuracion");
        alert.setHeaderText("¿Esta seguro de continuar?");
        alert.setContentText("El archivo será permanentemente borrado.");
        alert.setGraphic(new ImageView(this.getClass().getResource("/img/Recycle-Bin.png").toString()));
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            posicionArray = tablaNube.getSelectionModel().getSelectedIndex() + ((pages.getCurrentPageIndex() * limit.get()));

            String pdfSelec = (archivos.get(posicionArray).getNombreA());
            r = bd.consulta("call borrarPDF('" + pdfSelec + "','" + correoCifrado + "');");
            ResultSet r2 = bd.consulta("call verAjustes('" + correoCifrado + "');");
            if (r2.next()) {
                File archivosNube = new File(r2.getString("path") + "/MisArchivos/" + pdfSelec);
                if (archivosNube.exists()) {
                    archivosNube.delete();
                }
            }
            archivos.remove(posicionArray);
            posicionArray--;
            this.init();
        } else {
            alert.close();
        }

    }

    void addImage(Image i, StackPane pane) {
        imgUser.setImage(i);
        pane.getChildren().add(imgUser);
    }

    @FXML
    private void mouseDragDropped(final DragEvent e) {
        final Dragboard db = e.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            final File file = db.getFiles().get(0);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (!contentPane.getChildren().isEmpty()) {
                            contentPane.getChildren().remove(0);
                        }
                        byte[] imgbytes = Files.readAllBytes(file.toPath());
                        Webservicemethods wsm = new Webservicemethods();
                        wsm.uploadImg(imgbytes, Usuarios.getInstance().getCorreo().getText(), Usuarios.getInstance().getContrasenia().getText());
                        Image img = new Image(new FileInputStream(file.getAbsolutePath()));
                        addImage(img, contentPane);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (FileNotFoundException_Exception ex) {
                        Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException_Exception ex) {
                        Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException_Exception ex) {
                        Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
        e.setDropCompleted(success);
        e.consume();
    }

    @FXML
    private void mouseDragOver(final DragEvent e) {
        final Dragboard db = e.getDragboard();

        final boolean isAccepted
                = db.getFiles().get(0).getName().toLowerCase().endsWith(".png")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpeg")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".gif")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpg");

        if (db.hasFiles()) {
            if (isAccepted) {
                e.acceptTransferModes(TransferMode.COPY);
            }
        } else {
            e.consume();
        }
    }

    @FXML
    private void mouseDragOver2(final DragEvent e) {
        final Dragboard db = e.getDragboard();

        final boolean isAccepted
                = db.getFiles().get(0).getName().toLowerCase().endsWith(".png")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpeg")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".gif")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpg")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".doc")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".docx")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".ico")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".pdf");

        if (db.hasFiles()) {
            if (isAccepted) {
                e.acceptTransferModes(TransferMode.COPY);
            }
        } else {
            e.consume();
        }
    }

    @FXML
    private void mouseDragEntered(DragEvent event) throws SQLException {
        final Dragboard db = event.getDragboard();
        final boolean isAccepted
                = db.getFiles().get(0).getName().toLowerCase().endsWith(".png")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpeg")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpg")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".ico")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".gif")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".pdf")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".doc")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".docx");

        if (event.getGestureSource() == contentPaneArch && event.getDragboard().hasFiles()) {
            if (isAccepted) {
                r = bd.consulta("call validaarchivo('" + correoCifrado + "','"
                        + db.getFiles().get(0).getName() + "');");
                if (r.next()) {
                    if (r.getInt("msg") == 1) {
                        textoSoltar.setVisible(true);
                    }
                }
            }
        }
        event.consume();
    }

    @FXML
    private void mouseDragExited(DragEvent event) {
        textoSoltar.setVisible(false);
        event.consume();
    }

    @FXML
    private void mouseDragDropped2(DragEvent event) throws Throwable {

        try {
            bd.conectar();
            Dragboard db = event.getDragboard();
            boolean success = false;
            r = bd.consulta("call verajustes('" + correoCifrado + "');");
            if (r.next()) {
                ResultSet r2 = bd.consulta("call validaarchivo('" + correoCifrado + "','"
                        + db.getFiles().get(0).getName() + "')");
                if (db.hasFiles()) {
                    System.out.println("entro");
                    try {
                        if (r2.next()) {
                            System.out.println("entro2");
                            if (r2.getInt("msg") == 0) {
                                System.out.println("entro2.5");
                                alertVent.Alerts("Cloud Sync", "Archivo no subido.",
                                        "El nombre del archivo que ha querido subir coincide con otro.", "/img/check.png", 4);
                            } else {
                                System.out.println("entro3");
                                copyFile(db.getFiles().get(0), new File(r.getString("path") + "/MisArchivos/" + db.getFiles().get(0).getName()));
                                upPDF(new File(r.getString("path") + "/MisArchivos/" + db.getFiles().get(0).getName()));
                                this.cargarPDFs();
                                this.init();
                            }
                        }
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                        Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    success = true;
                }
                event.setDropCompleted(success);
            }
            event.consume();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void copyFile(File inFile, File outFile) throws IOException, SQLException {
        if (inFile.getCanonicalPath().equals(outFile.getCanonicalPath())) {
            return;
        }
        InputStream input = new FileInputStream(inFile.getPath());
        OutputStream output = new FileOutputStream(outFile.getPath());

        byte[] buffer = new byte[1024];
        while (input.read(buffer) > 0) {
            output.write(buffer);
        }
        output.flush();
        input.close();
        output.close();
    }

//    @FXML
//    private void mouseDrag(MouseEvent event) throws SQLException, InterruptedException {
//        this.elimPDF();
//    }
    public int aleatorio(int max, int min) {
        return (int) (Math.random() * (max - min)) + min;
    }

    @FXML
    public void linkNosotros(ActionEvent event) {
        if (java.awt.Desktop.isDesktopSupported()) {
            try {
                Desktop dk = Desktop.getDesktop();
                dk.browse(new URI(bd.webdirnosotros));
            } catch (URISyntaxException | IOException e) {
                System.out.println("Error al abrir URL: " + e.getMessage());
            }
        }
    }

    @FXML
    private void buscar(KeyEvent event) throws SQLException {
        pages.setCurrentPageIndex(0);
        changeTableView2(0, limit.get());
        resetPage();
        if (buscarText.getText().equals("")) {
            this.init();
        }
    }

    public ObservableList<Archivos> cargarArchivosEsp(String busq) {
        ObservableList<Archivos> traer = FXCollections.observableArrayList();
        try {
//            bd.conectar();
            r = bd.consulta("call archivopersona('" + correoCifrado + "','" + busq + "');");
            while (r.next()) {
                traer.add(new Archivos(r.getString("name"), r.getString("fechaArch"), (r.getBlob("File").length()) / 1024, new Button("Ver"), null, "MisArchivos"));
            }
        } catch (SQLException e) {
        } catch (NullPointerException npe) {
            System.out.println("c:" + npe.getMessage());
            alertVent.Alerts("Conexion Fallo", "Error Base de Datos.",
                    "No se ha podido conectar.", null, 0);
        }
        return traer;
    }

    public void detectaArchNuevos() throws SQLException, Throwable {
//        bd.conectar();
        r = bd.consulta("call verajustes('" + correoCifrado + "');");
        if (r.next()) {
            boolean check = false;
            String path = r.getString("path");
            File dir = new File(path.replace('\\', '/'));
            String[] ficheros = dir.list();
            File tmpFile;
            String nomA;
            if (ficheros != null) {
                for (String fichero : ficheros) {
                    nomA = fichero;
                    tmpFile = new File(path.replace('\\', '/') + "/" + nomA);
                    if (nomA.contains(".png") || nomA.contains(".jpg") || nomA.contains(".jpeg")
                            || nomA.contains(".pdf") //                            || nomA.contains(".docx")
                            ) {
                        ResultSet r2 = bd.consulta("call nuevoarchivo('" + correoCifrado + "','" + nomA + "');");
                        if (!r2.next()) {
                            check = true;
                            this.upPDF2(tmpFile);
                            if (r2.last()) {
                                alertVent.Alerts("Cloud Sync", "Archivo(s) subidos.",
                                        "Archivo(s) Nuevo(s) en la Carpeta " + path + " Sincronizados.", "/img/check.png", 4);
                            }
                        }
                    }
                }
            }
        }

    }

    public void detectaArchNuevos2() throws SQLException, Throwable {
//        bd.conectar();
        r = bd.consulta("call verajustes('" + correoCifrado + "');");
        if (r.next()) {
            boolean check = false;
            String path = r.getString("path");
            File dir = new File(path.replace('\\', '/'));
            String[] ficheros = dir.list();
            File tmpFile;
            String nomA;
            if (ficheros != null) {
                for (String fichero : ficheros) {
                    nomA = fichero;
                    tmpFile = new File(path.replace('\\', '/') + "/" + nomA);
                    if (nomA.contains(".png") || nomA.contains(".jpg") || nomA.contains(".jpeg")
                            || nomA.contains(".pdf") //                            || nomA.contains(".docx")
                            ) {
                        ResultSet r2 = bd.consulta("call nuevoarchivo('" + correoCifrado + "','" + nomA + "');");
                        if (!r2.next()) {
                            check = true;
                            this.upPDF2(tmpFile);
                            if (r2.last()) {
                                alertVent.Alerts("Cloud Sync", "Archivo(s) subidos.",
                                        "Archivo(s) Nuevo(s) en la Carpeta " + path + " Sincronizados.", "/img/check.png", 4);
                            }
                        }
                    }
                }
                if (check == false) {
                    alertVent.Alerts("Cloud Sync", "No Hay Archivos Nuevos.", "Copie Archivos a la Carpeta "
                            + path + " o a la tabla.", "/img/syncicon.png", 1);
                }
            }
        }

    }

    @FXML
    public void recharge(ActionEvent event) throws Throwable {
        this.detectaArchNuevos2();
    }

    @FXML
    private void elimina(KeyEvent event) throws SQLException {
        if (event.getCode().getName().equals("Delete") || event.getCode().getName().equals("Backspace")) {
            this.elimPDF();
        }
    }

    XYChart.Series series = new XYChart.Series();

    public void datosGrafica() throws SQLException {
        series.setName("Grafica de Presion/Dia");
        r = bd.consulta("call datospresion('" + correoCifrado + "', " + (Integer.parseInt(mesCB.getSelectionModel().getSelectedItem()))
                + "," + anoCB.getSelectionModel().getSelectedItem() + ");");
        while (r.next()) {
            if (r.getInt("msg") == 1) {
                series.setName("Grafica del " + r.getString("Mes") + "," + r.getString("Anio"));
                series.getData().add(new XYChart.Data(r.getString("Dia"), r.getInt("presion")));
            }
        }
        graphDatos.getData().add(series);
    }

    public void datosGraficaCamb(int nuevoMes, String nuevoAnio) throws SQLException {
        bd.conectar();
        r = bd.consulta("call datospresion('" + correoCifrado + "',"
                + nuevoMes
                + "," + nuevoAnio + ");");
        while (r.next()) {
            if (r.getInt("msg") == 1) {
                series.setName("Grafica del " + r.getString("Mes") + ", " + r.getString("Anio"));
                series.getData().add(new XYChart.Data(r.getString("Dia"), r.getInt("presion")));
                graphDatos.getData().removeAll(series);
                graphDatos.getData().add(series);
            } else if (r.getInt("msg") == 0) {
                series.setName("Grafica de Presion/Dia");
                graphDatos.getData().add(series);
            }
        }
    }

    @FXML
    private void verCarp(ActionEvent event) throws IOException, SQLException {
        bd.conectar();
        r = bd.consulta("call verajustes('" + correoCifrado + "');");
        if (r.next()) {
            File file = new File(r.getString("path").replace('\\', '/'));
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        }
    }

    public void init() {
        pages.setCurrentPageIndex(0);
        changeTableView(0, limit.get());
        resetPage();
    }

    public void changeTableView(int index, int limit) {
        int newIndex = index * limit;
        archivos = this.cargarArchivosBD();
        List<Archivos> trans = archivos.subList(Math.min(newIndex, archivos.size()), Math.min(archivos.size(), newIndex + limit));
        tablaNube.getItems().clear();
        tablaNube.setItems(null);
        ObservableList<Archivos> asd = FXCollections.observableArrayList();
        tablaNube.setItems(asd);
        for (Archivos t : trans) {
            asd.add(t);
        }
    }

    public void changeTableView2(int index, int limit) {
        int newIndex = index * limit;
        archivos = this.cargarArchivosEsp(buscarText.getText());

        List<Archivos> trans = archivos.subList(Math.min(newIndex, archivos.size()), Math.min(archivos.size(), newIndex + limit));
        tablaNube.getItems().clear();
        tablaNube.setItems(null);
        ObservableList<Archivos> asd = FXCollections.observableArrayList();
        tablaNube.setItems(asd);
        for (Archivos t : trans) {
            asd.add(t);
        }
    }

    public void resetPage() {
        if (archivos.size() == 0) {
            pages.setPageCount(1);
        } else {
            pages.setPageCount((int) (Math.ceil(archivos.size() * 1.0 / limit.get())));
        }
    }

//    public void anadeAnio() {
//        System.out.println("se agrega anio a choicebox");
////        anoCB.getSelectionModel().
//    }
    public void setValueFactory() {
        nombreA.setCellValueFactory(new PropertyValueFactory<>("nombreA"));
        fechaA.setCellValueFactory(new PropertyValueFactory<>("fechaA"));
        sizeA.setCellValueFactory(new PropertyValueFactory<>("sizeA"));
        linkVer.setCellValueFactory(new PropertyValueFactory<>("ver"));
        type.setCellValueFactory(new PropertyValueFactory<>("typeA"));
    }

    ObservableList<String> patients;

    public void agregaPacientes() throws SQLException, IOException {
        patients = FXCollections.observableArrayList();
        patients = pacientes();
        listpacientes.setItems(patients);
    }

    public ObservableList<String> pacientes() throws SQLException, IOException {
        ObservableList<String> traer = FXCollections.observableArrayList();
        try {
            bd.conectar();
            r = bd.consulta("call traepacientes2('" + correoCifrado + "');");
            traer.add(cifrado.decriptaAES(correoCifrado));
            while (r.next()) {
                traer.add(cifrado.decriptaAES(r.getString("Correo")));
            }
            this.cargarArchivosPacientes();
        } catch (SQLException e) {
        } catch (NullPointerException npe) {
            System.out.println("d:" + npe.getMessage());
            alertVent.Alerts("Conexion Fallo", "Error Base de Datos.",
                    "No se ha podido conectar.", null, 0);
        }
        return traer;
    }

    public void cargarArchivosPacientes() throws SQLException, FileNotFoundException, IOException {
        bd.conectar();
        r = bd.consulta("call traepacientesarch('" + correoCifrado + "');");
        ResultSet r2 = bd.consulta("call verajustes('" + correoCifrado + "');");
        if (r2.next()) {
            String carpInicio = r2.getString("path");

            while (r.next()) {
                String nombre = r.getString("nombre").replaceAll(" ", "");
                File theFile = new File(carpInicio + "/" + nombre + "/" + r.getString("name"));
                try {
                    FileOutputStream output = new FileOutputStream(theFile);
                    InputStream input = r.getBinaryStream("File");
                    byte[] buffer = new byte[1024];
                    while (input.read(buffer) > 0) {
                        output.write(buffer);
                    }
                    output.close();
                    input.close();
                } catch (FileNotFoundException ex) {
                    System.out.println("entros");
                    File usrFolder = new File(carpInicio + "/" + nombre);
                    usrFolder.mkdirs();

                    FileOutputStream output = new FileOutputStream(theFile);
                    InputStream input = r.getBinaryStream("File");
                    byte[] buffer = new byte[1024];
                    while (input.read(buffer) > 0) {
                        output.write(buffer);
                    }
                    output.close();
                    input.close();
                }

            }
        }
    }

    public ObservableList<Archivos> cargarArchBDPacientes(String corNuevo) {
        ObservableList<Archivos> traer = FXCollections.observableArrayList();
        try {
            r = bd.consulta("call traepacientesarchxid('" + correoCifrado + "','" + cifrado.encriptaAES(corNuevo) + "');");
            while (r.next()) {
                String nomfolder;
                if (correoCifrado.equals(cifrado.encriptaAES(corNuevo))) {
                    nomfolder = "MisArchivos";
                } else {
                    nomfolder = r.getString("nombre").replaceAll(" ", "");
                }

                traer.add(new Archivos(r.getString("name"), r.getString("fechaArch"), (r.getBlob("File").length()) / 1024, new Button("Ver"), null, nomfolder));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException npe) {
            System.out.println("e:" + npe.getMessage());
            alertVent.Alerts("Conexion Fallo", "Error Base de Datos.",
                    "No se ha podido conectar.", null, 0);
        }
        return traer;
    }

    public void cargaPacientesTabla(String corNuevo) {
        nombreA1.setCellValueFactory(new PropertyValueFactory<>("nombreA"));
        fechaA1.setCellValueFactory(new PropertyValueFactory<>("fechaA"));
        sizeA1.setCellValueFactory(new PropertyValueFactory<>("sizeA"));
        linkVer1.setCellValueFactory(new PropertyValueFactory<>("ver"));
        type1.setCellValueFactory(new PropertyValueFactory<>("typeA"));

        archivos2 = FXCollections.observableArrayList();
        archivos2 = cargarArchBDPacientes(corNuevo);
        tablaNube1.setItems(archivos2);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/img/logo.png").toString()));
            alert.setTitle("Cloud Sync");
            alert.setHeaderText("Cargando Archivos...");
            alert.setContentText("Espere un momento.");
            alert.setGraphic(new ImageView(this.getClass().getResource("/img/wait.png").toString()));
            alert.show();

            xAxis.setLabel("Dia");
            yAxis.setLabel("Presion");

//            xAxis2.setLabel("Segundos");
//            yAxis2.setLabel("Presion");
            mes = calendar.get(Calendar.MONTH);
            annio = Integer.toString(calendar.get(Calendar.YEAR));

            bd.conectar();
            bd.consulta("call path('" + correoCifrado + "','');");
            mesCB.getSelectionModel().select(mes);
            anoCB.getSelectionModel().select(annio);
            mesCB.setTooltip(new Tooltip("Selecciona el Mes"));
            anoCB.setTooltip(new Tooltip("Selecciona el Año"));

            conf.setGraphic(img);
            opcSync.setGraphic(img2);
            cerrarS.setGraphic(img4);
            exit.setGraphic(img3);
            verCar.setGraphic(img6);
            linkPagNosotros.setGraphic(img5);
            chat.setGraphic(img7);

            linkEnf.setVisible(false);

            limit = new SimpleIntegerProperty(5);
            this.datosUsuario();
            this.datosGrafica();

            animation = new Timeline();
            this.datosGraficaHoy();

            animation.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                    (ActionEvent actionEvent) -> startGrafica()));
            animation.setCycleCount(Animation.INDEFINITE);
            play();

//            String dirWeb = "www.wakeupinc.esy.es";
//            int puerto = 80;
//            try {
//                Socket s = new Socket(dirWeb, puerto);
//                if (s.isConnected()) {
//                    Reports.Reporte rep = new Reports.Reporte();
//                    rep.Reporte();
//                }
//                s.close();
//            } catch (Exception e) {
//                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
//                Stage stage1 = (Stage) alert3.getDialogPane().getScene().getWindow();
//                stage1.getIcons().add(new Image(this.getClass().getResource("/img/logo.png").toString()));
//                alert3.setGraphic(new ImageView(this.getClass().getResource("/img/warn.png").toString()));
//                alert3.setTitle("HeartCloud");
//                alert3.setHeaderText("No hay conexion.");
//                alert3.setContentText("Mueva sus archivos a la carpeta de HeartCloud y despues seran sincronizados.");
//                alert3.show();
//            }
            ResultSet r2 = bd.consulta("call verajustes('" + correoCifrado + "');");
            if (r2.next()) {
                if (r2.getInt("SyncAct") == 1) {
                    pages.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            changeTableView(newValue.intValue(), limit.get());
                        }
                    });
                    this.setValueFactory();
                    this.cargarPDFs();
                    this.agregaPacientes();
                    this.detectaArchNuevos();
                }
            }
            this.init();
            mesCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    try {
                        graphDatos.getXAxis().setAutoRanging(true);
                        graphDatos.getYAxis().setAutoRanging(true);
                        if (!series.getData().isEmpty()) {
                            series.getData().remove(0, series.getData().size());
                        }
                        graphDatos.getData().removeAll(series);
                        datosGraficaCamb(Integer.parseInt(mesCB.getItems().get((Integer) newValue)), anoCB.getSelectionModel().getSelectedItem());
                    } catch (SQLException ex) {
                        Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            anoCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    try {
                        graphDatos.getXAxis().setAutoRanging(true);
                        graphDatos.getYAxis().setAutoRanging(true);
                        if (!series.getData().isEmpty()) {
                            series.getData().remove(0, series.getData().size());
                        }
                        graphDatos.getData().removeAll(series);
                        datosGraficaCamb(Integer.parseInt(mesCB.getSelectionModel().getSelectedItem()), (anoCB.getItems().get((Integer) newValue)));
                    } catch (SQLException ex) {
                        Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
//            limit.addListener(new ChangeListener<Number>() {
//                @Override
//                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                    changeTableView(pages.getCurrentPageIndex(), newValue.intValue());
//                }
//
//            });

            listpacientes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable,
                        String oldValue, String newValue) {
                    cargaPacientesTabla(newValue);
                }
            });
            alert.close();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void abrirChat(ActionEvent event) throws IOException {
        ventanaNubeP = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Chat.fxml"));
        Scene escenaChat = new Scene(root);
        escenaChat.getStylesheets().add(getClass().getResource("/chat/chat.css").toExternalForm());
        ventanaNubeP.getIcons().add(new Image("/img/logo.png"));
        ventanaNubeP.setScene(escenaChat);
        ventanaNubeP.setTitle("HeartCloud Chat");
        ventanaNubeP.setResizable(false);
        ventanaNubeP.show();
    }

    private Timeline animation;
    private double sequence = 0;
    private double sequence2 = 0;
    private double y = 10;
    private final int max_puntos = 25, max = 120, min = 80;

    XYChart.Series series2 = new XYChart.Series<>();
    XYChart.Series series3 = new XYChart.Series<>();
    NumberAxis xAxis2;

    private int valores() {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    private int valores2() {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public void play() {
        animation.play();
    }

    public void stop() {
        animation.pause();
    }

    public void datosGraficaHoy() {
        xAxis2 = new NumberAxis(0, max_puntos + 1, 2);
        final NumberAxis yAxis2 = new NumberAxis(min - 10, max + 10, 0);

        graficaAhorita = new LineChart<>(xAxis2, yAxis2);
        graficaAhorita.setAnimated(false);
        graficaAhorita.setLegendVisible(false);
        graficaAhorita.setTitle("Presion Sistólica y Diastólica");
        xAxis2.setLabel("Valores Presiones");
        xAxis2.setForceZeroInRange(false);
        yAxis2.setLabel("Tiempo (s)");
        yAxis2.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis2, "", null));

        series2.setName("Presion Sistólica");
        series3.setName("Presion Diastólica");

        series2.getData().add(new XYChart.Data<>(5, 10));
        series3.getData().add(new XYChart.Data<>(8, 20));

        graficaAhorita.getData().addAll(series2, series3);
    }

    private void startGrafica() {
        series2.getData().add(new XYChart.Data<Number, Number>(++sequence, valores()));
        if (sequence > max_puntos) {
            series2.getData().remove(0);
        }
        if (sequence > max_puntos - 1) {
            xAxis2.setLowerBound(xAxis2.getLowerBound() + 1);
            xAxis2.setUpperBound(xAxis2.getUpperBound() + 1);
        }

        series3.getData().add(new XYChart.Data<Number, Number>(++sequence2, valores2()));
        if (sequence2 > max_puntos) {
            series3.getData().remove(0);
        }
        if (sequence2 > max_puntos - 1) {
            xAxis2.setLowerBound(xAxis2.getLowerBound() + 1);
            xAxis2.setUpperBound(xAxis2.getUpperBound() + 1);
        }
    }

    @FXML
    private void ponerPlay(ActionEvent event) {
        play();
    }

    @FXML
    private void ponerPausa(ActionEvent event) {
        stop();
    }

    public void deleteAll(String path) {
        Path dir = Paths.get(path);
        try {
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                        throws IOException {
                    System.out.println("Deleting file: " + file);
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir,
                        IOException exc) throws IOException {
                    System.out.println("Deleting dir: " + dir);
                    if (exc == null) {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    } else {
                        throw exc;
                    }
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
