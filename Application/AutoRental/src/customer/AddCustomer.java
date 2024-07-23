package customer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.StringConverter;

public class AddCustomer implements Initializable {
	
	public TextField personalId;
	public TextField firstName;
	public TextField lastName;
	public RadioButton genderM;
	public RadioButton genderF;
	public DatePicker birthDate;
	public TextField phone;
	public TextField email;
	public TextField licence;
	public TextField country;
	public TextField town;
	public TextField address;
	public TextField zipCode;
	public Button applyButton;
	
	private static Customer customerObj;
	private static Stage window;
	
	public static void display(Customer customer) {
		
		customerObj = customer;
		
		try {
			window = new Stage();
			Parent root = FXMLLoader.load(AddCustomer.class.getResource("AddCustomer.fxml"));
			Scene scene = new Scene(root, 800, 600);
			window.initModality(Modality.APPLICATION_MODAL);
			window.setScene(scene);
			if (customer == null) window.setTitle("Νέος Πελάτης");
			else {
				window.setTitle(customer.getFirstName() + " " + customer.getLastName());
			}
			window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void handleOkButtonClicked() {
		
		window.close();
		Controller.getInstance().handleRefreshCustomersClicked();
		
	}
	
	public void handlePrintButtonClicked() {
		
		FileChooser fc = new FileChooser();
		fc.setTitle("Αποθήκευση Στοιχείων Πελάτη");
		fc.getExtensionFilters().add(new ExtensionFilter("Αρχείο PDF", "*.pdf"));
		File file = fc.showSaveDialog(null);
		
		String gender = "";
		
		if (genderM.isSelected()) gender = "Άνδρας";
		else if (genderF.isSelected()) gender = "Γυναίκα";
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		try {
			FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile());
			PdfWriter writer = new PdfWriter(fos);
			PdfDocument pdf = new PdfDocument(writer);
			Document doc = new Document(pdf);
			PdfFont font = PdfFontFactory.createFont("fonts/DroidSans.ttf", "Identity-H", true);
			
			Paragraph para = new Paragraph("Στοιχεία Πελάτη");
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
			
			Cell nameLabelCell = new Cell();
			nameLabelCell.setWidth(UnitValue.createPercentValue(50));
			nameLabelCell.add(new Paragraph("Όνομα"));
			table.addCell(nameLabelCell);
			
			Cell nameCell = new Cell();
			nameCell.setWidth(UnitValue.createPercentValue(50));
			nameCell.add(new Paragraph(firstName.getText()));
			table.addCell(nameCell);
			
			table.addCell("Επώνυμο");
			table.addCell(lastName.getText());
			
			table.addCell("Φύλλο");
			table.addCell(gender);
			
			table.addCell("Ημ. Γέννησης");
			table.addCell(dateFormatter.format(birthDate.getValue()));
			
			table.addCell("Ταυτότητα");
			table.addCell(personalId.getText());
			
			table.addCell("Δίπλωμα");
			table.addCell(licence.getText());
			
			table.addCell("Τηλέφωνο");
			table.addCell(phone.getText());
			
			table.addCell("E-mail");
			table.addCell(email.getText());
			
			table.addCell("Διεύθυνση");
			table.addCell(address.getText() + ", " + town.getText() + ", "
						+ country.getText() + ", " + zipCode.getText());
						
			tableDiv.add(table);
			doc.add(tableDiv);
			
			doc.close();
			pdf.close();
			writer.close();
			fos.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void handleDeleteButtonClicked() {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Διαγραφή Πελάτη");
		alert.setHeaderText("Πρόκειται να διαγράψετε έναν πελάτη!");
		alert.setContentText("Είστε σίγουροι οτι θέλετε να διαγράψετε"
				+ " τον\nπελάτη με ονοματεπώνυμο " + customerObj.getFirstName() + " " + customerObj.getLastName() + ";");
		
		ButtonType buttonTypeOK = new ButtonType("OK");
		ButtonType buttonTypeCancel = new ButtonType("Ακύρωση", ButtonData.CANCEL_CLOSE);
		
		alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
		
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == buttonTypeOK){
			
			ResultSet rs = Database.getInstance().execute("select 1 from reservation where customer = " + customerObj.getId() + ";", true);
			
			try {
				if(rs.next())
					if(!deleteCustomersReservations(customerObj.getId())) return;
				
				Database.getInstance().execute("delete from customer where id = '" + customerObj.getId() + "';", false);
				handleOkButtonClicked();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		    
		    
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
				
	}
	
	private boolean deleteCustomersReservations(int customer) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Διαγραφή Κρατήσεων Πελάτη");
		alert.setHeaderText("Αυτός ο πελάτης έχει κρατήσεις!");
		alert.setContentText("Είστε σίγουροι οτι θέλετε να διαγράψετε"
				+ " όλες\nτις κρατήσεις του πελάτη αυτού;");
		
		ButtonType buttonTypeOK = new ButtonType("OK");
		ButtonType buttonTypeCancel = new ButtonType("Ακύρωση", ButtonData.CANCEL_CLOSE);
		
		alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
		
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == buttonTypeOK){
			Database.getInstance().execute("delete from reservation where customer = '" + customer + "';", false);
			return true;
		}
		else return false;
	}
	
	public void handleApplyButtonClicked() {
		
		String gender = "";
		String birthDates = "";
		
		if (genderM.isSelected()) gender = "Άνδρας";
		else if (genderF.isSelected()) gender = "Γυναίκα";
		
		if (birthDate.getValue() == null) birthDates = "null";
		else birthDates = birthDate.getValue().toString();
				
		if (customerObj == null) {
			
			Database.getInstance().execute("insert into customer("
											+ "\"firstName\", \"lastName\", \"gender\", \"phone\", \"email\", \"personalId\","
											+ "\"licence\", \"country\", \"town\", \"address\", \"zipCode\", \"birthDate\" " + ") values("  
											+ "'" + firstName.getText() + "'" + "," + "'" + lastName.getText() + "'" + ","
											+ "'" + gender + "'" + "," + "'" + phone.getText() + "'" + "," 
											+ "'" + email.getText() + "'" + "," + "'" + personalId.getText() + "'" + ","
											+ "'" + licence.getText() + "'" + "," 
											+ "'" + country.getText() + "'" + "," + "'"	+ town.getText() + "'" + "," 
											+ "'" + address.getText() + "'" + "," + "'" + zipCode.getText() + "'" + ","
											+ "'" + birthDates + "'" + ");", false);	
			
		}
		else {
			
			Database.getInstance().execute("update customer set " 
					+ "\"firstName\" = " + "'" + firstName.getText() + "'" + ","
					+ "\"lastName\" = " + "'" + lastName.getText() + "'" + ","
					+ "\"gender\" = " + "'" + gender + "'" + ","
					+ "\"phone\" = " + "'" + phone.getText() + "'" + ","
					+ "\"email\" = " + "'" + email.getText() + "'" + "," 
					+ "\"personalId\" = " + "'" + personalId.getText() + "'" + ","
					+ "\"licence\" = " + "'" + licence.getText() + "'" + "," 
					+ "\"country\" = " + "'" + country.getText() + "'" + ","
					+ "\"town\" = " + "'" + town.getText() + "'" + ","
					+ "\"address\" = " +  "'" + address.getText() + "'" + ","
					+ "\"zipCode\" = " + "'" + zipCode.getText() + "'" + ","
					+ "\"birthDate\" = " + "'" + birthDates + "'"
					+ " where \"id\" = " + "'" + customerObj.getId() + "'", false);
			
		}
		
		applyButton.setDisable(true);
		
		CustomerManager cm = CustomerManager.getInstance();
		
		cm.clearCustomerList();
		cm.loadCustomers();
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		birthDate.setConverter(new StringConverter<LocalDate>() {
		     DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		     @Override 
		     public String toString(LocalDate date) {
		         if (date != null) {
		        	 try {
		        		 return dateFormatter.format(date); 
		        	 }catch(Exception e){
		        		 return "";
		        	 }
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
		
		if (customerObj != null) {
			
			personalId.setText(customerObj.getPersonalId());
			
			firstName.setText(customerObj.getFirstName());
			lastName.setText(customerObj.getLastName());
						
			if (customerObj.getGender().equals("Άνδρας")) genderM.selectedProperty().set(true);
			else if (customerObj.getGender().equals("Γυναίκα")) genderF.selectedProperty().set(true);
						
			phone.setText(customerObj.getPhone());
			email.setText(customerObj.getEmail());
			if (!customerObj.getBirthDate().equals("null")) birthDate.setValue(LocalDate.parse(customerObj.getBirthDate()));
			licence.setText(customerObj.getLicence());
			country.setText(customerObj.getCountry());
			town.setText(customerObj.getTown());
			address.setText(customerObj.getAddress());
			zipCode.setText(customerObj.getZipCode());
			
			//applyButton.setDisable(true);
		}
		
	}
	
}
