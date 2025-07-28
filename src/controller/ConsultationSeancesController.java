package controller;

import dao.ConsultationSeancesDao;
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
import model.Seance;

import java.io.IOException;

public class ConsultationSeancesController {

    @FXML
    private TableView<Seance> seancesTable;

    @FXML
    private TableColumn<Seance, Integer> idColumn;

    @FXML
    private TableColumn<Seance, Integer> idMoniteurColumn;

    @FXML
    private TableColumn<Seance, Integer> idVehiculeColumn;

    @FXML
    private TableColumn<Seance, Integer> idEleveColumn;

    @FXML
    private TableColumn<Seance, String> dateColumn;

    @FXML
    private TableColumn<Seance, Integer> dureeColumn;

    @FXML
    private TableColumn<Seance, String> typeColumn;

    @FXML
    private TableColumn<Seance, String> statutColumn;

    @FXML
    private TableColumn<Seance, String> observationsColumn;

    private ObservableList<Seance> seancesList;

    @FXML
    public void initialize() {
        // Lier les colonnes du tableau avec les propriétés du modèle
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idSeance"));
        idMoniteurColumn.setCellValueFactory(new PropertyValueFactory<>("idMoniteur"));
        idVehiculeColumn.setCellValueFactory(new PropertyValueFactory<>("idVehicule"));
        idEleveColumn.setCellValueFactory(new PropertyValueFactory<>("idEleve"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateSeance"));
        dureeColumn.setCellValueFactory(new PropertyValueFactory<>("duree"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeSeance"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        observationsColumn.setCellValueFactory(new PropertyValueFactory<>("observations"));

        // Charger les données
        loadSeancesData();
    }

    private void loadSeancesData() {
        ConsultationSeancesDao dao = new ConsultationSeancesDao();
        seancesList = FXCollections.observableArrayList(dao.getAllSeances());
        seancesTable.setItems(seancesList);
    }

    @FXML
    private void returntomain(ActionEvent event) {
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
