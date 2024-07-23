package car;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

import application.Controller;
import application.Database;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import reservation.history.ReservationHistory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.AddService;
import service.Service;

public class AddCar implements Initializable {

	public TextField licencePlate;
	public TextField chassisNumber;
	public TextField manufacturer;
	public TextField model;
	public RadioButton engineP;
	public RadioButton engineG;
	public RadioButton engineLPG;
	public RadioButton categoryA;
	public RadioButton categoryB;
	public RadioButton categoryC;
	public RadioButton categoryD;
	public RadioButton categoryE;
	public Spinner<Integer> kilometers;
	public DatePicker expInsurance;
	public Slider fuelSlider;
	public TextField fuel;
	public TextField nextService;
	public Button serviceHistory;
	public Button customerHistory;
	public Button applyButton;
	public Label imageName;
	private File image;

	private static Car carObj;
	private static Stage window;

	public static void display(Car car) {

		carObj = car;

		try {
			window = new Stage();
			Parent root = FXMLLoader.load(AddCar.class.getResource("AddCar.fxml"));
			Scene scene = new Scene(root, 800, 600);
			window.initModality(Modality.APPLICATION_MODAL);
			window.setScene(scene);
			if (car == null)
				window.setTitle("Νέο Αυτοκίνητο");
			else {
				window.setTitle(car.getManufacturer() + " " + car.getModel());
			}
			window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void handleApplyButtonClicked() {

		String engine = "";
		String category = "";

		if (engineG.isSelected())
			engine = "Βενζίνη";
		else if (engineP.isSelected())
			engine = "Πετρέλαιο";
		else if (engineLPG.isSelected())
			engine = "Υγραέριο";

		if (categoryA.isSelected())
			category = "A";
		else if (categoryB.isSelected())
			category = "B";
		else if (categoryC.isSelected())
			category = "C";
		else if (categoryD.isSelected())
			category = "D";
		else if (categoryE.isSelected())
			category = "E";

		String img = "";

		if (image == null)
			img = " ";
		else
			img = image.getAbsolutePath();

		if (carObj == null) {

			Database.getInstance().execute("insert into car("
					+ "\"licencePlate\", \"chassisNumber\", \"manufacturer\", \"model\", \"engine\","
					+ "\"category\", \"kilometers\", \"expInsurance\", \"fuel\","
					+ "\"image\"" + ") values(" + "'" + licencePlate.getText() + "'" + "," + "'"
					+ chassisNumber.getText() + "'" + "," + "'" + manufacturer.getText() + "'" + "," + "'"
					+ model.getText() + "'" + "," + "'" + engine + "'" + "," + "'" + category + "'" + "," + "'"
					+ kilometers.getValue() + "'" + "," + "'"
					+ expInsurance.getValue().format(DateTimeFormatter.ISO_DATE) + "'" + "," + "'"
					+ fuel.getText() + "'" + "," + "'" + img + "'" + ");", false);

		} else {

			Database.getInstance().execute("update car set "
					+ "\"licencePlate\" = " + "'" + licencePlate.getText() + "'" + ","
					+ "\"chassisNumber\" = " + "'" + chassisNumber.getText() + "'" + ","
					+ "\"manufacturer\" = " + "'" + manufacturer.getText() + "'" + ","
					+ "\"model\" = " + "'" + model.getText() + "'" + ","
					+ "\"engine\" = " + "'" + engine + "'" + ","
					+ "\"category\" = " + "'" + category + "'" + ","
					+ "\"kilometers\" = " + "'" + kilometers.getValue() + "'" + ","
					+ "\"expInsurance\" = " + "'" + expInsurance.getValue().format(DateTimeFormatter.ISO_DATE) + "'"
					+ ","
					+ "\"fuel\" = " + "'" + fuel.getText() + "'" + ","
					+ "\"image\" = " + "'" + img + "'"
					+ " where \"id\" = " + "'" + carObj.getId() + "'", false);

		}

		applyButton.setDisable(true);

		CarManager cm = CarManager.getInstance();

		cm.clearCarList();
		cm.loadCars();

	}

	public void handleOkButtonClicked() {

		window.close();
		Controller.getInstance().handleRefreshCarsClicked();

	}

	public void handleDeleteButtonClicked() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Διαγραφή Οχήματος");
		alert.setHeaderText("Πρόκειται να διαγράψετε ένα όχημα!");
		alert.setContentText("Είστε σίγουροι οτι θέλετε να διαγράψετε"
				+ " το\nόχημα με αριθμό κυκλοφορίας " + carObj.getLicencePlate() + ";");

		ButtonType buttonTypeOK = new ButtonType("OK");
		ButtonType buttonTypeCancel = new ButtonType("Ακύρωση", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOK) {
			Database.getInstance().execute("delete from service where \"carId\" = " + carObj.getId() + ";", false);
			Database.getInstance().execute("delete from car where id = '" + carObj.getId() + "';", false);
			handleOkButtonClicked();

		} else {
			// ... user chose CANCEL or closed the dialog
		}

	}

	public void handlePrintButtonClicked() {

		FileChooser fc = new FileChooser();
		// File file = fc.showOpenDialog(null);
		fc.setTitle("Αποθήκευση Στοιχείων Αυτοκινήτου");
		fc.getExtensionFilters().add(new ExtensionFilter("Αρχείο PDF", "*.pdf"));
		File file = fc.showSaveDialog(null);

		String engine = "";
		String category = "";

		if (engineG.isSelected())
			engine = "Βενζίνη";
		else if (engineP.isSelected())
			engine = "Πετρέλαιο";
		else if (engineLPG.isSelected())
			engine = "Υγραέριο";

		if (categoryA.isSelected())
			category = "A";
		else if (categoryB.isSelected())
			category = "B";
		else if (categoryC.isSelected())
			category = "C";
		else if (categoryD.isSelected())
			category = "D";
		else if (categoryE.isSelected())
			category = "E";

		try {
			FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile());
			PdfWriter writer = new PdfWriter(fos);
			PdfDocument pdf = new PdfDocument(writer);
			Document doc = new Document(pdf);
			PdfFont font = PdfFontFactory.createFont("fonts/DroidSans.ttf", "Identity-H", true);

			Paragraph para = new Paragraph("Στοιχεία Οχήματος");
			para.setFont(font);
			para.setWidth(UnitValue.createPercentValue(100));
			// para.setHorizontalAlignment(HorizontalAlignment.CENTER);
			para.setTextAlignment(TextAlignment.CENTER);
			doc.add(para);

			Div tableDiv = new Div();
			tableDiv.setWidth(UnitValue.createPercentValue(100));

			Image img = null;
			try {
				img = new Image(ImageDataFactory.create(carObj.getImage()));
				img.scaleToFit(120, 120);
				img.setHorizontalAlignment(HorizontalAlignment.CENTER);
			} catch (Exception e) {
				img = null;
			}

			Table table = new Table(3);
			table.setHorizontalAlignment(HorizontalAlignment.CENTER);
			table.setFont(font);

			Cell imgCell = new Cell(4, 1);
			imgCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
			if (img != null)
				imgCell.add(img);
			imgCell.setWidth(UnitValue.createPercentValue(15));
			table.addCell(imgCell);

			Cell manLabelCell = new Cell();
			manLabelCell.setWidth(UnitValue.createPercentValue(20));
			manLabelCell.add(new Paragraph("Κατασκευαστής"));
			table.addCell(manLabelCell);

			Cell manCell = new Cell();
			manCell.setWidth(UnitValue.createPercentValue(35));
			manCell.add(new Paragraph(manufacturer.getText()));
			table.addCell(manCell);

			table.addCell("Μοντέλο");
			table.addCell(model.getText());
			table.addCell("Αριθμός Κυκλοφορίας");
			table.addCell(licencePlate.getText());
			table.addCell("Αριθμός Πλαισίου");
			table.addCell(chassisNumber.getText());

			Cell engineLabelCell = new Cell(1, 2);
			engineLabelCell.add(new Paragraph("Κινητήτρας"));
			table.addCell(engineLabelCell);

			table.addCell(engine);

			Cell catLabelCell = new Cell(1, 2);
			catLabelCell.add(new Paragraph("Κατηγορία"));
			table.addCell(catLabelCell);

			table.addCell(category);

			Cell kmLabelCell = new Cell(1, 2);
			kmLabelCell.add(new Paragraph("Χιλιόμετρα"));
			table.addCell(kmLabelCell);

			table.addCell(kilometers.getValue().toString());

			Cell expLabelCell = new Cell(1, 2);
			expLabelCell.add(new Paragraph("Ημ. Λήξης Ασφάλισης"));
			table.addCell(expLabelCell);

			table.addCell(expInsurance.getValue().format(DateTimeFormatter.ISO_DATE));

			Cell fuelLabelCell = new Cell(1, 2);
			fuelLabelCell.add(new Paragraph("Ποσοστό Καυσίμων"));
			table.addCell(fuelLabelCell);

			table.addCell(fuel.getText() + "%");

			Cell nsLabelCell = new Cell(1, 2);
			nsLabelCell.add(new Paragraph("Επόμενο Σέρβις"));
			table.addCell(nsLabelCell);

			table.addCell(nextService.getText());

			Cell shLabelCell = new Cell(1, 2);
			shLabelCell.add(new Paragraph("Ιστορικό Σέρβις"));
			table.addCell(shLabelCell);

			table.addCell("");

			Cell chLabelCell = new Cell(1, 2);
			chLabelCell.add(new Paragraph("Ιστορικό Πελατών"));
			table.addCell(chLabelCell);

			table.addCell(customerHistory.getText());

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

	public void handleSearchImageClicked() {

		FileChooser fc = new FileChooser();
		image = fc.showOpenDialog(null);

		if (image != null)
			imageName.setText(image.getName());

	}

	public void handleServiceHistoryButtonClicked() {
		AddService.display(carObj);
	}

	public void handleCustomerHistoryButtonClicked() {
		ReservationHistory.display(carObj);
	}

	private String getNextService() {

		ResultSet result = Database.getInstance().execute("select * from service "
				+ "where done = false and "
				+ "\"carId\"=" + carObj.getId() + " "
				+ "order by date asc limit 1;", true);
		Service service = null;

		try {
			if (result.next())
				service = new Service(result.getInt(1), result.getInt(2), result.getTimestamp(3),
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		fuel.setText("" + fuelSlider.getValue());
		fuelSlider.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> arg0, Object arg1, Object arg2) {
				fuel.setText(String.valueOf((int) fuelSlider.getValue()));
			}

		});

		fuel.textProperty()
				.addListener((v, oldValue, newValue) -> fuelSlider.setValue(Double.parseDouble(fuel.getText())));
		kilometers.setEditable(true);
		kilometers.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000000000, 0));

