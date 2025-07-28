package controller;

import dao.GestionInscriptionsDao;
import dao.GestionCandidatsDao;
import dao.GestionExamensDao;
import java.io.IOException;
import java.net.URL;
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
import model.Examen_Eleve;

public class GestionInscriptionsController implements Initializable {

    @FXML
    private TextField idExamen;
    @FXML
    private TextField idEleve;
    @FXML
    private TextField resultat;
    @FXML
    private TextField commentaire;
    @FXML
    private TableView<Examen_Eleve> inscriptionsTable;
    @FXML
    private TableColumn<Examen_Eleve, Integer> idColumn;
    @FXML
    private TableColumn<Examen_Eleve, Integer> idExamenColumn;
    @FXML
    private TableColumn<Examen_Eleve, Integer> idEleveColumn;
    @FXML
    private TableColumn<Examen_Eleve, String> resultatColumn;
    @FXML
    private TableColumn<Examen_Eleve, String> commentaireColumn;
    @FXML
    private Button addBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button returnBtn;

    private ObservableList<Examen_Eleve> inscriptionList;
    private final GestionCandidatsDao eleveDao = new GestionCandidatsDao();
    private final GestionExamensDao examenDao = new GestionExamensDao();
    private final GestionInscriptionsDao inscriptionDao = new GestionInscriptionsDao();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Associer les colonnes aux attributs du modèle Inscription
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idExamEleve"));
        idExamenColumn.setCellValueFactory(new PropertyValueFactory<>("idExamen"));
        idEleveColumn.setCellValueFactory(new PropertyValueFactory<>("idEleve"));
        resultatColumn.setCellValueFactory(new PropertyValueFactory<>("resultat"));
        commentaireColumn.setCellValueFactory(new PropertyValueFactory<>("commentaire"));

        // Charger les données
        loadInscriptionData();

        // Ajouter un gestionnaire de sélection
        inscriptionsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadInscriptionDetails(newSelection);
            } else {
                clearFields();
            }
        });
    }

    private void loadInscriptionData() {
        List<Examen_Eleve> inscriptions = inscriptionDao.getAllInscriptions();
        inscriptionList = FXCollections.observableArrayList(inscriptions);
        inscriptionsTable.setItems(inscriptionList);
    }

    private void loadInscriptionDetails(Examen_Eleve inscription) {
        if (inscription != null) {
            idExamen.setText(String.valueOf(inscription.getIdExamen()));
            idEleve.setText(String.valueOf(inscription.getIdEleve()));
            resultat.setText(inscription.getResultat());
            commentaire.setText(inscription.getCommentaire());
        } else {
            clearFields();
            showAlert("Erreur", "Impossible de charger les détails de l'inscription.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void addInscription(ActionEvent event) {
        try {
            int idExamenValue = Integer.parseInt(idExamen.getText());
            int idEleveValue = Integer.parseInt(idEleve.getText());
            String resultatValue = resultat.getText();
            String commentaireValue = commentaire.getText();

            /*if (resultatValue.isEmpty() || commentaireValue.isEmpty()) {
                showAlert("Erreur", "Tous les champs doivent être remplis.", Alert.AlertType.ERROR);
                return;
            }*/
            
            if (!examenDao.exists(idExamenValue)) {
                showAlert("Erreur", "L'examen spécifié n'existe pas.", Alert.AlertType.ERROR);
                return;
            }

            if (!eleveDao.exists(idEleveValue)) {
                showAlert("Erreur", "L'élève spécifié n'existe pas.", Alert.AlertType.ERROR);
                return;
            }

            Examen_Eleve newInscription = new Examen_Eleve();
            newInscription.setIdExamen(idExamenValue);
            newInscription.setIdEleve(idEleveValue);
            newInscription.setResultat(resultatValue);
            newInscription.setCommentaire(commentaireValue);

            inscriptionDao.addInscription(newInscription);
            inscriptionList.add(newInscription);
            clearFields();
            showAlert("Succès", "Inscription ajoutée avec succès.", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer des valeurs valides.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void updateInscription(ActionEvent event) {
        Examen_Eleve selectedInscription = inscriptionsTable.getSelectionModel().getSelectedItem();
        if (selectedInscription == null) {
            showAlert("Erreur", "Veuillez sélectionner une inscription.", Alert.AlertType.ERROR);
            return;
        }

        try {
            selectedInscription.setIdExamen(Integer.parseInt(idExamen.getText()));
            selectedInscription.setIdEleve(Integer.parseInt(idEleve.getText()));
            selectedInscription.setResultat(resultat.getText());
            selectedInscription.setCommentaire(commentaire.getText());

            inscriptionDao.updateInscription(selectedInscription);
            inscriptionsTable.refresh();
            clearFields();
            showAlert("Succès", "Inscription mise à jour avec succès.", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer des valeurs valides.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void deleteInscription(ActionEvent event) {
        Examen_Eleve selectedInscription = inscriptionsTable.getSelectionModel().getSelectedItem();
        if (selectedInscription == null) {
            showAlert("Erreur", "Veuillez sélectionner une inscription.", Alert.AlertType.ERROR);
            return;
        }

        inscriptionDao.deleteInscription(selectedInscription.getIdExamEleve());
        inscriptionList.remove(selectedInscription);
        clearFields();
        showAlert("Succès", "Inscription supprimée avec succès.", Alert.AlertType.INFORMATION);
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
        idExamen.clear();
        idEleve.clear();
        resultat.clear();
        commentaire.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
