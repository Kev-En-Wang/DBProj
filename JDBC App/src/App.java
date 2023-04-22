/*
 * Made by Kevin Wang
 * ID 99168103
 * Main method for starting up the app
 */

 import javafx.application.Application;
 import javafx.fxml.FXMLLoader;
 import javafx.scene.Parent;
 import javafx.scene.Scene;
 import javafx.stage.Stage;
 
 import java.io.IOException;
 
 public class App extends Application {
 
     @Override
     public void start(Stage stage) throws IOException {
         // Load the main window FXML file and create objects (controls etc.)
         Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
         
         Scene sales = new Scene(root);  // Create scene containing the FXML root node
 
         stage.setScene(sales);  // Place the scene in the stage
         stage.show();  // Display the stage
     }
 
     public static void main(String[] args) {
         launch(args);  // Call superclass to create JavaFX application
     }
 }
 