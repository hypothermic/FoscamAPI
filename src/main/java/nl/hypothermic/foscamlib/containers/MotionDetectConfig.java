package nl.hypothermic.foscamlib.containers;

/******************************\
 * > MotionDetectConfig.java  *
 * FoscamLib by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class MotionDetectConfig {
	
	public int isEnable;
	public Linkage linkage;
	public int snapInterval;
	public Sensitivity sens;
	public int triggerInterval;
	public MotionDetectScheduleMap mdsm;
	public MotionDetectAreaMap mdam;
	
	public MotionDetectConfig(int isEnable, Linkage linkage, int snapInterval, Sensitivity sens, int triggerInterval, MotionDetectScheduleMap mdsm, MotionDetectAreaMap mdam) {
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
		 * @return bit value of FTPMode instance
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
		 * @return sensitivity attached to instance
		 */
		public int getValue() {
			return this.value;
		}
	}

	@Override public String toString() {
		return "MotionDetectConfig [isEnable=" + this.isEnable + ", linkage=" + this.linkage + ", snapInterval=" + this.snapInterval + ", sens=" + this.sens + ", triggerInterval=" + this.triggerInterval + ", mdsm=" + this.mdsm + ", mdam=" + this.mdam + "]";
	}
}
