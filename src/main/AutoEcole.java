package main;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class AutoEcole extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            
            // Set the scene with preferred dimensions
            Scene scene = new Scene(root, 700, 500);
            
            // Set stage title and scene
            primaryStage.setTitle("Driving School Management - Login");
            primaryStage.setScene(scene);
            //primaryStage.setMaximized(true);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
       
    }
}
