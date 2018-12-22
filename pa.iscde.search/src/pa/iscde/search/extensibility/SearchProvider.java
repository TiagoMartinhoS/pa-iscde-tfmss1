package pa.iscde.search.extensibility;

import java.util.List;

import pa.iscde.search.model.MatchResult;

/**
 * Interface meant to be used by extensions that want to integrate with the Search Component through the search extension point
 * @author tiagomartinho.soares
 *
 */
public interface SearchProvider {

	/**
	 * This method will be implemented by other contributors. 
	 * The search component uses that implementation get a list of results for the given search type and search input.
	 * @param type what search type is being used (comment, package, method, etc)
	 * @param input what is being searched
	 * @return list of results found in the search
	 */
	List<MatchResult> searchFor(String type, String input);

}
