package com.antoniotassone.models;

import com.antoniotassone.utilities.DateManagement;
import com.antoniotassone.utilities.IdentifierManagement;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Calendar;

public abstract class Variations implements Models<Variations>,Serializable,Comparable<Variations>{
    @Serial
    private static final long serialVersionUID = 1L;
    private final StringProperty variationId;
    private final ObjectProperty<Items> item;
    private final ObjectProperty<Calendar> date;
    private final LongProperty quantity;
    private final ObjectProperty<VariationsType> type;

    public Variations(Items item,Calendar date,long quantity,VariationsType type){
        this.variationId = new SimpleStringProperty(IdentifierManagement.createIdentifier(24));
        this.item = new SimpleObjectProperty<>(new Items(item));
        date.set(Calendar.HOUR_OF_DAY,0);
        date.set(Calendar.MINUTE,0);
        date.set(Calendar.SECOND,0);
        date.set(Calendar.MILLISECOND,0);
        this.date = new SimpleObjectProperty<>(DateManagement.copyTimestamp(date));
        this.quantity = new SimpleLongProperty(quantity);
        this.type = new SimpleObjectProperty<>(type);
    }

    public Variations(String variationId,Items item,Calendar date,long quantity,VariationsType type){
        this.variationId = new SimpleStringProperty(variationId);
        this.item = new SimpleObjectProperty<>(new Items(item));
        date.set(Calendar.HOUR_OF_DAY,0);
        date.set(Calendar.MINUTE,0);
        date.set(Calendar.SECOND,0);
        date.set(Calendar.MILLISECOND,0);
        this.date = new SimpleObjectProperty<>(DateManagement.copyTimestamp(date));
        this.quantity = new SimpleLongProperty(quantity);
        this.type = new SimpleObjectProperty<>(type);
    }

    public Variations(Variations original){
        this.variationId = new SimpleStringProperty(original.variationId.get());
        this.item = new SimpleObjectProperty<>(new Items(original.item.get()));
        this.date = new SimpleObjectProperty<>(DateManagement.copyTimestamp(original.date.get()));
        this.quantity = new SimpleLongProperty(original.quantity.get());
        this.type = new SimpleObjectProperty<>(original.type.get());
    }

    public String getVariationId(){
        return variationId.get();
    }

    public StringProperty variationIdProperty(){
        return variationId;
    }

    public Items getItem(){
        return new Items(item.get());
    }

    public ObjectProperty<Items> itemProperty(){
        return item;
    }

    public Calendar getDate(){
        return DateManagement.copyTimestamp(date.get());
    }

    public ObjectProperty<Calendar> dateProperty(){
        return date;
    }

    public long getQuantity(){
        return quantity.get();
    }

    public LongProperty quantityProperty(){
        return quantity;
    }

    public VariationsType getType(){
        return type.get();
    }

    public ObjectProperty<VariationsType> typeProperty(){
        return type;
    }

    @Override
    public String toString(){
        String output = "{";
        output += "\"variationId\":\"" + variationId.get() + "\",";
        output += "\"item\":" + item.get() + ",";
        output += "\"date\":" + DateManagement.printTimestamp(date.get()) + ",";
        output += "\"quantity\":" + quantity.get() + ",";
        output += "\"type\":\"" + type.get().toString().toLowerCase() + "\"";
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
        return variationId.get().equals(other.variationId.get());
    }

    @Override
    public int hashCode(){
        return variationId.get().hashCode();
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
        int compare = date.get().compareTo(o.date.get());
        if(compare == 0){
            return item.get().compareTo(o.item.get());
        }
        return compare;
    }

    @Override
    public abstract Variations copy();
}