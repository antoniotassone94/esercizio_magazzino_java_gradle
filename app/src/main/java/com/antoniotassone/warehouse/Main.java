package com.antoniotassone.warehouse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class Main extends Application{
    @Override
    public void start(Stage stage){
        URL url = getClass().getResource("/com/antoniotassone/warehouse/fxml/main_view.fxml");
        if(url == null){
            System.err.println("The file fxml wasn't loaded correctly.");
            System.exit(-1);
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        Parent root = null;
        try{
            root = loader.load();
        }catch(IOException exception){
            exception.printStackTrace();
            System.exit(-1);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Warehouse manager");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args){
        //start the program with the graphical interface javafx
        Application.launch(args);

        //start the program with the user interface by console
        //ConsoleView view = new ConsoleView();
        //view.mainExecution();
    }
}