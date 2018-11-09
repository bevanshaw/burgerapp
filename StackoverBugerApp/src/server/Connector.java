package server;

import java.io.IOException
;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dataModel.Burger;
import dataModel.Ingredient;
import dataModel.Order;
public class Connector {
	
	OkHttpClient client1 = new OkHttpClient();
	public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
	Map<String, Order> orderMap = new HashMap<String, Order>();
	Map<String, Ingredient> ingredientMap = new HashMap<String, Ingredient>();

	public String get(String jsonUrl) throws IOException{
		Request request = new Request.Builder()
				.header("content-type", "application/json")
				.url(jsonUrl)
				.build();	
		try(Response response = client1.newCall(request).execute()){
			return response.body().string();
		}
	}
	
	public Map<String, Ingredient> getIngredients() throws IOException, JSONException{
		String ingredientsUrl = "https://stackover-burger.firebaseio.com/ingredient.json";
		
		String ingredientsJson = this.get(ingredientsUrl);
		
		JSONObject obj = new JSONObject(ingredientsJson);
		
		Iterator ingredientIter = obj.keys();
		
		while(ingredientIter.hasNext()) {
			String ingredientName = (String)ingredientIter.next();
			JSONObject ingredientObj = obj.getJSONObject(ingredientName);
			int quantity = ingredientObj.getInt("quantity");
			double price = ingredientObj.getDouble("price");
			String category = ingredientObj.getString("catagory");		
			Ingredient i = new Ingredient(ingredientName, quantity);	
			i.setPrice(price);
			i.setCategory(category);
			ingredientMap.put(ingredientName, i);
		}
		

		return ingredientMap;
	}
	


	String put(String jsonUrl, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder()
				.url(jsonUrl)
				.put(body)
				.build();
		Response response = client1.newCall(request).execute();
		return response.body().string();
	}
	
	String patch(String jsonUrl, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder()
				.url(jsonUrl)
				.patch(body)
				.build();
		Response response = client1.newCall(request).execute();
		return response.body().string();
	}

	public String run() throws IOException {

		Request request = new Request.Builder()
				.header("content-type", "application/json")
				.url("https://stackover-burger.firebaseio.com/customer.json")
				.build();	
		try(Response response = client1.newCall(request).execute()){
			return response.body().string();
		}


	}
//
//	public String getIngredients( ) throws IOException {
//		Request request = new Request.Builder()
//				.header("content-type", "application/json")
//				.url("https://stackover-burger.firebaseio.com/ingredient.json")
//				.build();	
//		try(Response response = client1.newCall(request).execute()){
//			return response.body().string();
//		}
//	}
	
	//get the order inv to update the order
	public void completeOrder(String orderInv)throws IOException {
		String jsonFile = "{ \"modifyTime\": \"7:00 10/10/2019\",\n" + 
				"\"status\": \"complete\"}";
		String url = "https://stackover-burger.firebaseio.com/order/"+orderInv+".json?";
		this.patch(url, jsonFile);
		
	}

	public Map<String, Order> getOrders() throws IOException, JSONException {

		//to do specific status use firebase
		String url = "https://stackover-burger.firebaseio.com/order.json?orderBy=\"status\"&equalTo=\"complete\"";
		String json = this.get(url);
		System.out.println(json);
		//create orders object
		JSONObject orders =new JSONObject(json);
		Iterator orderIter = orders.keys();
	
		while(orderIter.hasNext()) {
			String invoiceNumber = (String) orderIter.next();//"order1"
			//System.out.println(orders.getJSONObject(invoiceNumber));// test to get order key i.e. order1
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
					System.out.println(ingredientName+"  "+quantity);
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
	
//	public void updateOrders()
	

	public String getTextData () throws IOException {

		Request request = new Request.Builder()
				.header("content-type", "application/json")
				.url("https://stackover-burger.firebaseio.com/testData.json")
				.build();	
		try(Response response = client1.newCall(request).execute()){
			return response.body().string();
		}
	}

	
	


	public static void main(String[] args) throws IOException, JSONException {

		String jsonFile = "{\"avocado\":{\"category \":\"vege\",\"price\":2,\"quantity\":100},\"beef\":{\"category \":\"meat\",\"price\":1,\"quantity\":100},\"brioche\":{\"category \":\"bread\",\"price\":1,\"quantity\":100},\"camembert\":{\"category \":\"cheese\",\"price\":1,\"quantity\":100},\"cheddar\":{\"category \":\"cheese\",\"price\":1,\"quantity\":100},\"chicken\":{\"category \":\"meat\",\"price\":1,\"quantity\":100},\"falafel\":{\"category \":\"meat\",\"price\":1,\"quantity\":100},\"fish\":{\"category \":\"meat\",\"price\":1,\"quantity\":100},\"halloumi\":{\"category \":\"cheese\",\"price\":1,\"quantity\":100},\"ketchup\":{\"category \":\"sauce\",\"price\":1,\"quantity\":100},\"lettuce\":{\"category \":\"vege\",\"price\":1,\"quantity\":100},\"mayo\":{\"category \":\"sauce\",\"price\":1,\"quantity\":100},\"mustard\":{\"category \":\"sauce\",\"price\":1,\"quantity\":100},\"pickle\":{\"category \":\"vege\",\"price\":1,\"quantity\":100},\"sesame seed bun\":{\"category \":\"bread\",\"price\":1,\"quantity\":100},\"tomato\":{\"category \":\"vege\",\"price\":1,\"quantity\":100}}\n" + 
				"";

		Connector connector = new Connector();
		
		connector.getIngredients();
		//connector.put("https://stackover-burger.firebaseio.com/ingredient.json", jsonFile);
		
		//connector.completeOrder("order1");
//		System.out.println(connector.getIngredients());
		System.out.println(connector.getOrders());
//		System.out.println(connector.getTextData());
//
//		System.out.println(connector.run());
	}

}
