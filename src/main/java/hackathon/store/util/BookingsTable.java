package hackathon.store.util;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@SqlResultSetMappings({
		@SqlResultSetMapping(name = "BookingsTable", classes = @ConstructorResult(targetClass = BookingsTable.class, columns = {
				@ColumnResult(name = "id"), @ColumnResult(name = "station_id"), @ColumnResult(name = "from_loc"),
				@ColumnResult(name = "to_loc"), @ColumnResult(name = "user_id") })) })

@Entity
@Table(name = "bookingstable")
public class BookingsTable {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	public String id;

	@Override
	public String toString() {
		return "BookingsTable [id=" + id + ", station_id=" + station_id + ", from=" + from + ", to=" + to + ", user_id="
				+ user_id + "]";
	}

	private String station_id;

	@Column(name = "from_loc")
	private String from;

	@Column(name = "to_loc")
	private String to;

	private String user_id;

	public String getId() {
		return id;
	}

	public BookingsTable() {

	}

	public BookingsTable(String id, String station_id, String from, String to, String user_id) {
		this.id = id;
		this.station_id = station_id;
		this.from = from;
		this.to = to;
		this.user_id = user_id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStation_id() {
		return station_id;
	}

	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