		if (carObj != null) {

			licencePlate.setText(carObj.getLicencePlate());
			licencePlate.setDisable(true);

			chassisNumber.setText(carObj.getChassisNumber());
			chassisNumber.setDisable(true);

			manufacturer.setText(carObj.getManufacturer());
			model.setText(carObj.getModel());

			if (carObj.getEngine().equals("Βενζίνη"))
				engineG.selectedProperty().set(true);
			else if (carObj.getEngine().equals("Πετρέλαιο"))
				engineP.selectedProperty().set(true);
			else if (carObj.getEngine().equals("Υγραέριο"))
				engineLPG.selectedProperty().set(true);

			if (carObj.getCategory().equals("A"))
				categoryA.setSelected(true);
			else if (carObj.getCategory().equals("B"))
				categoryB.setSelected(true);
			else if (carObj.getCategory().equals("C"))
				categoryC.setSelected(true);
			else if (carObj.getCategory().equals("D"))
				categoryD.setSelected(true);
			else if (carObj.getCategory().equals("E"))
				categoryE.setSelected(true);

			kilometers.setValueFactory(
					new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000000000, carObj.getKilometers()));
			expInsurance.setValue(carObj.getExpInsurance().toLocalDateTime().toLocalDate());
			fuel.setText(carObj.getFuel());
			nextService.setText(getNextService());

			image = new File(carObj.getImage());
			imageName.setText(image.getName());

			// applyButton.setDisable(true);
		}

	}

}
