package pa.iscde.search.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jdt.core.dom.ASTNode;

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
