package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.<Parent>load(this.getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root, 1024, 768);
			scene.getStylesheets().add(this.getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Auto Rental");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void launch_func(String[] args) {
		launch(args);
	}

}