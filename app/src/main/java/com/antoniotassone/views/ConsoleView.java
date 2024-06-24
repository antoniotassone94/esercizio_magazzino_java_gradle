package com.antoniotassone.views;

import com.antoniotassone.controllers.Form;
import com.antoniotassone.controllers.ItemsForm;
import com.antoniotassone.models.Items;
import com.antoniotassone.models.Warehouses;
import com.antoniotassone.warehouse.Commands;
import com.antoniotassone.warehouse.Engine;
import com.antoniotassone.warehouse.EngineImpl;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleView implements Views{
    private final Engine engine;
    private boolean exit;
    private final Scanner in;

    public ConsoleView(){
        engine = new EngineImpl(this);
        exit = false;
        in = new Scanner(System.in);
    }

    @Override
    public Optional<Items> readItemData(){
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
    public void displayWarehouse(Warehouses warehouse){
        System.out.println(warehouse);
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
                engine.executeCommand(Commands.DELETE_ITEM);
                break;
            case 3:
                engine.executeCommand(Commands.INCREASE_QUANTITY);
                break;
            case 4:
                engine.executeCommand(Commands.DECREASE_QUANTITY);
                break;
            case 5:
                engine.executeCommand(Commands.PRINT_FULL_WAREHOUSE);
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
}