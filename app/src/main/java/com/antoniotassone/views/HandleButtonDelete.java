package com.antoniotassone.views;

import com.antoniotassone.models.Items;
import com.antoniotassone.warehouse.Engine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class HandleButtonDelete implements EventHandler<ActionEvent>{
    private final ActionsTableCell cell;
    private final Engine engine;
    private final Views view;

    public HandleButtonDelete(ActionsTableCell cell,Engine engine,Views view){
        this.cell = cell;
        this.engine = engine;
        this.view = view;
    }

    @Override
    public void handle(ActionEvent event){
        WarehouseTableRows row = cell.getTableView().getItems().get(cell.getIndex());
        Items item = new Items(row.getItem());
        if(engine.deleteItem(item)){
            view.displayInfo("The command has been executed successfully.");
        }else{
            view.displayError("The command could not be executed successfully.");
        }

    }
}