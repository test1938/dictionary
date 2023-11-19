package layouts;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import messageBox.ConfirmationBox;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import voiceAPI.*;

public class wordInformation extends Component{
    static Label wordName;
    static Label ipa;
    static Button btnUpdate;
    static Button btnDelete;
    static VBox wordInformation;
    static Label wordType;
    static Label USSpell;
    static Label UKSpell;
    static Button btnUK;
    static Button btnUS;
    static Text definition;
    static Text example;
    Connection myConn = null;
    Statement myStmt = null;
    ResultSet myRs = null;
    public ConfirmationBox confirmationBox = new ConfirmationBox();
    @Override public VBox create() {
        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dictionary2", "student", "student");
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        wordName = new Label("");
        wordName.getStyleClass().add("word-name");

        btnUpdate = new Button("", createIcon("icon/edit.png", 40));
        btnUpdate.getStyleClass().add("btn-update");
        btnUpdate.setOnAction(e -> {
            Label tmpType = (Label) wordInformation.getChildren().get(3);
            updateWordBox.init(wordName.getText(), tmpType.getText());
            for (int i = 0; i < wordInformation.getChildren().size(); ++ i) {
                if (wordInformation.getChildren().get(i).getStyleClass().size() > 0 &&
                wordInformation.getChildren().get(i).getStyleClass().get(0).equals("definition")) {
                    Label lblMeaning = new Label("Meaning:");
                    lblMeaning.getStyleClass().add("update-word-lbl");

                    Text tmp = (Text) wordInformation.getChildren().get(i);
                    TextField meaning = new TextField();
                    meaning.setText(tmp.getText());
                    System.out.println();
                    meaning.getStyleClass().add("update-meaning-txt");

                    updateWordBox.add(lblMeaning);
                    updateWordBox.add(meaning);
                } else if (wordInformation.getChildren().get(i).getStyleClass().size() > 0 &&
                        wordInformation.getChildren().get(i).getStyleClass().get(0).equals("example")) {
                    Label lblMeaning = new Label("Example:");
                    lblMeaning.getStyleClass().add("update-word-lbl");

                    Text tmp = (Text) wordInformation.getChildren().get(i);
                    TextField example = new TextField();
                    example.setText(tmp.getText());
                    example.getStyleClass().add("update-meaning-txt");

                    updateWordBox.add(lblMeaning);
                    updateWordBox.add(example);
                }
            }
            List<String> updateInformation = updateWordBox.show();
            String updateWord = updateInformation.get(0);
            String updateType = updateInformation.get(1);
            String updateMeaning = updateInformation.get(2);
            String updateExample = "";
            if (updateInformation.size() >=4) {
                updateExample = updateInformation.get(3);
                updateExample = updateExample.substring(3);
            }
            if (!wordName.getText().equals(updateWord)) {
                SearchLayout.updateDeletedWordList(wordName.getText());
                SearchLayout.updateWordList(updateWord);
            }
            try {
                String query;
                if (!updateExample.equals("")) {
                    query = "UPDATE english set word = '" + updateWord + "', type = '" + updateType + "', meaning = '" + updateMeaning
                            + "', example = '" + updateExample + "' WHERE word = '" + wordName.getText() + "'";
                } else {
                    query = "UPDATE english set word = '" + updateWord + "', type = '" + updateType + "', meaning = '" + updateMeaning
                            + "' WHERE word = '" + wordName.getText() + "'";
                }
                myStmt = myConn.createStatement();
                myStmt.executeUpdate(query);
            } catch (Exception exc) {
                exc.printStackTrace();
            }
            if (!wordName.getText().equals(updateWord)) {
                wordName.setText(updateWord);
                ipa.setText("/" + updateWord + "/");
            }
            Label t = (Label) wordInformation.getChildren().get(3);
            t.setText(updateType);
            Text d = (Text) wordInformation.getChildren().get(4);
            d.setText(updateMeaning);
            if (wordInformation.getChildren().size() >= 6) {
                Text ex = (Text) wordInformation.getChildren().get(5);
                ex.setText(updateExample);
            } else {
                Text ex = new Text();
                ex.setText(updateExample);
                ex.getStyleClass().add("example");
                ex.setWrappingWidth(800);
                wordInformation.getChildren().add(ex);
            }
        });
        btnDelete = new Button("",createIcon("icon/bin.png", 40));
        btnDelete.getStyleClass().add("btn-delete");
        btnDelete.setOnAction(e -> {
            boolean confirm = confirmationBox.show("Are you sure","Confirmation", "Yes", "No");
            if (confirm) {
                try {
                    SearchLayout.updateDeletedWordList(wordName.getText());
                    String delWord = "'" + wordName.getText() + "'";
                    String query = "DELETE FROM english WHERE word = " + delWord;
                    myStmt = myConn.createStatement();
                    myStmt.executeUpdate(query);
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                SearchLayout.updateDeletedWordList(wordName.getText());
                SearchLayout.resetListView();
                clear();
                setElementInvisible();
            }
        });


        Region spacer = new Region();

        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox word = new HBox(wordName, spacer, btnUpdate, btnDelete);
        word.setPrefWidth(800);



        USSpell = new Label("US");
        USSpell.getStyleClass().add("spell-lbl");


        UKSpell = new Label("UK");
        UKSpell.getStyleClass().add("spell-lbl");


        btnUS = new Button("\uD83D\uDD0A");
        btnUS.getStyleClass().add("spell-btn");
        btnUS.setOnAction(e -> {
            try {
                voice.speak(wordName.getText(), "en-us");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        btnUK = new Button("\uD83D\uDD0A");
        btnUK.getStyleClass().add("spell-btn");
        btnUK.setOnAction(e -> {
            try {
                voice.speak(wordName.getText(), "en-gb");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        HBox pronounceUS = new HBox(10, USSpell, btnUS);
        HBox pronouceUK = new HBox(10, UKSpell, btnUK);
        HBox pronounce = new HBox(20, pronounceUS, pronouceUK);

        ipa = new Label("/səkˈses/");
        ipa.getStyleClass().add("ipa");

        wordType = new Label("noun");
        wordType.getStyleClass().add("word-type");

        definition = new Text();
        definition.setText("The achieving of the results wanted or hoped for:");
        definition.getStyleClass().add("definition");

        example = new Text();
        example.setText("•  " + "The success of almost any project depends largely on its manager.");
        example.getStyleClass().add("example");

        wordInformation = new VBox(10, word, pronounce, ipa, wordType, definition, example);
        wordInformation.getStyleClass().add("word-information");

        setElementInvisible();
        return wordInformation;
    }

    @Override public void handleEvent() {

    }

    public static void setWord(String s) {
        wordName.setText(s);
    }

    public static void add(Node l) {
        wordInformation.getChildren().add(l);
    }

    public static void clear() {
        int endIndex = wordInformation.getChildren().size();
        wordInformation.getChildren().remove(3, endIndex);
    }
    public static void setIPA(String i) {
        ipa.setText(i);
    }

    public ImageView createIcon (String url, int size) {
        Image img = new Image(url);
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(size);
        imgView.setFitHeight(size);
        imgView.setPreserveRatio(true);

        return imgView;
    }

    public static void setElementVisible() {
        wordName.setVisible(true);
        btnDelete.setVisible(true);
        btnUpdate.setVisible(true);
        USSpell.setVisible(true);
        UKSpell.setVisible(true);
        btnUS.setVisible(true);
        btnUK.setVisible(true);
        ipa.setVisible(true);
        wordType.setVisible(true);
        definition.setVisible(true);
        example.setVisible(true);
    }

    public static void setElementInvisible() {
        wordName.setVisible(false);
        btnDelete.setVisible(false);
        btnUpdate.setVisible(false);
        USSpell.setVisible(false);
        UKSpell.setVisible(false);
        btnUS.setVisible(false);
        btnUK.setVisible(false);
        ipa.setVisible(false);
        wordType.setVisible(false);
        definition.setVisible(false);
        example.setVisible(false);
    }
}
