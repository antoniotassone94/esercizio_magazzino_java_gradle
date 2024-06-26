package com.antoniotassone.views;

import com.antoniotassone.models.Items;
import com.antoniotassone.warehouse.Engine;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import java.util.Optional;

public class ActionsTableCell implements Callback<TableColumn<WarehouseTableRows,Void>,TableCell<WarehouseTableRows,Void>>{
    private final Engine engine;
    private final Views view;
    private final GeneralView fxController;

    public ActionsTableCell(Engine engine,Views view,GeneralView fxController){
        this.engine = engine;
        this.view = view;
        this.fxController = fxController;
    }

    @Override
    public TableCell<WarehouseTableRows,Void> call(TableColumn<WarehouseTableRows,Void> column){
        return new InternalCell(engine,view,fxController);
    }

    private static class InternalCell extends TableCell<WarehouseTableRows,Void>{
        private final HBox container;

        public InternalCell(Engine engine,Views view,GeneralView fxController){
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
                if(fxController.getManager().showScene("variationsView")){
                    Optional<GeneralView> variationsFxController = fxController.getManager().getFXController("variationsView");
                    if(variationsFxController.isPresent()){
                        VariationsView variationsView = (VariationsView)(variationsFxController.get());
                        variationsView.setEngine(engine);
                        variationsView.setView(view);
                        variationsView.setItem(item);
                        variationsView.initializeComponents();
                    }else{
                        view.displayError("The JavaFX controller of the variations scene isn't present.");
                        if(fxController.getManager().showScene("mainView")){
                            System.out.println("Restored the main scene of the application.");
                        }else{
                            System.err.println("Error during the restore of the main scene of the application.");
                        }
                    }
                }else{
                    view.displayError("Error during the change of scene in the window.");
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