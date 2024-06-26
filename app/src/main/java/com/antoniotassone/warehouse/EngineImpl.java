package com.antoniotassone.warehouse;

import com.antoniotassone.controllers.Form;
import com.antoniotassone.controllers.LogicControllers;
import com.antoniotassone.controllers.LogicWarehouseController;
import com.antoniotassone.exceptions.ArchiveAlreadyLoadedException;
import com.antoniotassone.exceptions.ArchiveNotLoadedException;
import com.antoniotassone.exceptions.ItemNotValidException;
import com.antoniotassone.exceptions.QuantityNotSufficientException;
import com.antoniotassone.models.Items;
import com.antoniotassone.models.Variations;
import com.antoniotassone.views.Views;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EngineImpl implements Engine{
    private final Views view;
    private final LogicControllers controller;

    public EngineImpl(Views view) throws ArchiveAlreadyLoadedException,ArchiveNotLoadedException,ItemNotValidException{
        this.view = view;
        controller = new LogicWarehouseController(view);
    }

    @Override
    public LogicControllers getController(){
        return controller;
    }

    @Override
    public boolean createNewItem(){
        boolean result;
        try{
            Form<Items> form = view.readNewItem();
            if(controller.addItemToWarehouse(form)){
                view.displayInfo("The item created correctly and it has added to warehouse");
                result = true;
            }else{
                view.displayError("Error while creating the item, it hasn't added to warehouse");
                result = false;
            }
        }catch(ArchiveNotLoadedException | ItemNotValidException exception){
            exception.printStackTrace();
            view.displayError(exception.getMessage());
            result = false;
        }
        return result;
    }

    @Override
    public boolean deleteItem(Items item){
        try{
            if(controller.removeItemFromWarehouse(item)){
                view.displayInfo("Item deleted successfully.");
                return true;
            }else{
                view.displayError("Item could not be deleted.");
                return false;
            }
        }catch(ArchiveNotLoadedException | ItemNotValidException exception){
            exception.printStackTrace();
            view.displayError(exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean increaseQuantity(Items item){
        String quantityRead = view.getInputString("Insert the quantity");
        long quantity;
        try{
            quantity = Long.parseLong(quantityRead);
        }catch(NumberFormatException exception){
            exception.printStackTrace();
            view.displayError(exception.getMessage());
            return false;
        }
        try{
            if(controller.increaseQuantity(new Items(item),quantity)){
                view.displayInfo("Item updated correctly.");
                return true;
            }else{
                view.displayError("Error during the update of the item.");
                return false;
            }
        }catch(ArchiveNotLoadedException | ItemNotValidException exception){
            exception.printStackTrace();
            view.displayError(exception.getMessage());
            return false;
        }
    }

    @Override
    public boolean decreaseQuantity(Items item){
        String quantityRead = view.getInputString("Insert the quantity:");
        long quantity;
        try{
            quantity = Long.parseLong(quantityRead);
        }catch(NumberFormatException exception){
            exception.printStackTrace();
            view.displayError(exception.getMessage());
            return false;
        }
        try{
            if(controller.decreaseQuantity(new Items(item),quantity)){
                view.displayInfo("Item updated correctly.");
                return true;
            }else{
                view.displayError("Error during the update of the item.");
                return false;
            }
        }catch(ArchiveNotLoadedException | ItemNotValidException | QuantityNotSufficientException exception){
            exception.printStackTrace();
            view.displayError(exception.getMessage());
            return false;
        }
    }

    @Override
    public List<Variations> getListVariations(Items item){
        try{
            return controller.getFullWarehouse().getVariations(item);
        }catch(ItemNotValidException | ArchiveNotLoadedException exception){
            exception.printStackTrace();
            view.displayError(exception.getMessage());
            return new LinkedList<>();
        }
    }

    @Override
    public Map<Items,Long> getFullWarehouse(){
        try{
            return controller.getFullWarehouse().getItems();
        }catch(ArchiveNotLoadedException exception){
            exception.printStackTrace();
            view.displayError(exception.getMessage());
            return new HashMap<>();
        }
    }
}