package de.minimum.hawapp.server.mensa;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MensaManagerImpl implements MensaManager {
	private static MensaManager manager = new MensaManagerImpl();
	private MensaPlan plan;
	
	@Override
	public MensaManager getInstance() {
		return manager;	
	}
	
	private MensaManagerImpl(){
		plan = MensaPlanImpl.MensaPlan();
	}

	@Override
	public void update() throws IOException {
		plan.update();
	}

	@Override
	public List<Meal> getDayPlan(String day) {
		return plan.getDayPlan(day);
	}

	@Override
	public Map<String, List<Meal>> getWeekPlan() {
		return plan.getWeekPlan();
	}

	@Override
	public Date getUpdateTime() {
		return plan.getUpdateTime();
	}

}
