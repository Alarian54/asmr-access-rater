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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Data;
import model.Place;
import model.PlaceList;
import other.LoadSave;

import java.io.IOException;
import java.util.ArrayList;

public class GUI extends Application {

    private ArrayList<String> disabilities;
    private PlaceList places;
    private int height;
    private int width;
    private int place;

    public GUI() {  }

    @Override
    public void init() {
        LoadSave ls = new LoadSave();
        Data data;
        try {
            data = ls.load("places.txt");
            disabilities = data.getDisabilities();
            places = new PlaceList();
            places.setPlaces(data.getPlaces());
        } catch (IOException e) {
            disabilities = new ArrayList<>();
            places = new PlaceList();
        }
        width = 600;
    }

    @Override
    public void start(Stage primaryStage) {
        if (disabilities.size()!=0) {
            main(primaryStage);
        } else {
            primaryStage.setTitle("Access Rater - Setup");
            BorderPane border = new BorderPane();

            Text which = new Text("Which of the following best describe you?");
            which.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
            which.setStyle("-fx-fill: #FFFFFF;");

            Button buttonProceed = new Button("Proceed");
            buttonProceed.setPrefSize(100, 20);
            buttonProceed.setOnAction((ActionEvent event) -> {
                if (disabilities.size() > 0) {
                    main(primaryStage);
                }
            });
            buttonProceed.setVisible(false);

            HBox hbox = getHBox();
            border.setTop(hbox);
            hbox.getChildren().addAll(which, buttonProceed);

            HBox hbox2 = new HBox();
            hbox2.setPadding(new Insets(10));
            hbox2.setSpacing(20);

            Button buttonPhysical = new Button("Physically impaired");
            buttonPhysical.setPrefSize(130, 20);
            buttonPhysical.setOnAction((ActionEvent event) -> {
                disabilities.add("physical");
                if (disabilities.size()==3) {
                    main(primaryStage);
                } else {
                    buttonProceed.setVisible(true);
                    buttonPhysical.setVisible(false);
                }
            });

            Button buttonHearing = new Button("Hearing impaired");
            buttonHearing.setPrefSize(130, 20);
            buttonHearing.setOnAction((ActionEvent event) -> {
                disabilities.add("hearing");
                if (disabilities.size()==3) {
                    main(primaryStage);
                } else {
                    buttonProceed.setVisible(true);
                    buttonHearing.setVisible(false);
                }
            });

            Button buttonVisual = new Button("Visually impaired");
            buttonVisual.setPrefSize(130, 20);
            buttonVisual.setOnAction((ActionEvent event) -> {
                disabilities.add("visual");
                if (disabilities.size()==3) {
                    main(primaryStage);
                } else {
                    buttonProceed.setVisible(true);
                    buttonVisual.setVisible(false);
                }
            });

            height = 100;
            hbox2.getChildren().addAll(buttonPhysical, buttonHearing, buttonVisual);
            border.setCenter(hbox2);

            final Scene scene = new Scene(border, 450, height);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    private void main(Stage primaryStage) {
        primaryStage.setTitle("Access Rater");
        BorderPane border = new BorderPane();
        VBox[] vboxes = getPlaceList();
        border.setLeft(vboxes[0]);
        border.setCenter(vboxes[1]);
        border.setRight(vboxes[2]);

        Button buttonAddPlace = new Button("Add Place");
        buttonAddPlace.setPrefSize(100, 20);
        buttonAddPlace.setOnAction((ActionEvent event) -> {
            addPlace(primaryStage);
        });
        Button buttonDelPlace = new Button("Delete Place");
        buttonDelPlace.setPrefSize(100, 20);
        buttonDelPlace.setOnAction((ActionEvent event) -> {
            deletePlace(primaryStage);
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
        hbox.setSpacing(57);
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonAddPlace, buttonDelPlace, buttonReview, buttonQuit);

        Scene scene = new Scene(border, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addPlace(Stage primaryStage) {
        primaryStage.setTitle("Access Rater - Add Place");
        BorderPane border = new BorderPane();
        VBox[] vboxes = getPlaceList();
        border.setLeft(vboxes[0]);
        border.setCenter(vboxes[1]);
        border.setRight(vboxes[2]);

        Button buttonBack = new Button("Back");
        buttonBack.setPrefSize(100, 20);
        buttonBack.setOnAction((ActionEvent event) -> {
            main(primaryStage);
        });

        Text name = new Text("Enter place name:");
        name.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
        name.setStyle("-fx-fill: #FFFFFF;");

        TextField nameField = new TextField();
        nameField.setPrefSize(100, 20);

        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setPrefSize(100, 20);
        buttonSubmit.setOnAction((ActionEvent event) -> {
            selectType(primaryStage, nameField.getText());
        });

        HBox hbox = getHBox();
        hbox.setSpacing(15);
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonBack, name, nameField, buttonSubmit);

        final Scene scene = new Scene(border, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void selectType(Stage primaryStage, String name) {
        primaryStage.setTitle("Access Rater - Place Type");
        BorderPane border = new BorderPane();

        Button buttonBack = new Button("Back");
        buttonBack.setPrefSize(100, 20);
        buttonBack.setOnAction((ActionEvent event) -> {
            main(primaryStage);
        });

        Text which = new Text("Which type of place is this?");
        which.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
        which.setStyle("-fx-fill: #FFFFFF;");

        HBox hbox = getHBox();
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonBack, which);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(20);

        Button buttonFoodDrink = new Button("Food or drink");
        buttonFoodDrink.setPrefSize(130, 20);
        buttonFoodDrink.setOnAction((ActionEvent event) -> {
            places.add(new Place(name, "FoodDrink", 0, 0));
            main(primaryStage);
        });

        Button buttonTourist = new Button("Tourist attraction");
        buttonTourist.setPrefSize(130, 20);
        buttonTourist.setOnAction((ActionEvent event) -> {
            places.add(new Place(name, "Tourist", 0, 0));
            main(primaryStage);
        });

        Button buttonLifestyle = new Button("Lifestyle");
        buttonLifestyle.setPrefSize(130, 20);
        buttonLifestyle.setOnAction((ActionEvent event) -> {
            places.add(new Place(name, "Lifestyle", 0, 0));
            main(primaryStage);
        });

        height = 100;
        hbox2.getChildren().addAll(buttonFoodDrink, buttonTourist, buttonLifestyle);
        border.setCenter(hbox2);

        final Scene scene = new Scene(border, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void deletePlace(Stage primaryStage) {
        primaryStage.setTitle("Access Rater - Delete Place");
        BorderPane border = new BorderPane();

        Button buttonBack = new Button("Back");
        buttonBack.setPrefSize(100, 20);
        buttonBack.setOnAction((ActionEvent event) -> {
            main(primaryStage);
        });

        Text which = new Text("Which place would you like to delete?");
        which.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
        which.setStyle("-fx-fill: #FFFFFF;");

        HBox hbox = getHBox();
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonBack, which);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        height = 50;
        for (int i=0; i<places.size(); i++) {
            Button b = new Button(places.getPlace(i).getName());
            int j = i;
            b.setOnAction((ActionEvent event) -> {
                places.remove(j);
                main(primaryStage);
            });
            height+=37;
            vbox.getChildren().add(b);
        }
        border.setCenter(vbox);

        final Scene scene = new Scene(border, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addReview(Stage primaryStage) {
        primaryStage.setTitle("Access Rater - Add Review");
        BorderPane border = new BorderPane();

        Button buttonBack = new Button("Back");
        buttonBack.setPrefSize(100, 20);
        buttonBack.setOnAction((ActionEvent event) -> {
            main(primaryStage);
        });

        Text which = new Text("Which place would you like to review?");
        which.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
        which.setStyle("-fx-fill: #FFFFFF;");

        HBox hbox = getHBox();
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonBack, which);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        height = 50;
        for (int i=0; i<places.size(); i++) {
            Button b = new Button(places.getPlace(i).getName());
            int j = i;
            b.setOnAction((ActionEvent event) -> {
                place = j;
                places.getPlace(j).setTicks(0);
                places.getPlace(j).setMaxTicks(0);
                review(primaryStage);
            });
            height+=37;
            vbox.getChildren().add(b);
        }
        border.setCenter(vbox);

        final Scene scene = new Scene(border, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void review(Stage primaryStage) {
        if (disabilities.contains("physical")) {
            physical1(primaryStage);
        } else if (disabilities.contains("hearing")) {
            hearing1(primaryStage);
        } else {
            visual1(primaryStage);
        }
    }

    private void physical1(Stage primaryStage) {
        primaryStage.setTitle("Access Rater - Washrooms");
        BorderPane border = new BorderPane();

        Button buttonBack = new Button("Back");
        buttonBack.setPrefSize(100, 20);
        buttonBack.setOnAction((ActionEvent event) -> {
            addReview(primaryStage);
        });

        Text which = new Text("Does this place have accessible washrooms?");
        which.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
        which.setStyle("-fx-fill: #FFFFFF;");

        HBox hbox = getHBox();
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonBack, which);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(97);

        Button p1 = new Button();
        p1.setPrefSize(50, 20);
        p1.setVisible(false);
        Button p2 = new Button();
        p2.setPrefSize(50, 20);
        p2.setVisible(false);

        Button buttonYes = new Button("Yes");
        buttonYes.setPrefSize(50, 20);
        buttonYes.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addTick();
            places.getPlace(place).addMaxTick();
            physical2(primaryStage);
        });

        Button buttonNo = new Button("No");
        buttonNo.setPrefSize(50, 20);
        buttonNo.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addMaxTick();
            physical2(primaryStage);
        });

        height = 100;
        hbox2.getChildren().addAll(p1, buttonYes, buttonNo, p2);
        border.setCenter(hbox2);

        final Scene scene = new Scene(border, 500, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void physical2(Stage primaryStage) {
        primaryStage.setTitle("Access Rater - Entrances");
        BorderPane border = new BorderPane();

        Button buttonBack = new Button("Back");
        buttonBack.setPrefSize(100, 20);
        buttonBack.setOnAction((ActionEvent event) -> {
            physical1(primaryStage);
        });

        Text which = new Text("Does this place have accessible entrances?");
        which.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
        which.setStyle("-fx-fill: #FFFFFF;");

        HBox hbox = getHBox();
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonBack, which);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(97);

        Button p1 = new Button();
        p1.setPrefSize(50, 20);
        p1.setVisible(false);
        Button p2 = new Button();
        p2.setPrefSize(50, 20);
        p2.setVisible(false);

        Button buttonYes = new Button("Yes");
        buttonYes.setPrefSize(50, 20);
        buttonYes.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addTick();
            places.getPlace(place).addMaxTick();
            physical3(primaryStage);
        });

        Button buttonNo = new Button("No");
        buttonNo.setPrefSize(50, 20);
        buttonNo.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addMaxTick();
            physical3(primaryStage);
        });

        height = 100;
        hbox2.getChildren().addAll(p1, buttonYes, buttonNo, p2);
        border.setCenter(hbox2);

        final Scene scene = new Scene(border, 500, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void physical3(Stage primaryStage) {
        primaryStage.setTitle("Access Rater - Seating");
        BorderPane border = new BorderPane();

        Button buttonBack = new Button("Back");
        buttonBack.setPrefSize(100, 20);
        buttonBack.setOnAction((ActionEvent event) -> {
            physical2(primaryStage);
        });

        Text which = new Text("Does this place have suitable tables/seating?");
        which.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
        which.setStyle("-fx-fill: #FFFFFF;");

        HBox hbox = getHBox();
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonBack, which);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(97);

        Button p1 = new Button();
        p1.setPrefSize(50, 20);
        p1.setVisible(false);
        Button p2 = new Button();
        p2.setPrefSize(50, 20);
        p2.setVisible(false);

        Button buttonYes = new Button("Yes");
        buttonYes.setPrefSize(50, 20);
        buttonYes.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addTick();
            places.getPlace(place).addMaxTick();
            physical4(primaryStage);
        });

        Button buttonNo = new Button("No");
        buttonNo.setPrefSize(50, 20);
        buttonNo.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addMaxTick();
            physical4(primaryStage);
        });

        height = 100;
        hbox2.getChildren().addAll(p1, buttonYes, buttonNo, p2);
        border.setCenter(hbox2);

        final Scene scene = new Scene(border, 500, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void physical4(Stage primaryStage) {
        primaryStage.setTitle("Access Rater - Elevators");
        BorderPane border = new BorderPane();

        Button buttonBack = new Button("Back");
        buttonBack.setPrefSize(100, 20);
        buttonBack.setOnAction((ActionEvent event) -> {
            physical3(primaryStage);
        });

        Text which = new Text("Does this place have elevators, when needed?");
        which.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
        which.setStyle("-fx-fill: #FFFFFF;");

        HBox hbox = getHBox();
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonBack, which);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(97);

        Button p1 = new Button();
        p1.setPrefSize(50, 20);
        p1.setVisible(false);
        Button p2 = new Button();
        p2.setPrefSize(50, 20);
        p2.setVisible(false);

        Button buttonYes = new Button("Yes");
        buttonYes.setPrefSize(50, 20);
        buttonYes.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addTick();
            places.getPlace(place).addMaxTick();
            physical5(primaryStage);
        });

        Button buttonNo = new Button("No");
        buttonNo.setPrefSize(50, 20);
        buttonNo.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addMaxTick();
            physical5(primaryStage);
        });

        height = 100;
        hbox2.getChildren().addAll(p1, buttonYes, buttonNo, p2);
        border.setCenter(hbox2);

        final Scene scene = new Scene(border, 500, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void physical5(Stage primaryStage) {
        primaryStage.setTitle("Access Rater - Parking");
        BorderPane border = new BorderPane();

        Button buttonBack = new Button("Back");
        buttonBack.setPrefSize(100, 20);
        buttonBack.setOnAction((ActionEvent event) -> {
            physical4(primaryStage);
        });

        Text which = new Text("Does this place have disabled parking?");
        which.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
        which.setStyle("-fx-fill: #FFFFFF;");

        HBox hbox = getHBox();
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonBack, which);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(97);

        Button p1 = new Button();
        p1.setPrefSize(50, 20);
        p1.setVisible(false);
        Button p2 = new Button();
        p2.setPrefSize(50, 20);
        p2.setVisible(false);

        Button buttonYes = new Button("Yes");
        buttonYes.setPrefSize(50, 20);
        buttonYes.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addTick();
            places.getPlace(place).addMaxTick();
            if (disabilities.contains("hearing")) {
                hearing1(primaryStage);
            } else if (disabilities.contains("visual")) {
                visual1(primaryStage);
            } else {
                guide(primaryStage);
            }
        });

        Button buttonNo = new Button("No");
        buttonNo.setPrefSize(50, 20);
        buttonNo.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addMaxTick();
            if (disabilities.contains("hearing")) {
                hearing1(primaryStage);
            } else if (disabilities.contains("visual")) {
                visual1(primaryStage);
            } else {
                guide(primaryStage);
            }
        });

        height = 100;
        hbox2.getChildren().addAll(p1, buttonYes, buttonNo, p2);
        border.setCenter(hbox2);

        final Scene scene = new Scene(border, 500, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void hearing1(Stage primaryStage) {
        primaryStage.setTitle("Access Rater - ASL training");
        BorderPane border = new BorderPane();

        Button buttonBack = new Button("Back");
        buttonBack.setPrefSize(100, 20);
        buttonBack.setOnAction((ActionEvent event) -> {
            if (disabilities.contains("physical")) {
                physical5(primaryStage);
            } else {
                addReview(primaryStage);
            }
        });

        Text which = new Text("Does this place have ASL-trained employees?");
        which.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
        which.setStyle("-fx-fill: #FFFFFF;");

        HBox hbox = getHBox();
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonBack, which);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(97);

        Button p1 = new Button();
        p1.setPrefSize(50, 20);
        p1.setVisible(false);
        Button p2 = new Button();
        p2.setPrefSize(50, 20);
        p2.setVisible(false);

        Button buttonYes = new Button("Yes");
        buttonYes.setPrefSize(50, 20);
        buttonYes.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addTick();
            places.getPlace(place).addMaxTick();
            hearing2(primaryStage);
        });

        Button buttonNo = new Button("No");
        buttonNo.setPrefSize(50, 20);
        buttonNo.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addMaxTick();
            hearing2(primaryStage);
        });

        height = 100;
        hbox2.getChildren().addAll(p1, buttonYes, buttonNo, p2);
        border.setCenter(hbox2);

        final Scene scene = new Scene(border, 500, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void hearing2(Stage primaryStage) {
        primaryStage.setTitle("Access Rater - Visual Tools");
        BorderPane border = new BorderPane();

        Button buttonBack = new Button("Back");
        buttonBack.setPrefSize(100, 20);
        buttonBack.setOnAction((ActionEvent event) -> {
            hearing1(primaryStage);
        });

        Text which = new Text("Does this place have any visual tools for communication (e.g. tablets?)");
        which.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
        which.setStyle("-fx-fill: #FFFFFF;");

        HBox hbox = getHBox();
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonBack, which);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(97);

        Button p1 = new Button();
        p1.setPrefSize(50, 20);
        p1.setVisible(false);
        Button p2 = new Button();
        p2.setPrefSize(50, 20);
        p2.setVisible(false);

        Button buttonYes = new Button("Yes");
        buttonYes.setPrefSize(50, 20);
        buttonYes.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addTick();
            places.getPlace(place).addMaxTick();
            hearing3(primaryStage);
        });

        Button buttonNo = new Button("No");
        buttonNo.setPrefSize(50, 20);
        buttonNo.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addMaxTick();
            hearing3(primaryStage);
        });

        height = 100;
        hbox2.getChildren().addAll(p1, buttonYes, buttonNo, p2);
        border.setCenter(hbox2);

        final Scene scene = new Scene(border, 680, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void hearing3(Stage primaryStage) {
        primaryStage.setTitle("Access Rater - Lighting");
        BorderPane border = new BorderPane();

        Button buttonBack = new Button("Back");
        buttonBack.setPrefSize(100, 20);
        buttonBack.setOnAction((ActionEvent event) -> {
            hearing2(primaryStage);
        });

        Text which = new Text("Is this place well lit (for lip reading?)");
        which.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
        which.setStyle("-fx-fill: #FFFFFF;");

        HBox hbox = getHBox();
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonBack, which);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(97);

        Button p1 = new Button();
        p1.setPrefSize(50, 20);
        p1.setVisible(false);
        Button p2 = new Button();
        p2.setPrefSize(50, 20);
        p2.setVisible(false);

        Button buttonYes = new Button("Yes");
        buttonYes.setPrefSize(50, 20);
        buttonYes.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addTick();
            places.getPlace(place).addMaxTick();
            hearing4(primaryStage);
        });

        Button buttonNo = new Button("No");
        buttonNo.setPrefSize(50, 20);
        buttonNo.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addMaxTick();
            hearing4(primaryStage);
        });

        height = 100;
        hbox2.getChildren().addAll(p1, buttonYes, buttonNo, p2);
        border.setCenter(hbox2);

        final Scene scene = new Scene(border, 500, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void hearing4(Stage primaryStage) {
        primaryStage.setTitle("Access Rater - Employees");
        BorderPane border = new BorderPane();

        Button buttonBack = new Button("Back");
        buttonBack.setPrefSize(100, 20);
        buttonBack.setOnAction((ActionEvent event) -> {
            hearing3(primaryStage);
        });

        Text which = new Text("Do employees try their best to communicate with you?");
        which.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
        which.setStyle("-fx-fill: #FFFFFF;");

        HBox hbox = getHBox();
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonBack, which);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(97);

        Button p1 = new Button();
        p1.setPrefSize(50, 20);
        p1.setVisible(false);
        Button p2 = new Button();
        p2.setPrefSize(50, 20);
        p2.setVisible(false);

        Button buttonYes = new Button("Yes");
        buttonYes.setPrefSize(50, 20);
        buttonYes.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addTick();
            places.getPlace(place).addMaxTick();
            if (disabilities.contains("visual")) {
                visual1(primaryStage);
            } else {
                guide(primaryStage);
            }
        });

        Button buttonNo = new Button("No");
        buttonNo.setPrefSize(50, 20);
        buttonNo.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addMaxTick();
            if (disabilities.contains("visual")) {
                visual1(primaryStage);
            } else {
                guide(primaryStage);
            }
        });

        height = 100;
        hbox2.getChildren().addAll(p1, buttonYes, buttonNo, p2);
        border.setCenter(hbox2);

        final Scene scene = new Scene(border, 640, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void visual1(Stage primaryStage) {
        primaryStage.setTitle("Access Rater - Lighting");
        BorderPane border = new BorderPane();

        Button buttonBack = new Button("Back");
        buttonBack.setPrefSize(100, 20);
        buttonBack.setOnAction((ActionEvent event) -> {
            if (disabilities.contains("hearing")) {
                hearing4(primaryStage);
            } else if (disabilities.contains("physical")) {
                physical5(primaryStage);
            } else {
                addReview(primaryStage);
            }
        });

        Text which = new Text("Is this place well lit and navigable?");
        which.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
        which.setStyle("-fx-fill: #FFFFFF;");

        HBox hbox = getHBox();
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonBack, which);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(97);

        Button p1 = new Button();
        p1.setPrefSize(50, 20);
        p1.setVisible(false);
        Button p2 = new Button();
        p2.setPrefSize(50, 20);
        p2.setVisible(false);

        Button buttonYes = new Button("Yes");
        buttonYes.setPrefSize(50, 20);
        buttonYes.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addTick();
            places.getPlace(place).addMaxTick();
            visual2(primaryStage);
        });

        Button buttonNo = new Button("No");
        buttonNo.setPrefSize(50, 20);
        buttonNo.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addMaxTick();
            visual2(primaryStage);
        });

        height = 100;
        hbox2.getChildren().addAll(p1, buttonYes, buttonNo, p2);
        border.setCenter(hbox2);

        final Scene scene = new Scene(border, 500, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void visual2(Stage primaryStage) {
        primaryStage.setTitle("Access Rater - Braille or large text");
        BorderPane border = new BorderPane();

        Button buttonBack = new Button("Back");
        buttonBack.setPrefSize(100, 20);
        buttonBack.setOnAction((ActionEvent event) -> {
            visual1(primaryStage);
        });

        Text which = new Text("Does this place have Braille or large text signs?");
        which.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
        which.setStyle("-fx-fill: #FFFFFF;");

        HBox hbox = getHBox();
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonBack, which);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(97);

        Button p1 = new Button();
        p1.setPrefSize(50, 20);
        p1.setVisible(false);
        Button p2 = new Button();
        p2.setPrefSize(50, 20);
        p2.setVisible(false);

        Button buttonYes = new Button("Yes");
        buttonYes.setPrefSize(50, 20);
        buttonYes.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addTick();
            places.getPlace(place).addMaxTick();
            visual3(primaryStage);
        });

        Button buttonNo = new Button("No");
        buttonNo.setPrefSize(50, 20);
        buttonNo.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addMaxTick();
            visual3(primaryStage);
        });

        height = 100;
        hbox2.getChildren().addAll(p1, buttonYes, buttonNo, p2);
        border.setCenter(hbox2);

        final Scene scene = new Scene(border, 500, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void visual3(Stage primaryStage) {
        primaryStage.setTitle("Access Rater - Audio Tools");
        BorderPane border = new BorderPane();

        Button buttonBack = new Button("Back");
        buttonBack.setPrefSize(100, 20);
        buttonBack.setOnAction((ActionEvent event) -> {
            visual2(primaryStage);
        });

        Text which = new Text("Does this place have audio tools available?");
        which.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
        which.setStyle("-fx-fill: #FFFFFF;");

        HBox hbox = getHBox();
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonBack, which);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(97);

        Button p1 = new Button();
        p1.setPrefSize(50, 20);
        p1.setVisible(false);
        Button p2 = new Button();
        p2.setPrefSize(50, 20);
        p2.setVisible(false);

        Button buttonYes = new Button("Yes");
        buttonYes.setPrefSize(50, 20);
        buttonYes.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addTick();
            places.getPlace(place).addMaxTick();
            visual4(primaryStage);
        });

        Button buttonNo = new Button("No");
        buttonNo.setPrefSize(50, 20);
        buttonNo.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addMaxTick();
            visual4(primaryStage);
        });

        height = 100;
        hbox2.getChildren().addAll(p1, buttonYes, buttonNo, p2);
        border.setCenter(hbox2);

        final Scene scene = new Scene(border, 500, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void visual4(Stage primaryStage) {
        primaryStage.setTitle("Access Rater - Employees");
        BorderPane border = new BorderPane();

        Button buttonBack = new Button("Back");
        buttonBack.setPrefSize(100, 20);
        buttonBack.setOnAction((ActionEvent event) -> {
            visual3(primaryStage);
        });

        Text which = new Text("Do employees offer to guide you around?");
        which.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
        which.setStyle("-fx-fill: #FFFFFF;");

        HBox hbox = getHBox();
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonBack, which);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(97);

        Button p1 = new Button();
        p1.setPrefSize(50, 20);
        p1.setVisible(false);
        Button p2 = new Button();
        p2.setPrefSize(50, 20);
        p2.setVisible(false);

        Button buttonYes = new Button("Yes");
        buttonYes.setPrefSize(50, 20);
        buttonYes.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addTick();
            places.getPlace(place).addMaxTick();
            guide(primaryStage);
        });

        Button buttonNo = new Button("No");
        buttonNo.setPrefSize(50, 20);
        buttonNo.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addMaxTick();
            guide(primaryStage);
        });

        height = 100;
        hbox2.getChildren().addAll(p1, buttonYes, buttonNo, p2);
        border.setCenter(hbox2);

        final Scene scene = new Scene(border, 500, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void guide(Stage primaryStage) {
        primaryStage.setTitle("Access Rater - Service Animals");
        BorderPane border = new BorderPane();

        Button buttonBack = new Button("Back");
        buttonBack.setPrefSize(100, 20);
        buttonBack.setOnAction((ActionEvent event) -> {
            physical5(primaryStage);
        });

        Text which = new Text("Does this place allow guide dogs or other service animals?");
        which.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16 ));
        which.setStyle("-fx-fill: #FFFFFF;");

        HBox hbox = getHBox();
        border.setTop(hbox);
        hbox.getChildren().addAll(buttonBack, which);

        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(10));
        hbox2.setSpacing(97);

        Button p1 = new Button();
        p1.setPrefSize(50, 20);
        p1.setVisible(false);
        Button p2 = new Button();
        p2.setPrefSize(50, 20);
        p2.setVisible(false);

        Button buttonYes = new Button("Yes");
        buttonYes.setPrefSize(50, 20);
        buttonYes.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addTick();
            places.getPlace(place).addMaxTick();
            main(primaryStage);
        });

        Button buttonNo = new Button("No");
        buttonNo.setPrefSize(50, 20);
        buttonNo.setOnAction((ActionEvent event) -> {
            places.getPlace(place).addMaxTick();
            main(primaryStage);
        });

        height = 100;
        hbox2.getChildren().addAll(p1, buttonYes, buttonNo, p2);
        border.setCenter(hbox2);

        final Scene scene = new Scene(border, 600, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        LoadSave ls = new LoadSave();
        try {
            ls.save(disabilities, places.getPlaces(), "places.txt");
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

    private VBox[] getPlaceList() {
        VBox left = new VBox();
        VBox middle = new VBox();
        VBox right = new VBox();
        left.setPadding(new Insets(10));
        left.setSpacing(8);
        middle.setPadding(new Insets(10));
        middle.setSpacing(8);
        right.setPadding(new Insets(10));
        right.setSpacing(8);

        int lheight = 90;
        int cheight = 90;
        int rheight = 90;

        Label l = new Label("--- Food and Drink ---");
        l.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        left.getChildren().add(l);
        Label c = new Label("--- Tourist Attractions ---");
        c.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        middle.getChildren().add(c);
        Label r = new Label("--- Lifestyle ---");
        r.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        right.getChildren().add(r);

        if (places.size() > 0) {
            for (Place place : places.getPlaces()) {
                Label name = new Label(place.getName());
                name.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
                Label score = new Label(place.printScore());
                score.setTextFill(Color.web(place.scoreColor()));
                System.out.println(place.scoreColor());
                if (place.getType().equals("FoodDrink")) {
                    left.getChildren().addAll(name, score);
                    lheight += 55;
                }
                if (place.getType().equals("Tourist")) {
                    middle.getChildren().addAll(name, score);
                    cheight += 55;
                }
                if (place.getType().equals("Lifestyle")) {
                    right.getChildren().addAll(name, score);
                    rheight += 55;
                }
            }
        }

        height = Math.max(lheight, cheight);
        height = Math.max(height, rheight);
        height = Math.max(height, 250);
        return new VBox[]{left, middle, right};
    }
}
