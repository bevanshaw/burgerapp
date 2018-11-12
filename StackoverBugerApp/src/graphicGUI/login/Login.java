package graphicGUI.login;

import java.io.IOException;

import org.json.JSONException;

import graphicGUI.manager.ManagerScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import server.firebae.rest.Connector;

public class Login {
	
	@FXML
	private Button loginBtn;
	
	@FXML
	private TextField userEmail;
	
	@FXML
	private PasswordField userPassword;
	
	@FXML
	public void handleButtonClick(ActionEvent event) {
		
System.out.println("clicked");
		
//		loginBtn.setOnAction(new EventHandler<ActionEvent>() {
//			
//			//create condition to choose ManagerScene or WorkerScene
//			@Override
//			public void handle (ActionEvent event) {
				ManagerScene managerScene = new ManagerScene();
				//call the method from another class
				
				String password = userPassword.getText();
				System.out.println("pass "+password);
				String email = userEmail.getText();
				System.out.println("email " +email);
				Connector loginConnector = new Connector();
				
				String type = "";
						
				try {
					type = loginConnector.loginType(password, email);
					System.out.println(type);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(type.equals("manager")) {
					
					managerScene.CallthisMethod();
					
				}else if(type.equals("worker")) {
					
					
				} else {
					type = "This email: "+email+ " and password: "+password+ " is not a current staff member";
				}
				
				//windows.setScene(managerScene.CallthisMethod());
				
//			}
//		});
		
	}
}
	
//	@FXML
//    private Button exitBtn ;
//
//    @FXML
//    private Button openBtn ;
//
//    public void initialize() {
//        // initialization here, if needed...
//    }
//
//    @FXML
//    private void handleButtonClick(ActionEvent event) {
//        // I really don't recommend using a single handler like this,
//        // but it will work
//        if (event.getSource() == exitBtn) {
//            exitBtn.getScene().getWindow().hide();
//        } else if (event.getSource() == openBtn) {
//            // do open action...
//        }
//        // etc...
//    }

	

