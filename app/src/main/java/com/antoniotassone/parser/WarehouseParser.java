package com.antoniotassone.parser;

import com.antoniotassone.exceptions.ItemNotValidException;
import com.antoniotassone.exceptions.VariationNotValidException;
import com.antoniotassone.models.Items;
import com.antoniotassone.models.Variations;
import com.antoniotassone.models.Warehouses;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.LinkedList;

public class WarehouseParser implements Parser<Warehouses>{
    private final Parser<Items> itemParser;
    private final Parser<Variations> variationParser;

    public WarehouseParser(){
        itemParser = new ItemParser();
        variationParser = new VariationParser();
    }

    @Override
    public Warehouses parse(String json) throws ItemNotValidException{
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
        if(!jsonObj.keySet().contains("warehouseId") ||
                !jsonObj.keySet().contains("name") ||
                !jsonObj.keySet().contains("items") ||
                !jsonObj.keySet().contains("variations")){

            throw new ItemNotValidException("The JSON object doesn't contain all the necessary keys.");
        }
        String warehouseId;
        String name;
        JSONArray items;
        JSONArray variations;
        try{
            warehouseId = jsonObj.getString("warehouseId");
            name = jsonObj.getString("name");
            items = jsonObj.getJSONArray("items");
            variations = jsonObj.getJSONArray("variations");
        }catch(JSONException exception){
            exception.printStackTrace();
            throw new ItemNotValidException("The object is not a valid JSON object.");
        }
        Warehouses warehouse = new Warehouses(warehouseId,name,new HashMap<>(),new LinkedList<>());
        for(int i=0;i < variations.length();i++){
            JSONObject variationObject;
            try{
                variationObject = variations.getJSONObject(i);
            }catch(JSONException exception){
                exception.printStackTrace();
                throw new ItemNotValidException("The object is not a valid JSON object.");
            }
            Variations variation = variationParser.parse(variationObject.toString());
            try{
                if(warehouse.addVariation(variation)){
                    System.out.println("Variation has read from file and added to warehouse.");
                }else{
                    System.err.println("Variation hasn't read from file.");
                }
            }catch(VariationNotValidException exception){
                exception.printStackTrace();
                throw new ItemNotValidException(exception.getMessage());
            }
        }
        for(int i=0;i < items.length();i++){
            JSONObject itemObject;
            JSONObject item;
            long quantity;
            try{
                itemObject = items.getJSONObject(i);
                item = itemObject.getJSONObject("item");
                quantity = itemObject.getLong("quantity");
            }catch(JSONException exception){
                exception.printStackTrace();
                throw new ItemNotValidException("The object is not a valid JSON object.");
            }
            Items item1 = itemParser.parse(item.toString());
            if(warehouse.addItem(item1,quantity)){
                System.out.println("Item has read from file and added to warehouse.");
            }else{
                System.err.println("Item hasn't read from file.");
            }
        }
        return warehouse;
    }
}