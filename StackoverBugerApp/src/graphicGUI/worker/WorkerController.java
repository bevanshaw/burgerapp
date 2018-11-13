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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	public void getOrder() throws IOException, JSONException {
		
		
	}
	@FXML
	public void handleAction() {
		
	}
	/*
	@FXML
	public void getOrders(ActionEvent event) throws IOException, JSONException {
		pendingList.setItems(items);
		Connector c = new Connector();
		orderMap = c.getOrders();

		for(String key : orderMap.keySet() ) {
			System.out.println(key);
			System.out.println(orderMap.get(key));
			String orderInvoice = key;
			items.add(key);
		
			Order order = orderMap.get(key); // order detail
			List<Burger> burgers = order.getBurgers();
			List<String> burgerList = new ArrayList<String>();

			int burgerNum = burgers.size();
			System.out.println("burgerNum "+burgerNum);
			for(int i = 0; i<burgerNum; i++) {
				
				Burger burger = burgers.get(i); // each burger
				List<Ingredient> ingredients = burger.getIngredients();
				String detail = "";
				for(int j=0; j<ingredients.size(); j++) {		
					Ingredient ing = ingredients.get(j);
					System.out.println("ing "+ ing.getName());	
					detail = detail + " " +ing.getName()+" "+ing.getQuantity();
				}
				System.out.println("detail "+ detail);
				burgerList.add(detail);
			}	
			
			  pendingList.setCellFactory(lv -> {
		            ListCell<String> cell = new ListCell<String>() {
		                @Override
		                protected void updateItem(String item, boolean empty) {
		                    super.updateItem(item, empty);
		                    setText(item);
		                }
		            };
		            cell.setOnMouseClicked(e -> {
		                if (!cell.isEmpty()) {
		                	orderDetail.clear();
		                    System.out.println("You clicked on " + cell.getItem());
		                    orderInvTitle.setText(cell.getItem());
		                   for(String detail: burgerList) {
		                	   System.out.println(detail);
		                	  orderDetail.appendText(detail);
		                   }
		                    
		                    e.consume();
		                }
		            });
		            return cell;
		        });

		        pendingList.setOnMouseClicked(e -> {
		            System.out.println("You clicked on an empty cell");
		        });
		}	
		
	}
	
	*/
	@FXML
	public void getOrders(ActionEvent event) throws IOException, JSONException {
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
	Order order = orderMap.get(inv); // order detail
	List<Burger> burgers = order.getBurgers();
	List<String> burgerList = new ArrayList<String>();

	int burgerNum = burgers.size();
	System.out.println("burgerNum "+burgerNum);
	for(int i = 0; i<burgerNum; i++) {
		
		Burger burger = burgers.get(i); // each burger
		List<Ingredient> ingredients = burger.getIngredients();
		String detail = "";
		for(int j=0; j<ingredients.size(); j++) {		
			Ingredient ing = ingredients.get(j);
			System.out.println("ing "+ ing.getName());	
			detail = detail + " " +ing.getName()+" "+ing.getQuantity();
		}
		System.out.println("detail "+ detail);
		burgerList.add(detail);
	}	
	
	for(String detail: burgerList) {
 	   System.out.println(detail);
 	  orderDetail.appendText("burger : "+ detail);
 	  orderDetail.appendText("\n");
    }
	
}

}
