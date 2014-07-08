/*
 * Copyright (C) 2014 Shashank Tulsyan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package util.fx;

import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


/**
 * Example of a sidebar that slides in and out of view
 */
public class SlideOut extends Application {

    private static final String[] lyrics = {
        "And in the end,\nthe love you take,\nis equal to\nthe love\nyou make.",
        "She came in through\nthe bathroom window\nprotected by\na silver\nspoon.",
        "I've got to admit\nit's getting better,\nA little better\nall the time."
    };

    private static final String[] locs = {
        "http://www.youtube.com/watch?v=osAA8q86COY&feature=player_detailpage#t=367s",
        "http://www.youtube.com/watch?v=IM2Ttov_zR0&feature=player_detailpage#t=30s",
        "http://www.youtube.com/watch?v=Jk0dBZ1meio&feature=player_detailpage#t=25s"
    };
    WebView webView;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(final Stage stage) throws Exception {
        stage.setTitle("Slide out YouTube demo");

// create a WebView to show to the right of the SideBar.
        webView = new WebView();
        webView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override public void changed(ObservableValue<? extends Worker.State> ov, Worker.State t, Worker.State t1) {
                if (t1.equals(Worker.State.SUCCEEDED)) {
                    Document d = webView.getEngine().documentProperty().get();
                    NodeList nl =  d.getElementsByTagName("*");
                    for (int i = 0; i < nl.getLength(); i++) {
                        System.out.println(nl.item(i).getTextContent());
                    }
              }
            }
        });
        webView.setPrefSize(800, 600);

// create a sidebar with some content in it.
        final Pane lyricPane = createSidebarContent();
        SideBar sidebar = new SideBar(250, lyricPane);
        VBox.setVgrow(lyricPane, Priority.ALWAYS);

// layout the scene.
        final BorderPane layout = new BorderPane();
        Pane mainPane = VBoxBuilder.create().spacing(10)
                .children(
                        sidebar.getControlButton(),
                        webView
                ).build();
        layout.setLeft(sidebar);
        layout.setCenter(mainPane);

// show the scene.
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("slideout.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    private BorderPane createSidebarContent() {// create some content to put in the sidebar.
        final Text lyric = new Text();
        lyric.getStyleClass().add("lyric-text");
        final Button changeLyric = new Button("New Song");
        changeLyric.getStyleClass().add("change-lyric");
        changeLyric.setMaxWidth(Double.MAX_VALUE);
        changeLyric.setOnAction(new EventHandler<ActionEvent>() {
            int lyricIndex = 0;

            @Override
            public void handle(ActionEvent actionEvent) {
                lyricIndex++;
                if (lyricIndex == lyrics.length) {
                    lyricIndex = 0;
                }
                lyric.setText(lyrics[lyricIndex]);
                webView.getEngine().load(locs[lyricIndex]);
            }
        });
        changeLyric.fire();
        final BorderPane lyricPane = new BorderPane();
        lyricPane.setCenter(lyric);
        lyricPane.setBottom(changeLyric);
        return lyricPane;
    }

    /**
     * Animates a node on and off screen to the left.
     */
    class SideBar extends VBox {

        /**
         * @return a control button to hide and show the sidebar
         */
        public Button getControlButton() {
            return controlButton;
        }
        private final Button controlButton;

        /**
         * creates a sidebar containing a vertical alignment of the given nodes
         */
        SideBar(final double expandedWidth, Node... nodes) {
            getStyleClass().add("sidebar");
            this.setPrefWidth(expandedWidth);
            this.setMinWidth(0);

// create a bar to hide and show.
            setAlignment(Pos.CENTER);
            getChildren().addAll(nodes);

// create a button to hide and show the sidebar.
            controlButton = new Button("Collapse");
            controlButton.getStyleClass().add("hide-left");

// apply the animations when the button is pressed.
            controlButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
// create an animation to hide sidebar.
                    final Animation hideSidebar = new Transition() {
                        {
                            setCycleDuration(Duration.millis(250));
                        }

                        protected void interpolate(double frac) {
                            final double curWidth = expandedWidth * (1.0 - frac);
                            setPrefWidth(curWidth);
                            setTranslateX(-expandedWidth + curWidth);
                        }
                    };
                    hideSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            setVisible(false);
                            controlButton.setText("Show");
                            controlButton.getStyleClass().remove("hide-left");
                            controlButton.getStyleClass().add("show-right");
                        }
                    });
// create an animation to show a sidebar.
                    final Animation showSidebar = new Transition() {
                        {
                            setCycleDuration(Duration.millis(250));
                        }

                        protected void interpolate(double frac) {
                            final double curWidth = expandedWidth * frac;
                            setPrefWidth(curWidth);
                            setTranslateX(-expandedWidth + curWidth);
                        }
                    };
                    showSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            controlButton.setText("Collapse");
                            controlButton.getStyleClass().add("hide-left");
                            controlButton.getStyleClass().remove("show-right");
                        }
                    });
                    if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED) {
                        if (isVisible()) {
                            hideSidebar.play();
                        } else {
                            setVisible(true);
                            showSidebar.play();
                        }
                    }
                }
            });
        }
    }
}
