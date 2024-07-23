package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import car.Car;

public class AvailableCarManager {
	private List<Car> cars;
	private static AvailableCarManager cm;
	
	public AvailableCarManager() {
		super();
		this.cars = new ArrayList<>();
	}

	public static AvailableCarManager getInstance() {
		
		if (cm == null) {
			cm = new AvailableCarManager();
		}
		
		return cm;
	}
	
	public List<Car> getAvailableCars() {
		return cars;
	}
	
	public void addAvailableCar(Car car) {
		cars.add(car);
	}
	

	public void loadAvailableCars(String fromDate, String toDate, String category) {
		
		String text = "select * from car where \"id\" not in (select car from reservation where "
				+ "(tsrange(\"fromDate\"::timestamp - interval '2 hours', \"toDate\"::timestamp + interval '2 hours', '[]') "
				+ "&& tsrange('" + fromDate + "', '" + toDate + "', '()'))"
				+ ") and category in (" + category + ");";
				
		ResultSet result = Database.getInstance().execute(text, true);
		
		try {
			while(result.next()) {
				cars.add(new Car(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),
								result.getString(5), result.getString(6), result.getString(7), result.getInt(8),
								result.getTimestamp(9), result.getString(10), result.getString(11)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	public void clearAvailableCarList() {
		
		cars.clear();
		
	}
}
