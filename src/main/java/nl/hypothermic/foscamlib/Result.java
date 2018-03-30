package nl.hypothermic.foscamlib;

/** ------------------------ **\
 * > Result.java			< *
 * FoscamLib by hypothermic	  *
 * www.github.com/hypothermic *
\** ------------------------ **/

public enum Result {
	
	// ----- Foscam CGI results ----- //
	
	/** Success */
	SUCCESS(0),
	/** CGI request string format error */
	FORMATERR(-1),
	/** Username or password error */
	CREDSERR(-2),
	/** Access denied */
	ACCESSDENIED(-3),
	/** CGI execute fail */
	EXECFAIL(-4),
	/** Timeout */
	TIMEOUT(-5),
	/** Unknown error */
	UNKNOWNERR(-7),
	
	/** Reserved value */
	RESERVE1(-6),
	/** Reserved value */
	RESERVE2(-8),
	
	// ----- Non-foscam results ----- //
	
	/** Everything went fine */
	OK(10),
	/** Invalid address */
	INVALID_ADDRESS(30),
	/** Invalid response*/
	INVALID_RESPONSE(40);
	
	// ----- Methods ----- //
	
	private Result(int value) {
		this.value = value;
	}
	
	/** Result value */
	public final int value;
	
	/** Get the result value */
	public int getValue() {
		return value;
	}
}
