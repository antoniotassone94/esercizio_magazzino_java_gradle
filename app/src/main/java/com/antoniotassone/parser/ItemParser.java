package com.antoniotassone.parser;

import com.antoniotassone.exceptions.ItemNotValidException;
import com.antoniotassone.models.Items;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemParser implements Parser<Items>{
    public ItemParser(){}

    @Override
    public Items parse(String json) throws ItemNotValidException{
        if(json == null){
            throw new ItemNotValidException("The JSON object is null.");
        }
        if(json.isEmpty()){
            throw new ItemNotValidException("The JSON object is empty.");
        }
        JSONObject jsonObj;
        try{
            jsonObj = new JSONObject(json);
        }catch(JSONException exception){
            exception.printStackTrace();
            throw new ItemNotValidException("The object is not a valid JSON object.");
        }
        if(!jsonObj.keySet().contains("itemId") ||
                !jsonObj.keySet().contains("name") ||
                !jsonObj.keySet().contains("description") ||
                !jsonObj.keySet().contains("price")){

            throw new ItemNotValidException("The JSON object doesn't contain all the necessary keys.");
        }
        String itemId;
        String name;
        String description;
        double price;
        try{
            itemId = jsonObj.getString("itemId");
            name = jsonObj.getString("name");
            description = jsonObj.getString("description");
            price = jsonObj.getDouble("price");
        }catch(JSONException exception){
            exception.printStackTrace();
            throw new ItemNotValidException("The object is not a valid JSON object.");
        }
        return new Items(itemId,name,description,price);
    }
}