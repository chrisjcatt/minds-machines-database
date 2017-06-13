package hackathon.store.dto;

public class StatsCounter {
	
	@Override
	public String toString() {
		return "StatsCounter [hour=" + hour + ", count=" + count + ", date=" + date + "]";
	}

	public int hour;
	
	public int count;
	
	public String date;

	public StatsCounter(int hour, int count, String date) {
		super();
		this.hour = hour;
		this.count = count;
		this.date = date;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
