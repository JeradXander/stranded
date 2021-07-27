package sample.views;

import com.game.player.Player;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import sample.MenuMain;
import sample.models.ASTRO;
import sample.models.AstroPicker;
import sample.models.InfoLabel;
import sample.models.StrandedButton;
import sample.models.StrandedSubScene;


public class ViewManager {

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




    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;


    public ViewManager(){
        buttonList = new ArrayList<>();
        //creating main window to hold all children
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT, Color.RED);
        mainStage = new Stage();
        //setting scene
        mainStage.setScene(mainScene);

        mainStage.setResizable(false);

        createSubscenes();

        //creating buttons
        createButton();

        //creating Background from method
        createBackGround();

        createLogo();

        createSlider();

        createchooseSubscene();


        StrandedSubScene subscene = new StrandedSubScene();
        mainPane.getChildren().add(subscene);
    }

    //method to get main stage
    public  Stage getMainStage(){
        return mainStage;
    }

    private void createSubscenes(){
        creditSubscene = new StrandedSubScene();
        mainPane.getChildren().add(creditSubscene);

        helpSubscene = new StrandedSubScene();
        mainPane.getChildren().add(helpSubscene);

        playSubscene = new StrandedSubScene();
        mainPane.getChildren().add(playSubscene);

        scoreSubscene = new StrandedSubScene();
        mainPane.getChildren().add(scoreSubscene);

        astroChooserScene = new StrandedSubScene();
        mainPane.getChildren().add(astroChooserScene);



    }
// Volume slider
    private void createSlider(){
        Slider volumeControl = new Slider(0, 100, 5);

        mainPane.getChildren().add(volumeControl);
        volumeControl.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number t1) {
                MenuMain.mediaPlayer.setVolume(volumeControl.getValue() * 0.01);

                System.out.println("volume" + volumeControl.getValue());
            }
        });
    }

//    Create subscene
    private void createchooseSubscene(){
        astroChooserScene = new StrandedSubScene();
        InfoLabel astroLabel = new InfoLabel("CHOOSE ASTRO:\nLEFT: SOLDIER MIDDLE: MEDIC RIGHT: EXPLORER");
        astroLabel.setAlignment(Pos.CENTER);

        mainPane.getChildren().add(astroChooserScene);
        StrandedButton startButton = new StrandedButton("START");
        startButton.setLayoutY(150);
        startButton.setLayoutX(210);
        astroLabel.setLayoutY(-100);
        astroChooserScene.getAnchorPane().getChildren().add(astroToChoose());
        astroChooserScene.getAnchorPane().getChildren().add(astroLabel);
      astroChooserScene.getAnchorPane().getChildren().add(startButton);

        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("clicked");
                MenuMain.mediaPlayer.stop();
                GameViewManager manager = null;
                String type = chosenAstro.name();
                try {
                    manager = new GameViewManager(new Player("Matt Damon",type));
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                mainStage = manager.getMainStage();
                // Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));
//                mainStage.setTitle("GameView");

                mainStage.show();
            }
        });
    }

