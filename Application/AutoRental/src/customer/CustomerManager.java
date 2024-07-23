package customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import application.Database;

public class CustomerManager {
	
	private List<Customer> customers;
	private static CustomerManager cm;
	
	public CustomerManager() {
		super();
		this.customers = new ArrayList<>();
	}

	public static CustomerManager getInstance() {
		
		if (cm == null) {
			cm = new CustomerManager();
		}
		
		return cm;
	}

	public List<Customer> getCustomers() {
		return customers;
	}
	
	public void addCustomer(Customer customer) {
		customers.add(customer);
	}
	
	public void loadCustomers() {
		
		ResultSet result = Database.getInstance().execute("select * from customer;", true);	
		
		try {
			while(result.next()) {
				customers.add(new Customer(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),
								result.getString(5), result.getString(6), result.getString(7), result.getString(8),
								result.getString(9), result.getString(10), result.getString(11), result.getString(12),
								result.getString(13)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void clearCustomerList() {
		customers.clear();
	}
	
}
