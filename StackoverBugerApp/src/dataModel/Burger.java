package dataModel;

import java.util.ArrayList;
import java.util.List;

public class Burger {
	
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();

//	public Burger(ArrayList<Ingredient> ingredients) {
//		this.ingredients = ingredients;
//			
//	}
	public Burger() {
		
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	public void addIngredient(Ingredient i) {
		this.ingredients.add(i);
	}

}
