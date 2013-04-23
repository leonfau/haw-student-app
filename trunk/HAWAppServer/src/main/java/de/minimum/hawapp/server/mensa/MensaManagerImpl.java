package de.minimum.hawapp.server.mensa;

import java.io.IOException;
import java.util.ArrayList;
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
        
        //update jede Stunde
        startScheduledUpdate(new Date(System.currentTimeMillis()), 3600000);
       // startScheduledUpdate(firstTime, period)
    }

    @Override
    public void update() throws IOException {
        this.plan.update();
    }

    @Override
    public DayPlan getDayPlan(Day day) {
    	DayPlan dayPlan;
    	dayPlan = new DayPlanImpl(day, plan.getWeekPlan().get(day.toString()));
        return dayPlan;
    }


    @Override
    public List<DayPlan> getWeekPlan() {
    	List<DayPlan> dayPlanList = new ArrayList<DayPlan>();
    	DayPlan dayPlan;
        Map<String, List<Meal>> mealMap =  this.plan.getWeekPlan();
        
        for(Day d : Day.values()){
        	dayPlan = new DayPlanImpl(d, mealMap.get(d.toString()));
        	dayPlanList.add(dayPlan);
        }
        return dayPlanList;
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
