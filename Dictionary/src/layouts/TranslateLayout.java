package layouts;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class TranslateLayout extends Component{
    Translation translationBox;
    @Override public BorderPane create() {
        Button btnTranslate = new Button("Translate");
        btnTranslate.getStyleClass().add("button-translate");
        btnTranslate.setOnAction(e -> {
            String sentence = translationBox.getSentence();
            OkHttpClient client = new OkHttpClient();
            String url = "";
            if (Translation.getInputLanguage().equals("English")) {
                url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=en&tl=vi&dt=t&q=" + sentence;
            } else if (Translation.getInputLanguage().equals("Vietnamese")) {
                url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=vi&tl=en&dt=t&q=" + sentence;
            }
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                JsonArray jsonArray = JsonParser.parseString(response.body().string()).getAsJsonArray();
                String translation = "";
                for (int i = 0; i < jsonArray.get(0).getAsJsonArray().size(); ++ i) {
                    translation += jsonArray.get(0).getAsJsonArray().get(i).getAsJsonArray().get(0).getAsString();
                }
                if(translation != "") {
                    translationBox.setSentence(translation);
                }
            } catch (IOException ex) {
                translationBox.setSentence("Some error has been occured.");
                throw new RuntimeException(ex);
            }
        });
        HBox btnTranslateContainer = new HBox(btnTranslate);
        btnTranslateContainer.getStyleClass().add("button-translate-container");
        btnTranslateContainer.setAlignment(Pos.CENTER);

        translationBox = new Translation();
        NavBar navBar = new NavBar();
        BorderPane translateLayout = new BorderPane();
        translateLayout.setTop(navBar.create());
        translateLayout.setCenter(translationBox.create());
        translateLayout.setBottom(btnTranslateContainer);
        return translateLayout;
    }

    @Override public void handleEvent() {}

}
