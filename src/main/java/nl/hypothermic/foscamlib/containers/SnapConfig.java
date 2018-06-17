package nl.hypothermic.foscamlib.containers;

/******************************\
 * > SnapConfig.java		< *
 * FoscamAPI by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class SnapConfig {
	
	public PicQuality pq;
	public SaveLocation sl;
	
	public SnapConfig(PicQuality pq, SaveLocation sl) {
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
		 * @return Value of PicQuality instance
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
		 * @return Value of SaveLocation instance
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
		
		/**
		 * Set Result enum (see setRecordPath's setResult return in the user guide)
		 */
		public static enum SetResult {
			
			// Note that most of these javadoc comments are directly from the user guide, I don't understand them either.
			
			/** Success */
			SUCCESS(0),
			/** Sd card does not exist */
			NO_SDCARD(-1),
			/** Share direction is not set */
			NO_SHARE_DIRECTION(-2),
			/** Not enough space */
			NOT_ENOUGH_SPACE(-3),
			/** Parameter error */
			PARAMETER_ERROR(-4),
			/** Parameter recording */
			PARAMETER_RECORDING(-5);
			
			private final int value;
			
			private SetResult(final int value) {
				this.value = value;
			}
			
			/**
			 * Returns int value of SetResult instance
			 * @return Value of SetResult instance
			 */
			public int getValue() {
				return this.value;
			}
			
			public static SetResult fromInt(int value) {
			      for (SetResult l : SetResult.values()) {
			          if (l.value == value) return l;
			      }
			      throw new IllegalArgumentException("Not found.");
			}
		}
	}

	@Override public String toString() {
		return "SnapConfig [pq=" + this.pq + ", sl=" + this.sl + "]";
	}
}
