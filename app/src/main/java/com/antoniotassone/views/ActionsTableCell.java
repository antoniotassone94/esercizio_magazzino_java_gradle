package com.antoniotassone.views;

import com.antoniotassone.warehouse.Engine;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

public class ActionsTableCell extends TableCell<WarehouseTableRows,Void>{
    private final HBox container;

    public ActionsTableCell(Engine engine,Views view,GeneralView fxController){
        Button cmdDelete = new Button("Delete");
        cmdDelete.setOnAction(new HandleButtonDelete(this,engine,view));
        Button cmdVariations = new Button("Variations");
        cmdVariations.setOnAction(new HandleButtonVariations(this,fxController,engine,view));
        container = new HBox(cmdDelete,cmdVariations);
    }

    @Override
    public void updateItem(Void item,boolean empty){
        super.updateItem(item,empty);
        if(empty){
            setGraphic(null);
        }else{
            setGraphic(container);
        }
    }
}