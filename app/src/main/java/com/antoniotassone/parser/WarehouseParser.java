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
import java.util.Optional;

public class WarehouseParser implements Parser<Warehouses>{
    private final Parser<Items> itemParser;
    private final Parser<Variations> variationParser;

    public WarehouseParser(){
        itemParser = new ItemParser();
        variationParser = new VariationParser();
    }

    @Override
    public Optional<Warehouses> parse(String json){
        if(json == null){
            return Optional.empty();
        }
        if(json.isEmpty()){
            return Optional.empty();
        }
        JSONObject jsonObj;
        try{
            jsonObj = new JSONObject(json);
        }catch(JSONException exception){
            exception.printStackTrace();
            return Optional.empty();
        }
        if(!jsonObj.keySet().contains("warehouseId") ||
                !jsonObj.keySet().contains("name") ||
                !jsonObj.keySet().contains("items") ||
                !jsonObj.keySet().contains("variations")){

            return Optional.empty();
        }
        String warehouseId = jsonObj.getString("warehouseId");
        String name = jsonObj.getString("name");
        JSONArray items = jsonObj.getJSONArray("items");
        JSONArray variations = jsonObj.getJSONArray("variations");
        Warehouses warehouse = new Warehouses(warehouseId,name,new HashMap<>(),new LinkedList<>());
        for(int i=0;i < variations.length();i++){
            JSONObject variationObject = variations.getJSONObject(i);
            Optional<Variations> variation = variationParser.parse(variationObject.toString());
            if(variation.isEmpty()){
                return Optional.empty();
            }
            try{
                if(warehouse.addVariation(variation.get())){
                    System.out.println("Variation has read from file and added to warehouse.");
                }else{
                    System.err.println("Variation hasn't read from file.");
                }
            }catch(VariationNotValidException exception){
                exception.printStackTrace();
                return Optional.empty();
            }
        }
        for(int i=0;i < items.length();i++){
            JSONObject itemObject = items.getJSONObject(i);
            JSONObject item = itemObject.getJSONObject("item");
            long quantity = itemObject.getLong("quantity");
            Optional<Items> item1 = itemParser.parse(item.toString());
            if(item1.isEmpty()){
                return Optional.empty();
            }
            try{
                if(warehouse.addItem(item1.get(),quantity)){
                    System.out.println("Item has read from file and added to warehouse.");
                }else{
                    System.err.println("Item hasn't read from file.");
                }
            }catch(ItemNotValidException exception){
                exception.printStackTrace();
                return Optional.empty();
            }
        }
        return Optional.of(warehouse);
    }
}