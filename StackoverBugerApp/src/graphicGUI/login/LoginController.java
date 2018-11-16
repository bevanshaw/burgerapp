package graphicGUI.login;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.json.JSONException;

import dataModel.Ingredient;
import graphicGUI.worker.WorkerController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.firebae.rest.Connector;

public class LoginController implements Initializable{

	@FXML
	private Button loginBtn;

	@FXML
	private TextField userEmail;

	@FXML
	private PasswordField userPassword;

	@FXML
	private Label respondText;

	/**
	 * Staff log into the production line application. 
	 * Calls showManagerScene method if they are a manager.
	 * Calls showWorkerScene method if they are a worker.
	 * @param event
	 * @throws JSONException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@FXML
	public void handleLoginButtonClick(ActionEvent event) throws JSONException, IOException, InterruptedException {


		String password = userPassword.getText();
		System.out.println("pass "+password);
		String email = userEmail.getText();
		System.out.println("email " +email);
		Connector connector = new Connector();

		String type ="";

		try {
			type = connector.loginType(password, email);

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


	/**
	 * After staff member (manager) logs in they are taken to the ManagerScene.
	 * @throws IOException
	 * @throws JSONException
	 * @throws InterruptedException
	 */
	public void showManagerScene() throws IOException, JSONException, InterruptedException{
		Stage stage = (Stage)loginBtn.getScene().getWindow(); 

		Parent root = FXMLLoader.load(getClass().getResource("/graphicGUI/manager/ManagerScene.fxml"));

		Scene scene = new Scene(root);

		stage.setScene(scene);

		stage.show();

	}


	/**
	 * After staff member (worker) logs in they are taken to the WorkerScene.
	 * @throws IOException
	 * @throws JSONException
	 * @throws InterruptedException
	 */
	public void showWorkerScene() throws IOException, JSONException{
		Stage stage = (Stage)loginBtn.getScene().getWindow();

		Parent root = FXMLLoader.load(getClass().getResource("/graphicGUI/worker/WorkerScene.fxml"));

		Scene scene = new Scene(root);

		stage.setScene(scene);

		stage.show();


	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}



}





