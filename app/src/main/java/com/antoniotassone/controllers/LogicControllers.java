package com.antoniotassone.controllers;

import com.antoniotassone.exceptions.ArchiveAlreadyLoadedException;
import com.antoniotassone.exceptions.ArchiveNotLoadedException;
import com.antoniotassone.exceptions.ItemNotValidException;
import com.antoniotassone.exceptions.QuantityNotSufficientException;
import com.antoniotassone.models.Items;
import com.antoniotassone.models.Warehouses;

public interface LogicControllers{
    void loadArchive() throws ArchiveAlreadyLoadedException,ArchiveNotLoadedException,ItemNotValidException;

    Warehouses getFullWarehouse() throws ArchiveNotLoadedException;

    boolean addItemToWarehouse(Form<Items> dataItem) throws ArchiveNotLoadedException,ItemNotValidException;

    boolean removeItemFromWarehouse(Items item) throws ArchiveNotLoadedException,ItemNotValidException;

    boolean increaseQuantity(Items item,long quantity) throws ArchiveNotLoadedException,ItemNotValidException;

    boolean decreaseQuantity(Items item,long quantity) throws ArchiveNotLoadedException,ItemNotValidException,QuantityNotSufficientException;
}