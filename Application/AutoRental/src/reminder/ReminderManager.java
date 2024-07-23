package reminder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import application.Database;
import car.Car;
import customer.Customer;
import reservation.Reservation;
import service.Service;

public class ReminderManager {
	
	private List<Service> services;
	private List<Reservation> reservations;
	private static ReminderManager rm;
	
	public ReminderManager() {
		super();
		this.services = new ArrayList<>();
		this.reservations = new ArrayList<>();
	}

	public static ReminderManager getInstance() {
		
		if (rm == null) {
			rm = new ReminderManager();
		}
		
		return rm;
	}
	
	public List<Service> getServices() {
		return services;
	}
	
	public List<Reservation> getReservations() {
		return reservations;
	}
		
	public void clearServiceList() {
		services.clear();	
	}
	
	public void clearReservationList() {
		reservations.clear();
	}
	
	public void loadServices() {
		
		ResultSet result = Database.getInstance().execute("select * from service where "
														+ "done=false order by date asc;", true);	
		
		try {
			while(result.next()) {
				
				Service service = new Service(result.getInt(1), result.getInt(2), result.getTimestamp(3),
										result.getString(4), result.getInt(5), result.getString(6),
										result.getBoolean(7));
				services.add(service);
				
				ResultSet result2 = Database.getInstance().execute("select * from car where id="
																+ service.getCarId() + ";", true);
				
				if (result2.next()) service.setCar(new Car(result2.getInt(1), result2.getString(2), result2.getString(3),
														result2.getString(4), result2.getString(5), result2.getString(6),
														result2.getString(7), result2.getInt(8), result2.getTimestamp(9),
														result2.getString(10), result2.getString(11)));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
		
	public void loadReservations() {
		
		ResultSet result = Database.getInstance().execute("select * from reservation where "
														+ "delivered=false or returned=false "
														+ "order by \"fromDate\", \"toDate\" asc;", true);	
				
		try {
			while(result.next()) {
				
				Reservation reservation;
				
				Timestamp fromTimestamp = result.getTimestamp("fromDate");
				Timestamp toTimestamp = result.getTimestamp("toDate");
				
				LocalDateTime fromLDT = fromTimestamp.toLocalDateTime();
				LocalDateTime toLDT = toTimestamp.toLocalDateTime();
				
				LocalDate fromDate = fromLDT.toLocalDate();
				LocalDate toDate = toLDT.toLocalDate();
				
				LocalTime fromTime = fromLDT.toLocalTime();
				LocalTime toTime = toLDT.toLocalTime();
				
				reservations.add(reservation = new Reservation(result.getInt("id"), fromDate.toString(),
						toDate.toString(), fromTime.toString(), toTime.toString(),
						result.getString("fromPlace"), result.getString("toPlace"),
						"" + result.getInt("days"), result.getInt("car"), result.getInt("customer"),
						result.getBoolean("delivered"), result.getBoolean("returned")));
				
				ResultSet result2 = Database.getInstance().execute("select * from customer where id = " + (0 + reservation.getCustomerId()) + ";", true);
				if(result2.next()) reservation.setCustomer(new Customer(result2.getInt(1), result2.getString(2), result2.getString(3),
																		result2.getString(4), result2.getString(5), result2.getString(6),
																		result2.getString(7), result2.getString(8), result2.getString(9),
																		result2.getString(10), result2.getString(11), result2.getString(12),
																		result2.getString(13)));
				
				ResultSet result3 = Database.getInstance().execute("select * from car where id = " + (0 + reservation.getCarId()) + ";", true);
				if(result3.next()) reservation.setCar(new Car(result3.getInt(1), result3.getString(2), result3.getString(3),
						result3.getString(4), result3.getString(5), result3.getString(6), result3.getString(7),
						result3.getInt(8), result3.getTimestamp(9), result3.getString(10), result3.getString(11)));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
