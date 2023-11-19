package layouts;

import game.wordScramble;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.stage.*;
import game.*;
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public static Home home = new Home();
    public static TranslateLayout translateLayout = new TranslateLayout();
    public static addWordLayout addWordLayout = new addWordLayout();
    public static gameLayout gameLayout = new gameLayout();
    public static hangman hangmanLayout = new hangman();
    public static wordScramble wordScrambleLayout = new wordScramble();
    public static Scene translateScene = new Scene(translateLayout.create(), 1200, 700);
    public static Scene homeScene = new Scene(home.create(), 1200, 700);

    public static Scene addWordScene = new Scene(addWordLayout.create(), 1200, 700);
    public static Scene hangmanScene = new Scene(hangmanLayout.init(), 1200, 700);
    public static Scene wordScrambleScene = new Scene(wordScrambleLayout.init(), 1200, 700);
    public static Scene gameScene = new Scene(gameLayout.create(), 1200, 700);
    public static Stage stage;
    public static void switchScene(ActionEvent e) {
        Button b = (Button) e.getSource();
        if (b.getText().equals("Translate")) {
            stage.setScene(translateScene);
        } else if (b.getText().equals("Home")) {
            stage.setScene(homeScene);
        } else if (b.getText().equals("Add")) {
            stage.setScene(addWordScene);
        } else if (b.getText().equals("Game")) {
            stage.setScene(gameScene);
        } else if (b.getText().equals("Hangman")) {
            stage.setScene(hangmanScene);
        } else if (b.getText().equals("Word Scramble")) {
            wordScramble.startTimer();
            stage.setScene(wordScrambleScene);
        } else if (b.getText().equals("Exit")) {
            stage.setScene(gameScene);
        }
    }
    @Override public void start(Stage primaryStage) {
        stage = primaryStage;
        translateScene.getStylesheets().add("style/stylesheet.css");
        homeScene.getStylesheets().add("style/stylesheet.css");
        addWordScene.getStylesheets().add("style/stylesheet.css");
        gameScene.getStylesheets().add("style/stylesheet.css");
        hangmanScene.getStylesheets().add("style/stylesheet.css");
        wordScrambleScene.getStylesheets().add("style/stylesheet.css");
        stage.setTitle("Dictionary");
        stage.setScene(homeScene);
        stage.show();
    }

}