package com.antoniotassone.views;

import com.antoniotassone.controllers.LogicControllers;
import com.antoniotassone.exceptions.ArchiveNotLoadedException;
import com.antoniotassone.exceptions.ItemNotValidException;
import com.antoniotassone.models.Items;
import com.antoniotassone.models.Variations;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import java.util.List;

public class ActionsTableCell implements Callback<TableColumn<WarehouseTableRows,Void>,TableCell<WarehouseTableRows,Void>>{
    private final Views view;
    private final LogicControllers controller;

    public ActionsTableCell(Views view,LogicControllers controller){
        this.view = view;
        this.controller = controller;
    }

    @Override
    public TableCell<WarehouseTableRows,Void> call(TableColumn<WarehouseTableRows,Void> column){
        return new InternalCell(view,controller);
    }

    private static class InternalCell extends TableCell<WarehouseTableRows,Void>{
        private final HBox container;

        public InternalCell(Views view,LogicControllers controller){
            Button cmdDelete = new Button("Delete");
            cmdDelete.setOnAction(event -> {
                WarehouseTableRows row = getTableView().getItems().get(getIndex());
                Items item = new Items(row.getItem());
                try{
                    if(controller != null){
                        if(controller.removeItemFromWarehouse(item)){
                            view.displayInfo("Item deleted successfully.");
                        }else{
                            view.displayError("Item could not be deleted.");
                        }
                    }else{
                        view.displayError("The controller is null, so the command can't be executed.");
                    }
                }catch(ArchiveNotLoadedException | ItemNotValidException exception){
                    exception.printStackTrace();
                }
            });
            Button cmdVariations = new Button("Variations");
            cmdVariations.setOnAction(event -> {
                WarehouseTableRows row = getTableView().getItems().get(getIndex());
                Items item = new Items(row.getItem());
                try{
                    if(controller != null){
                        List<Variations> variationsList = controller.getFullWarehouse().getVariations(item);


                        System.out.println(variationsList);


                    }else{
                        view.displayError("The controller is null, so the command can't be executed.");
                    }
                }catch(ArchiveNotLoadedException | ItemNotValidException exception){
                    exception.printStackTrace();
                }
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