package pa.iscde.search.internal;

import java.io.File;

public class MatchResult {
	
	private File file;
	private String nodeName;
	private int lineNumber;
	private int startIndex;
	
	
	public MatchResult(File file, String nodeName, int lineNumber, int startIndex) {
		this.file = file;
		this.nodeName = nodeName;
		this.setLineNumber(lineNumber);
		this.setStartIndex(startIndex);
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	public String getNodeName() {
		return nodeName;
	}


	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}


	public int getLineNumber() {
		return lineNumber;
	}


	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}


	public int getStartIndex() {
		return startIndex;
	}


	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	
	@Override
	public String toString() {
		return file.getName() + ": " + nodeName + " on line " + lineNumber;
	}
	
	

	


	
	
	
	
	
	
	
	

}
