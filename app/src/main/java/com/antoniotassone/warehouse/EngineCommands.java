package com.antoniotassone.warehouse;

import com.antoniotassone.controllers.LogicControllers;

public interface EngineCommands{
    LogicControllers getController();

    void createItem();

    void increaseQuantity();

    void decreaseQuantity();
}