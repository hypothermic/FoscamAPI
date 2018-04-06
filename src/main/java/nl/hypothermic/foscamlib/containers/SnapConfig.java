package nl.hypothermic.foscamlib.containers;

public class SnapConfig {
	
	public final PicQuality pq;
	public final SaveLocation sl;
	
	public SnapConfig(final PicQuality pq, final SaveLocation sl) {
		this.pq = pq;
		this.sl = sl;
	}
	
	/**
	 * Picture Quality enum
	 */
	public static enum PicQuality {
		LOW(0),
		NORMAL(1),
		HIGH(2);
		
		private final int value;
		
		private PicQuality(final int value) {
			this.value = value;
		}
		
		/**
		 * Returns int value of PicQuality instance
		 */
		public int getValue() {
			return this.value;
		}
		
		public static PicQuality fromInt(int value) {
		      for (PicQuality l : PicQuality.values()) {
		          if (l.value == value) return l;
		      }
		      throw new IllegalArgumentException("Not found.");
		}
	}
	
	/**
	 * Save Location enum
	 */
	public static enum SaveLocation {
		/** Save to SD card */
		SDCARD(0),
		/** [reserved] */
		RESERVED1(1),
		/** Upload to FTP */
		FTP(2);
		
		private final int value;
		
		private SaveLocation(final int value) {
			this.value = value;
		}
		
		/**
		 * Returns int value of SaveLocation instance
		 */
		public int getValue() {
			return this.value;
		}
		
		public static SaveLocation fromInt(int value) {
		      for (SaveLocation l : SaveLocation.values()) {
		          if (l.value == value) return l;
		      }
		      throw new IllegalArgumentException("Not found.");
		}
	}
}
