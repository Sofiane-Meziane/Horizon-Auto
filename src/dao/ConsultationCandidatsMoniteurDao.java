package dao;

import model.Eleve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsultationCandidatsMoniteurDao {

    public List<Eleve> getAllEleves() {
        List<Eleve> eleves = new ArrayList<>();
        String query = "SELECT * FROM eleves";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getInstance(); // Récupération de la connexion via singleton
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Eleve eleve = new Eleve();
                eleve.setIdEleve(resultSet.getInt("idEleve")); // Colonne "id"
                eleve.setNom(resultSet.getString("nom"));
                eleve.setPrenom(resultSet.getString("prenom"));
                eleve.setAdresse(resultSet.getString("adresse"));
                eleve.setTelephone(resultSet.getString("telephone"));
                eleve.setEmail(resultSet.getString("email"));
                eleve.setDateInscription(resultSet.getDate("dateInscription").toLocalDate());
                eleve.setMontantTotal(resultSet.getDouble("montantTotal"));
                eleve.setMontantPaye(resultSet.getDouble("montantPaye"));

                eleves.add(eleve);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des élèves.", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return eleves;
    }

    public List<Eleve> getCandidatsByMoniteur(int idMoniteur) {
        List<Eleve> candidats = new ArrayList<>();
        String query = "SELECT DISTINCT e.* FROM eleves e " +
                       "JOIN seances s ON e.idEleve = s.idEleve " +
                       "WHERE s.idMoniteur = ?";

        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idMoniteur);
            System.out.println("Requête exécutée pour idMoniteur = " + idMoniteur);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
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

                    candidats.add(eleve);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des candidats assignés au moniteur.", e);
        }

        if (candidats.isEmpty()) {
            System.out.println("Aucun candidat trouvé pour le moniteur ID : " + idMoniteur);
        }

        return candidats;
    }
}