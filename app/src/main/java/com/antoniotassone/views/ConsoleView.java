package com.antoniotassone.views;

import com.antoniotassone.controllers.Form;
import com.antoniotassone.controllers.ItemsForm;
import com.antoniotassone.controllers.LogicControllers;
import com.antoniotassone.exceptions.ArchiveAlreadyLoadedException;
import com.antoniotassone.exceptions.ArchiveNotLoadedException;
import com.antoniotassone.exceptions.ItemNotValidException;
import com.antoniotassone.models.Items;
import com.antoniotassone.warehouse.Engine;
import com.antoniotassone.warehouse.EngineImpl;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleView implements Views{
    private Engine engine;
    private LogicControllers controller;
    private boolean exit;
    private final Scanner in;
    private final List<WarehouseTableRows> table;

    public ConsoleView(){
        try{
            engine = new EngineImpl(this);
        }catch(ArchiveAlreadyLoadedException | ArchiveNotLoadedException | ItemNotValidException exception){
            exception.printStackTrace();
            engine = null;
        }
        exit = false;
        in = new Scanner(System.in);
        table = new LinkedList<>();
        if(engine != null){
            controller = engine.getController();
            Map<Items,Long> warehouse;
            try{
                warehouse = controller.getFullWarehouse().getItems();
            }catch(ArchiveNotLoadedException exception){
                exception.printStackTrace();
                warehouse = null;
            }
            if(warehouse != null){
                for(Items item:warehouse.keySet()){
                    table.add(new WarehouseTableRows(new Items(item),warehouse.get(item)));
                }
            }
        }
    }

    @Override
    public void displayInfo(String message){
        System.out.println(message);
    }

    @Override
    public void displayError(String message){
        System.err.println(message);
    }

    @Override
    public String getInputString(String message){
        System.out.println(message);
        return in.nextLine();
    }

    @Override
    public Form<Items> readNewItem(){
        Optional<Items> item = readItemData();
        return item.map(ItemsForm::new).orElse(null);
    }

    @Override
    public boolean addRow(Items item,long quantity){
        if(item == null || quantity < 0){
            return false;
        }
        return table.add(new WarehouseTableRows(item,quantity));
    }

    @Override
    public boolean deleteRow(Items item){
        if(item == null){
            return false;
        }
        int i = 0;
        while(i < table.size() && !table.get(i).getItem().equals(item)){
            i++;
        }
        if(i >= table.size()){
            return false;
        }
        long quantity = table.get(i).getQuantity();
        return table.remove(new WarehouseTableRows(item,quantity));
    }

    public void printMenu(){
        System.out.println("|--------------------------------------|");
        System.out.println("|          WAREHOUSE MANAGER           |");
        System.out.println("|--------------------------------------|");
        System.out.println("|[1] Create a new item                 |");
        System.out.println("|[2] Delete an item existing           |");
        System.out.println("|[3] Increase the quantity of an item  |");
        System.out.println("|[4] Decrease the quantity of an item  |");
        System.out.println("|[5] Print the full warehouse          |");
        System.out.println("|[0] Exit to the program               |");
        System.out.println("|--------------------------------------|");
    }

    public void executeCommand(){
        if(engine == null || controller == null){
            System.err.println("Error during the execution of the command, because the archive doesn't exist.");
            return;
        }
        String string = getInputString("Choose a number write it on the console:");
        int number;
        try{
            number = Integer.parseInt(string);
        }catch(NumberFormatException exception){
            number = -1;
        }
        switch(number){
            case 1:
                if(engine.createNewItem()){
                    System.out.println("Item created successfully.");
                }else{
                    System.err.println("Item creation failed.");
                }
                break;
            case 2:
                try{
                    if(controller.removeItemFromWarehouse(null)){
                        System.out.println("Item deleted successfully");
                    }else{
                        System.err.println("Item could not be removed");
                    }
                }catch(ArchiveNotLoadedException | ItemNotValidException exception){
                    exception.printStackTrace();
                }
                break;
            case 3:

                //implementare increaseQuantity da riga di comando

                break;
            case 4:

                //implementare decreaseQuantity da riga di comando

                break;
            case 5:
                System.out.println("             Print of full warehouse");
                System.out.println("-------------------------------------------------");
                for(WarehouseTableRows row:table){
                    System.out.println("Item: " + row.getItem() + " - quantity: " + row.getQuantity());
                }
                System.out.println("-------------------------------------------------");
                break;
            case 0:
                exit = true;
                System.out.println("Thank you for using this application.");
                break;
            default:
                System.err.println("The command doesn't exist.");
                break;
        }
        getInputString("Press ENTER to continue.");
    }

    public void mainExecution(){
        while(!exit){
            printMenu();
            executeCommand();
        }
    }

    private Optional<Items> readItemData(){
        System.out.println("Insert name:");
        String name = in.nextLine();
        System.out.println("Insert description:");
        String description = in.nextLine();
        System.out.println("Insert price:");
        String priceString = in.nextLine();
        double price;
        try{
            price = Double.parseDouble(priceString);
        }catch(NumberFormatException exception){
            return Optional.empty();
        }
        return Optional.of(new Items(name,description,price));
    }
}