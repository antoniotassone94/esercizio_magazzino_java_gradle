package com.antoniotassone.models;

import com.antoniotassone.utilities.IdentifierManagement;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import java.io.Serial;
import java.io.Serializable;

public class Items implements Models<Items>,Cloneable,Serializable,Comparable<Items>{
    @Serial
    private static final long serialVersionUID = 1L;
    private final StringProperty itemId;
    private final StringProperty name;
    private final StringProperty description;
    private final DoubleProperty price;


    public Items(String name,String description,double price){
        this.itemId = new SimpleStringProperty(IdentifierManagement.createIdentifier(24));
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleDoubleProperty(price);
    }

    public Items(String itemId,String name,String description,double price){
        this.itemId = new SimpleStringProperty(itemId);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleDoubleProperty(price);
    }

    public Items(Items original){
        this.itemId = new SimpleStringProperty(original.itemId.get());
        this.name = new SimpleStringProperty(original.name.get());
        this.description = new SimpleStringProperty(original.description.get());
        this.price = new SimpleDoubleProperty(original.price.get());
    }

    public String getItemId(){
        return itemId.get();
    }

    public StringProperty itemIdProperty(){
        return itemId;
    }

    public String getName(){
        return name.get();
    }

    public StringProperty nameProperty(){
        return name;
    }

    public String getDescription(){
        return description.get();
    }

    public StringProperty descriptionProperty(){
        return description;
    }

    public double getPrice(){
        return price.get();
    }

    public DoubleProperty priceProperty(){
        return price;
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        super.clone();
        return new Items(this);
    }

    @Override
    public String toString(){
        String output = "{\n";
        output += "\"itemId\":\"" + itemId.get() + "\",\n";
        output += "\"name\":\"" + name.get() + "\",\n";
        output += "\"description\":\"" + description.get() + "\",\n";
        output += "\"price\":" + price.get() + "\n";
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
        if(!(obj instanceof Items)){
            return false;
        }
        Items other = (Items)(obj);
        return itemId.get().equals(other.itemId.get());
    }

    @Override
    public int hashCode(){
        return itemId.get().hashCode();
    }

    @Override
    public Items copy(){
        return new Items(this);
    }

    @Override
    public String getId(){
        return getItemId();
    }

    @Override
    public int compareTo(Items o){
        if(o == null){
            return -1;
        }
        if(this.equals(o)){
            return 0;
        }
        return name.get().compareTo(o.name.get());
    }
}