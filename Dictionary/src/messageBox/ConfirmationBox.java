package messageBox;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.event.*;
import javafx.geometry.*;
public class ConfirmationBox extends MessageBox
{
    public boolean btnYesClicked;
    public ConfirmationBox() {
        super();
    }
    public Button btnYes;
    public Button btnNo;
    public Label lbl;
    public HBox paneBtn;
    public boolean show(String message, String title, String textYes, String textNo)
    {
        stage.setTitle(title);
        lbl = new Label();
        lbl.setText(message);

        btnYes = new Button();
        btnYes.setText(textYes);

        btnNo = new Button();
        btnNo.setText(textNo);

        paneBtn = new HBox(40);

        if (textNo.equals("")) {
            btnNo.setVisible(false);
            paneBtn.getChildren().addAll(btnYes);
            paneBtn.setAlignment(Pos.CENTER);
        } else {
            paneBtn.getChildren().addAll(btnYes, btnNo);
            paneBtn.setAlignment(Pos.CENTER);
        }

        return show();
    }
    public boolean show() {
        btnYesClicked = false;
        lbl.setStyle("-fx-font-size: 30;" + "-fx-font-weight: bold");

        btnYes.getStyleClass().add("confirmation-box-btn");
        btnYes.setOnAction(e -> btnYes_Clicked() );
        btnYes.setStyle("-fx-font-size: 20;" + "-fx-background-color: #DD5577;" + "-fx-border-radius: 20;" + "-fx-text-fill:white;");

        btnNo.getStyleClass().add("confirmation-box-btn");
        btnNo.setOnAction(e -> btnNo_Clicked() );
        btnNo.setStyle("-fx-font-size: 20;" + "-fx-background-color: #DD5577;" + "-fx-border-radius: 20;" + "-fx-text-fill:white;");



        VBox pane = new VBox(30);
        pane.getChildren().addAll(lbl, paneBtn);
        pane.setAlignment(Pos.CENTER);
        pane.setStyle("-fx-background-color: #F6DAE1");
        scene = new Scene(pane, 400, 150);
        scene.getStylesheets().add("style/stylesheet.css");
        stage.setScene(scene);
        stage.showAndWait();
        return btnYesClicked;
    }
    private void btnYes_Clicked()
    {
        stage.close();
        btnYesClicked = true;
    }
    private void btnNo_Clicked()
    {
        stage.close();
        btnYesClicked = false;
    }
}
