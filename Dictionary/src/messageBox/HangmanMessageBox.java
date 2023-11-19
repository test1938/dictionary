package messageBox;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HangmanMessageBox extends MessageBox {
    public Image image;
    public Label message1;
    public Label message2;
    public HangmanMessageBox() {
        super();
    }
    public boolean show(boolean isWin, String word) {

        if (isWin) {
            image = new Image("image/victory.gif");
            message1 = new Label("Congrats!");
            message2 = new Label("You found the word: " + word);
        } else {
            image = new Image("image/lost.gif");
            message1 = new Label("Game Over!");
            message2 = new Label("The correct word was: " + word);
        }

        return show();
    }

    @Override
    public boolean show() {
        message1.getStyleClass().add("message1");
        message2.getStyleClass().add("message2");

        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(200);

        Button playAgainBtn = new Button("Play Again");
        playAgainBtn.getStyleClass().add("hangman-play-again-btn");
        playAgainBtn.setOnAction(e -> {
            stage.close();
        });

        VBox container = new VBox(30, imageView, message1, message2, playAgainBtn);
        container.setAlignment(Pos.CENTER);
        scene = new Scene(container, 700, 500);
        scene.getStylesheets().add("style/stylesheet.css");
        stage.setScene(scene);
        stage.showAndWait();
        return true;
    }

}
