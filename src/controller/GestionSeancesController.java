package controller;

import dao.GestionSeancesDao;
import dao.GestionCandidatsDao;
import dao.UtilisateurDao;
import dao.VehiculeDao;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Seance;

public class GestionSeancesController implements Initializable {

    @FXML
    private TextField idVehicule;
    @FXML
    private TextField idEleve;
    @FXML
    private TextField idMoniteur;
    @FXML
    private TextField DateSeance;
    @FXML
    private TextField TypeSeance;
    @FXML
    private TextField Duree;

    @FXML
    private TableView<Seance> SeancesTable;
    @FXML
    private TableColumn<Seance, Integer> idColumn;
    @FXML
    private TableColumn<Seance, Integer> idVehiculeColumn;
    @FXML
    private TableColumn<Seance, Integer> idCandidatColumn;
    @FXML
    private TableColumn<Seance, Integer> idMoniteurColumn;
    @FXML
    private TableColumn<Seance, LocalDate> DateColumn;
    @FXML
    private TableColumn<Seance, Integer> DureeColumn;
    @FXML
    private TableColumn<Seance, String> TypeColumn;
    @FXML
    private TableColumn<Seance, String> StatutColumn;
    @FXML
    private TableColumn<Seance, String> ObservationColumn;

    @FXML
    private Button addbtn;
    @FXML
    private Button updatebtn;
    @FXML
    private Button deletebtn;
    @FXML
    private Button returnbtn;

