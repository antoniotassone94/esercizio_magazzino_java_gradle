package com.antoniotassone.views;

import com.antoniotassone.controllers.Form;
import com.antoniotassone.models.Items;
import com.antoniotassone.models.Variations;

public interface Views{
    void displayInfo(String message);

    void displayError(String message);

    String getInputString(String message);

    Form<Items> readNewItem();

    boolean addRow(Items item,long quantity);

    boolean deleteRow(Items item);

    boolean updateRow(Variations newVariation,long newQuantity);
}