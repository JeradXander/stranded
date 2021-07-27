package sample.views;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.enemies.Alien;
import com.game.player.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.game.startmenu.Status;
import com.game.textparser.Directions;
import com.game.world.GameWorld;
import com.game.world.Location;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.MenuMain;
import sample.models.*;

import static com.game.world.GameWorld.currentLocation;


public class GameViewManager {

    private static final int HEIGHT = 775;
    private static final int WIDTH = 1050;
    private static final int MENU_BUTTONS_START_X = 100;
    private static final int MENU_BUTTONS_START_Y = 225;
    BackgroundImage background;
    private SmallHelpSubScene gameHelpSubscene;



    private ArrayList<StrandedButton> buttonList;
    private ArrayList<AstroPicker> astroList;
    private ASTRO chosenAstro;

    private StrandedSubScene sceneThatNeedsToSlide;
    private StrandedSubScene creditSubscene;
    private StrandedSubScene helpSubscene;
    private StrandedSubScene scoreSubscene;
    private StrandedSubScene playSubscene;
    private StrandedSubScene astroChooserScene;
    private SmallStrandedSubScene mapSubscene;
    private GameStrandedSubScene displayTextSubScene;

    private TextField textField;
    private StrandedButton submitButton;

    private ImageView map;


    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    private GameWorld ourGame;
    private HashMap<String, Location> planet1;

    public Label locationText;
    public Label descriptionText;
    private Label displayInventory;
    private Label displayName;
    private Label lastCommand;
    private Label displayPlayerHealth;
    private Label combatText;

//    User input variables
    private String moveDirection = "nowhere";
    private String itemGrabbed = "nothing";
    private String useItemGrabbed = "nothing";
    private String locationToSearch = "here";
    private String itemDropped = "none";
    private Player playerCreated;
    private Alien alienSoldier;
    private Status status;

// Constructor
    public GameViewManager(Player _playerCreated) throws IOException, InterruptedException {
         ourGame = new GameWorld();
         planet1 = ourGame.getPlanet1();
         status = new Status();

         playerCreated =  _playerCreated;

        if (playerCreated.getAstronautClass().equals("Medic")){
            //Player.addItem(Item med-pack);
            Location medpacks = planet1.get("Starting Items");
            playerCreated.move("Starting Items");
            status.action(new String[] {"grab", "med-pack"}, playerCreated, this);

            System.out.println("As the medic you start out with five med-packs!");
            playerCreated.move("Crash Site");
            playerCreated.setHP(100);
        }

        buttonList = new ArrayList<>();
        //creating main window to hold all children
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT, Color.RED);
        mainStage = new Stage();

        //setting scene
        mainStage.setScene(mainScene);

        mainStage.setResizable(false);

        mainStage.setTitle("Name: " + playerCreated.getName() + "  | Astronaut Class: " + playerCreated.getAstronautClass() + "  | Current Location: " + currentLocation);

        createSubscenes();

        //creating buttons
        createButton();
//
//        //creating Background from method
        createBackGround();
//
        creatMapButton();

        createSubmitTextButton();
//
        createSlider();
        createTextScene();
        createTextField();

