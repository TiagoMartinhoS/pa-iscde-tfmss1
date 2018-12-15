package pa.iscde.search.model;

import java.io.File;

/**
 * Model of a result displayed in the ListViewer
 * @author tiagomartinho.soares
 *
 */
public final class MatchResult {
	
	private File file;
	private String nodeName; //method, field, type, package name declarations
	private int lineNumber;
	private int startIndex;
	
	
	public MatchResult(File file, String nodeName, int lineNumber, int startIndex) {
		this.file = file;
		this.nodeName = nodeName;
		this.lineNumber = lineNumber;
		this.startIndex = startIndex;
	}


	public File getFile() {
		return file;
	}

	public String getNodeName() {
		return nodeName;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public int getStartIndex() {
		return startIndex;
	}
	
	@Override
	public String toString() {
		return file.getName() + ": " + nodeName + " on line " + lineNumber;
	}
	
	

	


	
	
	
	
	
	
	
	

}
