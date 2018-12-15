package pa.iscde.search.services;

import java.util.List;

import pa.iscde.search.internal.MatchResult;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;

/**
 * Services offered by the Search component.
 * @author tiagomartinho.soares
 *
 */
public interface SearchService {
	
	/**
	 * Searches type declarations in the current workspace for the given input and directory
	 * @param input (non-null) string that is being searched
	 * @param dir where the input is being searched
	 * @return list containing the results for the given input
	 */
	List<MatchResult> searchType(String input, PackageElement dir);
	
	/**
	 * Searches method declarations in the current workspace for the given input and directory
	 * @param input (non-null) string that is being searched
	 * @param dir where the input is being searched
	 * @return list containing the results for the given input
	 */
	
	List<MatchResult> searchMethod(String input, PackageElement dir);
	
	/**
	 * Searches field declarations in the current workspace for the given input and directory
	 * @param input (non-null) string that is being searched
	 * @param dir where the input is being searched
	 * @return list containing the results for the given input
	 */
	
	List<MatchResult> searchField(String input, PackageElement dir);
	
	/**
	 * Searches package declarations in the current workspace for the given input and directory
	 * @param input (non-null) string that is being searched
	 * @param dir where the input is being searched
	 * @return list containing the results for the given input
	 */
	
	List<MatchResult> searchPackage(String input, PackageElement dir);
	
	/**
	 * Adds a Search listener. If the listener already exists, does nothing.
	 * @param listener (non-null) reference to a listener.
	 */
	void addListener(SearchListener listener);

	/**
	 * Removes a Search listener. If the listener is not registered, does nothing.
	 * @param listener (non-null) reference to a listener.
	 */
	void removeListener(SearchListener listener);

}
