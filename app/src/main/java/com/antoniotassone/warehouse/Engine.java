package com.antoniotassone.warehouse;

import com.antoniotassone.controllers.LogicControllers;
import com.antoniotassone.exceptions.ArchiveNotLoadedException;
import com.antoniotassone.exceptions.ItemNotValidException;
import com.antoniotassone.models.Items;
import com.antoniotassone.models.Variations;

import java.util.List;
import java.util.Map;

public interface Engine{
    LogicControllers getController();

    boolean createNewItem();

    boolean deleteItem(Items item);

    boolean increaseQuantity(Items item);

    boolean decreaseQuantity(Items item);

    List<Variations> getListVariations(Items item);

    Map<Items,Long> getFullWarehouse();
}