package com.antoniotassone.views;

import com.antoniotassone.models.Items;
import com.antoniotassone.warehouse.Engine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.Optional;

public class HandleButtonVariations implements EventHandler<ActionEvent>{
    private final ActionsTableCell cell;
    private final GeneralView fxController;
    private final Engine engine;
    private final Views view;

    public HandleButtonVariations(ActionsTableCell cell,GeneralView fxController,Engine engine,Views view){
        this.cell = cell;
        this.fxController = fxController;
        this.engine = engine;
        this.view = view;
    }

    @Override
    public void handle(ActionEvent event){
        WarehouseTableRows row = cell.getTableView().getItems().get(cell.getIndex());
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
    }
}