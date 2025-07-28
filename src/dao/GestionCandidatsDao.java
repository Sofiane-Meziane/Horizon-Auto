package dao;

import model.Eleve;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestionCandidatsDao {

    /**
     * Récupérer tous les élèves dans la base de données.
     * @return Liste des élèves.
     */
    public List<Eleve> getAllEleves() {
        List<Eleve> eleves = new ArrayList<>();
        String query = "SELECT * FROM eleves";

        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                eleves.add(mapResultSetToEleve(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eleves;
    }

    /**
     * Récupérer un élève par son ID.
     * @param idEleve L'ID de l'élève.
     * @return L'objet Eleve si trouvé, sinon null.
     */
    public Eleve getEleveById(int idEleve) {
        String query = "SELECT * FROM eleves WHERE idEleve = ?";

        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idEleve);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEleve(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Ajouter un nouvel élève dans la base de données.
     * @param eleve L'objet Eleve à ajouter.
     */
    public void addEleve(Eleve eleve) {
        String query = "INSERT INTO eleves (nom, prenom, adresse, telephone, email, dateInscription, montantTotal, montantPaye) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, eleve.getNom());
            ps.setString(2, eleve.getPrenom());
            ps.setString(3, eleve.getAdresse());
            ps.setString(4, eleve.getTelephone());
            ps.setString(5, eleve.getEmail());
            ps.setDate(6, Date.valueOf(eleve.getDateInscription()));
            ps.setDouble(7, eleve.getMontantTotal());
            ps.setDouble(8, eleve.getMontantPaye());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    eleve.setIdEleve(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mettre à jour un élève existant dans la base de données.
     * @param eleve L'objet Eleve avec les détails mis à jour.
     */
    public void updateEleve(Eleve eleve) {
        String query = "UPDATE eleves SET nom = ?, prenom = ?, adresse = ?, telephone = ?, email = ?, dateInscription = ?, montantTotal = ?, montantPaye = ? WHERE idEleve = ?";

        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, eleve.getNom());
            ps.setString(2, eleve.getPrenom());
            ps.setString(3, eleve.getAdresse());
            ps.setString(4, eleve.getTelephone());
            ps.setString(5, eleve.getEmail());
            ps.setDate(6, Date.valueOf(eleve.getDateInscription()));
            ps.setDouble(7, eleve.getMontantTotal());
            ps.setDouble(8, eleve.getMontantPaye());
            ps.setInt(9, eleve.getIdEleve());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Supprimer un élève de la base de données par son ID.
     * @param idEleve L'ID de l'élève à supprimer.
     */
    public void deleteEleve(int idEleve) {
        String query = "DELETE FROM eleves WHERE idEleve = ?";

        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idEleve);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Vérifier si un élève existe dans la base de données.
     * @param idEleve L'ID de l'élève.
     * @return True si l'élève existe, sinon False.
     */
    public boolean exists(int idEleve) {
        String query = "SELECT 1 FROM eleves WHERE idEleve = ?";

        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idEleve);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Si une ligne est retournée, l'élève existe
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mettre à jour le montant payé d'un élève.
     * @param idEleve L'ID de l'élève.
     * @param montantPaye Le nouveau montant payé.
     */
    public void updateMontantPaye(int idEleve, double montantPaye) {
        String query = "UPDATE eleves SET montantPaye = ? WHERE idEleve = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setDouble(1, montantPaye);
            ps.setInt(2, idEleve);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode auxiliaire pour mapper un ResultSet à un objet Eleve.
     * @param rs Le ResultSet contenant les données.
     * @return Un objet Eleve.
     */
    private Eleve mapResultSetToEleve(ResultSet rs) throws SQLException {
        Eleve eleve = new Eleve();
        eleve.setIdEleve(rs.getInt("idEleve"));
        eleve.setNom(rs.getString("nom"));
        eleve.setPrenom(rs.getString("prenom"));
        eleve.setAdresse(rs.getString("adresse"));
        eleve.setTelephone(rs.getString("telephone"));
        eleve.setEmail(rs.getString("email"));
        eleve.setDateInscription(rs.getDate("dateInscription").toLocalDate());
        eleve.setMontantTotal(rs.getDouble("montantTotal"));
        eleve.setMontantPaye(rs.getDouble("montantPaye"));
        return eleve;
    }
}