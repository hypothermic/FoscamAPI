package nl.hypothermic.foscamlib.containers;

/******************************\
 * > ZoomSpeed.java			< *
 * FoscamLib by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

/**
 * Enum of PTZ speeds with their respective integers attached.
 */
public enum ZoomSpeed {
	
	SLOW(0),
	NORMAL(1),
	FAST(2);
	
	private final int value;
	
	private ZoomSpeed(final int value) {
		this.value = value;
	}
	
	/**
	 * Returns int value of PTZSpeed instance
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Get PTZSpeed instance from int
	 */
	public static ZoomSpeed match(int x) {
        switch(x) {
        case 0:
        	return SLOW;
        case 1:
        	return NORMAL;
        case 2:
        	return FAST;
        }
        return null;
    }
}