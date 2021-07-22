package sample;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;

import com.game.Main;
import javafx.application.Application;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import sample.views.ViewManager;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MenuMain extends Application {

    @Override
    public void start(Stage menuStage) throws Exception{
        music();
        ViewManager manager = new ViewManager();
        menuStage = manager.getMainStage();
       // Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));
        menuStage.setTitle("Stranded");
       // menuStage.setScene(new Scene(root, 300, 275, Color.GREENYELLOW));
        menuStage.show();

    }
    public static MediaPlayer mediaPlayer;
    public static MediaPlayer fxmediaPlayer;
    public void music() {
        // String s = "src/sample/views/resources/intro.wav";
        // String path = Paths.get(s).toUri().toString();
        String respath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("intro.wav")).toExternalForm();
        System.out.println(respath);
        Media h = new Media(respath);
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();


        String fxpath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("explosion.mp3")).toExternalForm();
        System.out.println(fxpath);
        Media fx = new Media(fxpath);
        fxmediaPlayer = new MediaPlayer(fx);
    }



    public static void main(String[] args) {
        launch(args);

    }
}
