package com.antoniotassone.controllers;

import com.antoniotassone.models.Models;
import com.antoniotassone.models.Warehouses;
import com.antoniotassone.parser.Parser;
import com.antoniotassone.utilities.FilesManagement;
import java.util.List;
import java.util.LinkedList;
import java.util.Optional;

public class CRUDWarehousesController implements CRUDControllers<Warehouses>{
    public CRUDWarehousesController(){}

    @Override
    public Optional<Warehouses> createElement(Form<Warehouses> data){
        return Optional.empty();
    }

    @Override
    public boolean updateElement(Warehouses data){
        if(data == null){
            return false;
        }
        if(FilesManagement.deleteObject(data)){
            return FilesManagement.writeObjectToFile(data);
        }
        return false;
    }

    @Override
    public boolean removeElement(Models<Warehouses> object){
        return false;
    }

    @Override
    public List<Warehouses> findAll(Parser<Warehouses> parser){
        if(parser == null){
            return new LinkedList<>();
        }
        List<String> files = FilesManagement.readListFiles();
        List<Warehouses> warehouses = new LinkedList<>();
        for(String file:files){
            if(file.contains("warehouses_")){
                List<String> rows = FilesManagement.readFile(FilesManagement.getUserDirectory() + "/" + file);
                StringBuilder json = new StringBuilder();
                for(String row:rows){
                    json.append(row);
                }
                Optional<Warehouses> warehouse = parser.parse(json.toString());
                warehouse.ifPresentOrElse(warehouses::add,() -> System.err.println("The warehouse " + file + " wasn't found"));
            }
        }
        return warehouses;
    }
}