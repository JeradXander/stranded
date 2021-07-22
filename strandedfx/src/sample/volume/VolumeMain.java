package sample.volume;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.views.ViewManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.IOException;
import java.util.Objects;

public class VolumeMain extends Application {


        @Override
        public void start(Stage stage) throws IOException {

            Parent root = FXMLLoader.load(getClass().getResource("volume.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent arg0) {

                    Platform.exit();
                    System.exit(0);
                }
            });
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
//        Parent root = FXMLLoader.load(getClass().getResource("volume.fxml"));
//
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//
//        stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
//            @Override
//            public void handle(WindowEvent arg0){
//                Platform.exit();
//                System.exit(0);
//            }
//        });
//
//    }
