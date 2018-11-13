package graphicGUI.login;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class MyApplication  extends Application {
    
  
    @Override
    public void start(Stage primaryStage) {
        try {
            // Read file fxml and draw interface.
          	//Parent root = FXMLLoader.load(getClass().getResource("/graphicGUI.login/SceneLogin.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("SceneLogin.fxml"));
            AnchorPane page = (AnchorPane) FXMLLoader.load(getClass().getResource("loginScene.fxml"));
            primaryStage.setTitle("My Application");
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
