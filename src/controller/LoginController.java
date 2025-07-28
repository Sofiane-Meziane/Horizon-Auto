package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.KeyCode; // Import KeyCode

// Import the necessary classes
import dao.UtilisateurDao;
import model.Utilisateur;

public class LoginController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    private UtilisateurDao utilisateurDao;
    private static int idMoniteur;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the UtilisateurDao object
        utilisateurDao = new UtilisateurDao();
    }

    /**
     * Getter for idMoniteur.
     * @return the authenticated monitor's ID.
     */
    public static int getIdMoniteur() {
        return idMoniteur;
    }

    /**
     * Setter for idMoniteur.
     * @param id The authenticated monitor's ID.
     */
    public static void setIdMoniteur(int id) {
        idMoniteur = id;
    }

    /**
     * Handle the login button click event.
     * @param event The action event.
     */
    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String uname = username.getText();
        String pword = password.getText();

        // Check for empty fields
        if (uname.isEmpty() || pword.isEmpty()) {
            showAlert(AlertType.WARNING, "Erreur de validation", "Veuillez remplir tous les champs.");
            return;
        }

        // Authenticate the user via UtilisateurDao
        Utilisateur user = utilisateurDao.authenticateUser(uname, pword);

        if (user != null) {
            // Log user information for debugging
            System.out.println("Utilisateur authentifié : " + user.getUsername() + " (ID : " + user.getIdUtilisateur() + ")");

            // If the credentials are correct, check the user's role
            switch (user.getRole()) {
                case "Admin":
                    loadInterface(event, "/view/AdminMainInterface.fxml", "Gestion d'auto-école - Admin");
                    break;
                case "Moniteur":
                    setIdMoniteur(user.getIdUtilisateur()); // Set the authenticated monitor's ID
                    System.out.println("ID du moniteur défini : " + getIdMoniteur());
                    loadInterface(event, "/view/MoniteurMainInterface.fxml", "Gestion d'auto-école - Moniteur");
                    break;
                case "Secretaire":
                    loadInterface(event, "/view/SecretaireMainInterface.fxml", "Gestion d'auto-école - Secretaire");
                    break;
                default:
                    showAlert(AlertType.ERROR, "Erreur de rôle", "Rôle non reconnu !");
                    break;
            }
        } else {
            // Show an alert if the login credentials are incorrect
            showAlert(AlertType.ERROR, "Erreur de connexion", "Nom d'utilisateur ou mot de passe incorrect.");
        }
    }

    /**
     * Handle the reset button click event.
     * @param event The action event.
     */
    @FXML
    private void handleResetButtonAction(ActionEvent event) {
        // Clear the input fields
        username.clear();
        password.clear();
    }

    /**
     * Load a specific interface based on the user's role.
     * @param event The action event.
     * @param fxmlPath The path to the FXML file.
     * @param title The window title.
     */
    private void loadInterface(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();

            // Set the stage style to UNDECORATED to remove the title bar
            primaryStage.initStyle(StageStyle.UNDECORATED);

            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true); // Open the window in fullscreen mode

            // Add key event handler to close the window on Escape key press
            scene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ESCAPE) {
                    primaryStage.close(); // Close the window
                }
            });

            primaryStage.show();

            // Close the current login window
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erreur de chargement", "Impossible de charger l'interface : " + title);
        }
    }

    /**
     * Utility method to display alerts.
     * @param alertType The alert type (ERROR, WARNING, etc.).
     * @param title The alert title.
     * @param message The alert message.
     */
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}