package nl.hypothermic.foscamlib.containers;

/******************************\
 * > WifiMode.java			< *
 * FoscamAPI by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

/**
 * Enum of Wi-Fi authenticate modes with their respective integers attached.
 */
public enum WifiMode {
	
	/** Station */
	STATION(0),
	/** SoftAP */
	SOFTAP(1);
	
	private final int value;
	
	private WifiMode(final int value) {
		this.value = value;
	}
	
	/**
	 * Returns int value of AuthMode instance
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Get AuthMode instance from int
	 */
	public static WifiMode match(int x) {
        switch(x) {
        case 0:
            return STATION;
        case 1:
            return SOFTAP;
        }
        return null;
    }
}