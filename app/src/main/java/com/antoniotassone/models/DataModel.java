package com.antoniotassone.models;

import com.antoniotassone.exceptions.ItemNotValidException;
import com.antoniotassone.exceptions.VariationNotValidException;
import com.antoniotassone.utilities.IdentifierManagement;
import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class DataModel implements Models<DataModel>,Cloneable,Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    private final String dataModelId;
    private final Warehouses warehouse;
    private final List<Items> items;
    private final List<Variations> variations;

    public DataModel(Warehouses warehouse,List<Items> items,List<Variations> variations){
        this.dataModelId = IdentifierManagement.createIdentifier(24);
        this.warehouse = new Warehouses(warehouse);
        this.items = new LinkedList<>(items);
        this.variations = new LinkedList<>(variations);
    }

    public DataModel(DataModel original){
        this.dataModelId = original.dataModelId;
        this.warehouse = new Warehouses(original.warehouse);
        this.items = new LinkedList<>(original.items);
        this.variations = new LinkedList<>(original.variations);
    }

    public String getDataModelId(){
        return dataModelId;
    }

    public Warehouses getWarehouse(){
        return new Warehouses(warehouse);
    }

    public List<Items> getItems(){
        return new LinkedList<>(items);
    }

    public List<Variations> getVariations(){
        return new LinkedList<>(variations);
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        super.clone();
        return new DataModel(this);
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder("{");
        output.append("\"dataModelId\":\"");
        output.append(dataModelId);
        output.append("\",");
        output.append("\"warehouse\":");
        output.append(warehouse.toString());
        output.append(",");
        output.append("\"items\":[");
        long i = 0;
        for(Items item:items){
            output.append(item.toString());
            if(i < (items.size() - 1)){
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
        if(!(obj instanceof DataModel)){
            return false;
        }
        DataModel other = (DataModel)(obj);
        return dataModelId.equals(other.dataModelId);
    }

    @Override
    public int hashCode(){
        return dataModelId.hashCode();
    }

    @Override
    public DataModel copy(){
        return new DataModel(this);
    }

    @Override
    public String getId(){
        return getDataModelId();
    }

    public boolean addVariation(Variations variation) throws VariationNotValidException{
        if(warehouse.addVariation(variation)){
            return variations.add(variation);
        }
        return false;
    }

    public long getQuantity(Items item) throws ItemNotValidException{
        return warehouse.getQuantity(item);
    }

    public boolean removeItem(Items item) throws ItemNotValidException{
        if(warehouse.removeItem(item)){
            return items.remove(item);
        }
        return false;
    }

    public boolean addItem(Items item,long quantity) throws ItemNotValidException{
        if(warehouse.addItem(item,quantity)){
            return items.add(item);
        }
        return false;
    }
}