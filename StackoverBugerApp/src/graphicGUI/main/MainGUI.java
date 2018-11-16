package graphicGUI.main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
/**
 *  This class has the main from which reads the fxml files and creates the graphic user interface.
 * @author shawbeva
 *
 */
public class MainGUI  extends Application {
    
  
    @Override
    public void start(Stage primaryStage) {
        try {
            // Read file fxml and draw interface.
            AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("/graphicGUI/login/LoginScene.fxml"));
            primaryStage.setTitle("Stack Over Burger");
            //primaryStage.setScene(new Scene(root));
            primaryStage.setScene(new Scene(page));
            primaryStage.show();
         
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
