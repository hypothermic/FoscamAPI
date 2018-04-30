package nl.hypothermic.foscamlib.containers;

import nl.hypothermic.foscamlib.containers.PushConfig.PushServer;

/******************************\
 * > PushCommand.java       < *
 * FoscamAPI by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

/**
 * Enum of Push Commands with their respective integers attached.
 */
public enum PushCommand {
	
	// Javadoc comments are directly from the user guide
	
	/** Register App device */
	REGISTER_APP_DEVICE(1),
	/** Unregister App device */
	UNREGISTER_APP_DEVICE(2),
	/** Test device */
	TEST_DEVICE(3),
	/** Register tag */
	REGISTER_TAG(4),
	/** Push msg to single app device */
	PUSH_TO_APP_DEVICE(5),
	/** Push msg to tag */
	PUSH_TO_TAG(6);
	
	private final int value;
	
	private PushCommand(final int value) {
		this.value = value;
	}
	
	/**
	 * Returns int value of PushCommand instance
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Get PushCommand instance from int
	 * @param x int to get PushCommand instance from
	 * @return PushCommand instance or null if error
	 */
	public static PushCommand match(int x) {
        switch(x) {
        case 1:
            return REGISTER_APP_DEVICE;
        case 2:
            return UNREGISTER_APP_DEVICE;
        case 3:
            return TEST_DEVICE;
        case 4:
            return REGISTER_TAG;
        case 5:
            return PUSH_TO_APP_DEVICE;
        case 6:
            return PUSH_TO_TAG;
        }
        return null;
    }
	
	/**
	 * Get PushCommand instance from String
	 * @param value String to get PushCommand instance from
	 * @return PushCommand instance or null if error
	 */
	public static PushCommand match(String value) {
		if (value.contains("1")) return REGISTER_APP_DEVICE;
		if (value.contains("2")) return UNREGISTER_APP_DEVICE;
		if (value.contains("3")) return TEST_DEVICE;
		if (value.contains("4")) return REGISTER_TAG;
		if (value.contains("5")) return PUSH_TO_APP_DEVICE;
		if (value.contains("6")) return PUSH_TO_TAG;
		return null;
	}
}