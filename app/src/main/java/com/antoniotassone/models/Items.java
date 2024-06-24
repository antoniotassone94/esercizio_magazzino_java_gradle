package com.antoniotassone.models;

import com.antoniotassone.utilities.IdentifierManagement;
import java.io.Serial;
import java.io.Serializable;

public class Items implements Models<Items>,Cloneable,Serializable,Comparable<Items>{
    @Serial
    private static final long serialVersionUID = 1L;
    private final String itemId;
    private final String name;
    private final String description;
    private final double price;

    public Items(String name,String description,double price){
        this.itemId = IdentifierManagement.createIdentifier(24);
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Items(String itemId,String name,String description,double price){
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Items(Items original){
        this.itemId = original.itemId;
        this.name = original.name;
        this.description = original.description;
        this.price = original.price;
    }

    public String getItemId(){
        return itemId;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        super.clone();
        return new Items(this);
    }

    @Override
    public String toString(){
        String output = "{";
        output += "\"itemId\":\"" + itemId + "\",";
        output += "\"name\":\"" + name + "\",";
        output += "\"description\":\"" + description + "\",";
        output += "\"price\":" + price;
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
        return itemId.equals(other.itemId);
    }

    @Override
    public int hashCode(){
        return itemId.hashCode();
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
        return name.compareTo(o.name);
    }
}