/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util.fx;

/**
 *
 * @author Shashank Tulsyan
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class VideoPlayer extends Application {    
  public static void main(String[] args) { launch(args); }

  @Override public void start(Stage stage) throws Exception {
    WebView webview = new WebView();
    webview.getEngine().load(
      //"http://www.youtube.com/embed/utUPth77L_o?autoplay=1"
            "http://www.youtube.com/watch?v=-rmGc4vTQM0"

    );
    //webview.getEngine().
    
    webview.setPrefSize(640, 390);

    stage.setScene(new Scene(webview));
    stage.show();
  }    
}
