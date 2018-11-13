package graphicGUI.login;

import graphicGUI.manager.ManagerScene;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginScene extends Application{
	Stage windows;
	
	public static void main (String [] args) {
		launch();
		ManagerScene ManagerGUI = new ManagerScene();
		ManagerGUI.CallthisMethod();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	windows = primaryStage;
	windows.setTitle("Loging StackoverBurger worker side");
	
	GridPane grid = new GridPane();
	grid.setAlignment(Pos.CENTER);
	grid.setVgap(10);
	grid.setHgap(10);
	grid.setPadding(new Insets(10));
	
	Text welcomeTxt=new Text ("Welcome");
	welcomeTxt.setFont(Font.font("Tahoma", FontWeight.LIGHT,25));
	grid.add(welcomeTxt, 0, 0);
	
	Label lblUser = new Label ("Username");
	grid.add(lblUser, 0, 1);
	
	TextField txtUser=new TextField();
	txtUser.setPromptText("username");
	grid.add(txtUser, 1, 1);
	
	Label lblPassword = new Label ("Password");
	grid.add(lblPassword, 0, 2);
	
	PasswordField pwBox=new PasswordField();
	pwBox.setPromptText("Password");
	grid.add(pwBox, 1, 2);
	
	Button logingBtn=new Button ("Login");
	grid.add(logingBtn,1,3);
	
	//set the condition or root of this button
	logingBtn.setOnAction(new EventHandler<ActionEvent>() {
		
		//create condition to choose ManagerScene or WorkerScene
		@Override
		public void handle (ActionEvent event) {
			ManagerScene managerScene = new ManagerScene();
			
			//call the method from another class
			windows.setScene(managerScene.CallthisMethod());
			managerScene.CallthisMethod();
		}
	});
	
	Scene scene=new Scene(grid,500,500);
	windows.setScene(scene);
	windows.show();
	
	
		
	}

}
