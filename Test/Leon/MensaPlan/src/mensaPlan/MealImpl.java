package mensaPlan;

public class MealImpl implements Meal {

	private String description;
	private double studentPrice;
	private double othersPrice;
	private Rating rating;
	private MealImpl(String description, double studentPrice, double othersPrice){
		this.description = description;
		this.studentPrice = studentPrice;
		this.othersPrice = othersPrice;
		rating = RatingImpl.Rating();
	}
	
	public static MealImpl Meal(String description, double studentPrice, double othersPrice){
		return new MealImpl(description, studentPrice, othersPrice);
	}
	

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public double getStudentPrice() {
		return studentPrice;
	}

	@Override
	public double getOthersPrice() {
		return othersPrice;
	}
	
	public String toString(){
		return description + " " + studentPrice + " E / " + othersPrice + " E";
	}
}
