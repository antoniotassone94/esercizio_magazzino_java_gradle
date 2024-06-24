package com.antoniotassone.warehouse;

import com.antoniotassone.controllers.Form;
import com.antoniotassone.controllers.LogicControllers;
import com.antoniotassone.controllers.LogicWarehouseController;
import com.antoniotassone.exceptions.ArchiveAlreadyLoadedException;
import com.antoniotassone.exceptions.ArchiveNotLoadedException;
import com.antoniotassone.exceptions.ItemNotValidException;
//import com.antoniotassone.exceptions.QuantityNotSufficientException;
import com.antoniotassone.models.Items;
import com.antoniotassone.views.Views;
//import java.util.Optional;

public class EngineCommandsImpl implements EngineCommands{
    private final Views view;
    private final LogicControllers controller;

    public EngineCommandsImpl(Views view) throws ArchiveAlreadyLoadedException,ArchiveNotLoadedException,ItemNotValidException{
        this.view = view;
        controller = new LogicWarehouseController(view);
        controller.loadArchive();
    }

    @Override
    public LogicControllers getController(){
        return controller;
    }

    @Override
    public void createItem(){
        try{
            Form<Items> form = view.readNewItem();
            if(controller.addItemToWarehouse(form)){
                view.displayInfo("The item created correctly and it has added to warehouse");
            }else{
                view.displayError("Error while creating the item, it hasn't added to warehouse");
            }
        }catch(ArchiveNotLoadedException | ItemNotValidException exception){
            exception.printStackTrace();
            view.displayError(exception.getMessage());
        }
    }

    @Override
    public void increaseQuantity(){
        /*try{
            Optional<Items> item = view.readItemData();
            if(item.isPresent()){
                String quantityRead = view.getInputString("Insert the quantity:");
                long quantity;
                try{
                    quantity = Long.parseLong(quantityRead);
                }catch(NumberFormatException exception1){
                    quantity = -1;
                }
                if(controller.increaseQuantity(new Items(item.get()),quantity)){
                    view.displayInfo("item updated");
                }else{
                    view.displayError("item not updated");
                }
            }else{
                view.displayError("item not found");
            }
        }catch(ArchiveNotLoadedException | ItemNotValidException exception){
            exception.printStackTrace();
            view.displayError(exception.getMessage());
        }*/
    }

    @Override
    public void decreaseQuantity(){
        /*try{
            Optional<Items> item = view.readItemData();
            if(item.isPresent()){
                String quantityRead = view.getInputString("Insert the quantity:");
                long quantity;
                try{
                    quantity = Long.parseLong(quantityRead);
                }catch(NumberFormatException exception1){
                    quantity = -1;
                }
                if(controller.decreaseQuantity(new Items(item.get()),quantity)){
                    view.displayInfo("item updated");
                }else{
                    view.displayError("item not updated");
                }
            }else{
                view.displayError("item not found");
            }
        }catch(ArchiveNotLoadedException | ItemNotValidException | QuantityNotSufficientException exception){
            exception.printStackTrace();
            view.displayError(exception.getMessage());
        }*/
    }
}