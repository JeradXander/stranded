package com.game.startmenu;

import com.game.conditions.Combat;
import com.game.items.Item;
import com.game.player.Player;
import com.game.world.GameWorld;
import com.game.world.Location;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import sample.views.GameViewManager;

public class Status {

    private String action = "None";
    private String noun = "";
    private String result = "";

    Player player = new Player("david", "Medic");

    GameViewManager gameViewManager;

    // Constructor
    public Status() {

    }

    //Methods for actions

public void action(String[] command, Player _player, GameViewManager _gameViewManager) throws IllegalArgumentException{
    //command[0] is action, command[1] is direction/item/etc.

    player = _player;

    System.out.println(player.getName()+player.getAstronautClass());
        //Initialize variables for action logic
        String currentLoc = GameWorld.getCurrentLocation();
        HashMap<String, ArrayList<Item>> inventoryMap = GameWorld.getGameItems();
        ArrayList<Item> inventoryArray = inventoryMap.get(currentLoc);
        HashMap<String, ArrayList<Item>> hiddenItemsMap = GameWorld.getHiddenItems();
        ArrayList<Item> hiddenItemsArray = hiddenItemsMap.get(currentLoc);
        ArrayList<Item> playerItems = _player.getInventory();

        if (currentLoc == null || currentLoc.equals("")) {
            currentLoc = GameWorld.getPreviousLocation();
        }

        if (command[0].equals("go")) {
            //Execute move to change current room to command[1]
           String nextLoc = GameWorld.getNextLocation(currentLoc,command[1]); //checks what location is n/e/s/w of current location
           if (nextLoc == null || nextLoc.equals("")) {
               nextLoc = currentLoc;
               setResult("You can't go that way");
           }

            // new method for moving player
            if (!nextLoc.equals(currentLoc)) { //This will prevent move method being called, so HP won't be penalized.
                _player.move(nextLoc);
            }
            //setResult("Moving is tiring and HP draining on this planet..");
        }

        if (command[0].equals("grab")) {
            //execute grab command & add item to player inventory / remove from room inventory

            int count = 0;
            Item removeItem = null;
            for(Item item: inventoryArray){
                if(item.getItemName().equals(command[1])){
                    _player.addItem(item);
                    removeItem = item;
                    setResult(item.getItemName() + " grabbed! " + item.getDescription());
                } else {
                    count += 1;
                }
            }

            if (count == inventoryArray.size()){
                setResult("Nothing there to grab!");
            } else {
                inventoryArray.remove(removeItem);
            }
        }

        if (command[0].equals("search")) {
            //Execute Search command and reveal hidden items in room + add to room inventory

            for (Item hiddenItem: hiddenItemsArray) {
                if (hiddenItem != null) {
                    setResult(hiddenItem.getItemName() + " uncovered!");
                    inventoryArray.add(hiddenItem);
                    break;
                }
                else {
                    setResult("No hidden items found.");
                }
            }
        }

        if (command[0].equals("drop")) {
            //user types drop + item name from player inventory

            Item dropItem = null;
            for (Item item : playerItems){
                if (item.getItemName().equals(command[1])) { //If item from player's inventory matches dropped item
                    dropItem = item;
                    //item is added to current location inventory
                    inventoryArray.add(item);
                    break;
                }
            }
            if (dropItem != null) {
                playerItems.remove(dropItem);
                setResult("You dropped a "+ dropItem.getItemName());
            }

        }

        if (command[0].equals("use") || command[0].equals("eat")) {
            //Check if command[1] is in player inventory
            Item usedItem = null;
            for (Item item : playerItems){
                if (item.getItemName().equals(command[1])) { //If item from player's inventory matches use item
                    usedItem = item;
                    break;
                }
            }
            if (usedItem == null) {
                setResult("You don't have " + command[1] +" in your inventory!");
            }

            //check if food item exists, then eat
            if (usedItem != null) {

                if (usedItem.getType().equals("food")) {

                    _player.eat(usedItem);

                    setResult(command[1] + " used!");
                }
                else if (usedItem.getType().equals("weapon")) {
                //Can update weapon use later with combat system
                    setResult("Pew Pew");
                } else {
                    setResult("No use for that here"); //If not weapon or food type, say "no use here"
                }
            }

        }

        //Set commands for last action taken by user
        setAction(command[0]);
        setNoun(command[1]);
    }

