package sample.volume;

import com.sun.glass.ui.Timer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TimerTask;

//import static org.graalvm.compiler.debug.DebugOptions.PrintGraphTarget.File;

public class VolumeController
        implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private Label songLabel;
    @FXML
    private Button playButton;

    @FXML
    private Slider volumeSlider;

    private Media media;
    private MediaPlayer mediaPlayer;

    private File directory;
    private File[] files;

    private ArrayList<File> songs;

    private int songNumber;
    private int[] speeds = {25, 50, 75, 100, 125, 150, 175, 200};

    private Timer timer;
    private TimerTask task;

    private boolean running;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        songs = new ArrayList<File>();

        directory = new File("music");

        files = directory.listFiles();

        if(files != null) {

            for(File file : files) {

                songs.add(file);
            }
        }

        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        songLabel.setText(songs.get(songNumber).getName());


        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {

                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
            }
        });

    }
    public void playMedia() {

//        beginTimer();
//        changeSpeed(null);
        mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
        mediaPlayer.play();
    }
//
//    public void pauseMedia() {
//
//        cancelTimer();
//        gameMediaPlayer.pause();
//    }
//
//    public void resetMedia() {
//
//        songProgressBar.setProgress(0);
//        gameMediaPlayer.seek(Duration.seconds(0));
//    }

//    public void previousMedia() {
//
//        if(songNumber > 0) {
//
//            songNumber--;
//
//            gameMediaPlayer.stop();
//
//            if(running) {
//
//                cancelTimer();
//            }
//
//            media = new Media(songs.get(songNumber).toURI().toString());
//            gameMediaPlayer = new MediaPlayer(media);
//
//            songLabel.setText(songs.get(songNumber).getName());
//
//            playMedia();
//        }
//        else {
//
//            songNumber = songs.size() - 1;
//
//            gameMediaPlayer.stop();
//
//            if(running) {
//
//                cancelTimer();
//            }
//
//            media = new Media(songs.get(songNumber).toURI().toString());
//            gameMediaPlayer = new MediaPlayer(media);
//
//            songLabel.setText(songs.get(songNumber).getName());
//
//            playMedia();
//        }
//    }
//
//    public void nextMedia() {
//
//        if(songNumber < songs.size() - 1) {
//
//            songNumber++;
//
//            gameMediaPlayer.stop();
//
//            if(running) {
//
////                cancelTimer();
//            }
//
//            media = new Media(songs.get(songNumber).toURI().toString());
//            gameMediaPlayer = new MediaPlayer(media);
//
//            songLabel.setText(songs.get(songNumber).getName());
//
//            playMedia();
//        }
//        else {
//
//            songNumber = 0;
//
//            gameMediaPlayer.stop();
//
//            media = new Media(songs.get(songNumber).toURI().toString());
//            gameMediaPlayer = new MediaPlayer(media);
//
//            songLabel.setText(songs.get(songNumber).getName());
//
//            playMedia();
//        }
//    }

//    public void changeSpeed(ActionEvent event) {
//
//        if(speedBox.getValue() == null) {
//
//            gameMediaPlayer.setRate(1);
//        }
//        else {
//
//            //gameMediaPlayer.setRate(Integer.parseInt(speedBox.getValue()) * 0.01);
//            gameMediaPlayer.setRate(Integer.parseInt(speedBox.getValue().substring(0, speedBox.getValue().length() - 1)) * 0.01);
//        }
//    }

//    public void beginTimer() {
//
//        timer = new Timer();
//
//        task = new TimerTask() {
//
//            public void run() {
//
//                running = true;
//                double current = gameMediaPlayer.getCurrentTime().toSeconds();
//                double end = media.getDuration().toSeconds();
////                songProgressBar.setProgress(current/end);
//
//                if(current/end == 1) {
//
//                    cancelTimer();
//                }
//            }
//        };
//
//        timer.scheduleAtFixedRate(task, 0, 1000);
//    }
//
//    public void cancelTimer() {
//
//        running = false;
//        timer.cancel();
//    }
}

//{
//    MediaPlayer gameMediaPlayer;
//
//    @FXML
//    void play(MouseEvent event) {
//        String fileName = "intro.wav";
//        playHitSound(fileName);
//    }
//
//    private void playHitSound(String fileName){
//        String path = getClass().getResource(fileName).getPath();
//        System.out.println(path);
//        Media media = new Media(new File(path).toURI().toString());
//        gameMediaPlayer = new MediaPlayer(media);
//        gameMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//        gameMediaPlayer.play();
//    }
//}
