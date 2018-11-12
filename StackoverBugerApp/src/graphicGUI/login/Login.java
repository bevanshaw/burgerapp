package graphicGUI.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONException;

import graphicGUI.manager.ManagerScene;
import graphicGUI.worker.WorkerScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import server.firebae.rest.Connector;

public class Login implements Initializable{

	@FXML
	private Button loginBtn;

	@FXML
	private TextField userEmail;

	@FXML
	private PasswordField userPassword;
	
	@FXML
	private Label respondText;

	@FXML
	public void handleButtonClick(ActionEvent event) {

		System.out.println("clicked");

		//To call method from ManagerScene class create ManagerScene object
		ManagerScene managerScene = new ManagerScene();
		
		//To call method from WorkerScene class create WorkerScene object
		WorkerScene workerScene = new WorkerScene();
		

		String password = userPassword.getText();
		System.out.println("pass "+password);
		String email = userEmail.getText();
		System.out.println("email " +email);
		Connector connector = new Connector();

		String type ="";

		try {
			type = connector.loginType(password, email);
			System.out.println(type);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(type.equals("manager")) {
			
			respondText.setText("");
			
			managerScene.callManagerScene();

		}else if(type.equals("worker")) {
			
			respondText.setText("");
			
			workerScene.callWorkerScene();

		} else if(type.equals("")) {
			
			respondText.setText("Invalid email and password combination.");
			respondText.setVisible(true);
			
		}

		//windows.setScene(managerScene.CallthisMethod());

		//			}
		//		});

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
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



