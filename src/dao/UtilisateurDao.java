package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Utilisateur;

public class UtilisateurDao {

    /**
     * Authenticate user based on username and password.
     * @param username the username
     * @param password the password
     * @return Utilisateur object if authentication is successful, otherwise null.
     */
    public Utilisateur authenticateUser(String username, String password) {
        String query = "SELECT * FROM utilisateurs WHERE username = ? AND password = ?";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DatabaseConnection.getInstance();
            ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToUtilisateur(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, connection);
        }
        return null; // If no matching user was found
    }

    /**
     * Retrieve all users from the database.
     * @return List of Utilisateur objects.
     */
    public List<Utilisateur> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String query = "SELECT * FROM utilisateurs";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DatabaseConnection.getInstance();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                utilisateurs.add(mapResultSetToUtilisateur(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, connection);
        }
        return utilisateurs;
    }

    /**
     * Retrieve a user by ID from the database.
     * @param idUtilisateur The ID of the user.
     * @return Utilisateur object if found, otherwise null.
     */
    public Utilisateur getUtilisateurById(int idUtilisateur) {
        String query = "SELECT * FROM utilisateurs WHERE idUtilisateur = ?";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DatabaseConnection.getInstance();
            ps = connection.prepareStatement(query);
            ps.setInt(1, idUtilisateur);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToUtilisateur(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, connection);
        }
        return null; // If no user with the given ID was found
    }

    /**
     * Add a new user to the database and retrieve the generated ID.
     * @param utilisateur The Utilisateur object to be added.
     */
    public void addUtilisateur(Utilisateur utilisateur) {
        String query = "INSERT INTO utilisateurs (nom, prenom, username, password, role) VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet generatedKeys = null;

        try {
            connection = DatabaseConnection.getInstance();
            ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getPrenom());
            ps.setString(3, utilisateur.getUsername());
            ps.setString(4, utilisateur.getPassword());
            ps.setString(5, utilisateur.getRole());

            ps.executeUpdate();

            generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                utilisateur.setIdUtilisateur(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'ajout de l'utilisateur.", e);
        } finally {
            closeResources(generatedKeys, ps, connection);
        }
    }

    /**
     * Update an existing user in the database.
     * @param utilisateur The Utilisateur object with updated details.
     */
    public void updateUtilisateur(Utilisateur utilisateur) {
        String query = "UPDATE utilisateurs SET nom = ?, prenom = ?, username = ?, password = ?, role = ? WHERE idUtilisateur = ?";
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DatabaseConnection.getInstance();
            ps = connection.prepareStatement(query);

            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getPrenom());
            ps.setString(3, utilisateur.getUsername());
            ps.setString(4, utilisateur.getPassword());
            ps.setString(5, utilisateur.getRole());
            ps.setInt(6, utilisateur.getIdUtilisateur());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps, connection);
        }
    }

    /**
     * Delete a user from the database based on their ID.
     * @param idUtilisateur The ID of the user to be deleted.
     */
    public void deleteUtilisateur(int idUtilisateur) {
        String query = "DELETE FROM utilisateurs WHERE idUtilisateur = ?";
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DatabaseConnection.getInstance();
            ps = connection.prepareStatement(query);

            ps.setInt(1, idUtilisateur);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps, connection);
        }
    }

    /**
     * Check if a user exists with the given ID and role.
     * @param idUtilisateur The ID of the user.
     * @param role The role to match.
     * @return true if the user exists with the specified role, otherwise false.
     */
    public boolean exists(int idUtilisateur, String role) {
        String query = "SELECT 1 FROM utilisateurs WHERE idUtilisateur = ? AND role = ?";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DatabaseConnection.getInstance();
            ps = connection.prepareStatement(query);

            ps.setInt(1, idUtilisateur);
            ps.setString(2, role);

            rs = ps.executeQuery();
            return rs.next(); // Retourne true si une ligne est trouvée

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la vérification de l'utilisateur avec id: " + idUtilisateur + " et rôle: " + role, e);
        } finally {
            closeResources(rs, ps, connection);
        }
    }

    /**
     * Helper method to map ResultSet data to a Utilisateur object.
     * @param rs The ResultSet containing user data.
     * @return A Utilisateur object.
     */
    public Utilisateur mapResultSetToUtilisateur(ResultSet rs) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIdUtilisateur(rs.getInt("idUtilisateur"));
        utilisateur.setNom(rs.getString("nom"));
        utilisateur.setPrenom(rs.getString("prenom"));
        utilisateur.setUsername(rs.getString("username"));
        utilisateur.setPassword(rs.getString("password"));
        utilisateur.setRole(rs.getString("role"));
        return utilisateur;
    }

    public int getIdByUsernameAndPassword(String username, String password) {
        int id = -1; // Valeur par défaut si l'utilisateur n'est pas trouvé
        String query = "SELECT idUtilisateur FROM utilisateurs WHERE username = ? AND password = ?";
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DatabaseConnection.getInstance();
            ps = connection.prepareStatement(query);

            ps.setString(1, username);
            ps.setString(2, password);

            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("idUtilisateur");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, connection);
        }

        return id;
    }

    // Méthode pour fermer les ressources
    private void closeResources(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}