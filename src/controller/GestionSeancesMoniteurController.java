package controller;

import dao.GestionSeancesDao;
import dao.SeanceMoniteurDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Seance;

import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GestionSeancesMoniteurController {

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

    @FXML
    private TextField Statut;

    @FXML
    private TextArea Observation;

    private ObservableList<Seance> seancesList;
    private final SeanceMoniteurDao seanceDao = new SeanceMoniteurDao();

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

        // Charger les données des séances
        loadSeancesData();
        
        // Listener pour charger les détails d'une séance sélectionnée
        seancesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadSeanceDetails(newSelection);
            } else {
                clearFields();
            }
        });
    }

    private void loadSeancesData() {
        int idMoniteur = LoginController.getIdMoniteur(); // Récupère l'ID du moniteur authentifié
        System.out.println("Chargement des séances pour le moniteur ID : " + idMoniteur);

        SeanceMoniteurDao dao = new SeanceMoniteurDao();
        seancesList = FXCollections.observableArrayList(dao.getSeancesByMoniteur(idMoniteur));

        if (seancesList.isEmpty()) {
            System.out.println("Aucune séance trouvée pour le moniteur ID : " + idMoniteur);
        }

        seancesTable.setItems(seancesList);
    }
    
    private void loadSeanceDetails(Seance seance) {
        Statut.setText(seance.getStatut());
        Observation.setText(seance.getObservations());
    }

    /*@FXML
    private void updateInfo() {
        // Logique pour mettre à jour les observations ou le statut d'une séance
        String newStatut = Statut.getText();
        String newObservation = Observation.getText();

        if (seancesTable.getSelectionModel().getSelectedItem() != null) {
            Seance selectedSeance = seancesTable.getSelectionModel().getSelectedItem();
            selectedSeance.setStatut(newStatut);
            selectedSeance.setObservations(newObservation);

            System.out.println("Statut mis à jour : " + newStatut);
            System.out.println("Observations mises à jour : " + newObservation);
        } else {
            System.out.println("Aucune séance sélectionnée.");
        }
    }*/
    @FXML
    private void updateInfo(ActionEvent event) {
    try {
        // Vérifier si une séance est sélectionnée
        Seance selectedSeance = seancesTable.getSelectionModel().getSelectedItem();
        if (selectedSeance == null) {
            showAlert("Erreur", "Veuillez sélectionner une séance à mettre à jour.", AlertType.ERROR);
            return;
        }

        // Récupération des valeurs dans les champs de l'interface utilisateur
        String newStatut = Statut.getText().trim();
        String newObservation = Observation.getText().trim();

        // Vérification si les champs obligatoires sont remplis
        if (newStatut.isEmpty() || newObservation.isEmpty()) {
            showAlert("Erreur", "Le statut et les observations doivent être renseignés.", AlertType.ERROR);
            return;
        }

        // Mise à jour des attributs de la séance sélectionnée
        selectedSeance.setStatut(newStatut);
        selectedSeance.setObservations(newObservation);

        // Mise à jour dans la base de données (simulation ici, à adapter selon votre DAO)
        seanceDao.updateInfo(selectedSeance);

        // Rafraîchir la table des séances
        int index = seancesTable.getSelectionModel().getSelectedIndex();
        seancesTable.refresh(); // Rafraîchit la table
        seancesTable.getSelectionModel().select(index); // Re-sélectionner la ligne mise à jour

        // Afficher un message de succès
        showAlert("Succès", "Informations de la séance mises à jour avec succès.", AlertType.INFORMATION);

    } catch (Exception e) {
        showAlert("Erreur", "Une erreur s'est produite lors de la mise à jour : " + e.getMessage(), AlertType.ERROR);
    }
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
            System.out.println("Erreur lors du chargement de la vue : /view/MoniteurMainInterface.fxml");
            e.printStackTrace();
        }
    }
    
    private void clearFields() {
        Statut.clear();
        Observation.clear();
    }
    
        private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
