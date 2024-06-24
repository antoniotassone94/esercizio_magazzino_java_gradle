package com.antoniotassone.warehouse;

import com.antoniotassone.views.Views;

public class EngineImpl implements Engine{
    private final Views view;
    private final EngineCommands commands;

    public EngineImpl(Views view){
        this.view = view;
        commands = new EngineCommandsImpl(view);
    }

    @Override
    public void executeCommand(Commands command){
        switch(command){
            case CREATE_ITEM:
                commands.createItem();
                break;
            case DELETE_ITEM:
                commands.deleteItem();
                break;
            case INCREASE_QUANTITY:
                commands.increaseQuantity();
                break;
            case DECREASE_QUANTITY:
                commands.decreaseQuantity();
                break;
            case PRINT_FULL_WAREHOUSE:
                commands.printFullWarehouse();
                break;
            default:
                view.displayError("The command is unknown, try again.");
                break;
        }
    }
}