package mensaPlan;

public class RatingImpl implements Rating{
	int positiv;
	int negativ;
	
	private RatingImpl(){
		positiv = 0;
		negativ = 0;
	}
	
	public static RatingImpl Rating(){
		return new RatingImpl();
	}
	
	public int getPosRatingInPercent(){
		if (positiv + negativ == 0) 
			return 0; 
		return (100/(positiv+negativ))*15; 
	}
	
	public void ratePoitiv(){
		positiv++;
	}
	
	public void rateNegativ(){
		negativ++;
	}

}
