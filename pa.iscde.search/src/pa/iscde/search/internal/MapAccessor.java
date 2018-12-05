package pa.iscde.search.internal;

import java.util.ArrayList;
import java.util.HashMap;

public interface MapAccessor {
	
	/**
	 * Gets map structure (node-lines) from implementing classes
	 * @return HashMap
	 */
	public HashMap<String, ArrayList<Integer>> getMap();
	
	/**
	 * Clears map from implementing classes
	 */
	public void clearMap();
	
}
