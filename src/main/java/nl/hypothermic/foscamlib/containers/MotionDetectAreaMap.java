package nl.hypothermic.foscamlib.containers;

/******************************\
 * > MotionDetectAreaMap.java *
 * FoscamLib by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class MotionDetectAreaMap {
	
	/**
	 * AreaMap is a 10x10 map of subregions
	 */
	
	public Integer[][] areamap;
	
	/**
	 * Create a new blank AreaMap
	 */
	public MotionDetectAreaMap() {
		areamap = new Integer[][] {
        	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        	{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
    	};
	}
	
	/**
	 * Set a user-defined AreaMap.
	 * If it is not 10x10 ints, it will be marked as invalid and it won't be set.
	 * @param areamap = user-defined AreaMap
	 */
	public MotionDetectAreaMap(Integer[][] areamap) {
		// Check for validity
		boolean invalid = false;
		if (areamap.length < 0 || areamap.length > 9) {
			invalid = true;
		}
		for (Integer[] row : areamap) {
			if (row.length < 0 || row.length > 9) {
				invalid = true;
			}
		}
		if (!invalid) {	
			this.areamap = areamap;
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
		if (x < 0 || x > 9 || y < 0 || y > 9) {
			return null;
		}
		areamap[x][y] = state ? 1 : 0;
		return true;
	}
}
