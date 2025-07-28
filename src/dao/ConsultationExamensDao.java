package dao;

import model.Examen;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultationExamensDao {

    public List<Examen> getAllExamens() {
        List<Examen> examens = new ArrayList<>();
        String query = "SELECT * FROM examens";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getInstance(); // Connexion à la base de données
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Examen examen = new Examen();
                examen.setIdExamen(resultSet.getInt("idExamen"));
                examen.setTypeExamen(resultSet.getString("typeExamen"));
                examen.setDateExamen(resultSet.getDate("dateExamen").toLocalDate());
                examen.setLieu(resultSet.getString("lieu"));

                examens.add(examen);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des examens.", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return examens;
    }
}
