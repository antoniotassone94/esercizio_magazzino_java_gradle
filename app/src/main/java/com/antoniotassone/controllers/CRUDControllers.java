package com.antoniotassone.controllers;

import com.antoniotassone.models.Models;
import com.antoniotassone.parser.Parser;
import java.util.List;
import java.util.Optional;

public interface CRUDControllers<T extends Models<T>>{
    Optional<T> createElement(Form<T> data);

    boolean updateElement(T data);

    boolean removeElement(Models<T> object);

    List<T> findAll(Parser<T> parser);
}