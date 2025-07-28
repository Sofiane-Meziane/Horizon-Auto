package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode; // Import KeyCode

/**
 * FXML Controller class
 *
 * @author sofia
 */
public class SecretaireMainInterfaceController implements Initializable {

    @FXML
    private Button inscriptionmanagementbtn;
    @FXML
    private Button examsmanagementbtn;
    @FXML
    private Button coursesmanagementbtn;
    @FXML
    private Button candidatsmanagementbtn;
    @FXML
    private Button paiementsmanagementbtn1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Add key event handler after the scene is fully initialized
        inscriptionmanagementbtn.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.ESCAPE) {
                        // Show a confirmation dialog
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText("Êtes-vous sûr de vouloir quitter ?");
                        alert.setContentText("Toutes les modifications non enregistrées seront perdues.");

                        // If the user confirms, close the window
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                Stage stage = (Stage) inscriptionmanagementbtn.getScene().getWindow();
                                stage.close();
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * Méthode utilitaire pour charger une vue FXML et l'afficher en plein écran.
     */
    private void loadView(ActionEvent event, String fxmlPath, String title) {
        try {
            // Charger la vue depuis le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);

            // Obtenir le Stage actuel à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Configurer et afficher la nouvelle scène
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setMaximized(true); // Toujours afficher en plein écran

            // Add key event handler to close the window on Escape key press
            scene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ESCAPE) {
                    // Show a confirmation dialog
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Êtes-vous sûr de vouloir quitter ?");
                    alert.setContentText("Toutes les modifications non enregistrées seront perdues.");

                    // If the user confirms, close the window
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            stage.close();
                        }
                    });
                }
            });

            stage.show();
        } catch (IOException e) {
            // Gérer les erreurs de chargement
            System.err.println("Erreur lors du chargement de la vue : " + fxmlPath);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleInscriptionButtonAction(ActionEvent event) {
        loadView(event, "/view/GestionInscription.fxml", "Gestion des Inscriptions");
    }

    @FXML
    private void handleExamsButtonAction(ActionEvent event) {
        loadView(event, "/view/GestionExamens.fxml", "Gestion des Examens");
    }

    @FXML
    private void handleCoursesButtonAction(ActionEvent event) {
        loadView(event, "/view/GestionSeances.fxml", "Gestion des Seances");
    }

    @FXML
    private void handleCandidatsButtonAction(ActionEvent event) {
        loadView(event, "/view/GestionCandidats.fxml", "Gestion des Candidats");
    }

    @FXML
    private void handlePaiementsButtonAction(ActionEvent event) {
        loadView(event, "/view/GestionPaiements.fxml", "Gestion des Paiements");
    }
}