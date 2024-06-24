package com.antoniotassone.controllers;

import com.antoniotassone.models.Variations;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

public class VariationsForm implements Form<Variations>{
    private final Variations variation;

    public VariationsForm(Variations variation){
        this.variation = variation;
    }

    @Override
    public Optional<Variations> readForm(){
        if(variation == null){
            return Optional.empty();
        }
        return Optional.of(variation);
    }

    @Override
    public boolean checkForm(){
        if(variation == null){
            return false;
        }
        Calendar today = new GregorianCalendar();
        today.set(Calendar.HOUR_OF_DAY,0);
        today.set(Calendar.MINUTE,0);
        today.set(Calendar.SECOND,0);
        today.set(Calendar.MILLISECOND,0);
        return !variation.getVariationId().isEmpty() &&
                variation.getItem() != null &&
                !variation.getDate().after(today) &&
                variation.getQuantity() > 0;
    }
}