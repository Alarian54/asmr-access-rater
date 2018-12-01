import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GUI extends Application {

    public GUI() {}

    @Override
    public void init() {}

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Title");
        BorderPane border = new BorderPane();
        Button buttonQuit = new Button("Quit");
        buttonQuit.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });
        HBox hbox = new HBox();
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonQuit);
        Scene scene = new Scene(border, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {}

    public static void main(final String[] arguments) {
        launch(arguments);
    }
}
