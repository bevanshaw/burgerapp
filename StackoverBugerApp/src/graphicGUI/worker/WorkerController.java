package graphicGUI.worker;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class WorkerController implements Initializable{

	@FXML
	private Button logoutBtn;

	@FXML
	private TextField quantityText;



	@FXML
	public void handleButtonClick(ActionEvent event) {

		System.out.println("logout clicked");


		//Separate method for later?
		String quantity = quantityText.getText();



		//windows.setScene(managerScene.CallthisMethod());

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	public void callWorkerScene() {
		System.out.println("Worker logged in");

	}

}
