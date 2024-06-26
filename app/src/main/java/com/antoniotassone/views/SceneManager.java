package com.antoniotassone.views;

import java.io.IOException;
import java.util.Optional;

public interface SceneManager{
    void loadScene(String filenameFXML,String sceneName) throws IOException;

    boolean showScene(String sceneName);

    Optional<GeneralView> getFXController(String sceneName);
}