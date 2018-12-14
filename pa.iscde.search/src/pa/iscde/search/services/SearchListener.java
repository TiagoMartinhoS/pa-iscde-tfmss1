package pa.iscde.search.services;

public interface SearchListener {
	
	/**
	 * Search complete event
	 */
	void searchComplete();
	
	
	/**
	 * Listener adapter that for each event does nothing.
	 */
	
	public class Adapter implements SearchListener {

		@Override
		public void searchComplete() {
			
		}
		
	}

}
