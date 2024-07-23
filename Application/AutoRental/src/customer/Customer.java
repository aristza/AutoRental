package customer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Customer {
	
	public static int FONT = 22;
	private int id;
	private String firstName;
	private String lastName;
	private String gender;
	private String phone;
	private String email;
	private String personalId;
	private String licence;
	private String country;
	private String town;
	private String address;
	private String zipCode;
	private String birthDate;

	

	public Customer(int id, String firstName, String lastName, String gender, String phone, String email,
			String personalId, String licence, String country, String town, String address, String zipCode,
			String birthDate) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.personalId = personalId;
		this.licence = licence;
		this.country = country;
		this.town = town;
		this.address = address;
		this.zipCode = zipCode;
		this.birthDate = birthDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public HBox createCustomerInfoBox() {
			
		HBox customer = new HBox();
		customer.setPadding(new Insets(5, 5, 5, 5));
		
		Label name = new Label(firstName + " " + lastName);
		name.setWrapText(true);
		name.setMinWidth(210);
		name.setPrefHeight(180);
		name.setAlignment(Pos.CENTER);
		name.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 1;" + "-fx-border-radius: 20;");
		name.setFont(Font.font(null, FontWeight.BOLD, FONT));;
		name.setTextAlignment(TextAlignment.CENTER);
		
		VBox info = new VBox();
		HBox line1 = new HBox();
		HBox line2 = new HBox();
		HBox line3 = new HBox();
		HBox line4 = new HBox();
		
		info.setPadding(new Insets(5,5,5,5));
		
		Label phoneLabel1 = new Label("Τηλέφωνο: ");
		phoneLabel1.setFont(Font.font(null, FontWeight.BOLD, FONT));
		phoneLabel1.setPadding(new Insets(5, 0, 5, 10));
		Label phoneLabel2 = new Label(phone);
		phoneLabel2.setFont(Font.font(FONT));
		phoneLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label idLabel1 = new Label("Ταυτότητα: ");
		idLabel1.setFont(Font.font(null, FontWeight.BOLD, FONT));
		idLabel1.setPadding(new Insets(5, 0, 5, 10));
		Label idLabel2 = new Label(personalId);
		idLabel2.setFont(Font.font(FONT));
		idLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label licenceLabel1 = new Label("Δίπλωμα: ");
		licenceLabel1.setFont(Font.font(null, FontWeight.BOLD, FONT));
		licenceLabel1.setPadding(new Insets(5, 0, 5, 10));
		Label licenceLabel2 = new Label(licence);
		licenceLabel2.setFont(Font.font(FONT));
		licenceLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label genderLabel1 = new Label("Φύλλο: ");
		genderLabel1.setFont(Font.font(null, FontWeight.BOLD, FONT));
		genderLabel1.setPadding(new Insets(5, 0, 5, 10));
		Label genderLabel2 = new Label(gender);
		genderLabel2.setFont(Font.font(FONT));
		genderLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label emailLabel1 = new Label("E-mail: ");
		emailLabel1.setFont(Font.font(null, FontWeight.BOLD, FONT));
		emailLabel1.setPadding(new Insets(5, 0, 5, 10));
		Label emailLabel2 = new Label(email);
		emailLabel2.setFont(Font.font(FONT));
		emailLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label addressLabel1 = new Label("Διεύθυνση: ");
		addressLabel1.setMinWidth(160);
		addressLabel1.setFont(Font.font(null, FontWeight.BOLD, FONT));
		addressLabel1.setPadding(new Insets(5, 0, 5, 10));
		Label addressLabel2 = new Label(address + ", " + town + ", " + country + ", " + zipCode);
		addressLabel2.setFont(Font.font(FONT));
		addressLabel2.setWrapText(true);
		addressLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		Label birthLabel1 = new Label("Ημ. Γέννησης: ");
		birthLabel1.setFont(Font.font(null, FontWeight.BOLD, FONT));
		birthLabel1.setPadding(new Insets(5, 0, 5, 10));
		Label birthLabel2 = new Label(birthDate);
		birthLabel2.setFont(Font.font(FONT));
		birthLabel2.setPadding(new Insets(5, 10, 5, 0));
		
		line1.getChildren().addAll(phoneLabel1, phoneLabel2, idLabel1, idLabel2);
		line2.getChildren().addAll(genderLabel1, genderLabel2, emailLabel1, emailLabel2);
		line3.getChildren().addAll(addressLabel1, addressLabel2);
		line4.getChildren().addAll(licenceLabel1, licenceLabel2, birthLabel1, birthLabel2);
		info.getChildren().addAll(line1, line2, line4, line3);
		customer.getChildren().addAll(name, info);
		
		customer.setOnMouseClicked(e -> AddCustomer.display(this));
		customer.setOnMouseEntered(e -> {
			customer.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 1;");
		});
		customer.setOnMouseExited(e -> customer.setStyle("-fx-border-width: 0;"));
		
		return customer;
	
	}
	
}
