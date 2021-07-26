package com.game;

import com.game.conditions.Lose;
import com.game.conditions.Travel;
import com.game.conditions.Win;
import com.game.player.Player;
import com.game.startmenu.StartMenu;
import com.game.startmenu.Status;
import com.game.textparser.UserInput;
import com.game.world.GameWorld;
import com.game.world.Location;

import java.io.IOException;
import java.util.HashMap;

public class Main {
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
                player = new Player();
                Run = true;

            }
            while (Run) {
                boolean move = false;

                while (!move) {

                    if (GameWorld.getCurrentLocation() != null){

                        if(Player.keyItemCheck() == 2 && GameWorld.getCurrentLocation().equals("Crash Site")){
                            Travel.goToAnotherPlanet();
                            Travel.lowFuelWarning();
                            GameWorld.setCurrentLocation("Landing Site");
                        }

                        if(Player.getHP() == 0){
                            Lose.youLose();
                            if(UserInput.playAgain() == true){
                                Run = false;
                                Player.clearInventory();
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

                        if (Player.keyItemCheck() == 3 && GameWorld.getCurrentLocation().equals("Landing Site")) {
                            Win.youWin();
                            Run = false;
                            isRunning = false;
                            break;
                        }
                    }


                    status.display();
                    String[] action = UserInput.action();// ==> [go,west]
                    //send action to a class to update location/inventory/status etc.
                    status.action(action);
                    move = true;
                }

            }

        }
    }
}