//    Choose astronaut
    private HBox astroToChoose(){

        HBox box = new HBox();
        box.setSpacing(40);
        astroList = new ArrayList<>();
        for(ASTRO astro: ASTRO.values()){
            System.out.println(astro);
            AstroPicker astroToPick = new AstroPicker(astro);
            astroList.add(astroToPick);
            box.getChildren().add(astroToPick);
            chosenAstro = astroToPick.getAstro();

            astroToPick.setOnMouseClicked(mouseEvent -> {
                for(AstroPicker astros : astroList){
                    astros.setIsBoxChecked(false);
                }
                astroToPick.setIsBoxChecked(true);
                chosenAstro = astroToPick.getAstro();
            });
        }
        box.setLayoutX(400 - (110*2));
        box.setLayoutY(275);
        return box;
    }


    private void createSTARTButtons(){
        StrandedButton button = new StrandedButton("Play");
        mainPane.getChildren().add(button);

        button.setLayoutX(200);
        button.setLayoutY(300);
    }


    private void addMenuButton(StrandedButton button){

        button.setLayoutY(MENU_BUTTONS_START_Y + buttonList.size() * 100);
        button.setLayoutX(MENU_BUTTONS_START_X);
        buttonList.add(button);
        mainPane.getChildren().add(button);
    }

    private void createButton(){

        createPlayButton();
        createScoreButton();
        createHelpButton();
        createCreditsButton();
        createExitButton();
    }

    private void createPlayButton(){
        StrandedButton playButton = new StrandedButton("PLAY");
        StrandedButton newGameButton = new StrandedButton("NEW GAME");
        StrandedButton continueGame = new StrandedButton("CONTINUE GAME");

        newGameButton.setLayoutX(200);
        newGameButton.setLayoutY(150);

        continueGame.setLayoutX(200);
        continueGame.setLayoutY(225);
        continueGame.setButtonFontForLongText();

        playSubscene.getAnchorPane().getChildren().add(newGameButton);
      //  playSubscene.getAnchorPane().getChildren().add(continueGame);
        addMenuButton(playButton);

        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showAndHideSubscenes(playSubscene);
            }
        });

        newGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {


                showAndHideSubscenes(astroChooserScene);

            }
        });
    }

    private void createScoreButton(){
        StrandedButton scoreButton = new StrandedButton("SCORE");
        addMenuButton(scoreButton);
        InfoLabel scoreLabel = new InfoLabel("Game Help");
        InfoLabel score = new InfoLabel("1. Name: Jerad: Health-80\n\n2. Name: Damien: Health-79\n3.0 Name: Dan: Health-78\n4.0 Name: Jun: Health-30\n");
        scoreLabel.setUnderline(true);
        scoreLabel.setLayoutY(-160);
        scoreLabel.setTextForTitle();



        scoreSubscene.getAnchorPane().getChildren().add(scoreLabel);
        scoreSubscene.getAnchorPane().getChildren().add(score);


        scoreButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showAndHideSubscenes(scoreSubscene);
            }
        });
    }

    private void createHelpButton(){
//        Font font = new Font(120);
        StrandedButton helpButton = new StrandedButton("HELP");
        InfoLabel helpLabel = new InfoLabel("Game Help");
        InfoLabel help = new InfoLabel("Type Commands to Play the Game. \n\nMove: Go + Direction 'North, East, South, West'\n\n" +
        "Grab Item: Grab + Item/Weapon/Food \n\nInspect Surroundings: Search + here/area name \n\nDrop Item: Drop + item \n\n" +
        "Use Item: Use/Eat + Item/Weapon/Food \n\nPress the Map button to check the Map. If you don't remember the commands use similar words");
        help.setFont(Font.font("Verdana", 16));
        helpLabel.setUnderline(true);
        helpLabel.setLayoutY(-160);
        helpLabel.setTextForTitle();

        helpSubscene.getAnchorPane().getChildren().add(helpLabel);
        helpSubscene.getAnchorPane().getChildren().add(help);
        addMenuButton(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showAndHideSubscenes(helpSubscene);
            }
        });
    }

    private void createCreditsButton(){

        StrandedButton credButton = new StrandedButton("CREDITS");
        InfoLabel creditLabel = new InfoLabel("Game Credits");
        InfoLabel names = new InfoLabel(" \n\nDamian Mercado\n\nDan Lasche\n\nJerad Alexander");
        creditLabel.setUnderline(true);
        creditLabel.setLayoutY(-160);
        creditLabel.setTextForTitle();


//        creditLabel.setLayoutX();
        creditSubscene.getAnchorPane().getChildren().add(creditLabel);
        creditSubscene.getAnchorPane().getChildren().add(names);

        addMenuButton(credButton);


        credButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                showAndHideSubscenes(creditSubscene);
            }
        });
    }

    private void createExitButton(){
        StrandedButton exitButton = new StrandedButton("EXIT");
        addMenuButton(exitButton);

        exitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Game closed by pressing exit");
                Platform.exit();
                System.exit(0);
            }
        });
    }

    private void createBackGround(){
        Image mainBackImage = new Image("sample/views/resources/mainBackground.png",1000,800,false,true);
        BackgroundImage background = new BackgroundImage(mainBackImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                                                         BackgroundPosition.DEFAULT,null);

        mainPane.setBackground(new Background(background));
    }

    private void createLogo(){
        ImageView logo = new ImageView("sample/views/resources/logo.png");
        logo.setLayoutX(325);
        logo.setLayoutY(20);
        logo.setFitWidth(650);
        logo.setPreserveRatio(true);

        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DropShadow dropshad = new DropShadow();

                dropshad.setColor(Color.ORANGE);
                logo.setEffect(dropshad);
            }
        });
        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                logo.setEffect(null);
            }
        });
        mainPane.getChildren().add(logo);
    }

    private void showAndHideSubscenes(StrandedSubScene subScene){
        if(sceneThatNeedsToSlide != null){
            sceneThatNeedsToSlide.moveSubScene();
        }

        subScene.moveSubScene();

        sceneThatNeedsToSlide = subScene;
    }
}
