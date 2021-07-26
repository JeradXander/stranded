package sample.models;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class SmallHelpSubScene extends SubScene {
    private final String FONT_PATH = "src/sample/models/resources/kenvector_future.ttf";
    private final String BACKGROUND_IMAGE = "sample/models/resources/red_panel.png";

    public boolean helpIsHidden() {
        return isHidden;
    }

    private boolean isHidden =true;

    public SmallHelpSubScene() {

        super(new AnchorPane(), 300, 300);
        prefHeight(300);
        prefWidth(300);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE,300,300,false,true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                                    BackgroundPosition.DEFAULT,null);

        AnchorPane root3 = (AnchorPane) this.getRoot();

        root3.setBackground(new Background(image));

        setLayoutX(1400);
        setLayoutY(25);

    }


    public void moveSubScene(){

        if(isHidden){
            TranslateTransition transition = new TranslateTransition();

            transition.setDuration(Duration.seconds(0.4));
            transition.setNode(this);

            transition.setToX(-725);

            transition.play();
            isHidden = false;
        }else{
            TranslateTransition transition = new TranslateTransition();

            transition.setDuration(Duration.seconds(0.4));
            transition.setNode(this);

            transition.setToX(725);

            transition.play();
            isHidden = true;
        }

    }

    public void setSmallSubScene(double width, double height){
//        this.prefHeight(height);
//        this.prefWidth(width);
        prefHeight(height);
        prefWidth(width);

    }

    public void hideHelpSubScene(){
        TranslateTransition transition = new TranslateTransition();

        transition.setDuration(Duration.seconds(0.4));
        transition.setNode(this);

        transition.setToX(725);

        transition.play();
        isHidden = true;
    }

    public void showHelpSubScene(){
        TranslateTransition transition = new TranslateTransition();

        transition.setDuration(Duration.seconds(0.4));
        transition.setNode(this);

        transition.setToX(-725);

        transition.play();
        isHidden = false;
    }

    public AnchorPane getHelpAnchorPane(){
        return (AnchorPane) this.getRoot();
    }
}
