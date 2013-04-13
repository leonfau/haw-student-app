package de.minimum.hawapp.server.mensa;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class MensaManagerImpl implements MensaManager {
    private MensaPlan plan;
    private Timer updateTimer= new Timer(true);
    private UpdateTimerTask updateTask;

    public MensaManagerImpl() {
        this.plan = MensaPlanImpl.MensaPlan();
        updateTask = new UpdateTimerTask(plan);
        try {
            update();
        }
        catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void update() throws IOException {
        this.plan.update();
    }

    @Override
    public List<Meal> getDayPlan(String day) {
        return this.plan.getDayPlan(day);
    }

    @Override
    public Map<String, List<Meal>> getWeekPlan() {
        return this.plan.getWeekPlan();
    }

    @Override
    public Date getUpdateTime() {
        return this.plan.getUpdateTime();
    }
    
    @Override
    public void startScheduledUpdate(Date firstTime, long period){
    	updateTimer.schedule(updateTask, firstTime, period);
    	
    }

    
    @Override
    public void stopScheduledUpdate(){
    	updateTimer.cancel();
    	
    }
}
