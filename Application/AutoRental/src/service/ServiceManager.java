package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.Database;

public class ServiceManager {
	
	private List<Service> services;
	private static ServiceManager sm;
	
	public ServiceManager() {
		super();
		this.services = new ArrayList<>();
	}

	public static ServiceManager getInstance() {
		
		if (sm == null) {
			sm = new ServiceManager();
		}
		
		return sm;
	}
	
	public List<Service> getServices() {
		return services;
	}
	
	public void addService(Service service) {
		services.add(service);
	}
	

	public void loadServices(int carId) {
		
		ResultSet result = Database.getInstance().execute("select * from service where \"carId\"=" + carId + ";", true);	
		
		try {
			while(result.next()) {
				services.add(new Service(result.getInt(1), result.getInt(2), result.getTimestamp(3),
										result.getString(4), result.getInt(5), result.getString(6),
										result.getBoolean(7)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	public void clearServiceList() {
		
		services.clear();
		
	}
	

}
