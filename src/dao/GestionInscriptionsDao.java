package dao;

import model.Examen_Eleve;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionInscriptionsDao {

    /**
     * Retrieve all inscriptions from the database.
     * @return List of Examen_Eleve objects.
     */
    public List<Examen_Eleve> getAllInscriptions() {
        List<Examen_Eleve> inscriptions = new ArrayList<>();
        String query = "SELECT * FROM examens_eleves";

        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                inscriptions.add(mapResultSetToInscription(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inscriptions;
    }

    /**
     * Retrieve an inscription by ID from the database.
     * @param idExamEleve The ID of the inscription.
     * @return Examen_Eleve object if found, otherwise null.
     */
    public Examen_Eleve getInscriptionById(int idExamEleve) {
        String query = "SELECT * FROM examens_eleves WHERE idExamEleve = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idExamEleve);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToInscription(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Add a new inscription to the database and set its generated ID.
     * @param inscription The Examen_Eleve object to be added.
     */
    public void addInscription(Examen_Eleve inscription) {
        String query = "INSERT INTO examens_eleves (idExamen, idEleve, resultat, commentaire) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, inscription.getIdExamen());
            ps.setInt(2, inscription.getIdEleve());
            //ps.setString(3, inscription.getResultat());
            if (inscription.getResultat() == null || inscription.getResultat().isEmpty()) {
                ps.setNull(3, Types.VARCHAR); // Passer NULL si aucun résultat n'est défini
            } else {
                ps.setString(3, inscription.getResultat().trim());
            }
            ps.setString(4, inscription.getCommentaire());
            ps.executeUpdate();

            // Retrieve the generated ID and set it to the Examen_Eleve object
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    inscription.setIdExamEleve(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update an existing inscription in the database.
     * @param inscription The Examen_Eleve object with updated details.
     */
    public void updateInscription(Examen_Eleve inscription) {
        String query = "UPDATE examens_eleves SET idExamen = ?, idEleve = ?, resultat = ?, commentaire = ? WHERE idExamEleve = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, inscription.getIdExamen());
            ps.setInt(2, inscription.getIdEleve());
            ps.setString(3, inscription.getResultat());
            ps.setString(4, inscription.getCommentaire());
            ps.setInt(5, inscription.getIdExamEleve());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete an inscription from the database based on its ID.
     * @param idExamEleve The ID of the inscription to be deleted.
     */
    public void deleteInscription(int idExamEleve) {
        String query = "DELETE FROM examens_eleves WHERE idExamEleve = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idExamEleve);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if an inscription exists in the database based on its ID.
     * @param idExamEleve The ID of the inscription.
     * @return True if the inscription exists, otherwise false.
     */
    public boolean exists(int idExamEleve) {
        String query = "SELECT 1 FROM examens_eleves WHERE idExamEleve = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idExamEleve);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // If a row is returned, the inscription exists
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Helper method to map ResultSet data to an Examen_Eleve object.
     * @param rs The ResultSet containing inscription data.
     * @return An Examen_Eleve object.
     * @throws SQLException if a database access error occurs.
     */
    private Examen_Eleve mapResultSetToInscription(ResultSet rs) throws SQLException {
        Examen_Eleve inscription = new Examen_Eleve();
        inscription.setIdExamEleve(rs.getInt("idExamEleve"));
        inscription.setIdExamen(rs.getInt("idExamen"));
        inscription.setIdEleve(rs.getInt("idEleve"));
        inscription.setResultat(rs.getString("resultat"));
        inscription.setCommentaire(rs.getString("commentaire"));
        return inscription;
    }
}
