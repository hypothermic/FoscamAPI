package nl.hypothermic.foscamlib.containers;

import java.util.Arrays;

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
	 * @param x = x-coordinate of subregion in map
	 * @param y = y-coordinate of subregion in map
	 * @param state = State to set subregion to
	 * @return True if succeeded, null if error
	 */
	public Boolean setState(int x, int y, boolean state) {
		if (x < 0 || x > 47 || y < 0 || y > 6) {
			return null;
		}
		schedulemap[x][y] = state ? 1 : 0;
		return true;
	}

	@Override public String toString() {
		return "MotionDetectScheduleMap [schedulemap=" + Arrays.toString(this.schedulemap) + "]";
	}
}
