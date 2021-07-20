package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.views.ViewManager;

public class GameMain extends Application {

    @Override
    public void start(Stage gameStage) throws Exception {
        ViewManager manager = new ViewManager();
        gameStage = manager.getMainStage();
        // Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("sample.fxml")));
        gameStage.setTitle("Stranded");
        // menuStage.setScene(new Scene(root, 300, 275, Color.GREENYELLOW));
        gameStage.show();
    }

}
