package com.game;

import com.game.conditions.Travel;
import com.game.conditions.Win;
import com.game.items.Item;
import com.game.player.Player;
import com.game.startmenu.StartMenu;
import com.game.startmenu.Status;
import com.game.world.GameWorld;
import com.game.world.Location;
import com.game.conditions.Lose;
import com.game.textparser.UserInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {
    Player player = new Player("david", "Medic");

    public static void main(String[] args) throws IOException, InterruptedException {
        boolean isRunning = true;
        StartMenu start = new StartMenu();
        Status status = new Status();



        boolean Run = true;
        Player player = null;

        while (isRunning){

            while (player == null) {
                GameWorld ourGame = new GameWorld();
                HashMap<String, Location> planet1 = ourGame.getPlanet1();
                player = new Player("dan", "Soldier");
                if (player.getAstronautClass().equals("Medic")){
                    //Player.addItem(Item med-pack);
                    Location medpacks = planet1.get("Starting Items");
                    player.move("Starting Items");
                    //status.action(new String[] {"grab", "med-pack"});

                    System.out.println("As the medic you start out with five med-packs!");
                    player.move("Crash Site");
                    player.setHP(100);
                }
                Run = true;

            }
            while (Run) {
                boolean move = false;

                while (!move) {

                    if (GameWorld.getCurrentLocation() != null){

                        if(player.keyItemCheck() == 2 && GameWorld.getCurrentLocation().equals("Crash Site")){
                            Travel.goToAnotherPlanet();
                            Travel.lowFuelWarning();
                            GameWorld.setCurrentLocation("Landing Site");
                        }

                        if(player.getHP() == 0){
                            Lose.youLose();
                            if(UserInput.playAgain() == true){
                                Run = false;
                                player.clearInventory();
                                GameWorld.setCurrentLocation("Crash Site");
                                status = new Status();
                                player = null;
                                break;
                            } else {
                                Run = false;
                                isRunning = false;
                                break;
                            }

                        }

                        if (player.keyItemCheck() == 3 && GameWorld.getCurrentLocation().equals("Landing Site")) {
                            Win.youWin();
                            Run = false;
                            isRunning = false;
                            break;
                        }
                    }


                   // status.display();
//                    String[] action = UserInput.action();// ==> [go,west]
                    //send action to a class to update location/inventory/status etc.
//                    status.action(action);
                    move = true;
                }

            }

        }
    }
    public static void gameToFX() throws IOException, InterruptedException {
        boolean isRunning = true;
        System.out.println("gameToFX is running: " + isRunning);
        StartMenu start = new StartMenu();
        Status status = new Status();



        boolean Run = true;
        Player player = null;

        while (isRunning){

            while (player == null) {
                GameWorld ourGame = new GameWorld();
                HashMap<String, Location> planet1 = ourGame.getPlanet1();
                player = new Player("Damian", "explorer");
                Run = true;

            }
            while (Run) {
                boolean move = false;

                while (!move) {

                    if (GameWorld.getCurrentLocation() != null){

                        if(player.keyItemCheck() == 2 && GameWorld.getCurrentLocation().equals("Crash Site")){
                            Travel.goToAnotherPlanet();
                            Travel.lowFuelWarning();
                            GameWorld.setCurrentLocation("Landing Site");
                        }

                        if(player.getHP() == 0){
                            Lose.youLose();
                            if(UserInput.playAgain() == true){
                                Run = false;
                                player.clearInventory();
                                GameWorld.setCurrentLocation("Crash Site");
                                status = new Status();
                                player = null;
                                break;
                            } else {
                                Run = false;
                                isRunning = false;
                                break;
                            }

                        }

                        if (player.keyItemCheck() == 3 && GameWorld.getCurrentLocation().equals("Landing Site")) {
                            Win.youWin();
                            Run = false;
                            isRunning = false;
                            break;
                        }
                    }


                   // status.display();
//                    String[] action = UserInput.action();// ==> [go,west]
                    //send action to a class to update location/inventory/status etc.
//                    status.action(action);
                    move = true;
                }

            }

        }
    }

}
