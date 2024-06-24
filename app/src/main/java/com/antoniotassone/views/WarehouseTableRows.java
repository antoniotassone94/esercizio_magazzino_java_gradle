package com.antoniotassone.views;

import com.antoniotassone.models.Items;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

public class WarehouseTableRows{
    private final String itemId;
    private final ObjectProperty<Items> item;
    private final LongProperty quantity;

    public WarehouseTableRows(Items item,long quantity){
        itemId = item.getItemId();
        this.item = new SimpleObjectProperty<>(new Items(item));
        this.quantity = new SimpleLongProperty(quantity);
    }

    public String getItemId(){
        return itemId;
    }

    public ObjectProperty<Items> itemProperty(){
        return item;
    }

    public Items getItem(){
        return item.get();
    }

    public LongProperty quantityProperty(){
        return quantity;
    }

    public long getQuantity(){
        return quantity.get();
    }

    public void setQuantity(long quantity){
        this.quantity.set(quantity);
    }
}