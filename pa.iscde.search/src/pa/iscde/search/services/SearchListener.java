package pa.iscde.search.services;

import java.util.List;

import pa.iscde.search.internal.MatchResult;

/**
 * Represents a listener for events in the Search component
 * @author tiagomartinho.soares
 *
 */
public interface SearchListener {
	
	/**
	 * Search complete event
	 * @param searchInput searched text
	 * @param resultList list of results for the given searchInput text
	 */
	void searchComplete(String searchInput, List<MatchResult> resultList);
	

}
