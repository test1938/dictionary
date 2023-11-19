package messageBox;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import game.*;

public class WordSrambleMessageBox extends MessageBox {
    public WordSrambleMessageBox() {
        super();
    }
    Button btnOK;
    public Label message;
    public boolean show(boolean isWin, String word, boolean isTimeoff) {
        if (isWin) {
            message = new Label("Congrats! " + word.toUpperCase() + " is the correct word");
        } else {
            if (isTimeoff) {
                message = new Label("Timeoff! " + word.toUpperCase() + " was the correct word");
            } else {
                message = new Label("Oops! " + word.toLowerCase() + " is not the correct word");
            }
        }
        btnOK = new Button("OK");
        btnOK.setOnAction(e -> {
            if (isWin || isTimeoff) {
                wordScramble.resetOutside();
                stage.close();
            }
            stage.close();
        });
        return show();
    }

    @Override
    public boolean show() {

        message.setStyle("-fx-font-size: 20");
        message.setPadding(new Insets(30));
        HBox messageContainer = new HBox(message);
        messageContainer.setAlignment(Pos.TOP_LEFT);
        HBox btnContainer = new HBox(btnOK);
        btnOK.getStyleClass().add("btn-word-scramble-message");

        btnContainer.setPadding(new Insets(30));
        btnContainer.setAlignment(Pos.BOTTOM_RIGHT);

        VBox container = new VBox(10, messageContainer, btnContainer);
        container.setStyle("-fx-background-color: #F6DAE1");
        scene = new Scene(container, 500, 200);
        scene.getStylesheets().add("style/stylesheet.css");
        stage.setScene(scene);
        stage.showAndWait();
        return true;
    }
}
