package reversiapp;
	
import javafx.application.Application;
import javafx.stage.Stage;
import settingspage.Settings;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;

/**
 * @author Ran Algiser and Ron Edi
 *
 */
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Settings settings = new Settings();
			settings.display();
			HBox root = (HBox)FXMLLoader.load(getClass().getResource("ReversiGame.fxml"));
			Scene scene = new Scene(root,520,400);
			
			primaryStage.setTitle("Reversi Game");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
