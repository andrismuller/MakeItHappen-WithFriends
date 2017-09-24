package hu.muller.andris.armando.makeithappen_withfriends.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Muller Andras on 9/24/2017.
 */

public class App {
    private String label;
    private Drawable icon;

    public App(){}

    public App(String label, Drawable icon){
        this.label = label;
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }


}
