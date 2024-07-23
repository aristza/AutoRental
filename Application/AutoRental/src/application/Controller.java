package application;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import car.AddCar;
import car.Car;
import car.CarManager;
import customer.AddCustomer;
import customer.Customer;
import customer.CustomerManager;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import reminder.ReminderManager;
import reservation.AddReservation;
import reservation.Reservation;
import reservation.ReservationManager;
import service.Service;

public class Controller implements Initializable {

	public VBox cars;
	public VBox customers;
	public VBox reservations;
	public VBox availableCars;
	public VBox generalReminders;
	public VBox reservationReminders;
	public VBox serviceReminders;
	public CheckBox categoryA;
	public CheckBox categoryB;
	public CheckBox categoryC;
	public CheckBox categoryD;
	public CheckBox categoryE;
	public DatePicker fromDay;
	public DatePicker toDay;
	public ComboBox<String> fromTime;
	public ComboBox<String> toTime;
	private static Controller instance;

	public Controller() {
		instance = this;
	}

	// static method to get instance of view
	public static Controller getInstance() {
		return instance;
	}

	public void handleAddCarClicked() {

		AddCar.display(null);

	}

	public void handleSearchButtonClicked() {

		String fromDate;
		String toDate;
		String category = "'K'";

		List<String> categoryList = new ArrayList<String>();

		if (categoryA.isSelected())
			categoryList.add("'A'");
		if (categoryB.isSelected())
			categoryList.add("'B'");
		if (categoryC.isSelected())
			categoryList.add("'C'");
		if (categoryD.isSelected())
			categoryList.add("'D'");
		if (categoryE.isSelected())
			categoryList.add("'E'");

		for (String i : categoryList)
			category += ", " + i;

		fromDate = fromDay.getValue().format(DateTimeFormatter.ISO_DATE) + " "
				+ fromTime.getValue();// .format(DateTimeFormatter.ISO_TIME);

		toDate = toDay.getValue().format(DateTimeFormatter.ISO_DATE) + " "
				+ toTime.getValue();// .format(DateTimeFormatter.ISO_TIME);

		AvailableCarManager acm = AvailableCarManager.getInstance();
		acm.clearAvailableCarList();
		availableCars.getChildren().clear();

		acm.loadAvailableCars(fromDate, toDate, category);

		List<Car> availableCarList = acm.getAvailableCars();

		for (Car i : availableCarList) {

			Reservation rwd = new Reservation(0, fromDay.getValue().format(DateTimeFormatter.ISO_DATE),
					toDay.getValue().format(DateTimeFormatter.ISO_DATE),
					fromTime.getValue(), // .format(DateTimeFormatter.ISO_TIME),
					toTime.getValue(), // .format(DateTimeFormatter.ISO_TIME),
					"", "", "", i.getId(), 0, false, false);

			rwd.setCar(i);

			availableCars.getChildren().add(Reservation.createReservationCarInfoBox(i, rwd));

		}

	}

	public void handleAddCustomerClicked() {

		AddCustomer.display(null);

	}

	public void handleAddReservationClicked() {
		AddReservation.display(null, null);
	}

	public void handleAddReminderClicked() {

	}

	public void handleRefreshReservationClicked() {

		ReservationManager rm = ReservationManager.getInstance();

		rm.clearReservationList();
		rm.loadReservations();
		List<Reservation> reservationList = rm.getReservations();

		reservations.getChildren().clear();
		for (Reservation i : reservationList)
			reservations.getChildren().add(i.createReservationInfoBox());

	}

	public void handleRefreshCarsClicked() {

		CarManager cm = CarManager.getInstance();

		cm.clearCarList();
		cm.loadCars();
		List<Car> carList = cm.getCars();

		cars.getChildren().clear();
		for (Car i : carList)
			cars.getChildren().add(i.createCarInfoBox());

	}

	public void handleRefreshCustomersClicked() {

		CustomerManager cm = CustomerManager.getInstance();

		cm.clearCustomerList();
		cm.loadCustomers();
		List<Customer> customerList = cm.getCustomers();

		customers.getChildren().clear();
		for (Customer i : customerList)
			customers.getChildren().add(i.createCustomerInfoBox());

	}

	public void handleRefreshRemindersClicked() {

		ReminderManager rmm = ReminderManager.getInstance();

		rmm.clearReservationList();
		rmm.clearServiceList();

		rmm.loadReservations();
		rmm.loadServices();

		reservationReminders.getChildren().clear();
		serviceReminders.getChildren().clear();

		for (Reservation i : rmm.getReservations())
			reservationReminders.getChildren().add(i.createReservationInfoBox());
		for (Service i : rmm.getServices())
			serviceReminders.getChildren().add(i.createServiceNotificationInfoBox());

	}

	@Override
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

		CarManager cm = CarManager.getInstance();
		CustomerManager csm = CustomerManager.getInstance();
		ReservationManager rm = ReservationManager.getInstance();
		ReminderManager rmm = ReminderManager.getInstance();

		cm.clearCarList();
		csm.clearCustomerList();
		rm.clearReservationList();
		rmm.clearReservationList();
		rmm.clearServiceList();

		cm.loadCars();
		csm.loadCustomers();
		rm.loadReservations();
		rmm.loadReservations();
		rmm.loadServices();

		List<Car> carList = cm.getCars();
		List<Customer> customerList = csm.getCustomers();
		List<Reservation> reservationList = rm.getReservations();
		List<Reservation> reservationReminder = rmm.getReservations();
		List<Service> serviceReminder = rmm.getServices();

		for (Car i : carList)
			cars.getChildren().add(i.createCarInfoBox());
		for (Customer i : customerList)
			customers.getChildren().add(i.createCustomerInfoBox());
		for (Reservation i : reservationList)
			reservations.getChildren().add(i.createReservationInfoBox());
		for (Reservation i : reservationReminder)
			reservationReminders.getChildren().add(i.createReservationInfoBox());
		for (Service i : serviceReminder)
			serviceReminders.getChildren().add(i.createServiceNotificationInfoBox());

	}

}
