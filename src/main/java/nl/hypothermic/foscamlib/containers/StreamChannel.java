package nl.hypothermic.foscamlib.containers;

import nl.hypothermic.foscamlib.containers.SystemTime.DateFormat;

/******************************\
 * > StreamChannel.java		< *
 * FoscamLib by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public enum StreamChannel {
	
	/** The camera's main stream channel */
	MAIN_STREAM(0),
	/** The camera's secondary stream channel */
	SUB_STREAM(1);
	
	private final int value;
	
	private StreamChannel(final int value) {
		this.value = value;
	}

	/** Get value related to stream channel
	 * @return Value of StreamChannel instance
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Get StreamChannel instance from int
	 * @param x = int to get StreamChannel instance from
	 * @return StreamChannel instance or null if error
	 */
	public static StreamChannel match(int x) {
        switch(x) {
        case 0:
            return MAIN_STREAM;
        case 1:
            return SUB_STREAM;
        }
        return null;
    }
	
	/**
	 * Get StreamChannel instance from String
	 * @param x = String to get StreamChannel instance from
	 * @return StreamChannel instance or null if error
	 */
	public static StreamChannel match(String x) {
        if (x.contains("0"))
            return MAIN_STREAM;
        if (x.contains("1"))
            return SUB_STREAM;
        return null;
    }
}
