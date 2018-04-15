package nl.hypothermic.foscamlib.core;

/******************************\
 * > LoginResult.java		< *
 * FoscamAPI by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public enum LoginResult {
	
	// ----- Foscam Login results ----- //
	// All javadoc descriptions are directly from the user guide, some are a bit confusing, idk either.
	
	/** Login success */
	SUCCESS(0),
	/** Parameter check error */
	PARAMERR(-1),
	/** Exceed max user */
	MAXUSER(-2),
	/** User not exist */
	NOSUCHUSER(-4),
	/** Password */
	PASSWORD(-5),
	/** Password error */
	PASSWORDERR(-6),
	
	/** Access deny */
	ACCESSDENIED(-7),
	/** Already login */
	ALREADYLOGGEDIN(-8);
	
	// ----- Methods ----- //
	
	private LoginResult(int value) {
		this.value = value;
	}
	
	/** Result value */
	public final int value;
	
	/** Get the result value */
	public int getValue() {
		return value;
	}
}