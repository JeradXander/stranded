package com.game.player;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.conditions.Combat;
import com.game.enemies.Alien;
import com.game.items.Item;
import com.game.textparser.UserInput;
import com.game.world.GameWorld;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Player {

    //Static Fields for player
    private static String name;
    private static int HP;
    private static int defense;
    private static int attack;
    private static ArrayList<Item> inventory = new ArrayList<Item>();
    private static int movePenalty = -10;
    public static String astronautClass = "Medic";
    public static boolean firstCombat = true;


    //Constant Fields
    private static final int MAX_HP = 100;
    private static final int MIN_HP = 0;

    //Player Constructor
    public Player(String _name, String _astronautClass){
        setName(_name);
        setHP(MAX_HP);
//        setHP(85); //for testing
        setDefense(1);
        setAstronautClass(_astronautClass);
    }



    public String getAstronautClass() {
        return astronautClass;
    }

    public  void setAstronautClass(String astronautClass) {
        Player.astronautClass = astronautClass;
    }

    //Inventory methods will go below
    public  void addItem(Item item){
        inventory.add(item);
    }

    public  ArrayList<Item> getInventory() {
        return inventory;
    }

    public  StringBuilder viewInventory(){
        StringBuilder inventoryString = new StringBuilder();

        for(Item item: inventory){
            inventoryString.append(item.getItemName()).append(" ");
        }
        return inventoryString;
    }

    public  StringBuilder viewInventory(String type) {
        StringBuilder inventoryString = new StringBuilder();

        for(Item item: inventory){
            if (item.getType().equals(type)) {
                inventoryString.append(item.getItemName()).append(" ");
            }

        }
        return inventoryString;

    }

    public  int keyItemCheck(){
        int keyItemsInInventory = 0;
        for(Item item: inventory){
            if(item.isKeyItem() == true){
                keyItemsInInventory += 1;
            }
        }
        return keyItemsInInventory;
    }

    public void clearInventory() {
        inventory = new ArrayList<Item>();
    }

    public  void eat(Item foodItem) {
        //add HP value from food type item and remove from inventory
        setHP(foodItem.getHpValue());
        inventory.remove(foodItem);
    }

    // Player manipulation methods
    public void move(String nextLoc) {
        //reduces HP for player movement & updates location

        setMovePenalty();  //readjust move penalty before moving

        setHP(getMovePenalty());
        GameWorld.setCurrentLocation(nextLoc);
    }

    //Fight Methods
    public void attack(Alien alien, Item weapon) throws IOException, InterruptedException {
        //Attack alien method!

        byte[] mapData = Files.readAllBytes(Paths.get("resources/synonyms.json"));
        Map<String, ArrayList<String>> synonymMap = new HashMap<String, ArrayList<String>>();

        ObjectMapper objectMapper = new ObjectMapper();
        synonymMap = objectMapper.readValue(mapData, HashMap.class);
        String[] synArray = synonymMap.get("combat").toArray(new String[0]);
        int synonymLength = synArray.length;
        Random synRand = new Random();
        int synSelector = synRand.nextInt(synonymLength);


        Random rand = new Random();
        int randDamage = 0;
        int atkPower = 0;

        //check for Soldier Class
        if(astronautClass.equals("Soldier") && firstCombat) {
            System.out.println("You remember your training as a Soldier...");
            Thread.sleep(1000);
            firstCombat = false;
        }


            if(astronautClass.equals("Soldier")) {
            atkPower += 10;


            if (weapon == null) {
                atkPower += 2; //Hand combat power
                randDamage = rand.nextInt(atkPower) + 1;

                alien.takeDamage(randDamage);

                Combat.setResult("Used your fists!" + " " + synArray[synSelector] + " attack!");

            } else {

                if (weapon.getType().equals("weapon")) {
                    atkPower += weapon.getHpValue();

                    //Randomly generate damage amount greater than at least half the attack power
                    while (randDamage < (atkPower / 2)) {
                        randDamage = rand.nextInt(atkPower) + 1;
                    }

                    //Combat.setResult("You used your " + weapon.getItemName() + "!");
                    alien.takeDamage(randDamage);
                }
            }
        }else{
                if (weapon == null) {
                    atkPower += 2; //Hand combat power
                    randDamage = rand.nextInt(atkPower) + 1;

                    alien.takeDamage(randDamage);

                    Combat.setResult("Used your fists!" + " " + synArray[synSelector] + " attack!");

                } else {

                    if (weapon.getType().equals("weapon")) {
                        atkPower += weapon.getHpValue();

                        //Randomly generate damage amount greater than at least half the attack power
                        while (randDamage < (atkPower / 2)) {
                            randDamage = rand.nextInt(atkPower) + 1;
                        }

                        //Combat.setResult("You used your " + weapon.getItemName() + "!");
                        alien.takeDamage(randDamage);
                    }
                }
            }
    }

    public void takeDamage(int AttackStr) {
        int totalDamage = AttackStr/defense;

        setHP(-totalDamage);
    }

    //Getters & Setters
    public String getName(){
        return name;
    }

    public void setName(String _name){
        name = _name;
        System.out.println("Welcome aboard Commander " + name + "!");
    }

    public  int getHP() {
        return HP;
    }
    // HP Getters & Setters
    public void setHP(int HP) {
        // If HP value is negative and takes HP below 0, just set to MIN_HP
        if (Player.HP + HP < MIN_HP) {
            Player.HP = MIN_HP;
        }
        // If HP value is positive and takes HP above 100, set to MAX_HP
        else if (Player.HP + HP > MAX_HP){
            Player.HP = MAX_HP;
        } else {
        // Else just add/subtract to Player HP.
            Player.HP += HP;
        }

    }


    public int getMaxHp() {
        return MAX_HP;
    }

    public int getMinHp() {
        return MIN_HP;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        Player.defense = defense;
    }

    //Private getters & setters
    private int getMovePenalty() {
        return movePenalty;
    }

    private void setMovePenalty() {
        //Set based on current player HP
        if (getHP() < 50 && astronautClass.equals("Explorer") ) {
            movePenalty = -2;
        }else if(getHP() < 50){
            movePenalty = -5;
        }else if(astronautClass.equals("Explorer")){
            movePenalty = -5;
        }else {
            movePenalty = -10;
        }
    }






}
