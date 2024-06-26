package com.antoniotassone.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SceneWarehouseManager implements SceneManager{
    private final Stage window;
    private final Map<String,Scene> scenes;
    private final Map<String,GeneralView> controllers;

    public SceneWarehouseManager(Stage window){
        this.window = window;
        scenes = new HashMap<>();
        controllers = new HashMap<>();
    }

    @Override
    public void loadScene(String filenameFXML,String sceneName) throws IOException{
        if(filenameFXML == null || sceneName == null){
            throw new IOException("The input parameters of the method are null.");
        }
        if(filenameFXML.isEmpty() || sceneName.isEmpty()){
            throw new IOException("The input parameters of the method are empty.");
        }
        if(scenes.containsKey(sceneName)){
            throw new IOException("The scene that you are specifying are already loaded.");
        }
        URL url = getClass().getResource(filenameFXML);
        if(url == null){
            throw new IOException("The file fxml wasn't found correctly.");
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(url);
        Parent root = loader.load();
        GeneralView controller = loader.getController();
        controller.setManager(this);
        Scene scene = new Scene(root);
        controllers.put(sceneName,controller);
        scenes.put(sceneName,scene);
    }

    @Override
    public boolean showScene(String sceneName){
        if(sceneName == null){
            return false;
        }
        if(sceneName.isEmpty()){
            return false;
        }
        if(!scenes.containsKey(sceneName)){
            return false;
        }
        Scene scene = scenes.get(sceneName);
        window.setScene(scene);
        return true;
    }

    @Override
    public Optional<GeneralView> getFXController(String sceneName){
        if(sceneName == null){
            return Optional.empty();
        }
        if(sceneName.isEmpty()){
            return Optional.empty();
        }
        if(!controllers.containsKey(sceneName)){
            return Optional.empty();
        }
        return Optional.of(controllers.get(sceneName));
    }
}