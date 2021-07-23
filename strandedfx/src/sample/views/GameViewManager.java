package sample.views;

import com.game.player.Player;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.MenuMain;
import sample.models.*;


public class GameViewManager {

    private static final int HEIGHT = 775;
    private static final int WIDTH = 1050;
    private static final int MENU_BUTTONS_START_X = 100;
    private static final int MENU_BUTTONS_START_Y = 225;
    private StrandedSubScene sceneThatNeedsToSlide;

    private ArrayList<StrandedButton> buttonList;
    private ArrayList<AstroPicker> astroList;
    private ASTRO chosenAstro;

    private StrandedSubScene creditSubscene;
    private StrandedSubScene helpSubscene;
    private StrandedSubScene scoreSubscene;
    private StrandedSubScene playSubscene;
    private StrandedSubScene astroChooserScene;
    private SmallStrandedSubScene mapSubscene;
    private StrandedSubScene displayTextSubScene;

    private TextField textField;
    private StrandedButton submitButton;

    private ImageView map;


    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

// Constructor
    public GameViewManager(Player playerCreated){
        buttonList = new ArrayList<>();
        //creating main window to hold all children
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT, Color.RED);
        mainStage = new Stage();
        //setting scene
        mainStage.setScene(mainScene);

        mainStage.setResizable(false);

        mainStage.setTitle(playerCreated.getName() + "special: " );

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
        createLabel();


        MenuMain.fxmediaPlayer.play();

//
//        createchooseSubscene();
//
//        StrandedSubScene subscene = new StrandedSubScene();
//        mainPane.getChildren().add(subscene);
    }

    //method to get main stage
    public  Stage getMainStage(){
        return mainStage;
    }

    private void createSubscenes(){
        creditSubscene = new StrandedSubScene();
        mainPane.getChildren().add(creditSubscene);

//        helpSubscene = new StrandedSubScene();
//        mainPane.getChildren().add(helpSubscene);
//
//        playSubscene = new StrandedSubScene();
//        mainPane.getChildren().add(playSubscene);
//
//        scoreSubscene = new StrandedSubScene();
//        mainPane.getChildren().add(scoreSubscene);

        astroChooserScene = new StrandedSubScene();
        mainPane.getChildren().add(astroChooserScene);

        mapSubscene = new SmallStrandedSubScene();
        mainPane.getChildren().add(mapSubscene);

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
                System.out.println("text changed");
                MenuMain.laserMediaPlayer.stop();
                MenuMain.laserMediaPlayer.play();
            }
        });

        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode().equals(KeyCode.ENTER))
                {
                    startParsing(textField.getText());
                    System.out.println("success!");

                }
            }
        });

    }

    private void startParsing(String input){
        System.out.println(input);


    }

    private void createLabel(){
        //This is to display all the game text
        Label displayText = new Label("Game Text");
        displayText.setLayoutX(500);
        displayText.setLayoutY(500);
        mainPane.getChildren().add(displayText);
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

                startParsing(textField.getText());
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

//        createPlayButton();
//        createScoreButton();
//        createHelpButton();
//        createCreditsButton();
        createExitButton();
    }








//    credit button main menu


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
// Crashsite
    private void createBackGround(){
        Image mainBackImage = new Image("sample/views/resources/crashsite.png",1000,800,false,true);
        BackgroundImage background = new BackgroundImage(mainBackImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
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

    private void createTextScene(){
        StrandedSubScene displayTextSubScene = new StrandedSubScene();
        mapSubscene.getAnchorPane().getChildren().add(displayTextSubScene);
        //add text to be displayed
        //mapSubscene.getAnchorPane().getChildren().add(map);

        mainPane.getChildren().add(displayTextSubScene);

    }

    private void showAndHideSubscenes(StrandedSubScene subScene){
        if(sceneThatNeedsToSlide != null){
            sceneThatNeedsToSlide.moveSubScene();
        }

        subScene.moveSubScene();

        sceneThatNeedsToSlide = subScene;
    }

}
