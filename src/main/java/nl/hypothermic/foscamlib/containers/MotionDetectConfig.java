package nl.hypothermic.foscamlib.containers;

public class MotionDetectConfig {
	
	public final int isEnable;
	public final Linkage linkage;
	public final int snapInterval;
	public final Sensitivity sens;
	public final int triggerInterval;
	public final MotionDetectScheduleMap mdsm;
	public final MotionDetectAreaMap mdam;
	
	public MotionDetectConfig(final int isEnable, final Linkage linkage, final int snapInterval, final Sensitivity sens, 
							  final int triggerInterval, final MotionDetectScheduleMap mdsm, final MotionDetectAreaMap mdam) {
		this.isEnable = isEnable;
		this.linkage = linkage;
		this.snapInterval = snapInterval;
		this.sens = sens;
		this.triggerInterval = triggerInterval;
		this.mdsm = mdsm;
		this.mdam = mdam;
	}
	
	/**
	 * Enum of motion alarm linkage modes.
	 * (bit3 | bit2 | bit1 | bit0)
	 */
	public static enum Linkage {
		RING("bit0"),
		SENDMAIL("bit1"),
		SNAPPICTURE("bit2"),
		RECORD("bit3");
		
		private final String value;
		
		private Linkage(final String value) {
			this.value = value;
		}
		
		/**
		 * Returns bit value of FTPMode instance
		 */
		public String getValue() {
			return this.value;
		}
	}
	
	/**
	 * Enum of motion alarm sensitivity
	 */
	public static enum Sensitivity {
		// This order doesn't make sense. Blame Foscam.
		LOW(0),
		NORMAL(1),
		HIGH(2),
		LOWER(3),
		LOWEST(4);
		
		private final int value;
		
		private Sensitivity(final int value) {
			this.value = value;
		}
		
		/**
		 * Returns sensitivity attached to instance
		 */
		public int getValue() {
			return this.value;
		}
	}
}
