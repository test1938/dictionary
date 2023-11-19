package layouts;

import javafx.scene.layout.BorderPane;

public class Home extends Component{
    @Override public BorderPane create() {
        mainLayout mainLayout = new mainLayout();
        SearchLayout searchLayout = new SearchLayout();
        BorderPane home = new BorderPane();
        home.setCenter(mainLayout.create());
        home.setLeft(searchLayout.create());
        return home;
    }

    @Override public void handleEvent() {}
}