        createHelpButton();
        MenuMain.fxmediaPlayer.play();

    }

    //method to get main stage
    public  Stage getMainStage(){
        return mainStage;
    }

    private void createSubscenes(){
        creditSubscene = new StrandedSubScene();
        mainPane.getChildren().add(creditSubscene);

        astroChooserScene = new StrandedSubScene();
        mainPane.getChildren().add(astroChooserScene);

        mapSubscene = new SmallStrandedSubScene();
        mainPane.getChildren().add(mapSubscene);

        gameHelpSubscene = new SmallHelpSubScene();
        mainPane.getChildren().add(gameHelpSubscene);
    }

    private void createSlider(){
        Slider volumeControl = new Slider(0, 100, 5);

        mainPane.getChildren().add(volumeControl);
        volumeControl.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number t1) {
                MenuMain.fxmediaPlayer.setVolume(volumeControl.getValue() * 0.01);
                MenuMain.laserMediaPlayer.setVolume(volumeControl.getValue() * 0.01);
                MenuMain.clickMediaPlayer.setVolume(volumeControl.getValue() * 0.01);
                MenuMain.launchMediaPlayer.setVolume(volumeControl.getValue() * 0.01);
                System.out.println("volume" + volumeControl.getValue());
            }
        });
    }

    private void createTextField(){

        textField = new TextField();
        textField.setLayoutX(700);
        textField.setLayoutY(675);
        textField.setPrefSize(250, 40);
        mainPane.getChildren().add(textField);
//        textField.setOnInputMethodTextChanged(new EventHandler<InputMethodEvent>() {
//            @Override
//            public void handle(InputMethodEvent inputMethodEvent) {
//                MenuMain.laserMediaPlayer.play();
//            }
//        });
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                MenuMain.laserMediaPlayer.stop();
                MenuMain.laserMediaPlayer.play();
            }
        });

        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode().equals(KeyCode.ENTER))
                {
                    try {
                        startParsing(textField.getText());
                        lastCommand.setText("Last Command: " + textField.getText());
                        textField.clear();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("success!");

                }
            }
        });

    }

    private void startParsing(String input) throws IOException, InterruptedException {
        System.out.println(input);
        input.toLowerCase();
        String [] actionArray = action(input);

        System.out.println(playerCreated.getName() + playerCreated.getAstronautClass());

        status.action(actionArray, playerCreated,this);

        HashMap<String,String> fxStatus = status.fxDisplayLocation();

        if (fxStatus.get("Location").contains("Alien Compound")){
            if(alienSoldier != null && alienSoldier.isAlive()){
                startCombat(alienSoldier);
                System.out.println("in combat");
                setMapImage(fxStatus.get("Location"));
            }else{
                alienSoldier = createAlien();
                startCombat(alienSoldier);
                System.out.println("in combat");
                setMapImage(fxStatus.get("Location"));
            }
        }else{
            combatText.setText("");
        }

        locationText.setText(fxStatus.get("Location"));

        setMapImage(fxStatus.get("Location"));

        descriptionText.setText(fxStatus.get("Description") + "\nItems you see: "+ fxStatus.get("Items"));
        //locationText.setText(fxStatus.get("Location"));

        //display new health
        displayPlayerHealth.setText(String.valueOf(playerCreated.getHP())+ "/100");
        displayInventory.setText("Inventory:  " + playerCreated.viewInventory());


        //check for take ooff
        if(playerCreated.keyItemCheck() == 2 && GameWorld.getCurrentLocation().equals("Crash Site")){

            MenuMain.launchMediaPlayer.play();
            background = new BackgroundImage(new Image("sample/models/resources/shuttle.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                             BackgroundPosition.DEFAULT,null);
            mainPane.setBackground(new Background(background));
            descriptionText.setText("\"Hmmmm....I think I have enough supplies to fix the craft and now\n" +
                                    "Fixing spacecraft...\n" +
                                    "Take OFF\n" +
                                    "Landed");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(7), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {


                    GameWorld.setCurrentLocation("Landing Site");
                    Location tempLocation = GameWorld.getPlanet1().get("Landing Site");
                    setMapImage(GameWorld.getCurrentLocation());
                    locationText.setText(GameWorld.getCurrentLocation());
                    descriptionText.setText("Description " + tempLocation.getDescription() + "\n"+ fxStatus.get("Items"));
                    playerCreated.setHP(100);
                    displayPlayerHealth.setText(String.valueOf(playerCreated.getHP())+ "/100");
                }
            }));
            timeline.play();

        }

        if(playerCreated.getHP() == 0){
           // Lose.youLose();

            descriptionText.setFont(Font.font("Verdana", 30));
            descriptionText.setText( "PITIFUL, YOUR WILL TO LIVE IS\n\n DEAD NOW, YOU ARE\n\n" +
                                     "OVER GAME IS ");

            displayName.setText("");
            displayPlayerHealth.setText("");
            locationText.setText("");
            displayInventory.setText("");
            lastCommand.setText("");
            combatText.setText("");


            GameWorld.setCurrentLocation("Crash Site");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                  mainStage.close();
                }
            }));
            timeline.play();

        }

        if (playerCreated.keyItemCheck() == 3 && GameWorld.getCurrentLocation().equals("Landing Site")) {
            // Lose.youLose();

            descriptionText.setFont(Font.font("Verdana", 30));
            descriptionText.setText( "AMAZING, YOU ARE\n\n DEAD NOW, YOU ARE NOT\n\n" +
                                     "GAME IS WON ");

            displayName.setText("");
            displayPlayerHealth.setText("");
            locationText.setText("");
            displayInventory.setText("");
            lastCommand.setText("");
            combatText.setText("");


            GameWorld.setCurrentLocation("Crash Site");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    Platform.exit();
                    System.exit(0);
                }
            }));
            timeline.play();
        }
    }

    private void setMapImage(String location) {
        mapSubscene.getAnchorPane().getChildren().remove(map);
        switch (location){

            case "Crash Site":
                map.setImage(new Image("sample/models/resources/maps/crashSite.png"));
                background = new BackgroundImage(new Image("crashSite.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                                 BackgroundPosition.DEFAULT, null);
                map.setFitWidth(250);
                map.setPreserveRatio(true);
                map.setLayoutX(25);
                map.setLayoutY(25);
                mapSubscene.getAnchorPane().getChildren().add(map);
                mainPane.setBackground(new Background(background));
                break;
            case "Frozen Tundra":
                background = new BackgroundImage(new Image("sample/models/resources/backs/frozenTundra.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                                 BackgroundPosition.DEFAULT, null);
                map.setImage(new Image("sample/models/resources/maps/frozenTundra.png"));
                map.setFitWidth(250);
                map.setPreserveRatio(true);
                map.setLayoutX(25);
                map.setLayoutY(25);
                mapSubscene.getAnchorPane().getChildren().add(map);
                mainPane.setBackground(new Background(background));
                break;
            case "Jungle":
                background = new BackgroundImage(new Image("sample/models/resources/backs/jungle.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                                 BackgroundPosition.DEFAULT, null);
                map.setImage(new Image("sample/models/resources/maps/jungle.png"));
                map.setFitWidth(250);
                map.setPreserveRatio(true);
                map.setLayoutX(25);
                map.setLayoutY(25);
                mapSubscene.getAnchorPane().getChildren().add(map);
                mainPane.setBackground(new Background(background));
                break;
            case "Beach":
                background = new BackgroundImage(new Image("sample/models/resources/backs/beach.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                                 BackgroundPosition.DEFAULT,null);
                map.setImage(new Image("sample/models/resources/maps/beach.png"));
                map.setFitWidth(250);
                map.setPreserveRatio(true);
                map.setLayoutX(25);
                map.setLayoutY(25);
                mapSubscene.getAnchorPane().getChildren().add(map);
                mainPane.setBackground(new Background(background));
                break;
            case "Creek":
                background = new BackgroundImage(new Image("sample/models/resources/backs/creek.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                                 BackgroundPosition.DEFAULT,null);
                map.setImage(new Image("sample/models/resources/maps/creek.png"));
                map.setFitWidth(250);
                map.setPreserveRatio(true);
                map.setLayoutX(25);
                map.setLayoutY(25);
                mapSubscene.getAnchorPane().getChildren().add(map);
                mainPane.setBackground(new Background(background));
                break;
            case "Mountains":
                background = new BackgroundImage(new Image("sample/models/resources/backs/mountains.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                                 BackgroundPosition.DEFAULT,null);
                map.setImage(new Image("sample/models/resources/maps/mountains.png"));
                map.setFitWidth(250);
                map.setPreserveRatio(true);
                map.setLayoutX(25);
                map.setLayoutY(25);
                mapSubscene.getAnchorPane().getChildren().add(map);
                mainPane.setBackground(new Background(background));
                break;

            case "Far East Crater":
                background = new BackgroundImage(new Image("sample/models/resources/backs/farEeastCrater.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                                 BackgroundPosition.DEFAULT,null);
                map.setImage(new Image("sample/models/resources/maps/farEastCrater.png"));
                map.setFitWidth(250);
                map.setPreserveRatio(true);
                map.setLayoutX(25);
                map.setLayoutY(25);
                mapSubscene.getAnchorPane().getChildren().add(map);
                mainPane.setBackground(new Background(background));
                break;

            case "Alien Compound":
                background = new BackgroundImage(new Image("sample/models/resources/backs/alienCompound.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                                 BackgroundPosition.DEFAULT, null);
                map.setImage(new Image("sample/models/resources/maps/alienCompound.png"));
                map.setFitWidth(250);
                map.setPreserveRatio(true);
                map.setLayoutX(25);
                map.setLayoutY(25);
                mapSubscene.getAnchorPane().getChildren().add(map);
                mainPane.setBackground(new Background(background));
                break;

            case "Abandoned Storage Facility":
                background = new BackgroundImage(new Image("sample/models/resources/backs/abandonedStorage.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                                 BackgroundPosition.DEFAULT, null);
                map.setImage(new Image("sample/models/resources/maps/abandonedStorageFacility.png"));
                map.setFitWidth(250);
                map.setPreserveRatio(true);
                map.setLayoutX(25);
                map.setLayoutY(25);
                mapSubscene.getAnchorPane().getChildren().add(map);
                mainPane.setBackground(new Background(background));
                break;
            case "Abandoned Ship":
                background = new BackgroundImage(new Image("sample/models/resources/backs/abandonedShip.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                                 BackgroundPosition.DEFAULT, null);
                map.setImage(new Image("sample/models/resources/maps/abandonedShip.png"));
                map.setFitWidth(250);
                map.setPreserveRatio(true);
                map.setLayoutX(25);
                map.setLayoutY(25);
                mapSubscene.getAnchorPane().getChildren().add(map);
                mainPane.setBackground(new Background(background));
                break;
            case "Lava Tubes":
                background = new BackgroundImage(new Image("sample/models/resources/backs/lavaTubes.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                                 BackgroundPosition.DEFAULT, null);
                map.setImage(new Image("sample/models/resources/maps/lavaTubes.png"));
                map.setFitWidth(250);
                map.setPreserveRatio(true);
                map.setLayoutX(25);
                map.setLayoutY(25);
                mapSubscene.getAnchorPane().getChildren().add(map);
                mainPane.setBackground(new Background(background));
                break;
            case "South Crater":
                background = new BackgroundImage(new Image("sample/models/resources/backs/southCrater.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                                 BackgroundPosition.DEFAULT, null);
                map.setImage(new Image("sample/models/resources/maps/southCrater.png"));
                map.setFitWidth(250);
                map.setPreserveRatio(true);
                map.setLayoutX(25);
                map.setLayoutY(25);
                mapSubscene.getAnchorPane().getChildren().add(map);
                mainPane.setBackground(new Background(background));
                break;
            case "Crater":
                background = new BackgroundImage(new Image("sample/models/resources/backs/crater.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                                 BackgroundPosition.DEFAULT, null);
                map.setImage(new Image("sample/models/resources/maps/crater.png"));
                map.setFitWidth(250);
                map.setPreserveRatio(true);
                map.setLayoutX(25);
                map.setLayoutY(25);
                mapSubscene.getAnchorPane().getChildren().add(map);
                mainPane.setBackground(new Background(background));
                break;
            case "Landing Site":
                background = new BackgroundImage(new Image("sample/models/resources/backs/landingSite.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                                 BackgroundPosition.DEFAULT,null);
                map.setImage(new Image("sample/models/resources/maps/landingSite.png"));
                map.setFitWidth(250);
                map.setPreserveRatio(true);
                map.setLayoutX(25);
                map.setLayoutY(25);
                mapSubscene.getAnchorPane().getChildren().add(map);
                mainPane.setBackground(new Background(background));
                break;
            case "Alien Compound 2":
                background = new BackgroundImage(new Image("sample/models/resources/backs/alienCompound2.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                                 BackgroundPosition.DEFAULT,null);
                map.setImage(new Image("sample/models/resources/maps/alienCompoundPlanet2.png"));
                map.setFitWidth(250);
                map.setPreserveRatio(true);
                map.setLayoutX(25);
                map.setLayoutY(25);
                mapSubscene.getAnchorPane().getChildren().add(map);
                mainPane.setBackground(new Background(background));
                break;
            case "Lava Tube":
                background = new BackgroundImage(new Image("sample/models/resources/backs/lavaTube.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                                 BackgroundPosition.DEFAULT,null);
                map.setImage(new Image("sample/models/resources/maps/lavaTube.png"));
                map.setFitWidth(250);
                map.setPreserveRatio(true);
                map.setLayoutX(25);
                map.setLayoutY(25);
                mapSubscene.getAnchorPane().getChildren().add(map);
                mainPane.setBackground(new Background(background));
                break;
            case "Rock Shrine":
                background = new BackgroundImage(new Image("sample/models/resources/backs/rockShrine.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                                 BackgroundPosition.DEFAULT,null);
                map.setImage(new Image("sample/models/resources/maps/rockShrine.png"));
                map.setFitWidth(250);
                map.setPreserveRatio(true);
                map.setLayoutX(25);
                map.setLayoutY(25);
                mapSubscene.getAnchorPane().getChildren().add(map);
                mainPane.setBackground(new Background(background));
                break;
            case "Dead Volcano":
                background = new BackgroundImage(new Image("sample/models/resources/backs/deadVolcano.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                                 BackgroundPosition.DEFAULT, null);
                map.setImage(new Image("sample/models/resources/maps/deadVolcano.png"));
                map.setFitWidth(250);
                map.setPreserveRatio(true);
                map.setLayoutX(25);
                map.setLayoutY(25);
                mapSubscene.getAnchorPane().getChildren().add(map);
                mainPane.setBackground(new Background(background));
                break;
            case "Fuel Outpost":
                background = new BackgroundImage(new Image("sample/models/resources/backs/fuelOutpost.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                                                 BackgroundPosition.DEFAULT,null);
                map.setImage(new Image("sample/models/resources/maps/fuelOutpost.png"));
                map.setFitWidth(250);
                map.setPreserveRatio(true);
                map.setLayoutX(25);
                map.setLayoutY(25);
                mapSubscene.getAnchorPane().getChildren().add(map);
                mainPane.setBackground(new Background(background));
                break;
        }

    }

    public String[] action(String playerControlString) throws IOException {
        /*Takes user input and it processes what type of action you are trying to take */
        byte[] mapData = Files.readAllBytes(Paths.get("src/resources/synonyms.json"));
        Map<String,ArrayList<String>> myMap = new HashMap<String, ArrayList<String>>();

        ObjectMapper objectMapper = new ObjectMapper();
        myMap = objectMapper.readValue(mapData, HashMap.class);


        System.out.println("Perform an action: ");
        String[] inputStringArray = playerControlString.split(" ", 3);
        String[] invalidArray = {"NOT", "VALID"};

        if(inputStringArray.length != 2){
            return invalidArray;
        }

        if(myMap.get("go").contains(inputStringArray[0])){
            inputStringArray[0] = "go";
            inputStringArray[1] = move(inputStringArray);
        } else if (myMap.get("grab").contains(inputStringArray[0])){
            inputStringArray[0] = "grab";
            inputStringArray[1] = grabItem(inputStringArray);
        }else if (myMap.get("use").contains(inputStringArray[0])){
            inputStringArray[0] = "use";
            inputStringArray[1] = useItem(inputStringArray);
        } else if (myMap.get("search").contains(inputStringArray[0])) {
            inputStringArray[0] = "search";
            inputStringArray[1] = search(inputStringArray);
        } else if (myMap.get("drop").contains(inputStringArray[0])){
            inputStringArray[0] = "drop";
            inputStringArray[1] = dropItem(inputStringArray);
        } else {
            return invalidArray;
        }

        return inputStringArray;
    }



    private void createSubmitTextButton(){
        submitButton = new StrandedButton("SEND COMMAND");
//        submitButton.setMaxWidth(200);
        submitButton.setLayoutX(725);
        submitButton.setLayoutY(725);
        //addMenuButton(submitButton);
        submitButton.setButtonFontForLongText();
        mainPane.getChildren().add(submitButton);


        submitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MenuMain.clickMediaPlayer.stop();
                MenuMain.clickMediaPlayer.play();

                try {
                    startParsing(textField.getText());
                    lastCommand.setText("Last Command: " + textField.getText());
                    textField.clear();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        submitButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DropShadow dropshad = new DropShadow();

                dropshad.setColor(Color.ORANGE);
                submitButton.setEffect(dropshad);
            }
        });


    }

    private void createButton(){

        createExitButton();
    }

    //exit button
    private void createExitButton(){
        StrandedButton exitButton = new StrandedButton("EXIT");
        mainPane.getChildren().add(exitButton);
        exitButton.setLayoutY(725);
        exitButton.setLayoutX(25);

        exitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Game closed by pressing exit");
                Platform.exit();
                System.exit(0);
            }
        });
    }

    //method for backgorund
    private void createBackGround(){
        Image mainBackImage = new Image("crashSite.png", 1000, 800, false, true);
        background = new BackgroundImage(mainBackImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                                                         BackgroundPosition.DEFAULT,null);

        mainPane.setBackground(new Background(background));
    }

//    Map placeholder
    private void creatMapButton(){
        StrandedButton mapButton = new StrandedButton("MAP");
        map = new ImageView("sample/models/resources/maps/crashSite.png");
        map.setFitWidth(250);
        map.setPreserveRatio(true);
        map.setLayoutX(25);
        map.setLayoutY(25);
        mapButton.setLayoutX(250);
        mapButton.setLayoutY(725);

        mapSubscene.getAnchorPane().getChildren().add(mapButton);
        mapSubscene.getAnchorPane().getChildren().add(map);

        mainPane.getChildren().add(mapButton);


        mapButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MenuMain.clickMediaPlayer.stop();
                MenuMain.clickMediaPlayer.play();
                if (!mapSubscene.isHidden()){
                    mapSubscene.hideSubScene();

                } else {
                    mapSubscene.showSubScene();
                }
            }
        });

        mapButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DropShadow dropshad = new DropShadow();

                dropshad.setColor(Color.ORANGE);
                mapButton.setEffect(dropshad);
            }
        });
    }

    private void createHelpButton(){
        StrandedButton helpButton = new StrandedButton("HELP");

        InfoLabelSmall help = new InfoLabelSmall("Type Commands to Play the Game. \n\nMove: Go + Direction 'North, East, South, West'\n\n" +
                                       "Grab Item: Grab + Item/Weapon/Food \n\nInspect Surroundings: Search + here/area name \n\nDrop Item: Drop + item \n\n" +
                                       "Use Item: Use/Eat + Item/Weapon/Food \n\nPress the Map button to check the Map. If you don't remember the commands use similar words");
        help.setFont(Font.font("Verdana", 12));

        help.setLayoutX(10);
        help.setLayoutY(5);

        //gameHelpSubscene = new SmallHelpSubScene();
        helpButton.setLayoutX(475);
        helpButton.setLayoutY(725);

        gameHelpSubscene.getHelpAnchorPane().getChildren().add(help);
        gameHelpSubscene.getHelpAnchorPane().getChildren().add(helpButton);
        mainPane.getChildren().add(helpButton);
        helpButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MenuMain.clickMediaPlayer.stop();
                MenuMain.clickMediaPlayer.play();
                if (!gameHelpSubscene.helpIsHidden()){
                     gameHelpSubscene.hideHelpSubScene();
                } else {
                    gameHelpSubscene.showHelpSubScene();
                }
            }
        });

        helpButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DropShadow dropshad = new DropShadow();
                dropshad.setColor(Color.ORANGE);
                helpButton.setEffect(dropshad);
            }
        });
    }


    private void createTextScene() throws IOException, InterruptedException {
        displayTextSubScene = new GameStrandedSubScene();

            //This is to display all the game text
            locationText = new Label("Participate i");

            locationText.setLayoutX(25);
            locationText.setLayoutY(25);
            displayTextSubScene.getAnchorPane().getChildren().add(locationText);
            locationText.setText("Location: " + currentLocation);
            HashMap<String, String> fxCurrLocation = status.fxDisplayLocation();
            descriptionText = new Label("Description: " + fxCurrLocation.get("Description"));
            descriptionText.setMaxWidth(350);
            descriptionText.setWrapText(true);
            descriptionText.setLayoutX(25);
            descriptionText.setLayoutY(50);
            displayTextSubScene.getAnchorPane().getChildren().add(descriptionText);

        combatText = new Label("");
        combatText.setMaxWidth(350);
        combatText.setWrapText(true);
        combatText.setLayoutX(25);
        combatText.setLayoutY(100);
        displayTextSubScene.getAnchorPane().getChildren().add(combatText);

        //inventory
        displayInventory = new Label("Inventory:  " + playerCreated.viewInventory());
        displayInventory.setLayoutX(25);
        displayInventory.setLayoutY(350);
        displayTextSubScene.getAnchorPane().getChildren().add(displayInventory);

        //name
        displayName = new Label("Name: " + playerCreated.getName());
        displayName.setLayoutX(25);
        displayName.setLayoutY(290);
        displayTextSubScene.getAnchorPane().getChildren().add(displayName);

        //lastCommand
        lastCommand = new Label("Last Command: ");
        lastCommand.setLayoutX(25);
        lastCommand.setLayoutY(310);
        displayTextSubScene.getAnchorPane().getChildren().add(lastCommand);

        //display Player Health
        displayPlayerHealth = new Label("Player Health: " + playerCreated.getHP() + " / " + playerCreated.getMaxHp());
        displayPlayerHealth.setLayoutX(25);
        displayPlayerHealth.setLayoutY(330);
        displayTextSubScene.getAnchorPane().getChildren().add(displayPlayerHealth);


        mainPane.getChildren().add(displayTextSubScene);
    }

    private void showAndHideSubscenes(StrandedSubScene subScene){
        if(sceneThatNeedsToSlide != null){
            sceneThatNeedsToSlide.moveSubScene();
        }

        subScene.moveSubScene();

        sceneThatNeedsToSlide = subScene;
    }


    // User Input Section
    public String move(String[] inputStringArrayArg){
        String directionString = inputStringArrayArg[1].toUpperCase();
        if(!directionString.equals("NORTH") && !directionString.equals("SOUTH") && !directionString.equals("EAST") && !directionString.equals("WEST")){
            return moveDirection;
        }

        for(Directions direction: Directions.values()){
            if(direction.equals(Directions.valueOf(directionString))){
                moveDirection = inputStringArrayArg[1];
            }
        }
        return moveDirection;
    }

    public String grabItem(String[] inputStringArrayArg){
        itemGrabbed = inputStringArrayArg[1];
        return itemGrabbed;
    }

    //Need to update on commands engine
    public String useItem(String[] inputStringArrayArg){
        String inventoryString = playerCreated.viewInventory().toString();
        if(inventoryString.contains(inputStringArrayArg[1])){
            useItemGrabbed = inputStringArrayArg[1];
        } else {
            useItemGrabbed = "";
        }

        return useItemGrabbed;
    }

    //needs validation?
    public String search(String[] inputStringArrayArg){
        return locationToSearch;
    }

    //Validation handled by status
    public String dropItem(String[] inputStringArray) {
        String inventoryString = playerCreated.viewInventory().toString();
        if (inventoryString.contains(inputStringArray[1])) {
            itemDropped = inputStringArray[1];
        } else {
            itemDropped = "";
        }
        return itemDropped;
    }

    //Combat methods
    private Alien createAlien() throws IOException {
        //Return alien from JSON file.
        //byte[] alienData = Files.readAllBytes(Paths.get("src/main/resources/enemies.json"));
        byte[] alienData = Files.readAllBytes(Paths.get("src/resources/enemies.json"));
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

            if(soldier.isAlive() && (playerCreated.getHP() > playerCreated.getMinHp())) {
                //Verify action

                    fightStatus(soldier);
//
            }
        }
    }


    private void fightStatus(Alien soldier) throws InterruptedException, IOException {

        String inventoryString =  playerCreated.viewInventory().toString();
        if(inventoryString.isEmpty()){

            String assClass = playerCreated.getAstronautClass().toLowerCase();
            if(assClass.equals("soldier")){
                soldier.takeDamage(20);
                playerCreated.takeDamage(10);
            }else {
                soldier.takeDamage(10);
                playerCreated.takeDamage(10);
            }
            String hp = String.valueOf(playerCreated.getHP());
            String maxHp = String.valueOf(playerCreated.getMaxHp());
            String alienhp = String.valueOf(soldier.getHp());
            displayPlayerHealth.setText(hp + "/" + maxHp);
            combatText.setText("**********ALERT, ALIEN IS ATTACKING YOU*************\n" +
                               "============================================\n" +
                               "Enemy:" + soldier.getType() + " HP: "+alienhp + "\n" +
                               "============================================" +
                               "Name: " + "Matt Damon" + " | HP: " + hp + "|" + maxHp + "\n" +
                               "You attacked with your fists. You should probably go somewhere else\n" +
                               "------------------------------------------------");

            if(soldier.getHp() < 0) {

                alienSoldier.setAlive(false);
                combatText.setText("**********ALERT, ALIEN IS ATTACKING YOU*************\n" +
                                   "============================================\n" +
                                   "Enemy:" + soldier.getType() + " HP: "+alienhp + "\n" +
                                   "============================================" +
                                   "Name: " + "Matt Damon" + " | HP: " + hp + "|" + maxHp + "\n" +
                                   "You attacked with your fists. " +
                                   "" +
                                   "You Killed the alien and found out that they just revive and keep coming please leave while you are still alive" +
                                   "\n" +
                                   "------------------------------------------------");
            }


        }else {

            String assClass = playerCreated.getAstronautClass().toLowerCase();
            if(assClass.equals("soldier")){
                soldier.takeDamage(25);
                playerCreated.takeDamage(5);
            }else {
                soldier.takeDamage(15);
                playerCreated.takeDamage(10);
            }

            String hp = String.valueOf(playerCreated.getHP());
            String maxHp = String.valueOf(playerCreated.getMaxHp());
            String alienhp = String.valueOf(soldier.getHp());
            displayPlayerHealth.setText(hp + "/" + maxHp);
            combatText.setText("**********ALERT, ALIEN IS ATTACKING YOU*************\n" +
                               "============================================\n" +
                               "Enemy:" + soldier.getType() + " HP: "+alienhp + "\n" +
                               "============================================" +
                               "Name: " + "Matt Damon" + " | HP: " + hp + "|" + maxHp + "\n" +
                               "You attacked with the first weapon in your inventory. You should probably go somewhere else though\n" +
                               "------------------------------------------------");

            if(soldier.getHp() < 0) {

                alienSoldier.setAlive(false);
                combatText.setText("**********ALERT, ALIEN IS ATTACKING YOU*************\n" +
                                   "============================================\n" +
                                   "Enemy:" + soldier.getType() + " HP: "+alienhp + "\n" +
                                   "============================================" +
                                   "Name: " + "Matt Damon" + " | HP: " + hp + "|" + maxHp + "\n" +
                                   "You attacked with the first weapon in your inventory. " +
                                   "" +
                                   "You Killed the alien and found out that they just revive and keep coming please leave while you are still alive" +
                                   "\n" +
                                   "------------------------------------------------");
            }
        }
//
    }

}