    private ObservableList<Seance> seanceList;
    private final GestionSeancesDao seanceDao = new GestionSeancesDao();
    private final GestionCandidatsDao personneDao = new GestionCandidatsDao();
    private final UtilisateurDao utilisateurDao = new UtilisateurDao();
    private final VehiculeDao vehiculeDao = new VehiculeDao();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configuration des colonnes du tableau
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idSeance"));
        idMoniteurColumn.setCellValueFactory(new PropertyValueFactory<>("idMoniteur"));
        idVehiculeColumn.setCellValueFactory(new PropertyValueFactory<>("idVehicule"));
        idCandidatColumn.setCellValueFactory(new PropertyValueFactory<>("idEleve"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<>("dateSeance"));
        DureeColumn.setCellValueFactory(new PropertyValueFactory<>("duree"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("typeSeance"));
        StatutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        ObservationColumn.setCellValueFactory(new PropertyValueFactory<>("observations"));

        // Chargement des données de la base dans la table
        loadSeanceData();

        // Listener pour charger les détails d'une séance sélectionnée
        SeancesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadSeanceDetails(newSelection);
            } else {
                clearFields();
            }
        });
    }

    private void loadSeanceData() {
        List<Seance> seances = seanceDao.getAllSeances();
        seanceList = FXCollections.observableArrayList(seances);
        SeancesTable.setItems(seanceList);
    }

    private void loadSeanceDetails(Seance seance) {
        idMoniteur.setText(String.valueOf(seance.getIdMoniteur()));
        idVehicule.setText(String.valueOf(seance.getIdVehicule()));
        idEleve.setText(String.valueOf(seance.getIdEleve()));
        DateSeance.setText(seance.getDateSeance().toString());
        Duree.setText(String.valueOf(seance.getDuree()));
        TypeSeance.setText(seance.getTypeSeance());
    }

    private void clearFields() {
        idVehicule.clear();
        idEleve.clear();
        idMoniteur.clear();
        DateSeance.clear();
        TypeSeance.clear();
        Duree.clear();
    }

    @FXML
    private void addseance(ActionEvent event) {
        String idMoniteurText = idMoniteur.getText();
        String idVehiculeText = idVehicule.getText();
        String idCandidatText = idEleve.getText();
        String dateSeanceText = DateSeance.getText();
        String dureeText = Duree.getText().trim();
        String typeSeanceText = TypeSeance.getText();
        String statutText = "En attente"; // Default statut
        String observationText = ""; // Default observation

        if ("code".equalsIgnoreCase(typeSeanceText.trim())) {
            if (idVehiculeText != null && !idVehiculeText.isEmpty()) {
                showAlert("Information", "Vous n'aurez pas besoin d'un véhicule pour une séance de type code.", AlertType.INFORMATION);
                return;
            }
        }

        if (idCandidatText.isEmpty() || idMoniteurText.isEmpty() || dateSeanceText.isEmpty() || typeSeanceText.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis sauf le champ du véhicule.", AlertType.ERROR);
            return;
        }

        if ((typeSeanceText.equalsIgnoreCase("créneau") || typeSeanceText.equalsIgnoreCase("conduite")) && idVehiculeText.isEmpty()) {
            showAlert("Erreur", "Un véhicule est obligatoire pour une séance de type créneau ou conduite.", AlertType.ERROR);
            return;
        }

        try {
            Integer idVehiculeInt = idVehiculeText.isEmpty() ? null : Integer.parseInt(idVehiculeText);
            int idMoniteurInt = Integer.parseInt(idMoniteurText);
            int idCandidatInt = Integer.parseInt(idCandidatText);
            int dureeInt = Integer.parseInt(dureeText);

            if (!utilisateurDao.exists(idMoniteurInt, "Moniteur")) {
                showAlert("Erreur", "Le moniteur spécifié n'existe pas.", AlertType.ERROR);
                return;
            }

            if (idVehiculeInt != null && !vehiculeDao.exists(idVehiculeInt)) {
                showAlert("Erreur", "Le véhicule spécifié n'existe pas.", AlertType.ERROR);
                return;
            }

            if (!personneDao.exists(idCandidatInt)) {
                showAlert("Erreur", "Le candidat spécifié n'existe pas.", AlertType.ERROR);
                return;
            }

            Seance newSeance = new Seance();
            newSeance.setIdVehicule(idVehiculeInt);
            newSeance.setIdEleve(idCandidatInt);
            newSeance.setIdMoniteur(idMoniteurInt);
            newSeance.setDateSeance(LocalDate.parse(dateSeanceText));
            newSeance.setDuree(dureeInt);
            newSeance.setTypeSeance(typeSeanceText);
            newSeance.setStatut(statutText);
            newSeance.setObservations(observationText);

            seanceDao.addSeance(newSeance);
            seanceList.add(newSeance);
            clearFields();
            showAlert("Succès", "Séance ajoutée avec succès.", AlertType.INFORMATION);

        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite : " + e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void updateseance(ActionEvent event) {
        try {
            Seance selectedSeance = SeancesTable.getSelectionModel().getSelectedItem();
            if (selectedSeance == null) {
                showAlert("Erreur", "Veuillez sélectionner une séance à mettre à jour.", AlertType.ERROR);
                return;
            }

            String idMoniteurText = idMoniteur.getText();
            String idVehiculeText = idVehicule.getText();
            String idCandidatText = idEleve.getText();
            String dateSeanceText = DateSeance.getText();
            String dureeText = Duree.getText().trim();
            String typeSeanceText = TypeSeance.getText();

            if (idCandidatText.isEmpty() || idMoniteurText.isEmpty() || dateSeanceText.isEmpty() || typeSeanceText.isEmpty()) {
                showAlert("Erreur", "Tous les champs doivent être remplis sauf le champ du véhicule.", AlertType.ERROR);
                return;
            }

            if ((typeSeanceText.equalsIgnoreCase("créneau") || typeSeanceText.equalsIgnoreCase("conduite")) && idVehiculeText.isEmpty()) {
                showAlert("Erreur", "Un véhicule est obligatoire pour une séance de type créneau ou conduite.", AlertType.ERROR);
                return;
            }

            Integer idVehiculeInt = idVehiculeText.isEmpty() ? null : Integer.parseInt(idVehiculeText);
            int idMoniteurInt = Integer.parseInt(idMoniteurText);
            int idCandidatInt = Integer.parseInt(idCandidatText);
            int dureeInt = Integer.parseInt(dureeText);

            if (!utilisateurDao.exists(idMoniteurInt, "Moniteur")) {
                showAlert("Erreur", "Le moniteur spécifié n'existe pas.", AlertType.ERROR);
                return;
            }

            if (idVehiculeInt != null && !vehiculeDao.exists(idVehiculeInt)) {
                showAlert("Erreur", "Le véhicule spécifié n'existe pas.", AlertType.ERROR);
                return;
            }

            if (!personneDao.exists(idCandidatInt)) {
                showAlert("Erreur", "Le candidat spécifié n'existe pas.", AlertType.ERROR);
                return;
            }

            selectedSeance.setIdVehicule(idVehiculeInt);
            selectedSeance.setIdEleve(idCandidatInt);
            selectedSeance.setIdMoniteur(idMoniteurInt);
            selectedSeance.setDateSeance(LocalDate.parse(dateSeanceText));
            selectedSeance.setDuree(dureeInt);
            selectedSeance.setTypeSeance(typeSeanceText);

            seanceDao.updateSeance(selectedSeance);

            int index = SeancesTable.getSelectionModel().getSelectedIndex();
            SeancesTable.refresh();
            SeancesTable.getSelectionModel().select(index);

            showAlert("Succès", "Séance mise à jour avec succès.", AlertType.INFORMATION);

        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite lors de la mise à jour : " + e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void deleteseance(ActionEvent event) {
        try {
            Seance selectedSeance = SeancesTable.getSelectionModel().getSelectedItem();
            if (selectedSeance == null) {
                showAlert("Erreur", "Veuillez sélectionner une séance à supprimer.", AlertType.ERROR);
                return;
            }

            seanceDao.deleteSeance(selectedSeance.getIdSeance());
            seanceList.remove(selectedSeance);
            clearFields();
            showAlert("Succès", "Séance supprimée avec succès.", AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite lors de la suppression : " + e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void returntomain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SecretaireMainInterface.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Main Interface");
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de retourner au menu principal : " + e.getMessage(), AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}