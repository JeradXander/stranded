package sample.models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class InfoLabelSmall extends Label {

    private final String FONT_PATH = "src/sample/models/resources/kenvector_future.ttf";

    public InfoLabelSmall(String text){

        setPrefHeight(280);
        setPrefWidth(270);
        setText(text);
        setWrapText(true);
        setTextForLabel();
        setAlignment(Pos.CENTER);
    }

    private void setTextForLabel(){

            setFont(Font.font("Verdana",12));

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
