package com.antoniotassone.warehouse;

import com.antoniotassone.controllers.LogicControllers;
import com.antoniotassone.exceptions.ArchiveAlreadyLoadedException;
import com.antoniotassone.exceptions.ArchiveNotLoadedException;
import com.antoniotassone.views.Views;

public class EngineImpl implements Engine{
    private final Views view;
    private final EngineCommands commands;

    public EngineImpl(Views view) throws ArchiveAlreadyLoadedException,ArchiveNotLoadedException{
        this.view = view;
        commands = new EngineCommandsImpl(view);
    }

    @Override
    public LogicControllers getController(){
        return commands.getController();
    }

    @Override
    public void executeCommand(Commands command){
        switch(command){
            case CREATE_ITEM:
                commands.createItem();
                break;
            case INCREASE_QUANTITY:
                commands.increaseQuantity();
                break;
            case DECREASE_QUANTITY:
                commands.decreaseQuantity();
                break;
            default:
                view.displayError("The command is unknown, try again.");
                break;
        }
    }
}