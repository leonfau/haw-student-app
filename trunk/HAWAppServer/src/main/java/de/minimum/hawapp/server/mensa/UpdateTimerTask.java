package de.minimum.hawapp.server.mensa;

import java.io.IOException;
import java.util.TimerTask;

public class UpdateTimerTask extends TimerTask {
	
		private MensaPlan plan;
	
		public UpdateTimerTask(MensaPlan plan){
			this.plan = plan;
		}
		int i = 0;
		public void run() {
			try {
				plan.update();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

}
