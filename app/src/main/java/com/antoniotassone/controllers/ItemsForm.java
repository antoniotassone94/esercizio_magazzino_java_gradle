package com.antoniotassone.controllers;

import com.antoniotassone.models.Items;
import java.util.Optional;

public class ItemsForm implements Form<Items>{
    private final Items item;

    public ItemsForm(Items item){
        this.item = item;
    }

    @Override
    public Optional<Items> readForm(){
        if(item == null){
            return Optional.empty();
        }
        return Optional.of(item);
    }

    @Override
    public boolean checkForm(){
        if(item == null){
            return false;
        }
        return !item.getItemId().isEmpty() && !item.getName().isEmpty() && !item.getDescription().isEmpty();
    }
}