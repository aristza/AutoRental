package reservation.history;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import car.Car;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import reservation.Reservation;

public class ReservationHistory implements Initializable {

	public VBox reservations;

	private static Car carObj;
	private static Stage window;

	public static void display(Car car) {

		carObj = car;

		try {
			window = new Stage();
			Parent root = FXMLLoader.load(ReservationHistory.class.getResource("ReservationHistory.fxml"));
			Scene scene = new Scene(root, 800, 600);
			window.initModality(Modality.APPLICATION_MODAL);
			window.setScene(scene);
			if (car == null)
				window.setTitle("Ιστορικό Κρατήσεων");
			else {
				window.setTitle("Ιστορικό Κρατήσεων " + car.getManufacturer() + " " + car.getModel() + " "
						+ car.getLicencePlate());
			}
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ReservationHistoryManager rhm = ReservationHistoryManager.getInstance();
		rhm.clearReservationList();
		rhm.loadReservations(carObj.getId());

		reservations.getChildren().clear();
		for (Reservation i : rhm.getReservations())
			reservations.getChildren().add(i.createReservationInfoBox());

	}

}
