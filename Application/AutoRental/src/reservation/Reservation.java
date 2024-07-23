package reservation;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import car.Car;
import customer.Customer;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Reservation {
	
	private static int FONT = 24;
	
	private int id;
	private String fromDay;
	private String toDay;
	private String fromTime;
	private String toTime;
	private String fromPlace;
	private String toPlace;
	private String days;
	private int carId;
	private int customerId;
	private Boolean delivered;
	private Boolean returned;
	private Customer customer;
	private Car car;
	
	public Reservation(int id, String fromDay, String toDay, String fromTime, String toTime, String fromPlace,
			String toPlace, String days, int carId, int customerId, Boolean delivered, Boolean returned) {
		super();
		this.id = id;
		this.fromDay = fromDay;
		this.toDay = toDay;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.fromPlace = fromPlace;
		this.toPlace = toPlace;
		this.days = days;
		this.carId = carId;
		this.customerId = customerId;
		this.delivered = delivered;
		this.returned = returned;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFromDay() {
		return fromDay;
	}

	public void setFromDay(String fromDay) {
		this.fromDay = fromDay;
	}

	public String getToDay() {
		return toDay;
	}

	public void setToDay(String toDay) {
		this.toDay = toDay;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getFromPlace() {
		return fromPlace;
	}

	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}

	public String getToPlace() {
		return toPlace;
	}

	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public Boolean getDelivered() {
		return delivered;
	}

	public void setDelivered(Boolean delivered) {
		this.delivered = delivered;
	}

	public Boolean getReturned() {
		return returned;
	}

	public void setReturned(Boolean returned) {
		this.returned = returned;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
	
	public HBox createReservationInfoBox() {
		
		HBox reservation = new HBox();
		reservation.setPadding(new Insets(5,5,5,5));
		//reservation.setMinHeight(250);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
		VBox info = new VBox();			
		HBox line1 = new HBox();
		HBox line2 = new HBox();
		HBox line3 = new HBox();
		HBox line4 = new HBox();
		HBox line5 = new HBox();
				
		Label fromDayLabel1 = new Label("Από: ");
		fromDayLabel1.setFont(Font.font(null, FontWeight.BOLD, FONT));
		fromDayLabel1.setPadding(new Insets(5, 0, 5, 10));
		Label fromDayLabel2 = new Label(LocalDate.parse(fromDay).format(formatter) + ", " + fromTime);
		fromDayLabel2.setFont(Font.font(FONT));
		fromDayLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label toDayLabel1 = new Label("Μέχρι: ");
		toDayLabel1.setFont(Font.font(null, FontWeight.BOLD, FONT));
		toDayLabel1.setPadding(new Insets(5, 0, 5, 10));
		Label toDayLabel2 = new Label(LocalDate.parse(toDay).format(formatter) + ", " + toTime);
		toDayLabel2.setFont(Font.font(FONT));
		toDayLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label fromPlaceLabel1 = new Label("Παράδοση: ");
		fromPlaceLabel1.setFont(Font.font(null, FontWeight.BOLD, FONT));
		fromPlaceLabel1.setPadding(new Insets(5, 0, 5, 10));
		Label fromPlaceLabel2 = new Label(fromPlace);
		fromPlaceLabel2.setFont(Font.font(FONT));
		fromPlaceLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label toPlaceLabel1 = new Label("Παραλαβή: ");
		toPlaceLabel1.setFont(Font.font(null, FontWeight.BOLD, FONT));
		toPlaceLabel1.setPadding(new Insets(5, 0, 5, 10));
		Label toPlaceLabel2 = new Label(toPlace);
		toPlaceLabel2.setFont(Font.font(FONT));
		toPlaceLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label carLabel1 = new Label("Αυτοκίνητο: ");
		carLabel1.setFont(Font.font(null, FontWeight.BOLD, FONT));
		carLabel1.setPadding(new Insets(5, 0, 5, 10));
		carLabel1.setMinWidth(190);
		Label carLabel2 = new Label(car.getLicencePlate() + " " + car.getManufacturer() + " " + car.getModel());
		carLabel2.setFont(Font.font(FONT));
		carLabel2.setPadding(new Insets(5, 10, 5, 0));
		carLabel2.setWrapText(true);
		carLabel2.setMinWidth(400);
		
		Label customer1 = new Label("Πελάτης: ");
		customer1.setFont(Font.font(null, FontWeight.BOLD, FONT));
		customer1.setPadding(new Insets(5, 0, 5, 10));
		Label customer2 = new Label(customer.getFirstName() + " " + customer.getLastName());
		customer2.setFont(Font.font(FONT));
		customer2.setPadding(new Insets(5, 10, 5, 0));
		customer2.setWrapText(true);
		customer2.setMinWidth(400);
		
		String returnedText = "";
		String deliveredText = "";
		
		if (delivered) deliveredText = "Ναι";
		else deliveredText = "Όχι";
		
		if (returned) returnedText = "Ναι";
		else returnedText = "Όχι";
		
		Label deliveredLabel1 = new Label("Παραδόθηκε: ");
		deliveredLabel1.setFont(Font.font(null, FontWeight.BOLD, FONT));
		deliveredLabel1.setPadding(new Insets(5, 0, 5, 10));
		Label deliveredLabel2 = new Label(deliveredText);
		deliveredLabel2.setFont(Font.font(FONT));
		deliveredLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label returnedLabel1 = new Label("Παρελήφθη: ");
		returnedLabel1.setFont(Font.font(null, FontWeight.BOLD, FONT));
		returnedLabel1.setPadding(new Insets(5, 0, 5, 10));
		Label returnedLabel2 = new Label(returnedText);
		returnedLabel2.setFont(Font.font(FONT));
		returnedLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		line1.getChildren().addAll(fromDayLabel1, fromDayLabel2, toDayLabel1, toDayLabel2);
		line2.getChildren().addAll(fromPlaceLabel1, fromPlaceLabel2, toPlaceLabel1, toPlaceLabel2);
		line3.getChildren().addAll(deliveredLabel1, deliveredLabel2, returnedLabel1, returnedLabel2);
		line4.getChildren().addAll(carLabel1, carLabel2);
		line5.getChildren().addAll(customer1, customer2);
		
		info.getChildren().addAll(line1, line2, line3, line4, line5);
		reservation.getChildren().add(info);
		
		reservation.setOnMouseClicked(e -> AddReservation.display(this, null));
		reservation.setOnMouseEntered(e -> {
			reservation.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 1;");
		});
		reservation.setOnMouseExited(e -> reservation.setStyle("-fx-border-width: 0;"));
		
		return reservation;
	}
	
public static HBox createReservationCarInfoBox(Car carObj, Reservation rwd) {
		
		HBox car = new HBox();
		car.setPadding(new Insets(5, 5, 5, 5));
		BorderPane imgVBox = new BorderPane();
		imgVBox.setMinSize(200, 200);
		Image carImg = new Image(new File(carObj.getImage()).toURI().toString());
		ImageView img = new ImageView(carImg);
		img.setEffect(new InnerShadow());
		img.setFitHeight(200);
		img.setFitWidth(200);
		img.setPreserveRatio(true);
		imgVBox.setCenter(img);
		VBox info = new VBox();			
		HBox line1 = new HBox();
		HBox line2 = new HBox();
		HBox line3 = new HBox();
		HBox line4 = new HBox();
		HBox line5 = new HBox();
		
		Label manufacturerLabel = new Label("Μάρκα/Μοντέλο: ");
		manufacturerLabel.setFont(Font.font(null, FontWeight.BOLD, FONT));
		manufacturerLabel.setPadding(new Insets(5, 0, 5, 10));
		manufacturerLabel.setMinWidth(330);
		Label manufacturerLabel2 = new Label(carObj.getManufacturer() + " " + carObj.getModel());
		manufacturerLabel2.setFont(Font.font(FONT));
		manufacturerLabel2.setPadding(new Insets(5, 10, 5, 0));
					
		Label licencePlateLabel = new Label("Αριθμός Κυκλοφορίας: ");
		licencePlateLabel.setFont(Font.font(null, FontWeight.BOLD, FONT));
		licencePlateLabel.setPadding(new Insets(5, 0, 5, 10));
		licencePlateLabel.setMinWidth(330);
		Label licencePlateLabel2 = new Label(carObj.getLicencePlate());
		licencePlateLabel2.setFont(Font.font(FONT));
		licencePlateLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label engineLabel = new Label("Κινητήρας/Κατηγορία: ");
		engineLabel.setFont(Font.font(null, FontWeight.BOLD, FONT));
		engineLabel.setPadding(new Insets(5, 0, 5, 10));
		engineLabel.setMinWidth(330);
		Label engineLabel2 = new Label(carObj.getEngine() + " " + carObj.getCategory());
		engineLabel2.setFont(Font.font(FONT));
		engineLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label kmLabel = new Label("Χιλίομετρα: ");
		kmLabel.setFont(Font.font(null, FontWeight.BOLD, FONT));
		kmLabel.setPadding(new Insets(5, 0, 5, 10));
		kmLabel.setMinWidth(330);
		Label kmLabel2 = new Label("" + carObj.getKilometers());
		kmLabel2.setFont(Font.font(FONT));
		kmLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label nsLabel = new Label("Επόμενο Σέρβις: ");
		nsLabel.setFont(Font.font(null, FontWeight.BOLD, FONT));
		nsLabel.setPadding(new Insets(5, 0, 5, 10));
		nsLabel.setMinWidth(330);
		Label nsLabel2 = new Label("");
		nsLabel2.setFont(Font.font(FONT));
		nsLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		line1.getChildren().addAll(manufacturerLabel, manufacturerLabel2);
		line2.getChildren().addAll(licencePlateLabel, licencePlateLabel2);
		line3.getChildren().addAll(engineLabel, engineLabel2);
		line4.getChildren().addAll(kmLabel, kmLabel2);
		line5.getChildren().addAll(nsLabel, nsLabel2);
		
		info.getChildren().addAll(line1, line2, line3, line4, line5);
		car.getChildren().addAll(imgVBox, info);
		
		car.setOnMouseClicked(e -> AddReservation.display(null, rwd));
		car.setOnMouseEntered(e -> {
			car.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 1;");
		});
		car.setOnMouseExited(e -> car.setStyle("-fx-border-width: 0;"));
		
		return car;
		
	}
	
}
