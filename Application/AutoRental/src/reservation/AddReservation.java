package reservation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import application.Controller;
import application.Database;
import car.AddCar;
import car.Car;
import car.CarManager;
import customer.AddCustomer;
import customer.Customer;
import customer.CustomerManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.StringConverter;

public class AddReservation implements Initializable {

	public Button customerDetailsButton;
	public Button carDetailsButton;
	public Button applyButton;
	public DatePicker fromDay;
	public DatePicker toDay;
	public ComboBox<String> fromTime;
	public ComboBox<String> toTime;
	public TextField fromPlace;
	public TextField toPlace;
	public TextField days;
	public TextField car;
	public TextField customerName;
	public RadioButton deliveredY;
	public RadioButton returnedY;
	public RadioButton deliveredN;
	public RadioButton returnedN;

	private static Reservation reservationObj;
	private static Reservation rwdObj;
	private static Stage window;

	public static void display(Reservation reservation, Reservation rwd) {

		reservationObj = reservation;
		rwdObj = rwd;

		try {
			window = new Stage();
			Parent root = FXMLLoader.load(AddReservation.class.getResource("AddReservation.fxml"));
			Scene scene = new Scene(root, 800, 600);
			window.initModality(Modality.APPLICATION_MODAL);
			window.setScene(scene);
			if (reservation == null)
				window.setTitle("Νέα Κράτηση");
			else {
				window.setTitle("Κράτηση: " + reservation.getId());
			}
			window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void handleOkButtonClicked() {

		window.close();
		Controller.getInstance().handleRefreshReservationClicked();

	}

	public void handlePrintButtonClicked() {

		FileChooser fc = new FileChooser();
		fc.setTitle("Αποθήκευση Στοιχείων Κράτησης");
		fc.getExtensionFilters().add(new ExtensionFilter("Αρχείο PDF", "*.pdf"));
		File file = fc.showSaveDialog(null);

		try {
			FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile());
			PdfWriter writer = new PdfWriter(fos);
			PdfDocument pdf = new PdfDocument(writer);
			Document doc = new Document(pdf);
			PdfFont font = PdfFontFactory.createFont("fonts/DroidSans.ttf", "Identity-H", true);

			Paragraph para = new Paragraph("Στοιχεία Κράτησης");
			para.setFont(font);
			para.setWidth(UnitValue.createPercentValue(100));
			para.setTextAlignment(TextAlignment.CENTER);
			doc.add(para);

			Div tableDiv = new Div();
			tableDiv.setWidth(UnitValue.createPercentValue(100));

			Table table = new Table(2);
			table.setHorizontalAlignment(HorizontalAlignment.CENTER);
			table.setWidth(UnitValue.createPercentValue(80));
			table.setFont(font);

			Cell fromDateLabelCell = new Cell();
			fromDateLabelCell.setWidth(UnitValue.createPercentValue(50));
			fromDateLabelCell.add(new Paragraph("Από"));
			table.addCell(fromDateLabelCell);

			Cell nameCell = new Cell();
			nameCell.setWidth(UnitValue.createPercentValue(50));
			nameCell.add(new Paragraph(fromDay.getValue().toString() + " " + fromTime.getValue().toString()));
			table.addCell(nameCell);

			table.addCell("Μέχρι");
			table.addCell(toDay.getValue().toString() + " " + toTime.getValue().toString());

			table.addCell("Σύνολο Ημερών");
			table.addCell(days.getText());

			table.addCell("Παράδοση");
			table.addCell(fromPlace.getText());

			table.addCell("Παραλαβή");
			table.addCell(toPlace.getText());

			table.addCell("Όχημα");
			table.addCell(car.getText());

			table.addCell("Πελάτης");
			table.addCell(customerName.getText());

			table.addCell("Παραδόθηκε");
			table.addCell(deliveredY.isSelected() ? "Ναι" : "Όχι");

			table.addCell("Παρελήφθη");
			table.addCell(returnedY.isSelected() ? "Ναι" : "Όχι");

			tableDiv.add(table);
			doc.add(tableDiv);

			doc.close();
			pdf.close();
			writer.close();
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void handleDeleteButtonClicked() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Διαγραφή Κράτησης");
		alert.setHeaderText("Πρόκειται να διαγράψετε μια κράτηση!");
		alert.setContentText("Είστε σίγουροι οτι θέλετε να διαγράψετε"
				+ " την\nκράτηση με αριθμό " + reservationObj.getId() + ";");

		ButtonType buttonTypeOK = new ButtonType("OK");
		ButtonType buttonTypeCancel = new ButtonType("Ακύρωση", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOK) {
			Database.getInstance().execute("delete from reservation where id = '" + reservationObj.getId() + "';",
					false);
			handleOkButtonClicked();

		} else {
			// ... user chose CANCEL or closed the dialog
		}

	}

	public void handleApplyButtonClicked() {

		Boolean delivered = false;
		Boolean returned = false;

		if (deliveredY.isSelected())
			delivered = true;
		else if (deliveredN.isSelected())
			delivered = false;

		if (returnedY.isSelected())
			returned = true;
		else if (returnedN.isSelected())
			returned = false;

		if (reservationObj == null) {

			Database.getInstance()
					.execute("insert into reservation (\"fromDate\", \"toDate\", \"fromPlace\", \"toPlace\"," +
							"days, car, customer, delivered, returned)" +
							"values ('" + fromDay.getValue().format(DateTimeFormatter.ISO_DATE) + " "
							+ fromTime.getValue() + "', '"
							+ toDay.getValue().format(DateTimeFormatter.ISO_DATE) + " "
							+ toTime.getValue() + "', '"
							+ fromPlace.getText() + "', '" + toPlace.getText() + "', "
							+ Integer.parseInt(days.getText()) + ", " + getCar().getId() + ", "
							+ getCustomer().getId() + ", '" + delivered + "', '" + returned + "');", false);

		} else {

			Database.getInstance().execute("update reservation set "
					+ "\"fromDate\" = " + "'" + fromDay.getValue().format(DateTimeFormatter.ISO_DATE) + " "
					+ fromTime.getValue() + "'" + ","
					+ "\"toDate\" = " + "'" + toDay.getValue().format(DateTimeFormatter.ISO_DATE) + " "
					+ toTime.getValue() + "'" + ","
					+ "\"fromPlace\" = " + "'" + fromPlace.getText() + "'" + ","
					+ "\"toPlace\" = " + "'" + toPlace.getText() + "'" + ","
					+ "\"days\" = " + Integer.parseInt(days.getText()) + ","
					+ "\"car\" = " + getCar().getId() + ","
					+ "\"customer\" = " + getCustomer().getId() + ","
					+ "\"delivered\" = " + "'" + delivered + "'" + ","
					+ "\"returned\" = " + "'" + returned + "'"
					+ " where \"id\" = " + "'" + reservationObj.getId() + "'", false);

		}

		applyButton.setDisable(true);

		CustomerManager cm = CustomerManager.getInstance();

		cm.clearCustomerList();
		cm.loadCustomers();
	}

	public void handleCustomerDetailsButtonClicked() {

		AddCustomer.display(getCustomer());

	}

	public void handleCarDetailsButtonClicked() {

		AddCar.display(getCar());

	}

	public void handleNewCustomerButtonClicked() {

		AddCustomer.display(null);
		refreshCustomers();

	}

	private Car getCar() {

		String[] parts = car.getText().split(" ");
		int carId = Integer.parseInt(parts[0]);

		ResultSet result = Database.getInstance().execute("select * from car where id = " + carId + ";", true);

		Car car = null;

		try {

			if (result.next())
				car = new Car(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),
						result.getString(5), result.getString(6), result.getString(7), result.getInt(8),
						result.getTimestamp(9), result.getString(10), result.getString(11));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return car;

	}

	private Customer getCustomer() {
		String[] parts = customerName.getText().split(" ");
		int customerId = Integer.parseInt(parts[0]);

		ResultSet result = Database.getInstance().execute("select * from customer where id = " + customerId + ";",
				true);

		Customer customer = null;

		try {

			if (result.next())
				customer = new Customer(result.getInt(1), result.getString(2), result.getString(3),
						result.getString(4), result.getString(5), result.getString(6),
						result.getString(7), result.getString(8), result.getString(9),
						result.getString(10), result.getString(11), result.getString(12),
						result.getString(13));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return customer;

	}

	public void refreshCustomers() {

		CustomerManager.getInstance().clearCustomerList();
		CustomerManager.getInstance().loadCustomers();
		List<Customer> customers = CustomerManager.getInstance().getCustomers();
		List<String> namesList = new ArrayList<String>();
		String[] names = {};
		for (Customer i : customers)
			namesList.add(i.getId() + " " + i.getFirstName() + " " + i.getLastName());
		names = namesList.toArray(new String[namesList.size()]);
		TextFields.bindAutoCompletion(customerName, names);

	}

	public void initialize(URL arg0, ResourceBundle arg1) {

		// fromTime._24HourViewProperty().set(true);
		// toTime._24HourViewProperty().set(true);

		// toTime.setConverter(new StringConverter<LocalTime>() {
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

		// fromTime.setConverter(new StringConverter<LocalTime>() {
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

		fromTime.setItems(FXCollections.observableArrayList(timeValues));
		toTime.setItems(FXCollections.observableArrayList(timeValues));

		fromDay.setConverter(new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});

		toDay.setConverter(new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});

		List<Customer> customers = CustomerManager.getInstance().getCustomers();
		List<String> namesList = new ArrayList<String>();
		String[] names = {};
		for (Customer i : customers)
			namesList.add(i.getId() + " " + i.getFirstName() + " " + i.getLastName());
		names = namesList.toArray(new String[namesList.size()]);
		TextFields.bindAutoCompletion(customerName, names);

		List<Car> carList = CarManager.getInstance().getCars();
		List<String> carsList = new ArrayList<String>();
		String[] cars = {};
		for (Car i : carList)
			carsList.add(i.getId() + " " + i.getLicencePlate() + " " + i.getManufacturer() + " " + i.getModel());
		cars = carsList.toArray(new String[carsList.size()]);
		TextFields.bindAutoCompletion(car, cars);

		if (reservationObj != null) {

			fromDay.setValue(LocalDate.parse(reservationObj.getFromDay()));
			toDay.setValue(LocalDate.parse(reservationObj.getToDay()));
			fromTime.setValue(reservationObj.getFromTime());
			toTime.setValue(reservationObj.getToTime());
			fromPlace.setText(reservationObj.getFromPlace());
			toPlace.setText(reservationObj.getToPlace());
			days.setText(reservationObj.getDays());
			car.setText("" + reservationObj.getCar());

			if (reservationObj.getCustomer() != null) {

				customerName.setText(reservationObj.getCustomer().getId() + " "
						+ reservationObj.getCustomer().getFirstName() + " "
						+ reservationObj.getCustomer().getLastName());

			} else
				customerDetailsButton.setDisable(true);

			if (reservationObj.getCar() != null) {

				car.setText(reservationObj.getCar().getId() + " "
						+ reservationObj.getCar().getLicencePlate() + " "
						+ reservationObj.getCar().getManufacturer() + " "
						+ reservationObj.getCar().getModel());

			} else
				carDetailsButton.setDisable(true);

			if (reservationObj.getDelivered())
				deliveredY.setSelected(true);
			else
				deliveredN.setSelected(true);

			if (reservationObj.getReturned())
				returnedY.setSelected(true);
			else
				returnedN.setSelected(true);

		} else {

			customerDetailsButton.setDisable(true);
			carDetailsButton.setDisable(true);

			if (rwdObj != null) {

				fromDay.setValue(LocalDate.parse(rwdObj.getFromDay()));
				toDay.setValue(LocalDate.parse(rwdObj.getToDay()));
				fromTime.setValue(rwdObj.getFromTime());
				toTime.setValue(rwdObj.getToTime());

				car.setText(rwdObj.getCar().getId() + " "
						+ rwdObj.getCar().getLicencePlate() + " "
						+ rwdObj.getCar().getManufacturer() + " "
						+ rwdObj.getCar().getModel());

				customerDetailsButton.setDisable(true);
				carDetailsButton.setDisable(false);

			}
		}

		customerName.textProperty().addListener((observable, oldValue, newValue) -> {
			customerDetailsButton.setDisable(false);
			refreshCustomers();
		});

		car.textProperty().addListener((observable, oldValue, newValue) -> {
			carDetailsButton.setDisable(false);
		});

	}

}
