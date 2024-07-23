package application;

import javafx.application.Application;

public class Main {

	public static void main(String[] args) {

		// Start start = new Start();

		Database db = Database.getInstance();
		db.connect();

		Application.launch(Start.class);

		// start.launch_func(args);

	}
}
