package game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import messageBox.HangmanMessageBox;
import words.*;
import java.util.Random;
import layouts.*;

public class hangman extends game {
    public String exactCurWord = "";
    public ImageView imgViewHangman;
    public Label currentWord;
    public Label hint;
    public Label incorrectGuess;
    public Button[] button;
    public int[] count;
    public String curWord;
    public String curHint;
    public String answer;
    public HangmanMessageBox hangmanMessageBox = new HangmanMessageBox();
    @Override public BorderPane init() {
        curWord = "";

        int wordListSize = words.getWordList().size();
        Random rand = new Random();
        int randomIndex = rand.nextInt(wordListSize);
        answer = words.getWordList().get(randomIndex);
        System.out.println(answer);
        curHint = words.getDefinition(answer);
        for (int i = 0; i < answer.length(); ++ i) {
            exactCurWord += "_";
            curWord += "_";
            if (i != answer.length() - 1) {
                curWord += " ";
            }
        }

        Image imgHangman = new Image("image/hangman-0.png");
        imgViewHangman = new ImageView();
        imgViewHangman.setImage(imgHangman);
        HBox hboxHangman = new HBox(imgViewHangman);
        hboxHangman.setAlignment(Pos.CENTER);

        Label lblHangman = new Label("HANGMAN GAME");
        lblHangman.getStyleClass().add("label-hangman");
        VBox vboxHangman = new VBox(30, hboxHangman, lblHangman);
        vboxHangman.setAlignment(Pos.CENTER);
        vboxHangman.getStyleClass().add("vbox-hangman");

        currentWord = new Label(curWord);
        currentWord.getStyleClass().add("game-current-word");

        hint = new Label(curHint);
        hint.getStyleClass().add("game-text");
        hint.setWrapText(true);
        incorrectGuess = new Label("Incorrect guesses: 0/6");
        incorrectGuess.getStyleClass().add("game-text");

        count = new int[]{0};
        button = new Button[26];
        for (int i = 0; i < 26; ++ i) {
            final int index = i;
            char letter = (char)('A' + i);
            button[i] = new Button("" + letter);
            button[i].getStyleClass().add("game-button");
            button[i].setOnAction(e -> {
                button[index].getStyleClass().clear();
                button[index].getStyleClass().add("pressed");
                button[index].setAlignment(Pos.CENTER);

                boolean isRight = false;
                StringBuilder sb = new StringBuilder(curWord);
                StringBuilder sb2 = new StringBuilder(exactCurWord);
                for (int pos = 0; pos < answer.length(); ++ pos) {
                    String tmp = "" + answer.charAt(pos);
                    if (button[index].getText().toLowerCase().equals(tmp.toLowerCase())) {
                        isRight = true;
                        sb.setCharAt(2 * pos, answer.charAt(pos));
                        sb2.setCharAt(pos,answer.charAt(pos));
                    }
                }
                exactCurWord = sb2.toString();
                curWord = sb.toString();
                currentWord.setText(curWord);
                if (exactCurWord.equals(answer)) {
                    hangmanMessageBox.show(true, answer);
                    reset();
                }
                if (isRight == false) {
                    count[0]++;
                    incorrectGuess.setText("Incorrect guesses: " + count[0] + "/6");
                    final String path = "image/hangman-" + count[0] + ".png";
                    Image image = new Image(path);
                    imgViewHangman.setImage(image);
                    if (count[0] == 6) {
                        hangmanMessageBox.show(false,answer);
                        reset();
                    }
                }
            });
        }

        HBox row1 = new HBox(10);
        for (int i = 0; i < 9; ++ i) {
            row1.getChildren().add(button[i]);
        }
        row1.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(10);
        for (int i = 9; i < 18; ++ i) {
            row2.getChildren().add(button[i]);
        }
        row2.setAlignment(Pos.CENTER);

        HBox row3 = new HBox(10);
        for (int i = 18; i < 26; ++ i) {
            row3.getChildren().add(button[i]);
        }
        row3.setAlignment(Pos.CENTER);

        VBox gameButton = new VBox(10, row1, row2, row3);
        VBox container = new VBox(30, currentWord, hint, incorrectGuess, gameButton);
        container.setAlignment(Pos.CENTER);

        HBox gameContainer = new HBox(50, vboxHangman, container);
        gameContainer.getStyleClass().add("game-container");

        Button btnExit = new Button("Exit");
        btnExit.getStyleClass().add("game-exit-btn");
        btnExit.setOnAction(e -> {
            reset();
            Main.switchScene(e);
        });

        HBox btnExitContainer = new HBox(btnExit);
        btnExitContainer.setPadding(new Insets(20, 0, 0 ,20));
        btnExitContainer.setAlignment(Pos.TOP_LEFT);

        BorderPane screen = new BorderPane();
        screen.setCenter(gameContainer);
        screen.setTop(btnExitContainer);
        screen.getStyleClass().add("screen");

        return screen;
    }


    @Override public void reset() {
        int wordListSize = words.getWordList().size();
        Random rand = new Random();
        int randomIndex = rand.nextInt(wordListSize);
        answer = words.getWordList().get(randomIndex);
        System.out.println(answer);
        curHint = words.getDefinition(answer);
        exactCurWord = "";
        curWord = "";
        for (int i = 0; i < answer.length(); ++ i) {
            exactCurWord += "_";
            curWord += "_";
            if (i != answer.length() - 1) {
                curWord += " ";
            }
        }
        Image imgHangman = new Image("image/hangman-0.png");
        imgViewHangman.setImage(imgHangman);
        currentWord.setText(curWord);
        hint.setText(curHint);
        incorrectGuess.setText("Incorrect guesses: 0/6");

        for (int index = 0; index < 26; ++ index) {
            button[index].getStyleClass().clear();
            button[index].getStyleClass().add("game-button");
            button[index].setAlignment(Pos.CENTER);
        }

        count = new int[] {0};
    }

    public boolean isWin() {
        return true;
    }
}