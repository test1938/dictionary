package game;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import messageBox.WordSrambleMessageBox;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import words.*;
import layouts.*;
public class wordScramble extends game {
    public static String scrambleWord = "";
    public static Label lblword;
    public static Label hint;
    public static TextField txtFieldWord;
    public static Timer timer;
    public static Label time;
    public static int secondLeft;
    public static String curWord;
    public static String curHint;
    public static String answer;
    public static WordSrambleMessageBox wordSrambleMessageBox = new WordSrambleMessageBox();
    @Override public BorderPane init() {
        curWord = "";

        int wordListSize = words.getWordList().size();
        Random rand = new Random();
        int randomIndex = rand.nextInt(wordListSize);
        answer = words.getWordList().get(randomIndex);
        System.out.println(answer);
        curHint = "Hint: " + words.getDefinition(answer);
        scrambleWord = scramble(answer);

        for (int i = 0; i < scrambleWord.length(); ++ i) {
            curWord += scrambleWord.charAt(i);
            if (i != scrambleWord.length() - 1) {
                curWord += " ";
            }
        }



        Label title = new Label("Word Scramble");
        title.getStyleClass().add("word-scramble-title");
        Line hline = new Line(0, 0, 599, 0);
        VBox titleContainer = new VBox(title, hline);
        titleContainer.setAlignment(Pos.TOP_LEFT);

        lblword = new Label(curWord);
        lblword.getStyleClass().add("word-scramble-word");

        hint = new Label(curHint);
        hint.getStyleClass().add("word-scramble-text");
        hint.setWrapText(true);

        time = new Label("Time left: 30s");
        time.getStyleClass().add("word-scramble-text");
        secondLeft = 31;
        timer = new Timer();

        VBox txtContainer = new VBox(20, hint, time);
        txtContainer.setPadding(new Insets(0, 30, 0, 30));
        txtContainer.setAlignment(Pos.CENTER_LEFT);

        txtFieldWord = new TextField();
        txtFieldWord.getStyleClass().add("word-scramble-textField");
        txtFieldWord.setPromptText("Enter a valid word");
        txtFieldWord.setFocusTraversable(false);
        Button btnRefresh = new Button("Refresh Word");
        btnRefresh.getStyleClass().add("word-scramble-button");
        btnRefresh.setStyle("-fx-background-color: #A76A81");
        btnRefresh.setOnAction(e -> {
            reset();
        });
        Button btnCheck = new Button("Check Word");
        btnCheck.getStyleClass().add("word-scramble-button");
        btnCheck.setStyle("-fx-background-color: #946C9B");
        btnCheck.setOnAction(e -> {
            if (txtFieldWord.getText().toLowerCase().equals(answer.toLowerCase())) {
                timer.cancel();
                wordSrambleMessageBox.show(true, answer.toUpperCase(), true);
            } else {
                timer.cancel();
                wordSrambleMessageBox.show(false, txtFieldWord.getText(), false);
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    public void run() {
                        Platform.runLater(() -> time.setText("Time left: " + secondLeft));
                        secondLeft--;
                        if (secondLeft == 0) {
                            timer.cancel();
                            Platform.runLater(() -> wordSrambleMessageBox.show(false, answer, true));
                        }
                    }
                }, 0, 1000);
            }
        });
        HBox buttonContainer = new HBox(20, btnRefresh, btnCheck);
        buttonContainer.setPadding(new Insets(0, 30, 0, 30));
        buttonContainer.setAlignment(Pos.CENTER);

        VBox game = new VBox(30, titleContainer, lblword, txtContainer, txtFieldWord, buttonContainer);
        game.setAlignment(Pos.TOP_CENTER);
        game.getStyleClass().add("word-scramble-game");

        Button btnExit = new Button("Exit");
        btnExit.getStyleClass().add("game-exit-btn");
        btnExit.setOnAction(e -> {
            timer.cancel();
            resetWithoutTimer();
            Main.switchScene(e);
        });
        HBox btnExitContainer = new HBox(btnExit);
        btnExitContainer.setPadding(new Insets(20, 0, 0 ,20));
        btnExitContainer.setAlignment(Pos.TOP_LEFT);
        BorderPane screen = new BorderPane();
        screen.setCenter(game);
        screen.setTop(btnExitContainer);
        screen.getStyleClass().add("word-scramble-screen");

        return screen;
    }

    public static String scramble(String word) {
        Random rand = new Random();
        String scrambleString = "";
        ArrayList<Character> a = new ArrayList<>();
        for (int i = 0; i < word.length(); ++ i) {
            a.add(word.charAt(i));
        }
        for (int i = 0; i < word.length(); ++ i) {
            int randomIndex = rand.nextInt(a.size());
            scrambleString += a.get(randomIndex);
            a.remove(randomIndex);
        }
        return scrambleString.toUpperCase();
    }
    @Override public void reset() {
        curWord = "";
        int wordListSize = words.getWordList().size();
        Random rand = new Random();
        int randomIndex = rand.nextInt(wordListSize);
        answer = words.getWordList().get(randomIndex);
        System.out.println(answer);
        curHint = "Hint: " + words.getDefinition(answer);
        scrambleWord = scramble(answer);

        for (int i = 0; i < scrambleWord.length(); ++ i) {
            curWord += scrambleWord.charAt(i);
            if (i != scrambleWord.length() - 1) {
                curWord += " ";
            }
        }
        lblword.setText(curWord);
        hint.setText(curHint);
        txtFieldWord.setText("");
        secondLeft = 31;
        timer.cancel();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> time.setText("Time left: " + secondLeft));
                secondLeft--;
                if (secondLeft == 0) {
                    timer.cancel();
                    Platform.runLater(() -> wordSrambleMessageBox.show(false, answer, true));
                }
            }
        }, 0, 1000);
    }

    public static void resetOutside() {
        curWord = "";
        int wordListSize = words.getWordList().size();
        Random rand = new Random();
        int randomIndex = rand.nextInt(wordListSize);
        answer = words.getWordList().get(randomIndex);
        System.out.println(answer);
        curHint = "Hint: " + words.getDefinition(answer);
        scrambleWord = scramble(answer);

        for (int i = 0; i < scrambleWord.length(); ++ i) {
            curWord += scrambleWord.charAt(i);
            if (i != scrambleWord.length() - 1) {
                curWord += " ";
            }
        }
        lblword.setText(curWord);
        hint.setText(curHint);
        txtFieldWord.setText("");
        secondLeft = 31;
        timer.cancel();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> time.setText("Time left: " + secondLeft));
                secondLeft--;
                if (secondLeft == 0) {
                    timer.cancel();
                    Platform.runLater(() -> wordSrambleMessageBox.show(false, answer, true));
                }
            }
        }, 0, 1000);
    }

    public static void startTimer() {
        secondLeft = 31;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.setImplicitExit(true);
                Platform.runLater(() -> time.setText("Time left: " + secondLeft));
                secondLeft--;
                if (secondLeft == 0) {
                    timer.cancel();
                    timer.purge();
                    Platform.runLater(() -> wordSrambleMessageBox.show(false, answer, true));
                }
            }
        }, 0, 1000);
    }

    public void resetWithoutTimer() {
        curWord = "";
        int wordListSize = words.getWordList().size();
        Random rand = new Random();
        int randomIndex = rand.nextInt(wordListSize);
        answer = words.getWordList().get(randomIndex);
        System.out.println(answer);
        curHint = "Hint: " + words.getDefinition(answer);
        scrambleWord = scramble(answer);

        for (int i = 0; i < scrambleWord.length(); ++ i) {
            curWord += scrambleWord.charAt(i);
            if (i != scrambleWord.length() - 1) {
                curWord += " ";
            }
        }
        lblword.setText(curWord);
        hint.setText(curHint);
        txtFieldWord.setText("");
        secondLeft = 31;
    }
}
