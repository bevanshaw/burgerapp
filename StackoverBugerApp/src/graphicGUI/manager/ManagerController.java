package graphicGUI.manager;


	import java.io.IOException;
	import java.net.URL;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.Iterator;
	import java.util.Map;
	import java.util.ResourceBundle;

	import org.json.JSONException;
	import dataModel.Ingredient;
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
	import javafx.scene.control.TextField;
	import javafx.stage.Stage;
	import server.firebae.rest.Connector;

	public class ManagerController implements Initializable{

		//Fields: Buttons.
		@FXML
		private Button logoutBtn;

		@FXML
		private Button viewKitchenBtn;

		@FXML
		private Button refreshBtn;

		@FXML
		private Button addIngredientsBtn;
		
		//Fields: TextField.
		@FXML
		private TextField quantityText;
		
		//Fields: Labels.
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

		//Fields: CheckBoxes.
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
		
		private final static int lowStockThreshold = 20;

		//Fields: Maps.
		private Map<CheckBox, String> checkBoxMap = new HashMap<CheckBox, String>();//key = ingredient checkBox, value = ingredient name
		private Map<String, Label> labelMap = new HashMap<String, Label>();//key = ingredient name, value = label
		
		
		/**
		 * To allow staff to logout from the Inventory page. 
		 * @param event, this is a click on the "logout" button in the Inventory page.
		 */
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
		 * To allow staff to navigate from Current Inventory to the WorkerScene.
		 * @param event, this is a click on the "view kitchen" button in the Inventory page.
		 * @throws IOException
		 */
		@FXML
		public void handleViewKitchenButtonClick(ActionEvent event) throws IOException {
			Stage stage = (Stage)viewKitchenBtn.getScene().getWindow();

			Parent root = FXMLLoader.load(getClass().getResource("/graphicGUI/worker/WorkerScene.fxml"));

			Scene scene = new Scene(root);

			stage.setScene(scene);

			stage.show();

		}

		/**
		 * This allows the manager to add or subtract the amount they specify in the "quantity" TextField 
		 * from any of the ingredients that have their CheckBox selected in the Inventory.
		 * @param event, this is a click on the "+/- QTY" button in the Inventory page.
		 * @throws IOException
		 * @throws JSONException
		 */
		@FXML
		public void handleQtyIngredientsButtonClick(ActionEvent event) throws IOException, JSONException {

			//Getting checkBoxMap and LabelMap (in above fields) ready for getting keys of ingredients in database.
			populateCheckBoxMap();

			populateLabelMap();

			//Get current inventory out of database into a map.
			Connector connector = new Connector();
			HashMap<String, Ingredient> databaseInventory = (HashMap<String, Ingredient>) connector.getIngredients();

			//Iterate over checkBoxMap to get find ingredients that have selected CheckBoxes.
			Iterator iterator= checkBoxMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry pair = (Map.Entry)iterator.next();
				CheckBox checkBox = (CheckBox) pair.getKey();	
				String ingredient = (String) pair.getValue();

				if(checkBox.isSelected()) {

					//Getting right Ingredient object from database map.
					Ingredient ingredAddQty = databaseInventory.get(ingredient);
					//Getting current quantity value inside Ingredient Object.
					int current = ingredAddQty.getQuantity();
					
					
					//Get user input from "quantity" textfield and handle incorrect inputs.
					String qtyText = quantityText.getText();

					//Replace anything except numbers, "." and "-" with empty String.
					qtyText = qtyText.replaceAll("[^0-9.-]", "");
					
					//If a decimal has been entered, ignore the fraction following the decimal.
					if(qtyText.contains(".")) {
					
						String[]noDecimal = qtyText.split("\\.");
					
						qtyText = noDecimal[0];
						
						
					}
					
					if(qtyText.equals("")){
						qtyText = "0";
						quantityText.clear();
					}
					
					//Getting String of numbers to add and converting to an int. 
					int changeQty = Integer.parseInt(qtyText);	
					
					
					int updateQTY = current + changeQty;
					
					if (updateQTY < 0) {
						updateQTY = 0;
					}

					String nameIngredient = ingredAddQty.getName();

					//Update ingredient in database.
					connector.updateIngredient(nameIngredient, updateQTY);


					//For updating associated label on inventory page.
					String updateLabelQ = Integer.toString(updateQTY);

					//Getting right label from map and updating the visible text (in this case a String number)
					displayIngredients();
				}

				iterator.remove(); // avoids a ConcurrentModificationException
			}

		}

		/**
		 * Inserting key = checkBox, value = ingredient name into checkBoxMap 
		 * so CheckBoxes are linked to ingredient they represent.
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

		/**
		 * Inserting key = ingredient name, value = label into labelMap. 
		 * so ingredient names are linked to appropriate labels.
		 */
		public void populateLabelMap() {

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

		
		/**
		 * This method calls displayIngredients method when the "refresh" button is clicked.
		 * @param event, user (staff) clicks the refresh button on the inventory page.
		 * @throws IOException
		 * @throws JSONException
		 */
		@FXML
		public void handleRefreshButtonClick(ActionEvent event) throws IOException, JSONException{

			displayIngredients();

		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub

		}

		/**
		 * Gets the current quantities of ingredients from the database. 
		 * Calls the method labelColour to update labels.
		 * @param event, user (staff) clicks the refresh button on the inventory page.
		 * @throws IOException
		 * @throws JSONException
		 */
		public void displayIngredients() throws IOException, JSONException {
			
			//Getting checkBoxMap in field ready for getting keys of ingredients in database.
			populateLabelMap();

			//Get ingredients map from database.
			Connector connector = new Connector();
			HashMap<String, Ingredient> databaseInventory = (HashMap<String, Ingredient>) connector.getIngredients();

			//Getting iterator to go through labelMap 
			Iterator iterator= labelMap.entrySet().iterator();

			while (iterator.hasNext()) {
				Map.Entry pair = (Map.Entry)iterator.next();	
				String ingredient = (String) pair.getKey();
				
				Label labelQ = (Label) pair.getValue();


				//Getting right Ingredient object from database map.
				Ingredient databaseIngredient = databaseInventory.get(ingredient);

				//Getting current quantity value inside Ingredient Object from the database.
				int current = databaseIngredient.getQuantity();
				

				//For updating associated label on FXML file.
				String updateLabelQ = Integer.toString(current);
			

				//getting right label from map and updating the visible text (in this case a String number).
			
				Label labelAdjust = labelMap.get(ingredient);

				labelColour(current, updateLabelQ, labelAdjust);

			}
		}
		
		/**
		 * Method updates labels depending on quantity of each ingredient. 
		 * Displays labels black when stock is above lowStockThreshold.
		 * Displays labels red when stock is low and message Reorder
		 * Diplays labels red when stock is zero with message out of stock.
		 * @param current
		 * @param updateLabelQ
		 * @param labelAdjust
		 */
		public void labelColour(int current, String updateLabelQ, Label labelAdjust) {
			
			if(current > lowStockThreshold) {
				
				//Set colour to black (in case oscillating between low and high).
				labelAdjust.setStyle("-fx-text-fill: black");
				labelAdjust.setText(updateLabelQ);

			}else if(current > 0 && current <= lowStockThreshold) {

				//Set colour of text to red because getting low i.e. less than 20.
				labelAdjust.setStyle("-fx-text-fill: red");
				labelAdjust.setText(updateLabelQ + " - REORDER");

			} else if (current == 0) {
				
				//Add concatenated String saying out of stock.
				labelAdjust.setStyle("-fx-text-fill: red");
				labelAdjust.setText(updateLabelQ + " - OUT OF STOCK");

			}
		}
	





}
