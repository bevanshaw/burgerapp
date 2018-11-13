package graphicGUI.worker;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainGUI extends Application{

	public MainGUI() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(Stage primaryStage) {
		
		AnchorPane page;
		try {
			page = (AnchorPane) FXMLLoader.load(getClass().getResource("SceneWorker.fxml"));
			primaryStage.setTitle("test");
			primaryStage.setScene(new Scene(page));
			
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
