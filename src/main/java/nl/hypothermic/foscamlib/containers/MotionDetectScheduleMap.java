package nl.hypothermic.foscamlib.containers;

/******************************\
 * > MotionDetectScheduleMap  *
 * FoscamLib by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class MotionDetectScheduleMap {
	
	/**
	 * AreaMap is a 10x10 map of subregions
	 */
	
	public Integer[][] schedulemap;
	
	/**
	 * Create a new blank ScheduleMap
	 */
	public MotionDetectScheduleMap() {
		schedulemap = new Integer[][] {
			// Each row represents half an hour of a day (40 == 20:00-20:30)
        	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // Monday
        	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // Tuesday
        	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // Wednesday
        	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // Thursday
        	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // Friday
        	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // Saturday
        	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }  // Sunday
    	};
	}
	
	/**
	 * Set a user-defined ScheduleMap.
	 * If it is not 7x48 ints, it will be marked as invalid and it won't be set.
	 */
	public MotionDetectScheduleMap(Integer[][] schedulemap) {
		// Check for validity
		boolean invalid = false;
		if (schedulemap.length < 0 || schedulemap.length > 6) {
			invalid = true;
		}
		for (Integer[] row : schedulemap) {
			if (row.length < 0 || row.length > 47) {
				invalid = true;
			}
		}
		if (!invalid) {	
			this.schedulemap = schedulemap;
		}
	}
	
	/**
	 * Set state of subregion
	 */
	public Boolean setState(int x, int y, boolean state) {
		if (x < 0 || x > 47 || y < 0 || y > 6) {
			return null;
		}
		if (state) {
			schedulemap[x][y] = 1;
		} else {
			schedulemap[x][y] = 0;
		}
		return true;
	}
}
