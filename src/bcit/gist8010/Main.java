package bcit.gist8010;
//==============================================================================
// File         : some_file.java
//
// Current Author: First_Name Last_Name
//
// Previous Author: None
//
// Contact Info: somebody@somewhere.com
//
// Purpose :
//
// Dependencies: None
//
// Modification Log :
//    --> Created MMM-DD-YYYY (fl)
//    --> Updated MMM-DD-YYYY (fl)
//
// =============================================================================
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
//===========================================================
// The following block does ....
//===========================================================

public class Main extends Application implements EventHandler<WindowEvent> {
//===========================================================
// The following block does ....
//===========================================================

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("GIST 8010 Mod 2");
        primaryStage.setScene(new Scene(root,800, 600));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/Map_32x32.png")));
        primaryStage.show();
        primaryStage.setOnCloseRequest(this);
    }

//===========================================================
// The following block does ....
//===========================================================

    public static void main(String[] args) {
        launch(args);
    }
//===========================================================
// The following block does ....
//===========================================================

    @Override
    public void handle(WindowEvent event) {
        System.exit(0);
    }
}
