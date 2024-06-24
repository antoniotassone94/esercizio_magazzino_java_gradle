package com.antoniotassone.parser;

import com.antoniotassone.exceptions.ItemNotValidException;
import com.antoniotassone.models.Models;

@FunctionalInterface
public interface Parser<T extends Models<T>>{
    T parse(String json) throws ItemNotValidException;
}