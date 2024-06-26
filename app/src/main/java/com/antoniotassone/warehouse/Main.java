package com.antoniotassone.warehouse;

import com.antoniotassone.views.SceneManager;
import com.antoniotassone.views.SceneWarehouseManager;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application{
    @Override
    public void start(Stage stage){
        SceneManager manager = new SceneWarehouseManager(stage);
        String mainPath = "/com/antoniotassone/warehouse/fxml/main_view.fxml";
        String variationsPath = "/com/antoniotassone/warehouse/fxml/variations_view.fxml";
        try{
            manager.loadScene(mainPath,"mainView");
            manager.loadScene(variationsPath,"variationsView");
        }catch(IOException exception){
            exception.printStackTrace();
            System.exit(-1);
        }
        if(manager.showScene("mainView")){
            stage.setTitle("Warehouse manager");
            stage.setResizable(false);
            stage.show();
        }else{
            System.err.println("Failed to load main scene of the application.");
            System.exit(-1);
        }
    }

    public static void main(String[] args){
        //start the program with the graphical interface javafx
        Application.launch(args);

        //start the program with the user interface by console
        //ConsoleView view = new ConsoleView();
        //view.mainExecution();
    }
}