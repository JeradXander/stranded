package sample;

import com.game.player.Player;
import com.game.startmenu.Status;
import com.game.world.GameWorld;
import com.game.world.Location;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import javafx.event.ActionEvent;
import com.game.Main;

//import static com.game.startmenu.Status.fxDisplayPlayer;
import static com.game.world.GameWorld.currentLocation;


public class Controller {

    private Stage stage;
    private Scene scene;
    private Parent root;
    public TextField playerName;
    @FXML
    public Label displayAstroName;
    public ToggleGroup astroGroup;
    @FXML
    public Label fxCurrentLocation;
    @FXML
    public Label fxDescription;
    @FXML
    Button sendCommand;
    @FXML
    public Label hp;
    @FXML
    public Label maxHP;


    GameWorld ourGame = new GameWorld();
    HashMap<String, Location> planet1 = ourGame.getPlanet1();

    public Controller() throws IOException {
    }




    public void switchToStartGame(ActionEvent event) throws IOException, InterruptedException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("./playScreen.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(currentLocation);

        Player  playa = submitName(event);
        scene = new Scene(root);
        stage.setScene(scene);

        displayAstroName.setText("dffsagsdfgsdfg");

        stage.showAndWait();
        //Label topCurrentLocation = new Label();
        //topCurrentLocation.setText(currentLocation);
        //System.out.println(topCurrentLocation);
        //stage.showAndWait();
        //fxCurrentLocation.setText(currentLocation);
        //System.out.println(fxCurrentLocation.toString());
       // startGameToFX();
        runningGame(stage, playa, ourGame);
    }

    public void startGameToFX() throws IOException, InterruptedException {
        Main.gameToFX();
    }



    public void setSendCommand() throws IOException, InterruptedException {
        fxCurrentLocation.setText(currentLocation);
        System.out.println(planet1.get("description"));
        HashMap<String, String> fxCurrLocation = Status.fxDisplayLocation();
        fxDescription.setText(fxCurrLocation.get("Description"));
//        HashMap<String, Integer> fxPlayerHP = fxDisplayPlayer();

    }
    private void runningGame(Stage stage ,Player player, GameWorld gameWorld){





        displayAstroName.setText("dsbwehbwhbrttnhrwehntrewhntertnhrewthnerhnterhntren");

    }



//    public void goBackToMainMenu(ActionEvent event){
//
//    }
//    public void runMain(){
//        Main startMain = new Main();
//        startMain();
//    }
    public Player  submitName(ActionEvent event){
        String name = playerName.getText();
        System.out.println("Player name is " + name);

        RadioButton radioButton = (RadioButton) astroGroup.getSelectedToggle();
        String astroGroup = radioButton.getText();
        System.out.println(astroGroup);

        Player pley = new Player(name,astroGroup);

        return pley;
//        displayAstroName.setText(name);
    }

    //Method that creates a new stage/scene that displays the travel ascii

    public void checkRadioButtons(){

    }
}
