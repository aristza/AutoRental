package service;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Database;
import car.Car;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddService implements Initializable {

	public DatePicker date;
	public ComboBox<String> time;
	public TextField type;
	public TextField kilometers;
	public RadioButton doneY;
	public RadioButton doneN;
	public TextArea info;
	public Button add;
	public Button update;
	public VBox services;

	private static Car carObj;
	private static Service serviceObj;
	private static Stage window;
	private static AddService as;

	private ServiceManager sm;

	public static void display(Car car) {

		carObj = car;

		try {
			window = new Stage();
			Parent root = FXMLLoader.load(AddService.class.getResource("AddService.fxml"));
			Scene scene = new Scene(root, 800, 600);
			window.initModality(Modality.APPLICATION_MODAL);
			window.setScene(scene);
			if (car == null)
				window.setTitle("Νέο Σέρβις");
			else {
				window.setTitle("Ιστορικό Σέρβις " + car.getManufacturer() + " " + car.getModel() + " "
						+ car.getLicencePlate());
			}
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static AddService getInstance() {
		return as;
	}

	public void handleAddButtonClicked() {

		Database.getInstance().execute("insert into service(\"carId\", date, type, km, info, done) values("
				+ carObj.getId() + ",'" + date.getValue().format(DateTimeFormatter.ISO_DATE) + " "
				+ time.getValue() + "','" + type.getText() + "',"
				+ kilometers.getText() + ",'" + info.getText() + "'," + (doneY.isSelected() ? "true" : "false") + ");",
				false);
		add.setDisable(true);

		loadServices(carObj);

	}

	public void handleUpdateButtonClicked() {

		Database.getInstance().execute("update service set "
				+ "\"date\"='" + date.getValue().format(DateTimeFormatter.ISO_DATE) + " "
				+ time.getValue() + "', "
				+ "\"type\"='" + type.getText() + "', "
				+ "\"km\"='" + kilometers.getText() + "', "
				+ "\"info\"='" + info.getText() + "', "
				+ "\"done\"=" + doneY.isSelected() + " "
				+ "where \"id\"=" + serviceObj.getId() + ";", false);

		update.setDisable(true);

		loadServices(carObj);

	}

	public void handleDeleteButtonClicked(Service service) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Διαγραφή Σέρβις");
		alert.setHeaderText("Πρόκειται να διαγράψετε ένα Σέρβις!");
		alert.setContentText("Είστε σίγουροι οτι θέλετε να διαγράψετε"
				+ " το\nΣέρβις με αύξοντα αριθμό " + service.getId() + ";");

		ButtonType buttonTypeOK = new ButtonType("OK");
		ButtonType buttonTypeCancel = new ButtonType("Ακύρωση", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOK) {
			Database.getInstance().execute("delete from service where id = '" + service.getId() + "';", false);
		} else {
			// ... user chose CANCEL or closed the dialog
		}

		loadServices(carObj);

	}

	public void showService(Service service) {

		// TODO find the right way to convert dates

		date.setValue(service.getDate().toLocalDateTime().toLocalDate());
		time.setValue(service.getDate().toLocalDateTime().toLocalTime().toString());
		type.setText(service.getType());
		kilometers.setText("" + service.getKm());
		doneY.setSelected(service.getDone());
		doneN.setSelected(!service.getDone());
		info.setText(service.getInfo());

		update.setDisable(false);
		add.setDisable(true);

		serviceObj = service;

	}

	public void loadServices(Car car) {

		sm = ServiceManager.getInstance();
		sm.clearServiceList();
		sm.loadServices(car.getId());

		services.getChildren().clear();
		for (Service i : sm.getServices())
			services.getChildren().add(i.createServiceInfoBox());

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		as = this;

		update.setDisable(true);
		// time.set24HourView(true);
		// time.setConverter(new StringConverter<LocalTime>() {
		// DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("HH:mm");

		// @Override
		// public String toString(LocalTime time) {
		// if (time != null) {
		// return dateFormatter.format(time);
		// } else {
		// return "";
		// }
		// }

		// @Override
		// public LocalTime fromString(String string) {
		// if (string != null && !string.isEmpty()) {
		// return LocalTime.parse(string, dateFormatter);
		// } else {
		// return null;
		// }
		// }
		// });

		String timeValues[] = { "00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00",
				"04:30", "05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30",
				"10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00",
				"15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30",
				"21:00", "21:30", "22:00", "22:30", "23:00", "23:30"
		};

		time.setItems(FXCollections.observableArrayList(timeValues));

		loadServices(carObj);

	}

}
