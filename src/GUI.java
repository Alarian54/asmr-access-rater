import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Place;
import model.PlaceList;
import other.LoadSave;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUI extends Application {

    private PlaceList places;
    private int height;
    private int width;

    public GUI() {}

    @Override
    public void init() {
        LoadSave ls = new LoadSave();
        places = new PlaceList();
        try {
            places.setPlaces(ls.load("places.txt"));
        } catch (IOException e) {
            places.setPlaces(new ArrayList<Place>());
        }
        width = 450;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Access Rater");
        BorderPane border = new BorderPane();

        Button buttonPlace = new Button("Add Place");
        buttonPlace.setPrefSize(100, 20);
        buttonPlace.setOnAction((ActionEvent event) -> {
            addPlace(primaryStage);
        });
        Button buttonReview = new Button("Add Review");
        buttonReview.setPrefSize(100, 20);
        buttonReview.setOnAction((ActionEvent event) -> {
            addReview(primaryStage);
        });
        Button buttonQuit = new Button("Quit");
        buttonQuit.setPrefSize(100, 20);
        buttonQuit.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });
        HBox hbox = getHBox();
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonPlace, buttonReview, buttonQuit);

        VBox vbox = getPlaceList();
        border.setCenter(vbox);

        Scene scene = new Scene(border, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void addPlace(Stage primaryStage) {
        primaryStage.setTitle("Access Rater - Add Place");
        BorderPane border = new BorderPane();
        border.setCenter(getPlaceList());

        Button buttonBack = new Button("Back");
        buttonBack.setPrefSize(100, 20);
        buttonBack.setOnAction((ActionEvent event) -> {
            start(primaryStage);
        });

        Text name = new Text("Enter place name:");
        name.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
        name.setStyle("-fx-fill: #FFFFFF;");

        TextField nameField = new TextField();
        nameField.setPrefSize(100, 20);

        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setPrefSize(100, 20);
        buttonSubmit.setOnAction((ActionEvent event) -> {
            places.add(new Place(nameField.getText()));
            start(primaryStage);
        });

        HBox hbox = getHBox();
        hbox.setSpacing(15);
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonBack, name, nameField, buttonSubmit);

        final Scene scene = new Scene(border, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void addReview(Stage primaryStage) {

    }

    @Override
    public void stop() {
        LoadSave ls = new LoadSave();
        try {
            ls.save(places.getPlaces(), "places.txt");
        } catch (IOException e) {};}

    public static void main(final String[] arguments) {
        launch(arguments);
    }

    private HBox getHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15));
        hbox.setSpacing(20);
        hbox.setStyle("-fx-background-color: #336699;");
        return hbox;
    }

    private VBox getPlaceList() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        List<String> displayList = new ArrayList<>();
        if (places.size() > 0) {
            for (Place place : places.getPlaces()) {
                for (String s : place.printArray()) {
                    displayList.add(s);
                }
            }
        }
        height = 60;
        for (int i=0; i<displayList.size(); i++) {
            Label l = new Label(displayList.get(i));
            if (!displayList.get(i).substring(0, 3).equals(" - ")) {
                l.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
                height+=29;
            } else {
                height+=26;
            }
            vbox.getChildren().add(l);
        }
        return vbox;
    }
}
