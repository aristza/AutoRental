package service;

import java.sql.Timestamp;

import car.AddCar;
import car.Car;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Service {
	
	private static int FONT = 14;
	
	private int id;
	private int carId;
	private Timestamp date;
	private String type;
	private int km;
	private String info;
	private Boolean done;
	
	private Car car;
	
	public Service(int id, int carId, Timestamp date, String type, int km, String info, Boolean done) {
		
		super();
		this.id = id;
		this.carId = carId;
		this.date = date;
		this.type = type;
		this.km = km;
		this.info = info;
		this.done = done;
	
	}

	public Car getCar() {
		return car;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getKm() {
		return km;
	}

	public void setCar(Car car) {
		this.car = car;
	}
	
	public void setKm(int km) {
		this.km = km;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}
		
	public HBox createServiceInfoBox() {
		
		Service service = this;
	
		HBox serviceBox = new HBox();
		serviceBox.setPadding(new Insets(5, 5, 5, 5));
		serviceBox.setAlignment(Pos.CENTER_LEFT);
		
		VBox info = new VBox();			
		HBox line1 = new HBox();
		HBox line2 = new HBox();
		
		Label idLabel = new Label("" + service.getId());
		idLabel.setFont(Font.font(null, FontWeight.BOLD, FONT));
		idLabel.setPadding(new Insets(5, 5, 5, 5));
		
		Label dateLabel = new Label("Ημερομηνία: ");
		dateLabel.setFont(Font.font(null, FontWeight.BOLD, FONT));
		dateLabel.setPadding(new Insets(5, 0, 5, 10));
		dateLabel.setMinWidth(100);
		Label dateLabel2 = new Label(service.getDate().toString());
		dateLabel2.setFont(Font.font(FONT));
		dateLabel2.setPadding(new Insets(5, 10, 5, 0));
					
		Label typeLabel = new Label("Τύπος: ");
		typeLabel.setFont(Font.font(null, FontWeight.BOLD, FONT));
		typeLabel.setPadding(new Insets(5, 0, 5, 10));
		typeLabel.setMinWidth(50);
		Label typeLabel2 = new Label(service.getType());
		typeLabel2.setFont(Font.font(FONT));
		typeLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label kmLabel = new Label("Χιλιόμετρα: ");
		kmLabel.setFont(Font.font(null, FontWeight.BOLD, FONT));
		kmLabel.setPadding(new Insets(5, 0, 5, 10));
		kmLabel.setMinWidth(100);
		Label kmLabel2 = new Label("" + service.getKm());
		kmLabel2.setFont(Font.font(FONT));
		kmLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label doneLabel = new Label("Πραγματοποιήθηκε: ");
		doneLabel.setFont(Font.font(null, FontWeight.BOLD, FONT));
		doneLabel.setPadding(new Insets(5, 0, 5, 10));
		doneLabel.setMinWidth(120);
		Label doneLabel2 = new Label(service.getDone()?"Ναι":"Όχι");
		doneLabel2.setFont(Font.font(FONT));
		doneLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Region spacing = new Region();
		HBox.setHgrow(spacing, Priority.ALWAYS);
		
		Button deleteButton = new Button("Διαγραφή");
		deleteButton.setOnMouseClicked(e -> AddService.getInstance().handleDeleteButtonClicked(service));
		
		line1.getChildren().addAll(dateLabel, dateLabel2, typeLabel, typeLabel2);
		line2.getChildren().addAll(kmLabel, kmLabel2, doneLabel, doneLabel2);
		
		info.getChildren().addAll(line1, line2);
		serviceBox.getChildren().addAll(idLabel, info, spacing, deleteButton);
		
		serviceBox.setOnMouseClicked(e -> AddService.getInstance().showService(service));
		serviceBox.setOnMouseEntered(e -> {
			serviceBox.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 1;");
		});
		serviceBox.setOnMouseExited(e -> serviceBox.setStyle("-fx-border-width: 0;"));
		
		return serviceBox;
		
	}
	
	public HBox createServiceNotificationInfoBox() {
		
		Service service = this;
	
		HBox serviceBox = new HBox();
		serviceBox.setPadding(new Insets(5, 5, 5, 5));
		serviceBox.setAlignment(Pos.CENTER_LEFT);
		
		VBox info = new VBox();			
		HBox line1 = new HBox();
		HBox line2 = new HBox();
		
		Label idLabel = new Label("" + service.getId());
		idLabel.setFont(Font.font(null, FontWeight.BOLD, FONT));
		idLabel.setPadding(new Insets(5, 5, 5, 5));
		
		Label dateLabel = new Label("Ημερομηνία: ");
		dateLabel.setFont(Font.font(null, FontWeight.BOLD, FONT));
		dateLabel.setPadding(new Insets(5, 0, 5, 10));
		dateLabel.setMinWidth(100);
		Label dateLabel2 = new Label(service.getDate().toString());
		dateLabel2.setFont(Font.font(FONT));
		dateLabel2.setPadding(new Insets(5, 10, 5, 0));
					
		Label typeLabel = new Label("Τύπος: ");
		typeLabel.setFont(Font.font(null, FontWeight.BOLD, FONT));
		typeLabel.setPadding(new Insets(5, 0, 5, 10));
		typeLabel.setMinWidth(50);
		Label typeLabel2 = new Label(service.getType());
		typeLabel2.setFont(Font.font(FONT));
		typeLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label kmLabel = new Label("Χιλιόμετρα: ");
		kmLabel.setFont(Font.font(null, FontWeight.BOLD, FONT));
		kmLabel.setPadding(new Insets(5, 0, 5, 10));
		kmLabel.setMinWidth(100);
		Label kmLabel2 = new Label("" + service.getKm());
		kmLabel2.setFont(Font.font(FONT));
		kmLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label doneLabel = new Label("Όχημα: ");
		doneLabel.setFont(Font.font(null, FontWeight.BOLD, FONT));
		doneLabel.setPadding(new Insets(5, 0, 5, 10));
		doneLabel.setMinWidth(120);
		Label doneLabel2 = new Label(car.getManufacturer() + " " + car.getModel() + " " + car.getLicencePlate());
		doneLabel2.setFont(Font.font(FONT));
		doneLabel2.setPadding(new Insets(5, 10, 5, 0));
				
		line1.getChildren().addAll(dateLabel, dateLabel2, typeLabel, typeLabel2);
		line2.getChildren().addAll(kmLabel, kmLabel2, doneLabel, doneLabel2);
		
		info.getChildren().addAll(line1, line2);
		serviceBox.getChildren().addAll(idLabel, info);
		
		serviceBox.setOnMouseClicked(e -> AddCar.display(car));
		serviceBox.setOnMouseEntered(e -> {
			serviceBox.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 1;");
		});
		serviceBox.setOnMouseExited(e -> serviceBox.setStyle("-fx-border-width: 0;"));
		
		return serviceBox;
		
	}
		
}
