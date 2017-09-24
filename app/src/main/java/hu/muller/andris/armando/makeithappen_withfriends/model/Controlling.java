package hu.muller.andris.armando.makeithappen_withfriends.model;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * Created by Muller Andras on 9/23/2017.
 */

public class Controlling extends SugarRecord {
    private String name;
    private String[] urls;
    private String[] apps;
    private boolean internet;
    private double durationValue;
    private String durationUnit;
    private long startTime;
    private boolean activated;
    @Ignore
    private long myId;

    public Controlling(){}
    public Controlling(String name, String[] urls, String[] apps, boolean internet, double durationValue, String durationUnit, long startTime){
        this.name = name;
        this.urls = urls;
        this.apps = apps;
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

    public String[] getUrls() {
        return urls;
    }

    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    public String[] getApps() {
        return apps;
    }

    public void setApps(String[] apps) {
        this.apps = apps;
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

    public String websitesToString(){
        String result = "";
        for (int i = 0; i < urls.length; ++i){
            result.concat(urls[i]);
            result.concat("\n");
        }
        return result;
    }

    public String appstoString(){
        String result = "";
        for (int i = 0; i < apps.length; ++i){
            result.concat(apps[i]);
            result.concat("\n");
        }
        return result;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
