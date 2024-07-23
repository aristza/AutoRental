package car;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import application.Database;
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
import service.Service;

public class Car {
	
	private static int FONT = 24;
	
	private int id;
	private String licencePlate;
	private String chassisNumber;
	private String manufacturer;
	private String model;
	private String engine;
	private String category;
	private int kilometers;
	private Timestamp expInsurance;
	private String fuel;
	private String image;
	
	public Car(int id, String licencePlate, String chassisNumber, String manufacturer, String model, String engine,
			String category, int kilometers, Timestamp expInsurance, String fuel, String image) {
		super();
		this.id = id;
		this.licencePlate = licencePlate;
		this.chassisNumber = chassisNumber;
		this.manufacturer = manufacturer;
		this.model = model;
		this.engine = engine;
		this.category = category;
		this.kilometers = kilometers;
		this.expInsurance = expInsurance;
		this.fuel = fuel;
		this.image = image;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLicencePlate() {
		return licencePlate;
	}

	public void setLicencePlate(String licencePlate) {
		this.licencePlate = licencePlate;
	}

	public String getChassisNumber() {
		return chassisNumber;
	}

	public void setChassisNumber(String chassisNumber) {
		this.chassisNumber = chassisNumber;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getKilometers() {
		return kilometers;
	}

	public void setKilometers(int kilometers) {
		this.kilometers = kilometers;
	}

	public Timestamp getExpInsurance() {
		return expInsurance;
	}

	public void setExpInsurance(Timestamp expInsurance) {
		this.expInsurance = expInsurance;
	}

	public String getFuel() {
		return fuel;
	}

	public void setFuel(String fuel) {
		this.fuel = fuel;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	private String getNextService() {
		
		ResultSet result = Database.getInstance().execute("select * from service "
													+ "where done = false and "
													+ "\"carId\"=" + this.getId() + " "
													+ "order by date asc limit 1;", true);
		Service service = null;
		
		
		try {
			if (result.next()) service = new Service(result.getInt(1), result.getInt(2), result.getTimestamp(3),
												result.getString(4), result.getInt(5), result.getString(6),
												result.getBoolean(7));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String serviceText = "Δε βρέθηκε επόμενο Σέρβις";
		
		if (service != null) {
			serviceText = service.getDate() + " " + service.getType() + " " + service.getKm();
		}
		
		return serviceText;
		
	}
	
	public HBox createCarInfoBox() {
		
		HBox car = new HBox();
		car.setPadding(new Insets(5, 5, 5, 5));
		BorderPane imgVBox = new BorderPane();
		imgVBox.setMinSize(200, 200);
		Image carImg = new Image(new File(image).toURI().toString());
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
		Label manufacturerLabel2 = new Label(manufacturer + " " + model);
		manufacturerLabel2.setFont(Font.font(FONT));
		manufacturerLabel2.setPadding(new Insets(5, 10, 5, 0));
					
		Label licencePlateLabel = new Label("Αριθμός Κυκλοφορίας: ");
		licencePlateLabel.setFont(Font.font(null, FontWeight.BOLD, FONT));
		licencePlateLabel.setPadding(new Insets(5, 0, 5, 10));
		licencePlateLabel.setMinWidth(330);
		Label licencePlateLabel2 = new Label(licencePlate);
		licencePlateLabel2.setFont(Font.font(FONT));
		licencePlateLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label engineLabel = new Label("Κινητήρας/Κατηγορία: ");
		engineLabel.setFont(Font.font(null, FontWeight.BOLD, FONT));
		engineLabel.setPadding(new Insets(5, 0, 5, 10));
		engineLabel.setMinWidth(330);
		Label engineLabel2 = new Label(engine + " " + category);
		engineLabel2.setFont(Font.font(FONT));
		engineLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label kmLabel = new Label("Χιλίομετρα: ");
		kmLabel.setFont(Font.font(null, FontWeight.BOLD, FONT));
		kmLabel.setPadding(new Insets(5, 0, 5, 10));
		kmLabel.setMinWidth(330);
		Label kmLabel2 = new Label("" + kilometers);
		kmLabel2.setFont(Font.font(FONT));
		kmLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label nsLabel = new Label("Επόμενο Σέρβις: ");
		nsLabel.setFont(Font.font(null, FontWeight.BOLD, FONT));
		nsLabel.setPadding(new Insets(5, 0, 5, 10));
		nsLabel.setMinWidth(330);
		Label nsLabel2 = new Label(getNextService());
		nsLabel2.setFont(Font.font(FONT));
		nsLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		line1.getChildren().addAll(manufacturerLabel, manufacturerLabel2);
		line2.getChildren().addAll(licencePlateLabel, licencePlateLabel2);
		line3.getChildren().addAll(engineLabel, engineLabel2);
		line4.getChildren().addAll(kmLabel, kmLabel2);
		line5.getChildren().addAll(nsLabel, nsLabel2);
		
		info.getChildren().addAll(line1, line2, line3, line4, line5);
		car.getChildren().addAll(imgVBox, info);
		
		car.setOnMouseClicked(e -> AddCar.display(this));
		car.setOnMouseEntered(e -> {
			car.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 1;");
		});
		car.setOnMouseExited(e -> car.setStyle("-fx-border-width: 0;"));
		
		return car;
		
	}
	
}
