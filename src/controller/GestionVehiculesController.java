package controller;

import dao.VehiculeDao;
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
import model.Vehicule;

public class GestionVehiculesController implements Initializable {

    @FXML
    private TextField brand;
    @FXML
    private TextField model;
    @FXML
    private TextField registrationnumber;
    @FXML
    private TextField type;
    @FXML
    private TableView<Vehicule> vehiculestable;
    @FXML
    private TableColumn<Vehicule, Integer> idcolomn;
    @FXML
    private TableColumn<Vehicule, String> brandcolomn;
    @FXML
    private TableColumn<Vehicule, String> modelcolomn;
    @FXML
    private TableColumn<Vehicule, String> registrationnumbercolomn;
    @FXML
    private TableColumn<Vehicule, String> typecolomn;
    @FXML
    private Button addbtn;
    @FXML
    private Button updatebtn;
    @FXML
    private Button deletebtn;
    @FXML
    private Button returnbtn;

    private ObservableList<Vehicule> vehiculeList;
    private final VehiculeDao vehiculeDao = new VehiculeDao();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Associer les colonnes aux attributs du modèle Vehicule
        idcolomn.setCellValueFactory(new PropertyValueFactory<>("idVehicule"));
        brandcolomn.setCellValueFactory(new PropertyValueFactory<>("marque"));
        modelcolomn.setCellValueFactory(new PropertyValueFactory<>("modele"));
        registrationnumbercolomn.setCellValueFactory(new PropertyValueFactory<>("immatriculation"));
        typecolomn.setCellValueFactory(new PropertyValueFactory<>("typeVehicule"));

        // Charger les données
        loadVehiculeData();

        // Ajouter un gestionnaire de sélection
        vehiculestable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadVehiculeDetails(newSelection);
            } else {
                clearFields();
            }
        });
    }

    private void loadVehiculeData() {
        List<Vehicule> vehicules = vehiculeDao.getAllVehicules();
        vehiculeList = FXCollections.observableArrayList(vehicules);
        vehiculestable.setItems(vehiculeList);
    }

    private void loadVehiculeDetails(Vehicule vehicule) {
        if (vehicule != null) {
            brand.setText(vehicule.getMarque());
            model.setText(vehicule.getModele());
            registrationnumber.setText(vehicule.getImmatriculation());
            type.setText(vehicule.getTypeVehicule());
        } else {
            clearFields();
            showAlert("Erreur", "Impossible de charger les détails du véhicule.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void addVehicule(ActionEvent event) {
        String brandValue = brand.getText();
        String modelValue = model.getText();
        String registrationNumberValue = registrationnumber.getText();
        String typeValue = type.getText();

        if (brandValue.isEmpty() || modelValue.isEmpty() || registrationNumberValue.isEmpty() || typeValue.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.", Alert.AlertType.ERROR);
            return;
        }

        Vehicule newVehicule = new Vehicule();
        newVehicule.setMarque(brandValue);
        newVehicule.setModele(modelValue);
        newVehicule.setImmatriculation(registrationNumberValue);
        newVehicule.setTypeVehicule(typeValue);

        vehiculeDao.addVehicule(newVehicule);
        vehiculeList.add(newVehicule);
        clearFields();
        showAlert("Succès", "Véhicule ajouté avec succès.", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void updateVehicule(ActionEvent event) {
        Vehicule selectedVehicule = vehiculestable.getSelectionModel().getSelectedItem();
        if (selectedVehicule == null) {
            showAlert("Erreur", "Veuillez sélectionner un véhicule.", Alert.AlertType.ERROR);
            return;
        }

        selectedVehicule.setMarque(brand.getText());
        selectedVehicule.setModele(model.getText());
        selectedVehicule.setImmatriculation(registrationnumber.getText());
        selectedVehicule.setTypeVehicule(type.getText());

        vehiculeDao.updateVehicule(selectedVehicule);
        vehiculestable.refresh();
        clearFields();
        showAlert("Succès", "Véhicule mis à jour avec succès.", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void deleteVehicule(ActionEvent event) {
        Vehicule selectedVehicule = vehiculestable.getSelectionModel().getSelectedItem();
        if (selectedVehicule == null) {
            showAlert("Erreur", "Veuillez sélectionner un véhicule.", Alert.AlertType.ERROR);
            return;
        }

        vehiculeDao.deleteVehicule(selectedVehicule.getIdVehicule());
        vehiculeList.remove(selectedVehicule);
        clearFields();
        showAlert("Succès", "Véhicule supprimé avec succès.", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void returnToMain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminMainInterface.fxml"));
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
        brand.clear();
        model.clear();
        registrationnumber.clear();
        type.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
