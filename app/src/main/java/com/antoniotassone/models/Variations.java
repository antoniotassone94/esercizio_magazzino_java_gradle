package com.antoniotassone.models;

import com.antoniotassone.utilities.DateManagement;
import com.antoniotassone.utilities.IdentifierManagement;
import java.io.Serial;
import java.io.Serializable;
import java.util.Calendar;

public abstract class Variations implements Models<Variations>,Serializable,Comparable<Variations>{
    @Serial
    private static final long serialVersionUID = 1L;
    private final String variationId;
    private final Items item;
    private final Calendar date;
    private final long quantity;
    private final VariationsType type;

    public Variations(Items item,Calendar date,long quantity,VariationsType type){
        this.variationId = IdentifierManagement.createIdentifier(24);
        this.item = new Items(item);
        this.date = DateManagement.copyTimestamp(date);
        this.date.set(Calendar.HOUR_OF_DAY,0);
        this.date.set(Calendar.MINUTE,0);
        this.date.set(Calendar.SECOND,0);
        this.date.set(Calendar.MILLISECOND,0);
        this.quantity = quantity;
        this.type = type;
    }

    public Variations(String variationId,Items item,Calendar date,long quantity,VariationsType type){
        this.variationId = variationId;
        this.item = new Items(item);
        this.date = DateManagement.copyTimestamp(date);
        this.date.set(Calendar.HOUR_OF_DAY,0);
        this.date.set(Calendar.MINUTE,0);
        this.date.set(Calendar.SECOND,0);
        this.date.set(Calendar.MILLISECOND,0);
        this.quantity = quantity;
        this.type = type;
    }

    public Variations(Variations original){
        this.variationId = original.variationId;
        this.item = new Items(original.item);
        this.date = DateManagement.copyTimestamp(original.date);
        this.quantity = original.quantity;
        this.type = original.type;
    }

    public String getVariationId(){
        return variationId;
    }

    public Items getItem(){
        return new Items(item);
    }

    public Calendar getDate(){
        return DateManagement.copyTimestamp(date);
    }

    public long getQuantity(){
        return quantity;
    }

    @Override
    public String toString(){
        String output = "{";
        output += "\"variationId\":\"" + variationId + "\",";
        output += "\"item\":" + item + ",";
        output += "\"date\":" + DateManagement.printTimestamp(date) + ",";
        output += "\"quantity\":" + quantity + ",";
        output += "\"type\":\"" + type.toString().toLowerCase() + "\"";
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
        if(!(this.getClass().getName().equals(obj.getClass().getName()))){
            return false;
        }
        Variations other = (Variations)(obj);
        return variationId.equals(other.variationId);
    }

    @Override
    public int hashCode(){
        return variationId.hashCode();
    }

    @Override
    public String getId(){
        return getVariationId();
    }

    @Override
    public int compareTo(Variations o){
        if(o == null){
            return -1;
        }
        if(this.equals(o)){
            return 0;
        }
        int compare = date.compareTo(o.date);
        if(compare == 0){
            return item.compareTo(o.item);
        }
        return compare;
    }

    @Override
    public abstract Variations copy();
}