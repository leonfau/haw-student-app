package mensa;

import static org.junit.Assert.*;

import org.junit.Test;


import de.minimum.hawapp.server.mensa.Meal;
import de.minimum.hawapp.server.mensa.MealImpl;

public class mealTest {
	
	@Test
	public void testEquals(){
		String descriptionA = "Blabla tolles Mensa essen";
		String descriptionB = "Essen von gestern";
		
		double studentPriceA = 2.99;
		double studentPriceB = 99.99;
		
		double otherPriceA = 5.99;
		double otherPriceB = 3.21;
		
		Meal mealA = MealImpl.Meal(descriptionA, studentPriceA, otherPriceA);
		Meal mealB = MealImpl.Meal(descriptionA, studentPriceA, otherPriceA);
		Meal mealC = MealImpl.Meal(descriptionB, studentPriceB, otherPriceB);
		Meal mealD = MealImpl.Meal(descriptionB, studentPriceB, otherPriceA);
		assertEquals(mealA, mealB);
		assertFalse(mealA.equals(mealC));
		assertFalse(mealC.equals(mealD));
		
	}

}
