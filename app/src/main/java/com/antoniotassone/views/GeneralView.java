package com.antoniotassone.views;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public abstract class GeneralView{
    private SceneManager manager;

    public GeneralView(){
        manager = null;
    }

    public SceneManager getManager(){
        return manager;
    }

    public void setManager(SceneManager manager){
        this.manager = manager;
    }

    public void printEventLog(ActionEvent actionEvent, Button button){
        String className = actionEvent.getSource().getClass().getName();
        String[] details = className.split("[.]");
        String specific = details[details.length - 1];
        String console = specific + " with text \"" + button.getText() + "\" has launched an event.";
        System.out.println(console);
    }

    public abstract void initializeComponents();
}