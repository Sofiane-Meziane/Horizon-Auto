package controller;

import dao.GestionExamensDao;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
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
import model.Examen;

public class GestionExamensController implements Initializable {

    @FXML
    private TextField typeexamen;
    @FXML
    private TextField dateexamen;
    @FXML
    private TextField lieuexamen;
    @FXML
    private TableView<Examen> examenstable;
    @FXML
    private TableColumn<Examen, Integer> idcolomn;
    @FXML
    private TableColumn<Examen, String> typeexamcolomn;
    @FXML
    private TableColumn<Examen, LocalDate> dateexamcolomn;
    @FXML
    private TableColumn<Examen, LocalTime> lieuexamcolomn;
    @FXML
    private Button addbtn;
    @FXML
    private Button updatebtn;
    @FXML
    private Button deletebtn;
    @FXML
    private Button returnbtn;

    private ObservableList<Examen> examenList;
    private final GestionExamensDao examenDao = new GestionExamensDao();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configuration des colonnes
        idcolomn.setCellValueFactory(new PropertyValueFactory<>("idExamen"));
        typeexamcolomn.setCellValueFactory(new PropertyValueFactory<>("typeExamen"));
        dateexamcolomn.setCellValueFactory(new PropertyValueFactory<>("dateExamen"));
        lieuexamcolomn.setCellValueFactory(new PropertyValueFactory<>("lieu"));

        // Chargement des données initiales
        loadExamenData();

        // Listener pour sélectionner un examen
        examenstable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadExamenDetails(newSelection.getIdExamen());
            } else {
                clearFields();
            }
        });
    }

    private void loadExamenData() {
        List<Examen> examens = examenDao.getAllExamens();
        examenList = FXCollections.observableArrayList(examens);
        examenstable.setItems(examenList);
    }

    private void loadExamenDetails(int idExamen) {
        Examen examen = examenDao.getExamenById(idExamen);
        if (examen != null) {
            typeexamen.setText(examen.getTypeExamen());
            dateexamen.setText(examen.getDateExamen().toString());
            lieuexamen.setText(examen.getLieu());
        }
    }

    @FXML
    private void addExam(ActionEvent event) {
        String type = typeexamen.getText().trim();
        String date = dateexamen.getText().trim();
        String lieu = lieuexamen.getText().trim();

        if (type.isEmpty() || date.isEmpty() || lieu.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis", Alert.AlertType.ERROR);
            return;
        }

        try {
            Examen newExamen = new Examen();
            newExamen.setTypeExamen(type);
            newExamen.setDateExamen(LocalDate.parse(date));
            newExamen.setLieu(lieu);

            examenDao.addExamen(newExamen);
            examenList.add(newExamen);
            examenstable.refresh();
            clearFields();
            showAlert("Succès", "Examen ajouté avec succès.", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Erreur", "Format invalide pour la date ou l'heure.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void updateExam(ActionEvent event) {
        Examen selectedExamen = examenstable.getSelectionModel().getSelectedItem();

        if (selectedExamen == null) {
            showAlert("Erreur", "Veuillez sélectionner un examen à modifier.", Alert.AlertType.ERROR);
            return;
        }

        try {
            selectedExamen.setTypeExamen(typeexamen.getText().trim());
            selectedExamen.setDateExamen(LocalDate.parse(dateexamen.getText().trim()));
            selectedExamen.setLieu(lieuexamen.getText().trim());

            examenDao.updateExamen(selectedExamen);
            examenstable.refresh();
            showAlert("Succès", "Examen mis à jour avec succès.", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Erreur", "Format invalide pour la date ou l'heure.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void deleteExam(ActionEvent event) {
        Examen selectedExamen = examenstable.getSelectionModel().getSelectedItem();

        if (selectedExamen == null) {
            showAlert("Erreur", "Veuillez sélectionner un examen à supprimer.", Alert.AlertType.ERROR);
            return;
        }

        examenDao.deleteExamen(selectedExamen.getIdExamen());
        examenList.remove(selectedExamen);
        examenstable.refresh();
        clearFields();
        showAlert("Succès", "Examen supprimé avec succès.", Alert.AlertType.INFORMATION);
    }

    private void clearFields() {
        typeexamen.clear();
        dateexamen.clear();
        lieuexamen.clear();
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void returnToMain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SecretaireMainInterface.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Secretaire Main Interface");
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
