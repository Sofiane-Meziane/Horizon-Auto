package dao;

import model.Paiement;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestionPaiementsDao {

    /**
     * Retrieve all payments from the database.
     * @return List of Paiement objects.
     */
    public List<Paiement> getAllPaiements() {
        List<Paiement> paiements = new ArrayList<>();
        String query = "SELECT * FROM paiements";

        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                paiements.add(mapResultSetToPaiement(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paiements;
    }

    /**
     * Retrieve a payment by ID from the database.
     * @param idPaiement The ID of the payment.
     * @return Paiement object if found, otherwise null.
     */
    public Paiement getPaiementById(int idPaiement) {
        String query = "SELECT * FROM paiements WHERE idPaiement = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idPaiement);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPaiement(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Add a new payment to the database and retrieve the generated ID.
     * @param paiement The Paiement object to be added.
     */
    public void addPaiement(Paiement paiement) {
        String query = "INSERT INTO paiements (idEleve, datePaiement, montant, moyenPaiement) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, paiement.getIdEleve());
            ps.setDate(2, Date.valueOf(paiement.getDatePaiement()));
            ps.setDouble(3, paiement.getMontant());
            ps.setString(4, paiement.getMoyenPaiement());
            ps.executeUpdate();

            // Retrieve the generated ID and set it to the Paiement object
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    paiement.setIdPaiement(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update an existing payment in the database.
     * @param paiement The Paiement object with updated details.
     */
    public void updatePaiement(Paiement paiement) {
        String query = "UPDATE paiements SET idEleve = ?, datePaiement = ?, montant = ?, moyenPaiement = ? WHERE idPaiement = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, paiement.getIdEleve());
            ps.setDate(2, Date.valueOf(paiement.getDatePaiement()));
            ps.setDouble(3, paiement.getMontant());
            ps.setString(4, paiement.getMoyenPaiement());
            ps.setInt(5, paiement.getIdPaiement());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete a payment from the database based on its ID.
     * @param idPaiement The ID of the payment to be deleted.
     */
    public void deletePaiement(int idPaiement) {
        String query = "DELETE FROM paiements WHERE idPaiement = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idPaiement);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if a payment exists in the database based on its ID.
     * @param idPaiement The ID of the payment.
     * @return True if the payment exists, otherwise false.
     */
    public boolean exists(int idPaiement) {
        String query = "SELECT 1 FROM paiements WHERE idPaiement = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idPaiement);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // If a row is returned, the payment exists
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Récupérer le montant total payé par un élève.
     * @param idEleve L'ID de l'élève.
     * @return Le montant total payé.
     */
    public double getMontantTotalPaye(int idEleve) {
        String query = "SELECT SUM(montant) AS total FROM paiements WHERE idEleve = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idEleve);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Helper method to map ResultSet data to a Paiement object.
     * @param rs The ResultSet containing payment data.
     * @return A Paiement object.
     */
    private Paiement mapResultSetToPaiement(ResultSet rs) throws SQLException {
        Paiement paiement = new Paiement();
        paiement.setIdPaiement(rs.getInt("idPaiement"));
        paiement.setIdEleve(rs.getInt("idEleve"));
        paiement.setDatePaiement(rs.getDate("datePaiement").toLocalDate());
        paiement.setMontant(rs.getDouble("montant"));
        paiement.setMoyenPaiement(rs.getString("moyenPaiement"));
        return paiement;
    }
}