package hackathon.store.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hackathon.store.dto.StationIdRequest;
import hackathon.store.dto.StatsCounter;
import hackathon.store.dto.StatsUsageRequest;
import hackathon.store.util.BookingsTable;
import hackathon.store.util.DatabaseUtils;

@RestController
@RequestMapping("/database")
public class ConfigurationStoreController {

	/**
	 * Database utility class for the database operations.
	 */
	@Autowired
	public DatabaseUtils configurationStoreDatabaseUtils;

	@CrossOrigin
	@RequestMapping(value = "bookings", method = { RequestMethod.GET })
	public List<BookingsTable> getBookings() {
		System.out.println("Getting all bookings");
		return configurationStoreDatabaseUtils.getAllBookings();
	}

	@CrossOrigin
	@RequestMapping(value = "bookings/stationid", method = { RequestMethod.POST })
	public List<BookingsTable> getBookingWhere(@RequestBody StationIdRequest stationIdRequest) {
		System.out.println("Getting bookings where station_id = " + stationIdRequest.getStation_id());
		return configurationStoreDatabaseUtils.getBookingFilterByStation(stationIdRequest.getStation_id());
	}

	@CrossOrigin
	@RequestMapping(value = "bookings", method = { RequestMethod.DELETE })
	public void deleteBookings() {
		System.out.println("Deleting all bookings");
		configurationStoreDatabaseUtils.deleteAllBookings();
	}

	@CrossOrigin
	@RequestMapping(value = "bookings", method = { RequestMethod.POST }, consumes = { "application/json" })
	public void createBookings(@RequestBody BookingsTable[] bookings) {
		System.out.println("Posting multiple bookings");
		configurationStoreDatabaseUtils.createBookings(bookings);
	}

	@CrossOrigin
	@RequestMapping(value = "booking", method = { RequestMethod.DELETE })
	public void deleteBooking(@RequestBody String id) {
		System.out.println("Deleting a booking");
		configurationStoreDatabaseUtils.deleteBooking(id);
	}

	@CrossOrigin
	@RequestMapping(value = "booking", method = { RequestMethod.POST })
	public void createBooking(@RequestBody BookingsTable booking) {
		System.out.println("Posting a booking");
		configurationStoreDatabaseUtils.createBooking(booking);
	}

	@CrossOrigin
	@RequestMapping(value = "usage", method = { RequestMethod.POST }, consumes = { "application/json" })
	public List<StatsCounter> createBookings(@RequestBody StatsUsageRequest request) {
		System.out.println("Getting stats");
		return getUsageStats(request.getStartDate(), request.getEndDate());
	}

	private List<StatsCounter> getUsageStats(String startDate, String endDate) {

		List<StatsCounter> counts = new ArrayList<StatsCounter>();
		int beginIndexHour = 12;
		int endIndexHour = 14;

		if (startDate.length() < beginIndexHour || endDate.length() < beginIndexHour) {
			System.out.println("Input strings are not in the format YYYY-MON-DD HH24:MI:SS");
			return counts;
		}

		List<BookingsTable> bookings = getBookings();

		HashMap<Integer, Integer> counter = new HashMap<Integer, Integer>();

		for (BookingsTable booking : bookings) {

			if (booking.getFrom().length() > endIndexHour && booking.getTo().length() > endIndexHour) {
				Integer bookStart = Integer.parseInt(booking.getFrom().substring(beginIndexHour, endIndexHour));
				Integer bookEnd = Integer.parseInt(booking.getTo().substring(beginIndexHour, endIndexHour));

				if (bookEnd < bookStart) {
					bookEnd = bookEnd + 24;
				}

				for (Integer i = bookStart; i <= bookEnd; i++) {

					int key = i % 24;
					System.out.println("key" + Integer.toString(key));
					if (counter.containsKey(key)) {
						counter.put(key, (counter.get(key) + 1));
					} else {
						counter.put(key, 1);
					}
				}

			}
		}

		for (Integer i : counter.keySet()) {
			if (i > 17) {
				counts.add(new StatsCounter(i, counter.get(i), "2017-JUN-12"));
			} else {
				counts.add(new StatsCounter(i, counter.get(i), "2017-JUN-13"));
			}
		}

		return counts;
	}
}