package com.antoniotassone.models;

import java.io.Serial;
import java.util.Calendar;

public class Entrances extends Variations implements Cloneable{
    @Serial
    private static final long serialVersionUID = 1L;

    public Entrances(Items item,Calendar date,long quantity){
        super(item,date,quantity,VariationsType.ENTRANCE);
    }

    public Entrances(String variationId,Items item,Calendar date,long quantity){
        super(variationId,item,date,quantity,VariationsType.ENTRANCE);
    }

    public Entrances(Entrances original){
        super(original);
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        super.clone();
        return new Entrances(this);
    }

    @Override
    public Variations copy(){
        return new Entrances(this);
    }
}