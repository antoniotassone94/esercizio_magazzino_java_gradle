package com.antoniotassone.views;

import com.antoniotassone.models.Items;
import com.antoniotassone.warehouse.Engine;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class ActionsTableCell implements Callback<TableColumn<WarehouseTableRows,Void>,TableCell<WarehouseTableRows,Void>>{
    private final Engine engine;
    private final Views view;

    public ActionsTableCell(Engine engine,Views view){
        this.engine = engine;
        this.view = view;
    }

    @Override
    public TableCell<WarehouseTableRows,Void> call(TableColumn<WarehouseTableRows,Void> column){
        return new InternalCell(engine,view);
    }

    private static class InternalCell extends TableCell<WarehouseTableRows,Void>{
        private final HBox container;

        public InternalCell(Engine engine,Views view){
            Button cmdDelete = new Button("Delete");
            cmdDelete.setOnAction(event -> {
                WarehouseTableRows row = getTableView().getItems().get(getIndex());
                Items item = new Items(row.getItem());
                if(engine.deleteItem(item)){
                    view.displayInfo("The command has been executed successfully.");
                }else{
                    view.displayError("The command could not be executed successfully.");
                }
            });
            Button cmdVariations = new Button("Variations");
            cmdVariations.setOnAction(event -> {
                WarehouseTableRows row = getTableView().getItems().get(getIndex());
                Items item = new Items(row.getItem());


                //implementare il cambio di scena verso variationsView e impostare engine, view, item



            });
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
}