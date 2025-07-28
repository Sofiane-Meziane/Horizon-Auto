package controller;

import dao.GestionPaiementsDao;
import dao.GestionCandidatsDao;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Paiement;

public class GestionPaiementsController implements Initializable {

    @FXML
    private TextField idEleve;
    @FXML
    private TextField datePaiement;
    @FXML
    private TextField montant;
    @FXML
    private TextField moyenPaiement;
    @FXML
    private TableView<Paiement> paiementsTable;
    @FXML
    private TableColumn<Paiement, Integer> idColumn;
    @FXML
    private TableColumn<Paiement, Integer> idEleveColumn;
    @FXML
    private TableColumn<Paiement, String> datePaiementColumn;
    @FXML
    private TableColumn<Paiement, Double> montantColumn;
    @FXML
    private TableColumn<Paiement, String> moyenPaiementColumn;
    @FXML
    private Button addBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button returnBtn;

    private ObservableList<Paiement> paiementList;
    private final GestionCandidatsDao personneDao = new GestionCandidatsDao();
    private final GestionPaiementsDao paiementDao = new GestionPaiementsDao();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Associer les colonnes aux attributs du modèle Paiement
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idPaiement"));
        idEleveColumn.setCellValueFactory(new PropertyValueFactory<>("idEleve"));
        datePaiementColumn.setCellValueFactory(new PropertyValueFactory<>("datePaiement"));
        montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        moyenPaiementColumn.setCellValueFactory(new PropertyValueFactory<>("moyenPaiement"));

        // Charger les données
        loadPaiementData();

        // Ajouter un gestionnaire de sélection
        paiementsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadPaiementDetails(newSelection);
            } else {
                clearFields();
            }
        });
    }

    private void loadPaiementData() {
        List<Paiement> paiements = paiementDao.getAllPaiements();
        paiementList = FXCollections.observableArrayList(paiements);
        paiementsTable.setItems(paiementList);
    }

    private void loadPaiementDetails(Paiement paiement) {
        if (paiement != null) {
            idEleve.setText(String.valueOf(paiement.getIdEleve()));
            datePaiement.setText(paiement.getDatePaiement() != null ? paiement.getDatePaiement().toString() : "");
            montant.setText(String.valueOf(paiement.getMontant()));
            moyenPaiement.setText(paiement.getMoyenPaiement());
        } else {
            clearFields();
            showAlert("Erreur", "Impossible de charger les détails du paiement.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void addPaiement(ActionEvent event) {
        try {
            int idEleveValue = Integer.parseInt(idEleve.getText());
            String datePaiementValue = datePaiement.getText();
            double montantValue = Double.parseDouble(montant.getText());
            String moyenPaiementValue = moyenPaiement.getText();

            if (datePaiementValue.isEmpty() || moyenPaiementValue.isEmpty()) {
                showAlert("Erreur", "Tous les champs doivent être remplis.", Alert.AlertType.ERROR);
                return;
            }
            
            if (!personneDao.exists(idEleveValue)) {
                showAlert("Erreur", "Le candidat spécifié n'existe pas.", Alert.AlertType.ERROR);
                return;
            }

            Paiement newPaiement = new Paiement();
            newPaiement.setIdEleve(idEleveValue);
            newPaiement.setDatePaiement(LocalDate.parse(datePaiement.getText()));
            newPaiement.setMontant(montantValue);
            newPaiement.setMoyenPaiement(moyenPaiementValue);

            paiementDao.addPaiement(newPaiement);
            paiementList.add(newPaiement);

            // Mettre à jour le montant payé dans la table eleves
            double montantTotalPaye = paiementDao.getMontantTotalPaye(idEleveValue);
            personneDao.updateMontantPaye(idEleveValue, montantTotalPaye);

            clearFields();
            showAlert("Succès", "Paiement ajouté avec succès.", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer des valeurs valides.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void updatePaiement(ActionEvent event) {
        Paiement selectedPaiement = paiementsTable.getSelectionModel().getSelectedItem();
        if (selectedPaiement == null) {
            showAlert("Erreur", "Veuillez sélectionner un paiement.", Alert.AlertType.ERROR);
            return;
        }

        try {
            selectedPaiement.setIdEleve(Integer.parseInt(idEleve.getText()));
            selectedPaiement.setDatePaiement(LocalDate.parse(datePaiement.getText()));
            selectedPaiement.setMontant(Double.parseDouble(montant.getText()));
            selectedPaiement.setMoyenPaiement(moyenPaiement.getText());

            paiementDao.updatePaiement(selectedPaiement);
            paiementsTable.refresh();
            clearFields();
            showAlert("Succès", "Paiement mis à jour avec succès.", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer des valeurs valides.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void deletePaiement(ActionEvent event) {
        Paiement selectedPaiement = paiementsTable.getSelectionModel().getSelectedItem();
        if (selectedPaiement == null) {
            showAlert("Erreur", "Veuillez sélectionner un paiement.", Alert.AlertType.ERROR);
            return;
        }

        paiementDao.deletePaiement(selectedPaiement.getIdPaiement());
        paiementList.remove(selectedPaiement);
        clearFields();
        showAlert("Succès", "Paiement supprimé avec succès.", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void returnToMain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SecretaireMainInterface.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Interface Principale");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        idEleve.clear();
        datePaiement.clear();
        montant.clear();
        moyenPaiement.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}