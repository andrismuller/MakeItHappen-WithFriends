package hu.muller.andris.armando.makeithappen_withfriends.model;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.ArrayList;

/**
 * Created by Muller Andras on 9/23/2017.
 */

public class Controlling extends SugarRecord {
    private String name;
    private String urls;
    private String apps;
    private boolean internet;
    private double durationValue;
    private String durationUnit;
    private long startTime;
    private boolean activated;
    @Ignore
    private long myId;

    public Controlling(){}
    public Controlling(String name, ArrayList<String> urlsList, ArrayList<String> appsList, boolean internet, double durationValue, String durationUnit, long startTime){
        this.name = name;
        this.setUrls(urlsList);
        this.setApps(appsList);
        this.internet = internet;
        this.durationValue = durationValue;
        this.durationUnit = durationUnit;
        this.startTime = startTime;
        this.activated = false;
    }

    public long getMyId() {
        return myId;
    }

    public void setMyId(long myId) {
        this.myId = myId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(ArrayList<String> urlsList) {
        String result = "";
        if (urlsList != null){
            for (int i = 0; i < urlsList.size(); ++i){
                result = result.concat(urlsList.get(i));
                result = result.concat(";");
            }
        }
        this.urls = result;
    }

    public String getApps() {
        return apps;
    }

    public void setApps(ArrayList<String> appsList) {
        String result = "";
        if (appsList != null){
            for (int i = 0; i < appsList.size(); ++i){
                result = result.concat(appsList.get(i));
                result = result.concat(";");
            }
        }
        this.apps = result;
    }

    public boolean isInternet() {
        return internet;
    }

    public void setInternet(boolean internet) {
        this.internet = internet;
    }

    public double getDurationValue() {
        return durationValue;
    }

    public void setDurationValue(int durationValue) {
        this.durationValue = durationValue;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
