package car;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import application.Database;

public class CarManager {
	
	private List<Car> cars;
	private static CarManager cm;
	
	public CarManager() {
		super();
		this.cars = new ArrayList<>();
	}

	public static CarManager getInstance() {
		
		if (cm == null) {
			cm = new CarManager();
		}
		
		return cm;
	}
	
	public List<Car> getCars() {
		return cars;
	}
	
	public void addCar(Car car) {
		cars.add(car);
	}
	

	public void loadCars() {
		
		ResultSet result = Database.getInstance().execute("select * from car;", true);	
		
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
	
	public void clearCarList() {
		
		cars.clear();
		
	}
	
}