    public void display() throws InterruptedException, IOException {
        clearConsole();
        String currentLoc = GameWorld.getCurrentLocation();
        if (currentLoc == null || currentLoc.equals("")) {
            currentLoc = GameWorld.getPreviousLocation();
        }
        Combat combat = null;
        if (currentLoc.contains("Alien Compound")) {
            combat = new Combat();
        }
        Location currentLocData = GameWorld.getPlanet1().get(currentLoc);

//        gameViewManager.locationText.setText(currentLocData.getName());
//
//        gameViewManager.descriptionText.setText("===================================================\n" +
//                                                "Description: \" +  currentLocData.getDescription()\n" +
//                                                "Items you see: " + GameWorld.getItemsByLocation(currentLoc)+
//                                                "===================================================\n" +
//                                                "" );

        System.out.println("===================================================");
        System.out.println("Location: " + currentLocData.getName());
        System.out.println("===================================================");
        System.out.println("Description: " +  currentLocData.getDescription());
        System.out.println("\n");
        System.out.println("Items you see: " + GameWorld.getItemsByLocation(currentLoc));
        System.out.println("===================================================");
        System.out.println("Name: " + "dan" + " | HP: " + player.getHP() + " / " + player.getMaxHp());
        //Player.getName()
        System.out.println("Current Inventory: " + player.viewInventory());
        System.out.println("---------------------------------------------------");
        System.out.println("Last action taken: " + getAction() + " "+ getNoun());
        System.out.println(getResult()); //Display action results
        setResult(""); //Reset action results for next action

    }

    //method to send info to javafx
    public HashMap<String, String> fxDisplayLocation() throws InterruptedException, IOException {
        HashMap<String, String> fxLocationHMap = new HashMap<>();
        String currentLoc = GameWorld.getCurrentLocation();
        if (currentLoc == null || currentLoc.equals("")) {
            currentLoc = GameWorld.getPreviousLocation();
        }
        Combat combat = null;
        if (currentLoc.contains("Alien Compound")) {
            combat = new Combat();
        }
        Location currentLocData = GameWorld.getPlanet1().get(currentLoc);

        System.out.println("===================================================");
        System.out.println("Location: " + currentLocData.getName());
        System.out.println("===================================================");
        System.out.println("Description: " +  currentLocData.getDescription());
        System.out.println("\n");
        System.out.println("Items you see: " + GameWorld.getItemsByLocation(currentLoc));
        System.out.println("===================================================");
        fxLocationHMap.put("Location", currentLocData.getName());
        fxLocationHMap.put("Description", currentLocData.getDescription());
        fxLocationHMap.put("Items", GameWorld.getItemsByLocation(currentLoc).toString());
        //System.out.println("Last action taken: " + getAction() + " "+ getNoun());
        //System.out.println(getResult()); //Display action results
        //setResult(""); //Reset action results for next action
        return fxLocationHMap;

    }

//    public static HashMap<String, Integer> fxDisplayPlayer() throws InterruptedException, IOException {
//        HashMap<String, Integer> fxPlayerHMap = new HashMap<>();
//        //System.out.println("Name: " + Player.getName() + " | HP: " + Player.getHP() + " / " + Player.getMaxHp());
//        fxPlayerHMap.put("HP", player.getHP());
//        fxPlayerHMap.put("MaxHP", Player.getMaxHp());
//
//        return fxPlayerHMap;
//
//    }

    public static void clearConsole() throws InterruptedException {
        /* Code Attributed to DelftStack
            December 10, 2020
            Use ProcessBuilder to Clear Console in Java
            Example code
            https://www.delftstack.com/howto/java/java-clear-console/
         */

        Thread.sleep(1000);

        try{
            String operatingSystem = System.getProperty("os.name"); //Check the current operating system

            if(operatingSystem.contains("Windows")){
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }



    //private methods
    private String getAction() {
        return action;
    }

    private void setAction(String action) {
        this.action = action;
    }

    private String getResult() {
        return result;
    }

    private void setResult(String result) {
        this.result = ">" + result;
    }

    private String getNoun() {
        return noun;
    }

    private void setNoun(String noun) {
        this.noun = noun;
    }
}
