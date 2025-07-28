package controller;

import dao.ConsultationCandidatsMoniteurDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Eleve;

import java.io.IOException;

public class ConsultationCandidatsMoniteurController {

    @FXML
    private TableView<Eleve> CandidatsTable;

    @FXML
    private TableColumn<Eleve, Integer> idcolomn;

    @FXML
    private TableColumn<Eleve, String> NomColumn;

    @FXML
    private TableColumn<Eleve, String> PrenomColumn;

    @FXML
    private TableColumn<Eleve, String> AdresseColumn;

    @FXML
    private TableColumn<Eleve, String> TelColumn;

    @FXML
    private TableColumn<Eleve, String> EmailColumn;

    @FXML
    private TableColumn<Eleve, String> DateInscriptionColumn;

    private ObservableList<Eleve> elevesList;

    @FXML
    public void initialize() {
        // Lier les colonnes du tableau avec les propriétés du modèle
        idcolomn.setCellValueFactory(new PropertyValueFactory<>("idEleve"));
        NomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        PrenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        AdresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        TelColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        DateInscriptionColumn.setCellValueFactory(new PropertyValueFactory<>("dateInscription"));

        // Charger les données
        loadCandidatsData();
    }

    private void loadCandidatsData() {
        int idMoniteur = LoginController.getIdMoniteur(); // Récupère l'ID du moniteur authentifié
        System.out.println("Chargement des candidats pour le moniteur ID : " + idMoniteur);

        ConsultationCandidatsMoniteurDao dao = new ConsultationCandidatsMoniteurDao();
        elevesList = FXCollections.observableArrayList(dao.getCandidatsByMoniteur(idMoniteur));

        if (elevesList.isEmpty()) {
            System.out.println("Aucun candidat trouvé pour le moniteur ID : " + idMoniteur);
        }

        CandidatsTable.setItems(elevesList);
    }

    @FXML
    private void returntomain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MoniteurMainInterface.fxml"));
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