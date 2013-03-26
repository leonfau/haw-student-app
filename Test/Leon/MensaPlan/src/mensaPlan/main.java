package mensaPlan;

import java.io.IOException;

public class main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		MensaPlan a = MensaPlanImpl.MensaPlan();
		a.update();
		System.out.println(a.getWeekPlan());

	}

}
