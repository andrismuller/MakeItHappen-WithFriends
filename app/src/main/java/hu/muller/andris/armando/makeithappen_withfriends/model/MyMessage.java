package hu.muller.andris.armando.makeithappen_withfriends.model;

import com.orm.SugarRecord;

/**
 * Created by Muller Andras on 10/18/2017.
 */

public class MyMessage extends SugarRecord {
    private String message;
    private long datetime;
    private String fromUser;
    private String toUser;

    public MyMessage() {
    }

    public MyMessage(String message, long datetime, String fromUser, String toUser) {
        this.message = message;
        this.datetime = datetime;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
}
