package server.firebae.rest;

import java.io.IOException
;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import dataModel.Burger;
import dataModel.Ingredient;
import dataModel.Order;
public class Connector {
	
	Map<String, Order> orderMap = new HashMap<String, Order>();
	Map<String, Ingredient> ingredientMap = new HashMap<String, Ingredient>();

	public Map<String, Ingredient> getIngredients() throws IOException, JSONException{
		String ingredientsUrl = "https://stackover-burger.firebaseio.com/ingredient.json";
		
		String ingredientsJson = OkImp.get(ingredientsUrl);
		System.out.println(ingredientsJson);
		JSONObject obj = new JSONObject(ingredientsJson);
		
		Iterator ingredientIter = obj.keys();
		
		while(ingredientIter.hasNext()) {
			String ingredientName = (String)ingredientIter.next();
			JSONObject ingredientObj = obj.getJSONObject(ingredientName);
			
			int quantity = ingredientObj.getInt("quantity");
			double price = ingredientObj.getDouble("price");
			String category = ingredientObj.getString("category");		
			Ingredient i = new Ingredient(ingredientName, quantity);	
			i.setPrice(price);
			i.setCategory(category);
			ingredientMap.put(ingredientName, i);
		}
		return ingredientMap;
	}
	// get the ingredientName and quantity to update the ingredient. 
	public void updateIngredient(String ingredientName, int number) throws IOException {
		String url = "https://stackover-burger.firebaseio.com/ingredient/" + ingredientName+ ".json";
		System.out.println(url);
		String jsonFile = "{\"quantity\":\""+number+"\"}";
		String test = OkImp.patch(url, jsonFile);
		System.out.println("test "+test);
	}
	

	//get the order invoiceNum to update the order
	public void completeOrder(String orderInv)throws IOException {
		
		String modifyTime = TimeToISO.format(new Date());
		String jsonFile = "{ \"modifyTime\": \""+modifyTime+"\",\n" + 
				"\"status\": \"complete\"}";
		String url = "https://stackover-burger.firebaseio.com/order/"+orderInv+".json?";
		OkImp.patch(url, jsonFile);
		
	}

	
	public Map<String, Order> getOrders() throws IOException, JSONException {

		String url = "https://stackover-burger.firebaseio.com/order.json?orderBy=\"status\"&equalTo=\"complete\"";
		String json = OkImp.get(url);
		//create orders object
		JSONObject orders =new JSONObject(json);
		Iterator orderIter = orders.keys();
	
		while(orderIter.hasNext()) {
			String invoiceNumber = (String) orderIter.next();//"order1"
			JSONObject orderObject= orders.getJSONObject(invoiceNumber); // get the order1 object			
			String createTime = orderObject.getString("createTime");
			String modifyTime = orderObject.getString("modifyTime");
			String customer = orderObject.getString("customer");
			String status = orderObject.getString("status");
			
			JSONObject detailsObject = orderObject.getJSONObject("details");// details has burger object
			
			
			List<Burger> detailList = new ArrayList<Burger>();
			
			Iterator detailIter = detailsObject.keys();
			
			while(detailIter.hasNext()) {
				String burgerKey = (String) detailIter.next();	// key	= burger		
				JSONObject burgerObject= detailsObject.getJSONObject(burgerKey);// value = burgerObject
				
				Iterator burgerIter = burgerObject.keys();
				Burger burger = new Burger();
				while (burgerIter.hasNext()) {
					String ingredientName = (String) burgerIter.next(); // key = ingredient
					int quantity = burgerObject.getInt(ingredientName); // value = quantity
					Ingredient ingredient = new Ingredient(ingredientName, quantity);
					burger.addIngredient(ingredient);
				}		
				detailList.add(burger);
			}

			Order order = new Order(invoiceNumber, customer, status,detailList);
			
			orderMap.put(invoiceNumber, order);
		}
			
		return orderMap;
	}
		

	public static void main(String[] args) throws IOException, JSONException {

		Connector connector = new Connector();
		
		connector.getIngredients();
	   
		connector.updateIngredient("beef", 1000);
		
		connector.completeOrder("order2");

}

}
