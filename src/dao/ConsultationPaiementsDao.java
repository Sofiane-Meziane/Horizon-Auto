package dao;

import model.Paiement;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultationPaiementsDao {

    public List<Paiement> getAllPaiements() {
        List<Paiement> paiements = new ArrayList<>();
        String query = "SELECT * FROM paiements";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getInstance(); // Connexion à la base de données
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Paiement paiement = new Paiement();
                paiement.setIdPaiement(resultSet.getInt("idPaiement"));
                paiement.setIdEleve(resultSet.getInt("idEleve"));
                paiement.setDatePaiement(resultSet.getDate("datePaiement").toLocalDate());
                paiement.setMontant(resultSet.getDouble("montant"));
                paiement.setMoyenPaiement(resultSet.getString("moyenPaiement"));

                paiements.add(paiement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des paiements.", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return paiements;
    }
}
