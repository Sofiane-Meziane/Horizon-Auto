package controller;

import dao.ConsultationPaiementsDao;
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
import model.Paiement;

import java.io.IOException;

public class ConsultationPaiementsController {

    @FXML
    private TableView<Paiement> paiementsTable;

    @FXML
    private TableColumn<Paiement, Integer> idColumn;

    @FXML
    private TableColumn<Paiement, Integer> idEleveColumn;

    @FXML
    private TableColumn<Paiement, String> dateColumn;

    @FXML
    private TableColumn<Paiement, Double> montantColumn;

    @FXML
    private TableColumn<Paiement, String> moyenPaiementColumn;

    private ObservableList<Paiement> paiementsList;

    @FXML
    public void initialize() {
        // Lier les colonnes du tableau avec les propriétés du modèle
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idPaiement"));
        idEleveColumn.setCellValueFactory(new PropertyValueFactory<>("idEleve"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("datePaiement"));
        montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        moyenPaiementColumn.setCellValueFactory(new PropertyValueFactory<>("moyenPaiement"));

        // Charger les données
        loadPaiementsData();
    }

    private void loadPaiementsData() {
        ConsultationPaiementsDao dao = new ConsultationPaiementsDao();
        paiementsList = FXCollections.observableArrayList(dao.getAllPaiements());
        paiementsTable.setItems(paiementsList);
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
