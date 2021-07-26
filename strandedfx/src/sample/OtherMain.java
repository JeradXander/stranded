package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

public class OtherMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));
        primaryStage.setTitle("Stranded");
        primaryStage.setScene(new Scene(root, 800, 800, Color.OLIVEDRAB));
        //        Image icon = new Image("");
//        stage.getIcons().add(icon);
//        stage.setTitle("Stage Demo woot woot");
//        stage.setWidth(420);
//        staget.setHeight(420);
//
//        stage.setScene(scene);
        primaryStage.setResizable(false);
//        Full screen options
//        primaryStage.setFullScreen(true);
//        primaryStage.setFullScreenExitHint("You can't escape unless you press q");
//        primaryStage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
