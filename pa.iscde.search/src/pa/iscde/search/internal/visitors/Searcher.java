package pa.iscde.search.internal.visitors;

import java.io.File;
import java.util.ArrayList;

import pa.iscde.search.model.MatchResult;

/**
 * Utility interface meant for visitors to implement. Allows visitors to set information for their current visit such as
 * the file that's being parsed, what's being searched and results of said search
 * @author tiagomartinho.soares
 *
 */
public interface Searcher {
	
	/**
	 * Sets the file that's being inspected by the visitor
	 * @param file
	 */
	public void setFile(File file);
	
	/**
	 * Sets the text that is being searched
	 * @param input
	 */
	public void setSearchInput(String input);
	
	/**
	 * Gets the results found for a given search
	 * @return
	 */
	public ArrayList<MatchResult> getResults();
	
	/**
	 * Clears the results found
	 */
	public void clearResults();
	
	

}
