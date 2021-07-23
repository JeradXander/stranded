package sample.models;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

public class SmallStrandedSubScene extends SubScene {
    private final String FONT_PATH = "src/sample/models/resources/kenvector_future.ttf";
    private final String BACKGROUND_IMAGE = "sample/models/resources/red_panel.png";

    public boolean isHidden() {
        return isHidden;
    }

    private boolean isHidden =true;

    public SmallStrandedSubScene() {

        super(new AnchorPane(), 300, 300);
        prefHeight(300);
        prefWidth(300);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE,300,300,false,true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                                    BackgroundPosition.DEFAULT,null);

        AnchorPane root2 = (AnchorPane) this.getRoot();

        root2.setBackground(new Background(image));

        setLayoutX(1400);
        setLayoutY(350);

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

    public void hideSubScene(){
        TranslateTransition transition = new TranslateTransition();

        transition.setDuration(Duration.seconds(0.4));
        transition.setNode(this);

        transition.setToX(725);

        transition.play();
        isHidden = true;
    }

    public void showSubScene(){
        TranslateTransition transition = new TranslateTransition();

        transition.setDuration(Duration.seconds(0.4));
        transition.setNode(this);

        transition.setToX(-725);

        transition.play();
        isHidden = false;
    }

    public AnchorPane getAnchorPane(){
        return (AnchorPane) this.getRoot();
    }
}
