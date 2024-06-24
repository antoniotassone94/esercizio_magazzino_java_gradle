package com.antoniotassone.models;

import com.antoniotassone.exceptions.ItemNotValidException;
import com.antoniotassone.exceptions.VariationNotValidException;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Warehouses implements Models<Warehouses>,Cloneable,Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    private final String warehouseId;
    private final String name;
    private final Map<Items,Long> items;
    private final List<Variations> variations;

    public Warehouses(String warehouseId,String name,Map<Items,Long> items,List<Variations> variations){
        this.warehouseId = warehouseId;
        this.name = name;
        this.items = new HashMap<>(items);
        this.variations = new LinkedList<>(variations);
    }

    public Warehouses(Warehouses original){
        this.warehouseId = original.warehouseId;
        this.name = original.name;
        this.items = new HashMap<>(original.items);
        this.variations = new LinkedList<>(original.variations);
    }

    public String getWarehouseId(){
        return warehouseId;
    }

    public String getName(){
        return name;
    }

    public Map<Items,Long> getItems(){
        return new HashMap<>(items);
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        super.clone();
        return new Warehouses(this);
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder("{");
        output.append("\"warehouseId\":\"");
        output.append(warehouseId);
        output.append("\",");
        output.append("\"name\":\"");
        output.append(name);
        output.append("\",");
        output.append("\"items\":[");
        int i = 0;
        for(Items item:items.keySet()){
            String itemObject = "{";
            itemObject += "\"item\":" + item + ",";
            itemObject += "\"quantity\":" + items.get(item);
            itemObject += "}";
            output.append(itemObject);
            if(i < (items.keySet().size() - 1)){
                output.append(",");
            }
            i++;
        }
        output.append("],");
        output.append("\"variations\":[");
        i = 0;
        for(Variations variation:variations){
            output.append(variation.toString());
            if(i < (variations.size() - 1)){
                output.append(",");
            }
            i++;
        }
        output.append("]");
        output.append("}");
        return output.toString();
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(this == obj){
            return true;
        }
        if(!(obj instanceof Warehouses)){
            return false;
        }
        Warehouses other = (Warehouses)(obj);
        return warehouseId.equals(other.warehouseId);
    }

    @Override
    public int hashCode(){
        return warehouseId.hashCode();
    }

    @Override
    public Warehouses copy(){
        return new Warehouses(this);
    }

    @Override
    public String getId(){
        return getWarehouseId();
    }

    public boolean addItem(Items item,long quantity) throws ItemNotValidException{
        if(item == null || quantity < 0){
            throw new ItemNotValidException("The item is null and it can't be processed.");
        }
        if(items.containsKey(item)){
            throw new ItemNotValidException("The item already exists.");
        }
        return items.put(new Items(item),quantity) == null;
    }

    public boolean removeItem(Items item) throws ItemNotValidException{
        if(item == null){
            throw new ItemNotValidException("The item is null and it can't be processed.");
        }
        if(!items.containsKey(item)){
            throw new ItemNotValidException("The item doesn't exist.");
        }
        return items.remove(new Items(item)) != null;
    }

    public long getQuantity(Items item) throws ItemNotValidException{
        if(item == null){
            throw new ItemNotValidException("The item is null and it can't be processed.");
        }
        if(!items.containsKey(item)){
            throw new ItemNotValidException("The item searched doesn't exist.");
        }
        return items.get(item);
    }

    public boolean addVariation(Variations variation) throws VariationNotValidException{
        if(variation == null){
            throw new VariationNotValidException("The variation is null and it can't be processed.");
        }
        if(variations.contains(variation)){
            throw new VariationNotValidException("The variation already exists.");
        }
        return variations.add(variation.copy());
    }
}