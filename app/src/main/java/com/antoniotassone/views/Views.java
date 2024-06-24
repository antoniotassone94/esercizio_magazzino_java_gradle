package com.antoniotassone.views;

import com.antoniotassone.controllers.Form;
import com.antoniotassone.models.Items;
import com.antoniotassone.models.Warehouses;
import java.util.Optional;

public interface Views{
    Optional<Items> readItemData();

    void displayInfo(String message);

    void displayError(String message);

    String getInputString(String message);

    Form<Items> readNewItem();

    void displayWarehouse(Warehouses warehouse);
}