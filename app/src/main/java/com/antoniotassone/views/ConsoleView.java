package com.antoniotassone.views;

import com.antoniotassone.controllers.Form;
import com.antoniotassone.controllers.ItemsForm;
import com.antoniotassone.controllers.LogicControllers;
import com.antoniotassone.exceptions.ArchiveAlreadyLoadedException;
import com.antoniotassone.exceptions.ArchiveNotLoadedException;
import com.antoniotassone.exceptions.ItemNotValidException;
import com.antoniotassone.models.Items;
import com.antoniotassone.warehouse.Commands;
import com.antoniotassone.warehouse.Engine;
import com.antoniotassone.warehouse.EngineImpl;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleView implements Views{
    private Engine engine;
    private LogicControllers controller;
    private boolean exit;
    private final Scanner in;

    public ConsoleView(){
        try{
            engine = new EngineImpl(this);
            controller = engine.getController();
        }catch(ArchiveAlreadyLoadedException | ArchiveNotLoadedException | ItemNotValidException exception){
            exception.printStackTrace();
            engine = null;
            controller = null;
        }
        exit = false;
        in = new Scanner(System.in);
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
        Optional<Items> item = this.readItemData();
        return item.map(ItemsForm::new).orElse(null);
    }

    @Override
    public boolean addRow(Items item,long quantity){
        return false;
    }

    @Override
    public boolean deleteRow(Items item){
        return false;
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
            System.err.println("Error during the execution of the command.");
            return;
        }
        String string = this.getInputString("Choose a number write it on the console:");
        int number;
        try{
            number = Integer.parseInt(string);
        }catch(NumberFormatException exception){
            number = -1;
        }
        switch(number){
            case 1:
                engine.executeCommand(Commands.CREATE_ITEM);
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
                engine.executeCommand(Commands.INCREASE_QUANTITY);
                break;
            case 4:
                engine.executeCommand(Commands.DECREASE_QUANTITY);
                break;
            case 5:
                try{
                    System.out.println(controller.getFullWarehouse());
                }catch(ArchiveNotLoadedException exception){
                    exception.printStackTrace();
                }
                break;
            case 0:
                exit = true;
                this.displayInfo("Thank you for using this application.");
                break;
            default:
                this.displayError("The command doesn't exist.");
                break;
        }
        this.getInputString("Press ENTER to continue.");
    }

    public void mainExecution(){
        while(!exit){
            this.printMenu();
            this.executeCommand();
        }
    }

    private Optional<Items> readItemData(){
        System.out.println("Insert name:");
        String name = in.nextLine();
        System.out.println("Insert description:");
        String description = in.nextLine();
        System.out.println("Insert price:");
        double price;
        try{
            price = in.nextDouble();
            in.nextLine();
        }catch(NumberFormatException exception){
            return Optional.empty();
        }
        return Optional.of(new Items(name,description,price));
    }
}