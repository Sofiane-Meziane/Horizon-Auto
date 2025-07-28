package dao;

import model.Seance;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultationSeancesDao {

    public List<Seance> getAllSeances() {
        List<Seance> seances = new ArrayList<>();
        String query = "SELECT * FROM seances";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getInstance(); // Connexion à la base de données
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Seance seance = new Seance();
                seance.setIdSeance(resultSet.getInt("idSeance"));
                seance.setIdMoniteur(resultSet.getInt("idMoniteur"));
                //seance.setIdVehicule(resultSet.getInt("idVehicule"));
                int idVehicule = resultSet.getInt("idVehicule");
                    if (resultSet.wasNull()) {
                        seance.setIdVehicule(null);
                    } else {
                        seance.setIdVehicule(idVehicule);
                }
                seance.setIdEleve(resultSet.getInt("idEleve"));
                seance.setDateSeance(resultSet.getDate("dateSeance").toLocalDate());
                seance.setDuree(resultSet.getInt("duree"));
                seance.setTypeSeance(resultSet.getString("typeSeance"));
                seance.setStatut(resultSet.getString("statut"));
                seance.setObservations(resultSet.getString("observations"));

                seances.add(seance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des séances.", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return seances;
    }
}
