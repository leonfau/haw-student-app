package de.minimum.hawapp.server.mensa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;

public class MensaManagerImpl implements MensaManager {
    private MensaPlan plan;
    private Timer updateTimer = new Timer(true);
    private UpdateTimerTask updateTask;

    public MensaManagerImpl() {
        this.plan = MensaPlanImpl.MensaPlan();
        this.updateTask = new UpdateTimerTask(this.plan);

        // update jede Stunde
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
        dayPlan = new DayPlanImpl(day, this.plan.getWeekPlan().get(day.toString()));
        return dayPlan;
    }

    @Override
    public List<DayPlan> getWeekPlan() {
        List<DayPlan> dayPlanList = new ArrayList<DayPlan>();
        DayPlan dayPlan;
        Map<String, List<Meal>> mealMap = this.plan.getWeekPlan();

        for(Day d : Day.values()) {
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
    public void startScheduledUpdate(Date firstTime, long period) {
        this.updateTimer.schedule(this.updateTask, firstTime, period);

    }

    @Override
    public void stopScheduledUpdate() {
        this.updateTimer.cancel();

    }


    @Override
    public int rate(UUID id, int stars) {
        this.plan.getMealByID(id).getRating().rate(stars);
        return getMealRaiting(id);
    }

    @Override
    public int getMealRaiting(UUID id) {
        return this.plan.getMealByID(id).getRating().getPosRatingInPercent();
    }
}
