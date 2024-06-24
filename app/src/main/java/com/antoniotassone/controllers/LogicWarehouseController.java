package com.antoniotassone.controllers;

import com.antoniotassone.exceptions.ArchiveAlreadyLoadedException;
import com.antoniotassone.exceptions.ArchiveNotLoadedException;
import com.antoniotassone.exceptions.ItemNotValidException;
import com.antoniotassone.exceptions.QuantityNotSufficientException;
import com.antoniotassone.exceptions.VariationNotValidException;
import com.antoniotassone.models.DataModel;
import com.antoniotassone.models.Entrances;
import com.antoniotassone.models.Exits;
import com.antoniotassone.models.Items;
import com.antoniotassone.models.Variations;
import com.antoniotassone.models.Warehouses;
import com.antoniotassone.parser.ItemParser;
import com.antoniotassone.parser.VariationParser;
import com.antoniotassone.parser.WarehouseParser;
import com.antoniotassone.views.Views;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

public class LogicWarehouseController implements LogicControllers{
    private final Views view;
    private DataModel model;

    public LogicWarehouseController(Views view){
        this.view = view;
        model = null;
    }

    @Override
    public void loadArchive() throws ArchiveAlreadyLoadedException,ArchiveNotLoadedException,ItemNotValidException{
        if(model != null){
            throw new ArchiveAlreadyLoadedException();
        }
        CRUDControllers<Warehouses> warehouseControllers = new CRUDWarehousesController();
        CRUDControllers<Items> itemsControllers = new CRUDItemsController();
        CRUDControllers<Variations> variationsControllers = new CRUDVariationsController();
        List<Warehouses> warehouses = warehouseControllers.findAll(new WarehouseParser());
        List<Items> items = itemsControllers.findAll(new ItemParser());
        List<Variations> variations = variationsControllers.findAll(new VariationParser());
        if(warehouses.size() != 1 && items != null && variations != null){
            throw new ArchiveNotLoadedException();
        }
        model = new DataModel(warehouses.getFirst(),items,variations);
    }

    @Override
    public Warehouses getFullWarehouse() throws ArchiveNotLoadedException{
        if(model == null){
            throw new ArchiveNotLoadedException();
        }
        return model.getWarehouse();
    }

    @Override
    public boolean addItemToWarehouse(Form<Items> dataItem) throws ArchiveNotLoadedException,ItemNotValidException{
        if(dataItem == null){
            throw new ItemNotValidException("The item is null and it can't be processed.");
        }
        if(model == null){
            throw new ArchiveNotLoadedException();
        }
        CRUDControllers<Items> itemControllers = new CRUDItemsController();
        Optional<Items> item = itemControllers.createElement(dataItem);
        if(item.isEmpty()){
            return false;
        }
        if(model.addItem(item.get(),0)){
            CRUDControllers<Warehouses> warehouseControllers = new CRUDWarehousesController();
            if(warehouseControllers.updateElement(model.getWarehouse())){
                return view.addRow(item.get(),0);
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean removeItemFromWarehouse(Items item) throws ArchiveNotLoadedException,ItemNotValidException{
        if(item == null){
            throw new ItemNotValidException("The item is null and it can't be processed.");
        }
        if(model == null){
            throw new ArchiveNotLoadedException();
        }
        if(model.removeItem(new Items(item))){
            CRUDControllers<Items> itemControllers = new CRUDItemsController();
            if(itemControllers.removeElement(new Items(item))){
                CRUDControllers<Warehouses> warehouseControllers = new CRUDWarehousesController();
                if(warehouseControllers.updateElement(model.getWarehouse())){
                    return view.deleteRow(new Items(item));
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean increaseQuantity(Items item,long quantity) throws ArchiveNotLoadedException,ItemNotValidException{
        if(item == null || quantity <= 0){
            throw new ItemNotValidException("The item is null and it can't be processed.");
        }
        if(model == null){
            throw new ArchiveNotLoadedException();
        }
        long actualQuantity = model.getQuantity(item);
        long newQuantity = actualQuantity + quantity;
        if(model.removeItem(new Items(item))){
            if(model.addItem(new Items(item),newQuantity)){
                CRUDControllers<Variations> variationsController = new CRUDVariationsController();
                Form<Variations> data = new VariationsForm(new Entrances(item,new GregorianCalendar(),quantity));
                Optional<Variations> newVariation = variationsController.createElement(data);
                if(newVariation.isEmpty()){
                    return false;
                }
                try{
                    if(model.addVariation(newVariation.get())){
                        CRUDControllers<Warehouses> warehouseControllers = new CRUDWarehousesController();
                        return warehouseControllers.updateElement(model.getWarehouse());
                    }
                    return false;
                }catch(VariationNotValidException exception){
                    exception.printStackTrace();
                    return false;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean decreaseQuantity(Items item,long quantity) throws ArchiveNotLoadedException,ItemNotValidException,QuantityNotSufficientException{
        if(item == null || quantity <= 0){
            throw new ItemNotValidException("The item is null and it can't be processed.");
        }
        if(model == null){
            throw new ArchiveNotLoadedException();
        }
        long actualQuantity = model.getQuantity(item);
        long newQuantity = actualQuantity - quantity;
        if(newQuantity < 0){
            throw new QuantityNotSufficientException();
        }
        if(model.removeItem(new Items(item))){
            if(model.addItem(new Items(item),newQuantity)){
                CRUDControllers<Variations> variationsController = new CRUDVariationsController();
                Form<Variations> data = new VariationsForm(new Exits(item,new GregorianCalendar(),quantity));
                Optional<Variations> newVariation = variationsController.createElement(data);
                if(newVariation.isEmpty()){
                    return false;
                }
                try{
                    if(model.addVariation(newVariation.get())){
                        CRUDControllers<Warehouses> warehouseControllers = new CRUDWarehousesController();
                        return warehouseControllers.updateElement(model.getWarehouse());
                    }
                    return false;
                }catch(VariationNotValidException exception){
                    exception.printStackTrace();
                    return false;
                }
            }
            return false;
        }
        return false;
    }
}