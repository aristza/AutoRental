package reservation.history;

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

public class ReservationHistoryManager {

	private List<Reservation> reservations;
	private static ReservationHistoryManager rhm;

	public ReservationHistoryManager() {
		super();
		this.reservations = new ArrayList<>();
	}

	public static ReservationHistoryManager getInstance() {
		if (rhm == null)
			rhm = new ReservationHistoryManager();
		return rhm;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void clearReservationList() {
		reservations.clear();
	}

	public void loadReservations(int carId) {

		ResultSet result = Database.getInstance().execute("select * from reservation where "
				+ "car=" + carId + ";", true);

		try {
			while (result.next()) {

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

				ResultSet result2 = Database.getInstance()
						.execute("select * from customer where id = " + (0 + reservation.getCustomerId()) + ";", true);
				if (result2.next())
					reservation.setCustomer(new Customer(result2.getInt(1), result2.getString(2), result2.getString(3),
							result2.getString(4), result2.getString(5), result2.getString(6),
							result2.getString(7), result2.getString(8), result2.getString(9),
							result2.getString(10), result2.getString(11), result2.getString(12),
							result2.getString(13)));

				ResultSet result3 = Database.getInstance()
						.execute("select * from car where id = " + (0 + reservation.getCarId()) + ";", true);
				if (result3.next())
					reservation.setCar(new Car(result3.getInt(1), result3.getString(2), result3.getString(3),
							result3.getString(4), result3.getString(5), result3.getString(6), result3.getString(7),
							result3.getInt(8), result3.getTimestamp(9), result3.getString(10), result3.getString(11)));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
