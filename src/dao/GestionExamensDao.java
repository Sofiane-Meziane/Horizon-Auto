package dao;

import model.Examen;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestionExamensDao {

    private Connection connection;

    // Constructor
    public GestionExamensDao() {
        this.connection = DatabaseConnection.getInstance();
    }

    /**
     * Retrieve all exams from the database.
     * 
     * @return List of Examen objects.
     */
    public List<Examen> getAllExamens() {
        List<Examen> examens = new ArrayList<>();
        String query = "SELECT * FROM examens";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                examens.add(mapResultSetToExamen(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all exams: " + e.getMessage());
        }
        return examens;
    }

    /**
     * Retrieve an exam by ID from the database.
     * 
     * @param idExamen The ID of the exam.
     * @return Examen object if found, otherwise null.
     */
    public Examen getExamenById(int idExamen) {
        String query = "SELECT * FROM examens WHERE idExamen = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idExamen);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToExamen(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching exam by ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Add a new exam to the database and retrieve the generated ID.
     * 
     * @param examen The Examen object to be added.
     */
    public void addExamen(Examen examen) {
        String query = "INSERT INTO examens (typeExamen, dateExamen, lieu) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, examen.getTypeExamen());
            ps.setDate(2, Date.valueOf(examen.getDateExamen()));
            ps.setString(3, examen.getLieu());
            ps.executeUpdate();

            // Retrieve the generated ID and set it to the Examen object
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    examen.setIdExamen(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding exam: " + e.getMessage());
        }
    }

    /**
     * Update an existing exam in the database.
     * 
     * @param examen The Examen object with updated details.
     */
    public void updateExamen(Examen examen) {
        String query = "UPDATE examens SET typeExamen = ?, dateExamen = ?, lieu = ? WHERE idExamen = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, examen.getTypeExamen());
            ps.setDate(2, Date.valueOf(examen.getDateExamen()));
            ps.setString(3, examen.getLieu());
            ps.setInt(4, examen.getIdExamen());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating exam: " + e.getMessage());
        }
    }

    /**
     * Delete an exam from the database based on its ID.
     * 
     * @param idExamen The ID of the exam to be deleted.
     */
    public void deleteExamen(int idExamen) {
        String query = "DELETE FROM examens WHERE idExamen = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idExamen);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting exam: " + e.getMessage());
        }
    }

    /**
     * Check if an exam exists in the database by ID.
     * 
     * @param idExamen The ID of the exam to check.
     * @return True if the exam exists, otherwise false.
     */
    /**
 * Check if an exam exists in the database by ID.
 * @param idExamen The ID of the exam to check.
 * @return True if the exam exists, otherwise false.
 */
public boolean exists(int  idExamen) {
        String query = "SELECT 1 FROM examens WHERE  idExamen = ?";

        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1,  idExamen);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Si une ligne est retournée, l'élève existe
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Helper method to map ResultSet data to an Examen object.
     * 
     * @param rs The ResultSet containing exam data.
     * @return An Examen object.
     */
    private Examen mapResultSetToExamen(ResultSet rs) throws SQLException {
        Examen examen = new Examen();
        examen.setIdExamen(rs.getInt("idExamen"));
        examen.setTypeExamen(rs.getString("typeExamen"));
        examen.setDateExamen(rs.getDate("dateExamen").toLocalDate());
        examen.setLieu(rs.getString("lieu"));
        return examen;
    }
}
