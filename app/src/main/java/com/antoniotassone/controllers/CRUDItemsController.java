package com.antoniotassone.controllers;

import com.antoniotassone.models.Items;
import com.antoniotassone.models.Models;
import com.antoniotassone.parser.Parser;
import com.antoniotassone.utilities.FilesManagement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class CRUDItemsController implements CRUDControllers<Items>{
    public CRUDItemsController(){}

    @Override
    public Optional<Items> createElement(Form<Items> data){
        if(data == null){
            return Optional.empty();
        }
        Optional<Items> item = data.readForm();
        if(item.isPresent()){
            if(data.checkForm()){
                if(FilesManagement.writeObjectToFile(item.get())){
                    return item;
                }
                return Optional.empty();
            }
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public boolean updateElement(Items data){
        return false;
    }

    @Override
    public boolean removeElement(Models<Items> object){
        if(object == null){
            return false;
        }
        return FilesManagement.deleteObject(object);
    }

    @Override
    public List<Items> findAll(Parser<Items> parser){
        if(parser == null){
            return new LinkedList<>();
        }
        List<String> files = FilesManagement.readListFiles();
        List<Items> items = new LinkedList<>();
        for(String file:files){
            if(file.contains("items_")){
                List<String> rows = FilesManagement.readFile(FilesManagement.getUserDirectory() + "/" + file);
                StringBuilder json = new StringBuilder();
                for(String row:rows){
                    json.append(row);
                }
                Optional<Items> item = parser.parse(json.toString());
                item.ifPresentOrElse(items::add,() -> System.err.println("The item " + file + " wasn't found"));
            }
        }
        return items;
    }
}