package com.game.conditions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.enemies.Alien;
import com.game.items.Item;
import com.game.player.Player;
import com.game.startmenu.Status;
import com.game.textparser.UserInput;
import com.game.world.GameWorld;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Combat {

    //Static fields for combat results
    private static String result = "";
    private static String enemyResult = "";

    private String verb = "None";
    private String noun = "";


    //default constructor
    public Combat() throws InterruptedException, IOException {
        Alien soldier = createAlien();
        startCombat(soldier);

    }

    //Combat methods
    private Alien createAlien() throws IOException {
        //Return alien from JSON file.
        byte[] alienData = Files.readAllBytes(Paths.get("src/main/resources/enemies.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        Alien[] alien = objectMapper.readValue(alienData, Alien[].class);

        Alien myAlien = null;

        //Returns the alien based on the "location" field in the enemies.json  Has to match current player location.
        for (Alien enemy:alien) {
            if (enemy.getLocation().equals(GameWorld.getCurrentLocation())) {
                myAlien = enemy;
                break;
            }
        }

        return myAlien;
    }

    private void startCombat(Alien soldier) throws InterruptedException, IOException {
        if (soldier != null ) {

            while (soldier.isAlive() && (Player.getHP() > Player.getMinHp())) {
                //Verify action

                boolean doAction = false;
                while (!doAction) {

                    fightStatus(soldier);
                    String[] action = UserInput.action();

                    //verify a fight command was used somehow...
                    if (action[0].equals("use") || action[0].equals("shoot")) {

                        //Grab me the right weapon or null from inventory
                        Item weapon = null;
                        for (Item item : Player.getInventory()) {
                            if (item.getType().equals("weapon") && item.getItemName().equals(action[1])) {
                                weapon = item;
                            } else {
                                Combat.setResult("That's not a weapon in your inventory!");
                            }
                        }


                        //Combat
                        Player.attack(soldier, weapon);
                        soldier.Attack();
                        soldier.checkAlive(); //Updates Alive Status
                        if (!soldier.isAlive()) {
                            winCombat();
                        }

                        //Results
                        setVerb(action[0]);
                        setNoun(action[1]);
                        doAction = true;
                    } else {
                        Combat.setResult("You can't do that right now, you're in combat!");
                    }

                }


            }
        }
    }


    private void fightStatus(Alien soldier) throws InterruptedException {
        Status.clearConsole();

        System.out.println("**********ALERT, ALIEN IS ATTACKING YOU*************");
        System.out.println("===================================================");
        System.out.println("Enemy: " + soldier.getType() + " HP: " + soldier.getHp());
        System.out.println("===================================================");
        System.out.println("Name: " + Player.getName() + " | HP: " + Player.getHP() + " / " + Player.getMaxHp());
        System.out.println("Weapons available: fists " + Player.viewInventory("weapon"));
        System.out.println("---------------------------------------------------");
        System.out.println("Last action taken: " + getVerb() + " "+ getNoun());
        System.out.println(">" + getResult());
        System.out.println(">" + getEnemyResult());
        Combat.setResult("");
        Combat.setEnemyResult("");

    }

    private void winCombat() throws InterruptedException{
        Status.clearConsole();

        System.out.println("        _..._" +
                "      .'     '.      _\n" +
                "     /    .-\"\"-\\   _/ \\\n" +
                "   .-|   /:.   |  |   |\n" +
                "   |  \\  |:.   /.-'-./\n" +
                "   | .-'-;:__.'    =/\n" +
                "   .'=  *=|    _.='\n"+
                "  /   _.  |    ;\n"+
                " ;-.-'|    \\   |\n"+
                "/   | \\    _\\  _\\\n"+
                "\\__/'._;.  ==' ==\\\n"+
                "         \\    \\   |\n"+
                "         /    /   /\n"+
                "         /-._/-._/\n"+
                "         \\   `\\  \\\n"+
                "          `-._/._/\n" +
                "COMBAT CLEARED"
        );
        Thread.sleep(2000);
        Status.clearConsole();
    }

    //Getters & Setters

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getNoun() {
        return noun;
    }

    public void setNoun(String noun) {
        this.noun = noun;
    }
    public static void setResult(String results) {
        Combat.result = results;
    }

    public String getResult() {
        return result;
    }

    public static String getEnemyResult() {
        return enemyResult;
    }

    public static void setEnemyResult(String enemyResult) {
        Combat.enemyResult = enemyResult;
    }
}
