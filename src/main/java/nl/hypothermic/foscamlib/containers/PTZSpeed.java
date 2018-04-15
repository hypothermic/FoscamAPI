package nl.hypothermic.foscamlib.containers;

/******************************\
 * > PTZSpeed.java			< *
 * FoscamAPI by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

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
	 * @param x = int to get PTZSpeed instance from
	 * @return PTZSpeed instance or null if error
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