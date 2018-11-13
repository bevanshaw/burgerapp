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
	private Button logoutBtn;

	@FXML
	private Button viewKitchenBtn;

	@FXML
	private Button refreshBtn;

	@FXML
	private Button addIngredientsBtn;

	@FXML
	private TextField userEmail;

	@FXML
	private TextField quantityText;

	@FXML
	private PasswordField userPassword;

	@FXML
	private Label respondText;

	@FXML
	private Label sesameQ;

	@FXML
	private Label briocheQ;

	@FXML
	private Label beefQ;

	@FXML
	private Label chickenQ;

	@FXML
	private Label fishQ;

	@FXML
	private Label falafelQ;

	@FXML
	private Label lettuceQ;

	@FXML
	private Label tomatoQ;

	@FXML
	private Label pickleQ;

	@FXML
	private Label avocadoQ;

	@FXML
	private Label cheddarQ;

	@FXML
	private Label camembertQ;

	@FXML
	private Label halloumiQ;

	@FXML
	private Label ketchupQ;

	@FXML
	private Label mayoQ;

	@FXML
	private Label mustardQ;

	@FXML
	private CheckBox orderSesame;

	@FXML
	private CheckBox orderBrioche;

	@FXML
	private CheckBox orderBeef;

	@FXML
	private CheckBox orderChicken;

	@FXML
	private CheckBox orderFish;

	@FXML
	private CheckBox orderFalafel;

	@FXML
	private CheckBox orderLettuce;

	@FXML
	private CheckBox orderTomato;

	@FXML
	private CheckBox orderPickle;

	@FXML
	private CheckBox orderAvocado;

	@FXML
	private CheckBox orderCheddar;

	@FXML
	private CheckBox orderCamembert;

	@FXML
	private CheckBox orderHalloumi;

	@FXML
	private CheckBox orderKetchup;

	@FXML
	private CheckBox orderMayo;

	@FXML
	private CheckBox orderMustard;

	private CheckBox[] orderArray = {orderAvocado, orderBeef, orderBrioche, orderCamembert, 
			orderCheddar, orderChicken, orderFalafel, orderFish, 
			orderHalloumi, orderKetchup, orderLettuce, orderMayo, 
			orderMustard, orderPickle, orderSesame, orderTomato};

	private Map<CheckBox, String> checkBoxMap = new HashMap<CheckBox, String>();//key = ingredient checkBox, value = ingredient name
	private Map<String, Label> labelMap = new HashMap<String, Label>();//key = ingredient name, value = label
	//private Map<CheckBox, Map<String,Label>> checkBoxMap = new HashMap<CheckBox, Map<String,Label>>();//key = ingredient checkBox, value = map<ingredient name(key),labelQ(value>>

	private String[] orderLabels = {"avocado", "beef", "brioche", "camembert"};


	@FXML
	public void handleLoginButtonClick(ActionEvent event) {


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

	@FXML
	public void handleAddIngredientsButtonClick(ActionEvent event) throws IOException, JSONException {
		
		//Getting checkBoxMap in field ready for getting keys of ingredients in database.
		populateCheckBoxMap();
		
		populateLabelQMap();
		
		//Get current invetory out of database into a map.
		Connector connector = new Connector();
		HashMap<String, Ingredient> currentInventory = (HashMap<String, Ingredient>) connector.getIngredients();
		
		Iterator iterator= checkBoxMap.entrySet().iterator();
	    while (iterator.hasNext()) {
	        Map.Entry pair = (Map.Entry)iterator.next();
	        CheckBox checkBox = (CheckBox) pair.getKey();	
	        String ingredient = (String) pair.getValue();
	       
	        if(checkBox.isSelected()) {
	        	
	        	//Getting right Ingredient object from database map.
	        	Ingredient ingredAddQty = currentInventory.get(ingredient);
	        	//Getting current quantity value inside Ingredient Object.
	        	int current = ingredAddQty.getQuantity();
	        	//Getting String of numbers to add and converting to an int. 
	        	//NOTE: Check input on this textfield can only be numbers.
	        	String qtyText = quantityText.getText();
	        	
	        	System.out.println(qtyText);
	       
	        	int addQty = Integer.parseInt(qtyText);	
	        	
	        	int increaseQTY = current + addQty;
	        	
	        	String nameIngredient = ingredAddQty.getName();
	        	
	        	connector.updateIngredient(nameIngredient, increaseQTY);
	        	
	        	
	        	//For updating associated label on FXML file.
	        	String updateLabelQ = Integer.toString(increaseQTY);
	        	System.out.println("You got to here "+updateLabelQ);//
	        	
	        	//getting right label from map and updating the visible text (in this case a String number)
	        	labelMap.get(nameIngredient).setText(updateLabelQ);;
	        }
	        
	        iterator.remove(); // avoids a ConcurrentModificationException
	    }

	}

	/**
	 * Last resort hardcoding map so checkboxes relate to ingredient they represent.
	 */
	public void populateCheckBoxMap() {

		checkBoxMap.put(orderAvocado, "avocado");
		checkBoxMap.put(orderBeef, "beef");
		checkBoxMap.put(orderBrioche, "brioche");
		checkBoxMap.put(orderCamembert, "camembert");
		checkBoxMap.put(orderCheddar, "cheddar");
		checkBoxMap.put(orderChicken, "chicken");
		checkBoxMap.put(orderFalafel, "falafel");
		checkBoxMap.put(orderFish, "fish");
		checkBoxMap.put(orderHalloumi, "halloumi");
		checkBoxMap.put(orderKetchup, "ketchup");
		checkBoxMap.put(orderLettuce, "lettuce");
		checkBoxMap.put(orderMayo, "mayo");
		checkBoxMap.put(orderMustard, "mustard");
		checkBoxMap.put(orderPickle, "pickle");
		checkBoxMap.put(orderSesame, "sesame");
		checkBoxMap.put(orderTomato, "tomato");

	}
	
	public void populateLabelQMap() {

		labelMap.put("avocado", avocadoQ);
		labelMap.put("beef", beefQ);
		labelMap.put("brioche", briocheQ);
		labelMap.put("camembert", camembertQ);
		labelMap.put("cheddar", cheddarQ);
		labelMap.put("chicken", chickenQ);
		labelMap.put("falafel", falafelQ);
		labelMap.put("fish", fishQ);
		labelMap.put("halloumi", halloumiQ);
		labelMap.put("ketchup", ketchupQ);
		labelMap.put("lettuce", lettuceQ);
		labelMap.put("mayo", mayoQ);
		labelMap.put("mustard", mustardQ);
		labelMap.put("pickle", pickleQ);
		labelMap.put("sesame", sesameQ);
		labelMap.put("tomato", tomatoQ);

	}

	public void showWorkerScene() throws IOException{
		Stage stage = (Stage)loginBtn.getScene().getWindow();

		Parent root = FXMLLoader.load(getClass().getResource("/graphicGUI/worker/SceneWorker.fxml"));

		Scene scene = new Scene(root);

		stage.setScene(scene);

		stage.show();

	}

	/**
	 * Refreshes managerScene - NOTE: needs a calls to database to get realtime ingredients numbers displayed.
	 * @param event
	 * @throws IOException
	 * @throws JSONException
	 */
	@FXML
	public void handleRefreshButtonClick(ActionEvent event) throws IOException, JSONException{

		Stage stage = (Stage)refreshBtn.getScene().getWindow();

		Parent root = FXMLLoader.load(getClass().getResource("/graphicGUI/manager/managerScene.fxml"));

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





