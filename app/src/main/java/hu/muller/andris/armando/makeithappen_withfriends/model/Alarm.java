package hu.muller.andris.armando.makeithappen_withfriends.model;

/**
 * Created by Muller Andras on 9/18/2017.
 */

public class Alarm {
    private String note;
    private long time;
    private long id;
    private String repeatType;

    public Alarm(){}

    public Alarm(String note, long time, long id, String repeatType){
        this.note = note;
        this.time = time;
        this.id = id;
        this.repeatType = repeatType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }
}
