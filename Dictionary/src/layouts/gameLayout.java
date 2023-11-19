package layouts;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class gameLayout extends Component{
    @Override public BorderPane create() {
        Button btnHangman = new Button("", createIcon("image/hangman.png", 300));
        btnHangman.getStyleClass().add("game-layout-button");
        btnHangman.setOnAction(e -> {
            btnHangman.setText("Hangman");
            Main.switchScene(e);
            btnHangman.setText("");
        });
        Label lblHangman = new Label("HANGMAN");
        lblHangman.getStyleClass().add("game-layout-lbl");

        VBox hangmanContainer = new VBox(btnHangman, lblHangman);
        hangmanContainer.setAlignment(Pos.CENTER);
        hangmanContainer.setPadding(new Insets(0, 0, 0 , 40));

        Button btnWordScramble = new Button("", createIcon("image/wordScramble.png", 300));
        btnWordScramble.getStyleClass().add("game-layout-button");
        btnWordScramble.setOnAction(e -> {
            btnWordScramble.setText("Word Scramble");
            Main.switchScene(e);
            btnWordScramble.setText("");
        });
        Label lblWordScramble = new Label("WORD SCRAMBLE");
        lblWordScramble.getStyleClass().add("game-layout-lbl");

        VBox wordScrambleContainer = new VBox(btnWordScramble, lblWordScramble);
        wordScrambleContainer.setAlignment(Pos.CENTER);

        HBox container = new HBox(40, hangmanContainer, wordScrambleContainer);

        NavBar navBar = new NavBar();
        BorderPane screen = new BorderPane();
        screen.getStyleClass().add("game-layout-screen");
        screen.setCenter(container);
        screen.setTop(navBar.create());
        //container.setAlignment(Pos.CENTER);
        return screen;
    }

    @Override
    public void handleEvent() {

    }

    public ImageView createIcon (String url, int size) {
        Image img = new Image(url);
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(size);
        imgView.setPreserveRatio(true);

        return imgView;
    }
}
