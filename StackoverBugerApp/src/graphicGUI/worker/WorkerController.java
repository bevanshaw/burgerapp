package graphicGUI.worker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import dataModel.Burger;
import dataModel.Ingredient;
import dataModel.Order;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.firebae.rest.Connector;
import javafx.event.EventHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class WorkerController {

	@FXML
	private Label label;
	private Map<String, Order> orderMap = new HashMap<String, Order>();
	@FXML
	ListView<String> pendingList;
	ObservableList<String> items = FXCollections.observableArrayList();
	@FXML
	TextField orderInvTitle;
	@FXML
	TextArea orderDetail;
	
	@FXML
	private Button viewInventoryBtn;

	String currentOrderInv;

	@FXML
	public void getOrders(ActionEvent event) throws IOException, JSONException {

		getOrder();
	}

	public void getOrder() throws IOException, JSONException {
		pendingList.getItems().clear();
		items = FXCollections.observableArrayList();
		pendingList.setItems(items);
		Connector c = new Connector();
		orderMap = c.getOrders();

		for(String key : orderMap.keySet() ) {
			items.add(key);
		}

		pendingList.setCellFactory(lv -> {
			ListCell<String> cell = new ListCell<String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					setText(item);
				}
			};

			cell.setOnMouseClicked(e -> getDetail(cell.getItem()));
			return cell;
		});
	}

	public void getDetail(String inv) {

		orderDetail.clear();
		orderInvTitle.setText(inv);
		currentOrderInv = inv;
		Order order = orderMap.get(inv); // order detail
		List<Burger> burgers = order.getBurgers();
		List<String> burgerList = new ArrayList<String>();

		int burgerNum = burgers.size();
		//System.out.println("burgerNum "+burgerNum);
		for(int i = 0; i<burgerNum; i++) {

			Burger burger = burgers.get(i); // each burger
			List<Ingredient> ingredients = burger.getIngredients();
			String detail = "";
			for(int j=0; j<ingredients.size(); j++) {		
				Ingredient ing = ingredients.get(j);
				//System.out.println("ing "+ ing.getName());	
				detail = detail + " " +ing.getName()+" "+ing.getQuantity();
			}
			//System.out.println("detail "+ detail);
			burgerList.add(detail);
		}	

		for(String detail: burgerList) {
			//System.out.println(detail);
			orderDetail.appendText("burger : "+ detail);
			orderDetail.appendText("\n");
		}
	}

	public void finishOrder(String inv) throws IOException {
		Connector c = new Connector();
		c.completeOrder(inv);
	}

	@FXML
	public void completeOrder(ActionEvent event) throws IOException, JSONException {
		finishOrder(currentOrderInv);
		getOrder();
		orderDetail.clear();
		orderInvTitle.clear();


	}

	@FXML
	public void handleLogoutButtonClick(ActionEvent event) {
		Stage stage = (Stage)pendingList.getScene().getWindow();

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
	 * To allow Manager to navigate from Worker Kitchen to the Inventory.
	 * @param event, this is a click on the view Inventory button in kitchen.
	 * @throws IOException
	 */
	@FXML
	public void handleViewInventoryButtonClick(ActionEvent event) throws IOException {
		
		Stage stage = (Stage)viewInventoryBtn.getScene().getWindow();

		Parent root = FXMLLoader.load(getClass().getResource("/graphicGUI/manager/managerScene.fxml"));

		Scene scene = new Scene(root);

		stage.setScene(scene);

		stage.show();


	}

}
