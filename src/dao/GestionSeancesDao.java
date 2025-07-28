package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.Seance;

public class GestionSeancesDao {

    // Récupérer toutes les séances de la base de données
    public List<Seance> getAllSeances() {
        List<Seance> seances = new ArrayList<>();
        String query = "SELECT * FROM seances";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getInstance();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Seance seance = new Seance();
                seance.setIdSeance(resultSet.getInt("idSeance"));
                seance.setIdMoniteur(resultSet.getInt("idMoniteur"));
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
            closeResources(resultSet, preparedStatement, connection);
        }

        return seances;
    }

    // Ajouter une séance
    public void addSeance(Seance seance) {
        String query = "INSERT INTO seances (idMoniteur, idVehicule, idEleve, dateSeance, duree, typeSeance, statut, observations) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = DatabaseConnection.getInstance();
            preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, seance.getIdMoniteur());

            if (seance.getIdVehicule() == null) {
                preparedStatement.setNull(2, Types.INTEGER);
            } else {
                preparedStatement.setInt(2, seance.getIdVehicule());
            }

            preparedStatement.setInt(3, seance.getIdEleve());
            preparedStatement.setDate(4, Date.valueOf(seance.getDateSeance()));
            preparedStatement.setInt(5, seance.getDuree());

            String typeSeance = seance.getTypeSeance();
            if (typeSeance != null && typeSeance.length() > 20) {
                typeSeance = typeSeance.substring(0, 20);
            }
            preparedStatement.setString(6, typeSeance);

            preparedStatement.setString(7, seance.getStatut());
            preparedStatement.setString(8, seance.getObservations());

            preparedStatement.executeUpdate();

            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                seance.setIdSeance(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Échec de la récupération de l'ID de la séance générée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'ajout de la séance.", e);
        } finally {
            closeResources(generatedKeys, preparedStatement, connection);
        }
    }

    // Mettre à jour une séance
    public void updateSeance(Seance seance) {
        String query = "UPDATE seances SET idMoniteur = ?, idVehicule = ?, idEleve = ?, dateSeance = ?, duree = ?, typeSeance = ?, statut = ?, observations = ? WHERE idSeance = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DatabaseConnection.getInstance();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, seance.getIdMoniteur());
            preparedStatement.setInt(2, seance.getIdVehicule());
            preparedStatement.setInt(3, seance.getIdEleve());
            preparedStatement.setDate(4, java.sql.Date.valueOf(seance.getDateSeance()));
            preparedStatement.setInt(5, seance.getDuree());

            String typeSeance = seance.getTypeSeance();
            if (typeSeance != null && typeSeance.length() > 20) {
                typeSeance = typeSeance.substring(0, 20);
            }
            preparedStatement.setString(6, typeSeance);

            preparedStatement.setString(7, seance.getStatut());
            preparedStatement.setString(8, seance.getObservations());
            preparedStatement.setInt(9, seance.getIdSeance());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour de la séance.", e);
        } finally {
            closeResources(null, preparedStatement, connection);
        }
    }

    // Supprimer une séance
    public void deleteSeance(int idSeance) {
        String query = "DELETE FROM seances WHERE idSeance = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DatabaseConnection.getInstance();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, idSeance);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression de la séance.", e);
        } finally {
            closeResources(null, preparedStatement, connection);
        }
    }

    // Vérifier si une séance existe
    public boolean exists(int idSeanceFormation) {
        String query = "SELECT COUNT(*) FROM seances WHERE idSeance = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getInstance();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idSeanceFormation);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la vérification de l'existence de la séance.", e);
        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }
        return false;
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