package com.antoniotassone.warehouse;

public interface EngineCommands{
    void createItem();

    void deleteItem();

    void increaseQuantity();

    void decreaseQuantity();

    void printFullWarehouse();
}