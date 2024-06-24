package com.antoniotassone.views;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public abstract class GeneralView{
    public void printEventLog(ActionEvent actionEvent,Button button){
        String className = actionEvent.getSource().getClass().getName();
        String[] details = className.split("[.]");
        String specific = details[details.length - 1];
        String console = specific + " with text \"" + button.getText() + "\" has launched an event.";
        System.out.println(console);
    }
}