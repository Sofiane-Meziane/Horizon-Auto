package dao;

import model.Vehicule;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculeDao {

    /**
     * Retrieve all vehicles from the database.
     * @return List of Vehicule objects.
     */
    public List<Vehicule> getAllVehicules() {
        List<Vehicule> vehicules = new ArrayList<>();
        String query = "SELECT * FROM vehicules";

        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                vehicules.add(mapResultSetToVehicule(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicules;
    }

    /**
     * Retrieve a vehicle by ID from the database.
     * @param idVehicule The ID of the vehicle.
     * @return Vehicule object if found, otherwise null.
     */
    public Vehicule getVehiculeById(int idVehicule) {
        String query = "SELECT * FROM vehicules WHERE idVehicule = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idVehicule);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToVehicule(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Add a new vehicle to the database and retrieve the generated ID.
     * @param vehicule The Vehicule object to be added.
     */
    public void addVehicule(Vehicule vehicule) {
        String query = "INSERT INTO vehicules (marque, modele, immatriculation, typeVehicule) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, vehicule.getMarque());
            ps.setString(2, vehicule.getModele());
            ps.setString(3, vehicule.getImmatriculation());
            ps.setString(4, vehicule.getTypeVehicule());
            ps.executeUpdate();

            // Retrieve the generated ID and set it to the Vehicule object
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    vehicule.setIdVehicule(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update an existing vehicle in the database.
     * @param vehicule The Vehicule object with updated details.
     */
    public void updateVehicule(Vehicule vehicule) {
        String query = "UPDATE vehicules SET marque = ?, modele = ?, immatriculation = ?, typeVehicule = ? WHERE idVehicule = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, vehicule.getMarque());
            ps.setString(2, vehicule.getModele());
            ps.setString(3, vehicule.getImmatriculation());
            ps.setString(4, vehicule.getTypeVehicule());
            ps.setInt(5, vehicule.getIdVehicule());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete a vehicle from the database based on its ID.
     * @param idVehicule The ID of the vehicle to be deleted.
     */
    public void deleteVehicule(int idVehicule) {
        String query = "DELETE FROM vehicules WHERE idVehicule = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idVehicule);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if a vehicle exists in the database based on its ID.
     * @param idVehicule The ID of the vehicle.
     * @return True if the vehicle exists, otherwise false.
     */
    public boolean exists(int idVehicule) {
        String query = "SELECT 1 FROM vehicules WHERE idVehicule = ?";
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idVehicule);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // If a row is returned, the vehicle exists
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Helper method to map ResultSet data to a Vehicule object.
     * @param rs The ResultSet containing vehicle data.
     * @return A Vehicule object.
     */
    private Vehicule mapResultSetToVehicule(ResultSet rs) throws SQLException {
        Vehicule vehicule = new Vehicule();
        vehicule.setIdVehicule(rs.getInt("idVehicule"));
        vehicule.setMarque(rs.getString("marque"));
        vehicule.setModele(rs.getString("modele"));
        vehicule.setImmatriculation(rs.getString("immatriculation"));
        vehicule.setTypeVehicule(rs.getString("typeVehicule"));
        return vehicule;
    }
}
