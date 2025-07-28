package controller;

import dao.GestionCandidatsDao;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
import model.Eleve;

public class GestionCandidatsController implements Initializable {

    @FXML
    private TextField lname;
    @FXML
    private TextField fname;
    @FXML
    private TextField adresse;
    @FXML
    private TextField tel;
    @FXML
    private TextField dateInscription;
    @FXML
    private TextField montantTotal;
    @FXML
    private TextField montantPaye;
    @FXML
    private TextField email;
    @FXML
    private TableView<Eleve> CandidatsTable;
    @FXML
    private TableColumn<Eleve, Integer> idcolomn;
    @FXML
    private TableColumn<Eleve, String> lnamecolomn;
    @FXML
    private TableColumn<Eleve, String> fnamecolomn;
    @FXML
    private TableColumn<Eleve, String> AdresseColumn;
    @FXML
    private TableColumn<Eleve, String> TelColumn;
    @FXML
    private TableColumn<Eleve, String> EmailColumn;
    @FXML
    private TableColumn<Eleve, String> DateInscriptionColumn;
    @FXML
    private TableColumn<Eleve, Double> MontantTotalColumn;
    @FXML
    private TableColumn<Eleve, Double> MontantPayeColumn;
    @FXML
    private Button addbtn;
    @FXML
    private Button updatebtn;
    @FXML
    private Button deletebtn;
    @FXML
    private Button returnbtn;

    private ObservableList<Eleve> candidatsList;

    private final GestionCandidatsDao candidatsDao = new GestionCandidatsDao();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idcolomn.setCellValueFactory(new PropertyValueFactory<>("idEleve"));
        lnamecolomn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        fnamecolomn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        AdresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        TelColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        DateInscriptionColumn.setCellValueFactory(new PropertyValueFactory<>("dateInscription"));
        MontantTotalColumn.setCellValueFactory(new PropertyValueFactory<>("montantTotal"));
        MontantPayeColumn.setCellValueFactory(new PropertyValueFactory<>("montantPaye"));

        loadCandidatData();

        CandidatsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadCandidatDetails(newSelection.getIdEleve());
            } else {
                clearFields();
            }
        });
    }

    private void loadCandidatData() {
        List<Eleve> candidats = candidatsDao.getAllEleves();
        candidatsList = FXCollections.observableArrayList(candidats);
        CandidatsTable.setItems(candidatsList);
    }

    private void loadCandidatDetails(int idEleve) {
        Eleve eleve = candidatsDao.getEleveById(idEleve);
        if (eleve != null) {
            lname.setText(eleve.getNom());
            fname.setText(eleve.getPrenom());
            adresse.setText(eleve.getAdresse());
            tel.setText(eleve.getTelephone());
            email.setText(eleve.getEmail());
            dateInscription.setText(eleve.getDateInscription() != null ? eleve.getDateInscription().toString() : "");
            montantTotal.setText(String.valueOf(eleve.getMontantTotal()));
            montantPaye.setText(String.valueOf(eleve.getMontantPaye()));
        } else {
            clearFields();
            showAlert("Erreur", "Impossible de charger les détails du candidat.", AlertType.ERROR);
        }
    }

    @FXML
    private void addEleve(ActionEvent event) {
        if (!validateFields()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.", AlertType.ERROR);
            return;
        }
        try {
            Eleve newEleve = new Eleve();
            newEleve.setNom(lname.getText());
            newEleve.setPrenom(fname.getText());
            newEleve.setAdresse(adresse.getText());
            newEleve.setTelephone(tel.getText());
            newEleve.setEmail(email.getText());
            newEleve.setDateInscription(LocalDate.parse(dateInscription.getText()));
            newEleve.setMontantTotal(Double.parseDouble(montantTotal.getText()));
            newEleve.setMontantPaye(Double.parseDouble(montantPaye.getText()));
            

            candidatsDao.addEleve(newEleve);
            candidatsList.add(newEleve);
            clearFields();
            showAlert("Succès", "Candidat ajouté avec succès", AlertType.INFORMATION);
        } catch (DateTimeParseException e) {
            showAlert("Erreur", "Format de date invalide. Utilisez le format AAAA-MM-JJ.", AlertType.ERROR);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Montants invalides.", AlertType.ERROR);
        }
    }

    @FXML
    private void updateEleve(ActionEvent event) {
        Eleve selectedEleve = CandidatsTable.getSelectionModel().getSelectedItem();
        if (selectedEleve == null) {
            showAlert("Erreur", "Veuillez sélectionner un candidat", AlertType.ERROR);
            return;
        }

        try {
            selectedEleve.setNom(lname.getText());
            selectedEleve.setPrenom(fname.getText());
            selectedEleve.setAdresse(adresse.getText());
            selectedEleve.setTelephone(tel.getText());
            selectedEleve.setEmail(email.getText());
            selectedEleve.setDateInscription(LocalDate.parse(dateInscription.getText()));
            selectedEleve.setMontantTotal(Double.parseDouble(montantTotal.getText()));
            selectedEleve.setMontantPaye(Double.parseDouble(montantPaye.getText()));

            candidatsDao.updateEleve(selectedEleve);
            CandidatsTable.refresh();
            clearFields();
            showAlert("Succès", "Candidat mis à jour avec succès", AlertType.INFORMATION);
        } catch (DateTimeParseException e) {
            showAlert("Erreur", "Format de date invalide. Utilisez le format AAAA-MM-JJ.", AlertType.ERROR);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Montants invalides.", AlertType.ERROR);
        }
    }

    @FXML
    private void deleteEleve(ActionEvent event) {
        Eleve selectedEleve = CandidatsTable.getSelectionModel().getSelectedItem();
        if (selectedEleve == null) {
            showAlert("Erreur", "Veuillez sélectionner un candidat", AlertType.ERROR);
            return;
        }

        candidatsDao.deleteEleve(selectedEleve.getIdEleve());
        candidatsList.remove(selectedEleve);
        clearFields();
        showAlert("Succès", "Candidat supprimé avec succès", AlertType.INFORMATION);
    }

    @FXML
    private void returntomain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SecretaireMainInterface.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Interface Principale");
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        lname.clear();
        fname.clear();
        adresse.clear();
        tel.clear();
        email.clear();
        dateInscription.clear();
        montantTotal.clear();
        montantPaye.clear();
    }
    
    private boolean validateFields() {
        return !(lname.getText().isEmpty() ||
                fname.getText().isEmpty() ||
                adresse.getText().isEmpty() ||
                tel.getText().isEmpty() ||
                email.getText().isEmpty() ||
                dateInscription.getText().isEmpty() ||
                montantTotal.getText().isEmpty() ||
                montantPaye.getText().isEmpty());
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
