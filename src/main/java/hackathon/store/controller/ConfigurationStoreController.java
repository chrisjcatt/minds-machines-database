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
		System.out.println("Deleting bookings");
		configurationStoreDatabaseUtils.deleteAllBookings();
	}

	@CrossOrigin
	@RequestMapping(value = "bookings", method = { RequestMethod.POST }, consumes = { "application/json" })
	public void createBookings(@RequestBody BookingsTable[] bookings) {
		System.out.println("Posting bookings");
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

		HashMap<Integer, Integer> counter = new HashMap<Integer, Integer>();
		List<StatsCounter> counts = new ArrayList<StatsCounter>();
		int beginIndex = 12;
		int endIndex = 14;

		if (startDate.length() < beginIndex || endDate.length() < beginIndex) {
			System.out.println("Input strings too short");
			return counts;
		}

		System.out.println("startDate hour: " + startDate.substring(beginIndex, endIndex));
		System.out.println("endDate hour: " + endDate.substring(beginIndex, endIndex));

		Integer startHour = Integer.parseInt(startDate.substring(beginIndex, endIndex));
		Integer endHour = Integer.parseInt(endDate.substring(beginIndex, endIndex));

		String dateDay = startDate.substring(0, 12);

		List<BookingsTable> bookings = getBookings();

		for (BookingsTable booking : bookings) {
			System.out.println(booking.toString());
			if (booking.getFrom().length() > endIndex && booking.getTo().length() > endIndex) {
				Integer bookStart = Integer.parseInt(booking.getFrom().substring(beginIndex, endIndex));
				Integer bookEnd = Integer.parseInt(booking.getTo().substring(beginIndex, endIndex));

				for (Integer i = bookStart; i <= bookEnd; i++) {
					if (i >= startHour && i <= endHour) {
						if (counter.containsKey(i)) {
							counter.put(i, (counter.get(i) + 1));
						} else {
							counter.put(i, 1);
						}
					}
				}
			}
		}

		for (Integer i : counter.keySet()) {
			counts.add(new StatsCounter(i, counter.get(i), dateDay));

		}

		for (StatsCounter cc : counts) {
			System.out.println(cc.toString());
		}
		return counts;
	}

}