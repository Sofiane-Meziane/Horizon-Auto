package controller;

import dao.UtilisateurDao;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Utilisateur;

public class GestionUtilisateursController implements Initializable {

    @FXML
    private TextField lname;
    @FXML
    private TextField fname;
    @FXML
    private TextField username;
    @FXML
    private TextField role;
    @FXML
    private PasswordField password;
    @FXML
    private TableView<Utilisateur> usersTable;
    @FXML
    private TableColumn<Utilisateur, Integer> idColumn;
    @FXML
    private TableColumn<Utilisateur, String> lnameColumn;
    @FXML
    private TableColumn<Utilisateur, String> fnameColumn;
    @FXML
    private TableColumn<Utilisateur, String> usernameColumn;
    @FXML
    private TableColumn<Utilisateur, String> roleColumn;
    @FXML
    private TableColumn<Utilisateur, String> passwordColumn;
    @FXML
    private Button addBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button returnBtn;

    private ObservableList<Utilisateur> userList;

    private final UtilisateurDao utilisateurDao = new UtilisateurDao(); // DAO pour gérer les données

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Associer les colonnes du tableau aux attributs de la classe Utilisateur
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idUtilisateur"));
        lnameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        fnameColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        

        // Charger les données dans la table
        loadUserData();

        // Ajouter un gestionnaire pour la sélection d'une ligne dans la table
        usersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadUserDetails(newSelection.getIdUtilisateur());
            } else {
                clearFields();
            }
        });
    }

    private void loadUserData() {
        List<Utilisateur> utilisateurs = utilisateurDao.getAllUtilisateurs(); // Utilise la méthode correcte de UtilisateurDao
        userList = FXCollections.observableArrayList(utilisateurs);
        usersTable.setItems(userList);
    }

    /**
     * Récupérer les détails d'un utilisateur via son ID et remplir les champs.
     */
    private void loadUserDetails(int idUtilisateur) {
        Utilisateur utilisateur = utilisateurDao.getUtilisateurById(idUtilisateur);
        if (utilisateur != null) {
            lname.setText(utilisateur.getNom());
            fname.setText(utilisateur.getPrenom());
            username.setText(utilisateur.getUsername());
            role.setText(utilisateur.getRole());
            password.setText(utilisateur.getPassword());
        } else {
            clearFields();
            showAlert("Erreur", "Impossible de charger les détails de l'utilisateur.", AlertType.ERROR);
        }
    }

    @FXML
    private void addUser(ActionEvent event) {
        String lastName = lname.getText();
        String firstName = fname.getText();
        String userName = username.getText();
        String userRole = role.getText();
        String userPassword = password.getText();

        if (lastName.isEmpty() || firstName.isEmpty() || userName.isEmpty() || userRole.isEmpty() || userPassword.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis", AlertType.ERROR);
            return;
        }

        if (!userRole.equals("Secretaire") && !userRole.equals("Moniteur")) {
            showAlert("Erreur", "Le rôle doit être 'Secretaire' ou 'Moniteur'", AlertType.ERROR);
            return;
        }

        Utilisateur newUser = new Utilisateur();
        newUser.setNom(lastName);
        newUser.setPrenom(firstName);
        newUser.setUsername(userName);
        newUser.setRole(userRole);
        newUser.setPassword(userPassword);

        utilisateurDao.addUtilisateur(newUser); // Ajoute l'utilisateur dans la base de données

        // Ajout à la liste observable pour rafraîchir le tableau
        userList.add(newUser);
        clearFields();
        showAlert("Succès", "Utilisateur ajouté avec succès", AlertType.INFORMATION);
    }

    @FXML
    private void updateUser(ActionEvent event) {
        Utilisateur selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert("Erreur", "Veuillez sélectionner un utilisateur", AlertType.ERROR);
            return;
        }

        selectedUser.setNom(lname.getText());
        selectedUser.setPrenom(fname.getText());
        selectedUser.setUsername(username.getText());
        selectedUser.setRole(role.getText());
        selectedUser.setPassword(password.getText());

        utilisateurDao.updateUtilisateur(selectedUser); // Met à jour l'utilisateur dans la base de données

        // Rafraîchir les données dans le tableau
        usersTable.refresh();
        clearFields();
        showAlert("Succès", "Utilisateur mis à jour avec succès", AlertType.INFORMATION);
    }

    @FXML
    private void deleteUser(ActionEvent event) {
        Utilisateur selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert("Erreur", "Veuillez sélectionner un utilisateur", AlertType.ERROR);
            return;
        }

        utilisateurDao.deleteUtilisateur(selectedUser.getIdUtilisateur()); // Supprime l'utilisateur dans la base de données

        // Supprime de la liste observable pour rafraîchir le tableau
        userList.remove(selectedUser);
        clearFields();
        showAlert("Succès", "Utilisateur supprimé avec succès", AlertType.INFORMATION);
    }

    @FXML
    private void returntomain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminMainInterface.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Admin Main Interface");
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        lname.clear();
        fname.clear();
        username.clear();
        role.clear();
        password.clear();
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
