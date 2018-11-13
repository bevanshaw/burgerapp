package graphicGUI.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONException;

import graphicGUI.worker.WorkerController;
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
import javafx.stage.Stage;
import server.firebae.rest.Connector;

public class LoginController implements Initializable{

	@FXML
	private Button loginBtn;
	
	@FXML
	private Button logoutBtn;
	
	@FXML
	private Button viewKitchenBtn;

	@FXML
	private TextField userEmail;
	
	@FXML
	private TextField quantityText;

	@FXML
	private PasswordField userPassword;

	@FXML
	private Label respondText;

	@FXML
	public void handleLoginButtonClick(ActionEvent event) {

		System.out.println("clicked");

		//To call method from ManagerScene class create ManagerScene object
		//ManagerScene managerScene = new ManagerScene();

		//To call method from WorkerScene class create WorkerScene object
		WorkerController workerScene = new WorkerController();


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

			try {
				showManagerScene();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}else if(type.equals("worker")) {

			respondText.setText("");

			try {
				showWorkerScene();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if(type.equals("")) {

			respondText.setText("Invalid email and password combination.");
			respondText.setVisible(true);

		}

	}
	
	@FXML
	public void handleLogoutButtonClick(ActionEvent event) {
		Stage stage = (Stage)logoutBtn.getScene().getWindow();

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/graphicGUI/login/loginScene.fxml"));
			Scene scene = new Scene(root);

			stage.setScene(scene);

			stage.show();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * To allow Manager to navigate from Current Inventory to the WorkerScene.
	 * @param event, this is a click on the view kitchen button in current inventory.
	 * @throws IOException
	 */
	@FXML
	public void handleViewKitchenButtonClick(ActionEvent event) throws IOException {
		Stage stage = (Stage)viewKitchenBtn.getScene().getWindow();

		Parent root = FXMLLoader.load(getClass().getResource("/graphicGUI/worker/SceneWorker.fxml"));

		Scene scene = new Scene(root);

		stage.setScene(scene);

		stage.show();

		
	}

	public void showManagerScene() throws IOException{
		Stage stage = (Stage)loginBtn.getScene().getWindow();

		Parent root = FXMLLoader.load(getClass().getResource("/graphicGUI/manager/managerScene.fxml"));

		Scene scene = new Scene(root);

		stage.setScene(scene);

		stage.show();

	}

	public void showWorkerScene() throws IOException{
		Stage stage = (Stage)loginBtn.getScene().getWindow();

		Parent root = FXMLLoader.load(getClass().getResource("/graphicGUI/worker/SceneWorker.fxml"));

		Scene scene = new Scene(root);

		stage.setScene(scene);

		stage.show();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
	private void goManager() {

	}
}





