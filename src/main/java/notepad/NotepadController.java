package notepad;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;

public class NotepadController extends Application implements Initializable {

    private FileWrapper fileWrapper;
    private Stage stage;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField ownerTF;

    @FXML
    private TextField lastDateTF;

    @FXML
    private TextField creationTF;

    @FXML
    private TextField sizeTF;

    @FXML
    private TextField authorNameTF;

    @FXML
    private TextField authorOrgTF;

    @FXML
    private TextField typeTF;

    @FXML
    private TextField fileNameTF;

    @FXML
    private AnchorPane anchorPane;

    public void open(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(getStage());
        try {
            fileWrapper.readFromFile(file);
            textArea.setText(fileWrapper.getText());
            Map<String, String> attr = fileWrapper.getAttr();
            typeTF.setText(attr.get("user:type"));
            sizeTF.setText(attr.get("size"));
            lastDateTF.setText(attr.get("lastModifiedTime"));
            creationTF.setText(attr.get("creationTime"));
            ownerTF.setText(attr.get("acl:owner"));
            authorNameTF.setText(attr.get("user:authorName"));
            authorOrgTF.setText(attr.get("user:authorOrganization"));
            fileNameTF.setText(file.getName());
        } catch (IOException e) {
            createExceptionAlert(e);
        }
    }

    public void save(MouseEvent mouseEvent) {
        fileWrapper.updateFileWrapper(
                textArea.getText(),
                typeTF.getText(),
                authorNameTF.getText(),
                authorOrgTF.getText()
        );

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(getStage());

        try {
            fileWrapper.saveToFile(file);
        } catch (IOException e) {
            createExceptionAlert(e);
        }
    }

    public void exit(MouseEvent mouseEvent) {
        getStage().close();
    }

    public void initialize(URL location, ResourceBundle resources) {
        this.fileWrapper = new FileWrapper();
    }

    private Stage getStage() {
        if (stage == null) {
            stage = (Stage) anchorPane.getScene().getWindow();
        }
        return stage;
    }

    public NotepadController() {
        fileWrapper = new FileWrapper();
    }

    private void createExceptionAlert(Throwable e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/notepad.fxml"));
        primaryStage.setTitle("Notepad--");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
