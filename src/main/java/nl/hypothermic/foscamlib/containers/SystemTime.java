package nl.hypothermic.foscamlib.containers;

/******************************\
 * > SystemTime.java		< *
 * FoscamLib by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class SystemTime {
	
	/** Time Source to use */
	public TimeSource timeSource;
	/** NTP Server to use */
	public NTPServer ntpServer;
	/** DateFormat to use */
	public DateFormat dateFormat;
	/** TimeFormat to use (12h or 24h) */
	public TimeFormat timeFormat;
	/** The amount of seconds between local time and GMT time */
	public int timeZone;
	/** Is Daylight Saving Time enabled */
	public boolean isDST;
	/** [not in use currently] */
	public String dst;
	/** Year */
	public int year;
	/** Month */
	public int month;
	/** Day */
	public int day;
	/** Hour */
	public int hour;
	/** Minute */
	public int minute;
	/** Second */
	public int second;
	
	/** Constructor for a SystemTime object */
	public SystemTime(TimeSource timeSource, NTPServer ntpServer, DateFormat dateFormat, TimeFormat timeFormat,
					  int timeZone, boolean isDST, String dst, int year, int month, int day, int hour, int minute, int second) {
		this.timeSource = timeSource;
		this.ntpServer = ntpServer;
		this.dateFormat = dateFormat;
		this.timeFormat = timeFormat;
		this.timeZone = timeZone;
		this.isDST = isDST;
		this.dst = dst;
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
	
	/**
	 * Returns the GMT offset (in hours) <br><br>
	 * Please note that DST is not (and should not be) factored in returned int.
	 * @return GMT offset in hours
	 */
	public int getGMT() {
		// Note: we have to negate the number. What the fuck Foscam, why do you store it like this??
		return -(this.timeZone / 3600);
	}

	/**
	 * Enum of time sources with their respective integers attached.
	 */
	public static enum TimeSource {
		NTP(0),
		MANUAL(1);
		
		private final int value;
		
		private TimeSource(final int value) {
			this.value = value;
		}
		
		/**
		 * Returns int value of TimeSource instance
		 */
		public int getValue() {
			return this.value;
		}
		
		/**
		 * Get TimeSource instance from int
		 */
		public static TimeSource match(int x) {
	        switch(x) {
	        case 0:
	            return NTP;
	        case 1:
	            return MANUAL;
	        }
	        return null;
	    }
		
		/**
		 * Get TimeSource instance from String
		 */
		public static TimeSource match(String x) {
			if (x.contains("0"))
	            return NTP;
	        if (x.contains("1"))
	            return MANUAL;
	        return null;
	    }
	}
	
	/**
	 * Enum of supported NTP Servers with their respective addresses attached as String.
	 */
	public static enum NTPServer {
		/** time.nist.gov */
		NIST("time.nist.gov"),
		/** time.kriss.re.kr */
		KRISS("time.kriss.re.kr"),
		/** time.windows.com */
		WINDOWS("time.windows.com"),
		/** time.nuri.net */
		NURI("time.nuri.net");
		
		private final String value;
		
		private NTPServer(final String value) {
			this.value = value;
		}
		
		/**
		 * Returns String value of NTPServer instance
		 */
		public String getValue() {
			return this.value;
		}
		
		/**
		 * Get NTPServer instance from String
		 */
		public static NTPServer match(String x) {
	        if (x == "time.nist.gov")
	            return NIST;
	        if (x == "time.kriss.re.kr")
	            return KRISS;
			if (x == "time.windows.com")
				return WINDOWS;
			if (x == "time.nuri.net")
				return NURI;
	        return null;
	    }
	}
	
	/**
	 * Enum of date formats with their respective integers attached.
	 */
	public static enum DateFormat {
		YYYY_MM_DD(0),
		DD_MM_YYYY(1),
		MM_DD_YYYY(2);
		
		private final int value;
		
		private DateFormat(final int value) {
			this.value = value;
		}
		
		/**
		 * Returns int value of DateFormat instance
		 */
		public int getValue() {
			return this.value;
		}
		
		/**
		 * Get DateFormat instance from int
		 */
		public static DateFormat match(int x) {
	        switch(x) {
	        case 0:
	            return YYYY_MM_DD;
	        case 1:
	            return DD_MM_YYYY;
	        case 2:
	            return MM_DD_YYYY;
	        }
	        return null;
	    }
		
		/**
		 * Get DateFormat instance from String
		 */
		public static DateFormat match(String x) {
	        if (x.contains("0"))
	            return YYYY_MM_DD;
	        if (x.contains("1"))
	            return DD_MM_YYYY;
			if (x.contains("2"))
				return MM_DD_YYYY;
	        return null;
	    }
	}
	
	/**
	 * Enum of time formats with their respective integers attached.
	 */
	public static enum TimeFormat {
		
		// I wish that enum entries could begin with an integer. rip.
		
		/** 12-hour clock */
		x12(0),
		/** 24-hour clock */
		x24(1);
		
		private final int value;
		
		private TimeFormat(final int value) {
			this.value = value;
		}
		
		/**
		 * Returns int value of TimeFormat instance
		 */
		public int getValue() {
			return this.value;
		}
		
		/**
		 * Get TimeFormat instance from int
		 */
		public static TimeFormat match(int x) {
	        switch(x) {
	        case 0:
	            return x12;
	        case 1:
	            return x24;
	        }
	        return null;
	    }
		
		/**
		 * Get TimeFormat instance from String
		 */
		public static TimeFormat match(String x) {
	        if (x.contains("0"))
	            return x12;
	        if (x.contains("1"))
	            return x24;
	        return null;
	    }
	}
}
