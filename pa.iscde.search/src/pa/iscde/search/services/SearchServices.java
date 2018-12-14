package pa.iscde.search.services;

import pa.iscde.search.internal.ResultList;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;

/**
 * Services offered by the Search component.
 * @author tiagomartinho.soares
 *
 */
public interface SearchServices {
	
	/**
	 * Searches types in the current workspace for the given input and directory
	 * @param input (non-null) string that is being searched
	 * @return ResultList containing the results for the given input
	 */
	ResultList searchType(String input, PackageElement dir);
	
	/**
	 * Searches methods in the current workspace for the given input and directory
	 * @param input (non-null) string that is being searched
	 * @return ResultList containing the results for the given input
	 */
	
	ResultList searchMethod(String input, PackageElement dir);
	
	/**
	 * Searches fields in the current workspace for the given input and directory
	 * @param input (non-null) string that is being searched
	 * @return ResultList containing the results for the given input
	 */
	
	ResultList searchField(String input, PackageElement dir);
	
	/**
	 * Searches constructors in the current workspace for the given input and directory
	 * @param input (non-null) string that is being searched
	 * @return ResultList containing the results for the given input
	 */
	
	ResultList searchConstructor(String input, PackageElement dir);
	
	/**
	 * Searches packages in the current workspace for the given input and directory
	 * @param input (non-null) string that is being searched
	 * @return ResultList containing the results for the given input
	 */
	
	ResultList searchPackage(String input, PackageElement dir);
	
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
