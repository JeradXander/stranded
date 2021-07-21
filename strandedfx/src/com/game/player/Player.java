package com.game.player;

import com.game.conditions.Combat;
import com.game.enemies.Alien;
import com.game.items.Item;
import com.game.textparser.UserInput;
import com.game.world.GameWorld;
import java.util.ArrayList;
import java.util.Random;

public class Player {

    //Static Fields for player
    private static String name;
    private static int HP;
    private static int defense;
    private static ArrayList<Item> inventory = new ArrayList<Item>();
    private static int movePenalty = -10;
    private static String astronautClass = "medic";


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

    public static String getAstronautClass() {
        return astronautClass;
    }

    public static void setAstronautClass(String astronautClass) {
        Player.astronautClass = astronautClass;
    }

    //Inventory methods will go below
    public static void addItem(Item item){
        inventory.add(item);
    }

    public static ArrayList<Item> getInventory() {
        return inventory;
    }

    public static StringBuilder viewInventory(){
        StringBuilder inventoryString = new StringBuilder();

        for(Item item: inventory){
            inventoryString.append(item.getItemName()).append(" ");
        }
        return inventoryString;
    }

    public static StringBuilder viewInventory(String type) {
        StringBuilder inventoryString = new StringBuilder();

        for(Item item: inventory){
            if (item.getType().equals(type)) {
                inventoryString.append(item.getItemName()).append(" ");
            }

        }
        return inventoryString;

    }

    public static int keyItemCheck(){
        int keyItemsInInventory = 0;
        for(Item item: inventory){
            if(item.isKeyItem() == true){
                keyItemsInInventory += 1;
            }
        }
        return keyItemsInInventory;
    }

    public static void clearInventory() {
        inventory = new ArrayList<Item>();
    }

    public static void eat(Item foodItem) {
        //add HP value from food type item and remove from inventory
        setHP(foodItem.getHpValue());
        inventory.remove(foodItem);
    }

    // Player manipulation methods
    public static void move(String nextLoc) {
        //reduces HP for player movement & updates location
        setMovePenalty();  //readjust move penalty before moving

        setHP(getMovePenalty());
        GameWorld.setCurrentLocation(nextLoc);
    }

    //Fight Methods
    public static void attack(Alien alien, Item weapon) {
        //Attack alien method!
        Random rand = new Random();
        int randDamage = 0;
        int atkPower;

        if (weapon == null ) {
            atkPower = 2; //Hand combat power
            randDamage = rand.nextInt(atkPower)+1;

            alien.takeDamage(randDamage);
            Combat.setResult("Used your fists!");
        }
        else {

            if (weapon.getType().equals("weapon")) {
                atkPower = weapon.getHpValue();

                //Randomly generate damage amount greater than at least half the attack power
                while (randDamage < (atkPower/2)) {
                    randDamage = rand.nextInt(atkPower)+1;
                }

                //Combat.setResult("You used your " + weapon.getItemName() + "!");
                alien.takeDamage(randDamage);
            }
        }
    }

    public static void takeDamage(int AttackStr) {
        int totalDamage = AttackStr/defense;

        Player.setHP(-totalDamage);
    }

    //Getters & Setters
    public String getName(){
        return name;
    }

    public void setName(String _name){
        name = _name;
        System.out.println("Welcome aboard Commander " + name + "!");
    }

    public static int getHP() {
        return HP;
    }
    // HP Getters & Setters
    public static void setHP(int HP) {
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


    public static int getMaxHp() {
        return MAX_HP;
    }

    public static int getMinHp() {
        return MIN_HP;
    }

    public static int getDefense() {
        return defense;
    }

    public static void setDefense(int defense) {
        Player.defense = defense;
    }

    //Private getters & setters
    private static int getMovePenalty() {
        return movePenalty;
    }

    private static void setMovePenalty() {
        //Set based on current player HP
        if (getHP() < 50 ) {
            movePenalty = -5;
        }
        else {
            movePenalty = -10;
        }
    }






}
