package com.antoniotassone.views;

import com.antoniotassone.models.Items;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.io.Serial;
import java.io.Serializable;

public class WarehouseTableRows implements Serializable,Cloneable,Comparable<WarehouseTableRows>{
    @Serial
    private static final long serialVersionUID = 1L;
    private final ObjectProperty<Items> item;
    private final LongProperty quantity;

    public WarehouseTableRows(Items item,long quantity){
        this.item = new SimpleObjectProperty<>(new Items(item));
        this.quantity = new SimpleLongProperty(quantity);
    }

    public WarehouseTableRows(WarehouseTableRows original){
        this.item = new SimpleObjectProperty<>(new Items(original.getItem()));
        this.quantity = new SimpleLongProperty(original.getQuantity());
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

    @Override
    public Object clone() throws CloneNotSupportedException{
        super.clone();
        return new WarehouseTableRows(this);
    }

    @Override
    public String toString(){
        String output = "{";
        output += "\"item\":" + item.get().toString() + ",";
        output += "\"quantity\":" + quantity.get();
        output += "}";
        return output;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(this == obj){
            return true;
        }
        if(!(obj instanceof WarehouseTableRows)){
            return false;
        }
        WarehouseTableRows other = (WarehouseTableRows)(obj);
        return item.get().equals(other.item.get());
    }

    @Override
    public int hashCode(){
        return item.get().hashCode();
    }

    @Override
    public int compareTo(WarehouseTableRows o){
        if(o == null){
            return -1;
        }
        if(this.equals(o)){
            return 0;
        }
        return item.get().compareTo(o.item.get());
    }
}