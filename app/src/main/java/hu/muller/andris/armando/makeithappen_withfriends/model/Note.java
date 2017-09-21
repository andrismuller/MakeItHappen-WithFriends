package hu.muller.andris.armando.makeithappen_withfriends.model;

/**
 * Created by Muller Andras on 9/20/2017.
 */

public class Note {
    private String note;
    private long timeCreated;
    private long id;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
