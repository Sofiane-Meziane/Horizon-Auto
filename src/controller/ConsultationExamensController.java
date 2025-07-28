package controller;

import dao.ConsultationExamensDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Examen;

import java.io.IOException;

public class ConsultationExamensController {

    @FXML
    private TableView<Examen> ExamensTable;

    @FXML
    private TableColumn<Examen, Integer> idColumn;

    @FXML
    private TableColumn<Examen, String> typeExamenColumn;

    @FXML
    private TableColumn<Examen, String> dateExamenColumn;

    @FXML
    private TableColumn<Examen, String> lieuColumn;

    private ObservableList<Examen> examensList;

    @FXML
    public void initialize() {
        // Lier les colonnes du tableau avec les propriétés du modèle
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idExamen"));
        typeExamenColumn.setCellValueFactory(new PropertyValueFactory<>("typeExamen"));
        dateExamenColumn.setCellValueFactory(new PropertyValueFactory<>("dateExamen"));
        lieuColumn.setCellValueFactory(new PropertyValueFactory<>("lieu"));

        // Charger les données
        loadExamensData();
    }

    private void loadExamensData() {
        ConsultationExamensDao dao = new ConsultationExamensDao();
        examensList = FXCollections.observableArrayList(dao.getAllExamens());
        ExamensTable.setItems(examensList);
    }

    @FXML
    private void returnToMain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminMainInterface.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Main Interface");
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
