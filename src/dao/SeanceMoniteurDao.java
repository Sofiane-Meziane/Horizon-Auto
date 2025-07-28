package dao;

import model.Seance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeanceMoniteurDao {

    public List<Seance> getSeancesByMoniteur(int idMoniteur) {
        // Validation de l'ID du moniteur
        if (idMoniteur <= 0) {
            throw new IllegalArgumentException("ID du moniteur invalide : " + idMoniteur);
        }

        List<Seance> seances = new ArrayList<>();
        String query = "SELECT * FROM seances WHERE idMoniteur = ?";

        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idMoniteur);
            System.out.println("Requête exécutée pour idMoniteur = " + idMoniteur);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Seance seance = new Seance();
                    seance.setIdSeance(rs.getInt("idSeance"));
                    seance.setIdMoniteur(rs.getInt("idMoniteur"));
                    seance.setIdVehicule(rs.getInt("idVehicule"));
                    seance.setIdEleve(rs.getInt("idEleve"));
                    seance.setDateSeance(rs.getDate("dateSeance").toLocalDate());
                    seance.setDuree(rs.getInt("duree"));
                    seance.setTypeSeance(rs.getString("typeSeance"));
                    seance.setStatut(rs.getString("statut"));
                    seance.setObservations(rs.getString("observations"));

                    seances.add(seance);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des séances pour le moniteur.", e);
        }

        if (seances.isEmpty()) {
            System.out.println("Aucune séance trouvée pour le moniteur ID : " + idMoniteur);
        }

        return seances;
    }
    
    public void updateInfo(Seance seance) {
    String query = "UPDATE seances SET statut = ?, observations = ? WHERE idSeance = ?";
    try (Connection connection = DatabaseConnection.getInstance();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

        // Mise à jour des colonnes "statut" et "observations"
        preparedStatement.setString(1, seance.getStatut());
        preparedStatement.setString(2, seance.getObservations());
        preparedStatement.setInt(3, seance.getIdSeance());

        // Exécution de la requête
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Erreur lors de la mise à jour des informations de la séance.", e);
    }
}

}
