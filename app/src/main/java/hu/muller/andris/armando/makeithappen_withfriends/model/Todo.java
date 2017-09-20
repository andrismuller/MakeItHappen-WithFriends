package hu.muller.andris.armando.makeithappen_withfriends.model;

/**
 * Created by Muller Andras on 9/16/2017.
 */

public class Todo {
    private long Id;
    private String title;
    private String priority;
    private long createdDate;
    private long alarm_id;
    private String description;
    private String requiredInformation;
    private boolean isDone;
    private String todoCategory;

    public Todo(){
    }

    public Todo(String title, String priority, long createdDate) {
        this.title = title;
        this.priority = priority;
        this.createdDate = createdDate;
        isDone = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public long getAlarm_id() {
        return alarm_id;
    }

    public void setAlarm_id(long alarm_id) {
        this.alarm_id = alarm_id;
    }


    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }


    public String getRequiredInformation() {
        return requiredInformation;
    }

    public void setRequiredInformation(String requiredInformation) {
        this.requiredInformation = requiredInformation;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }


    public String getTodoCategory() {
        return todoCategory;
    }

    public void setTodoCategory(String todoCategory) {
        this.todoCategory = todoCategory;
    }


    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

}
