package dao;

import model.Eleve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsultationCandidatsDao {

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
}
