package com.antoniotassone.controllers;

import com.antoniotassone.models.Models;
import java.util.Optional;

public interface Form<T extends Models<T>>{
    Optional<T> readForm();

    boolean checkForm();
}