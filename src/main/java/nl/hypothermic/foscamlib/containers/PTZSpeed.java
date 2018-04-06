package nl.hypothermic.foscamlib.containers;

/**
 * Enum of PTZ speeds with their respective integers attached.
 */
public enum PTZSpeed {
	
	VERYSLOW(0),
	SLOW(1),
	NORMAL(2),
	FAST(3),
	VERYFAST(4);
	
	private final int value;
	
	private PTZSpeed(final int value) {
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
	public static PTZSpeed match(int x) {
        switch(x) {
        case 0:
            return VERYSLOW;
        case 1:
            return SLOW;
        case 2:
            return NORMAL;
        case 3:
            return FAST;
        case 4:
            return VERYFAST;
        }
        return null;
    }
}