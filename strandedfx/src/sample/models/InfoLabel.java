package sample.models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class InfoLabel extends Label {

    private final String FONT_PATH = "src/sample/models/resources/kenvector_future.ttf";

    public InfoLabel (String text){

        setPrefHeight(400);
        setPrefWidth(600);
        setPadding(new Insets(40,40,40,40))   ;
        setText(text);
        setWrapText(true);
        setTextForLabel();
        setAlignment(Pos.CENTER);
    }

    private void setTextForLabel(){
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 22));
        }catch(FileNotFoundException e){
            e.printStackTrace();
            setFont(Font.font("Verdana",23));
        }
    }

    public void setTextForTitle(){
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 26));
        }catch(FileNotFoundException e){
            e.printStackTrace();
            setFont(Font.font("Verdana",23));
        }
    }
}
