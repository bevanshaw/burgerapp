package server.firebae.rest;

import java.sql.Timestamp;

public class TimestampToDate {

	public TimestampToDate() {
		// TODO Auto-generated constructor stub
	}
	
	public static Timestamp format(long time) {
		 Timestamp ts = new Timestamp(time);
		return ts;
	}

}
