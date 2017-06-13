/*
 * Copyright (c) 2016 General Electric Company. All rights reserved.
 *
 * The copyright to the computer software herein is the property of
 * General Electric Company. The software may be used and/or copied only
 * with the written permission of General Electric Company or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package hackathon.store.util;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import hackathon.store.BookingsTable;

/**
 * Database utility class for retrieving snapshot configurations data.
 * 
 * @author 502604074
 */
@Service
@Transactional
public class DatabaseUtils implements Serializable {

	private static final long serialVersionUID = -5074609646334332298L;

	private String QUERY_TRUNCATE = "TRUNCATE TABLE hackathon.bookingstable";

	/** default database schema. */
	@Value("${spring.jpa.properties.hibernate.default_schema}")
	private String defaultSchema;

	/** JPA entity manager. */
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public List<BookingsTable> getAllBookings() {

		System.out.println(defaultSchema);

		String queryString = "SELECT * FROM hackathon.bookingstable";

		final Query queryDefNames = em.createNativeQuery(queryString, "BookingsTable");

		@SuppressWarnings("unchecked")
		final List<BookingsTable> bookings = queryDefNames.getResultList();

		return bookings;

	}

	@Transactional
	public List<BookingsTable> getBookingFilterByStation(String stationFilter) {

		System.out.println(defaultSchema);

		String queryString = "SELECT * FROM hackathon.bookingstable WHERE station_id = '*WHERE*'";
		queryString = queryString.replace("*WHERE*", stationFilter);
		final Query queryDefNames = em.createNativeQuery(queryString, "BookingsTable");

		@SuppressWarnings("unchecked")
		final List<BookingsTable> bookings = queryDefNames.getResultList();

		return bookings;

	}
	
	@Transactional
	public void createBookings(BookingsTable[] bookings) {
		System.out.println("adding multiple bookings");
		for (BookingsTable booking : bookings) {
			em.persist(booking);
		}
	}

	@Transactional
	public void createBooking(BookingsTable booking) {
		System.out.println("adding a booking");
		em.persist(booking);
	}

	@Transactional
	public void deleteAllBookings() {

		em.createNativeQuery(QUERY_TRUNCATE).executeUpdate();

	}

	@Transactional
	public void deleteBooking(String id) {
		String queryString = "DELETE FROM bookingstable WHERE id = '*ID*'";
		queryString = queryString.replace("*ID*", id);
		em.createNativeQuery(QUERY_TRUNCATE).executeUpdate();
	}
}
