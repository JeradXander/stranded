package sample.models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StrandedButton extends Button {


    private final String FONT_PATH = "src/sample/models/resources/kenvector_future.ttf";
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/sample/models/resources/red_buttonpressed.png');";
    private final String BUTTON_UNPRESSED    = "-fx-background-color: transparent; -fx-background-image: url('/sample/models/resources/red_button01.png');";

    public StrandedButton(String text ){
        setText(text);
        setButtonFont();
        setPrefWidth(190);
        setPrefHeight(49);
        setStyle(BUTTON_UNPRESSED);
        inititializedButtonListeners();
    }

    private void setButtonFont(){
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH),22));
        }catch(FileNotFoundException e){
            e.printStackTrace();
            setFont(Font.font("Verdana",23));
        }
    }

    public void setButtonFontForLongText(){
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH),16));
        }catch(FileNotFoundException e){
            e.printStackTrace();
            setFont(Font.font("Verdana",23));
        }
    }

    private void setbuttonPressedStyle(){
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY() +4);
    }
    private void setbuttonReleasedStyle(){
        setStyle(BUTTON_UNPRESSED);
        setPrefHeight(50);
        setLayoutY(getLayoutY() - 4);
    }

    private void inititializedButtonListeners(){
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    setbuttonPressedStyle();
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    setbuttonReleasedStyle();
                }
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                DropShadow dropshad = new DropShadow();

                dropshad.setColor(Color.ORANGE);
                setEffect(dropshad);
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setEffect(null);
            }
        });
    }

}
