package com.antoniotassone.models;

import java.io.Serial;
import java.util.Calendar;

public class Exits extends Variations implements Cloneable{
    @Serial
    private static final long serialVersionUID = 1L;

    public Exits(Items item,Calendar date,long quantity){
        super(item,date,quantity,VariationsType.EXIT);
    }

    public Exits(String variationId,Items item,Calendar date,long quantity){
        super(variationId,item,date,quantity,VariationsType.EXIT);
    }

    public Exits(Exits original){
        super(original);
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        super.clone();
        return new Exits(this);
    }

    @Override
    public Variations copy(){
        return new Exits(this);
    }
}