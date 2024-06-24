package com.antoniotassone.warehouse;

import com.antoniotassone.controllers.LogicControllers;

public interface Engine{
    LogicControllers getController();

    void executeCommand(Commands command);
}