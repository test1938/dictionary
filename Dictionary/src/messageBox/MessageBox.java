package messageBox;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class MessageBox {
    public Stage stage;
    public Scene scene;
    public MessageBox() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
    }
    public abstract boolean show();
}
