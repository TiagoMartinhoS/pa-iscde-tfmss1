package pa.iscde.search.internal;

import java.io.File;

public class MatchResult {
	
	private File file;
	private String methodName;
	private int lineNumber;
	private int startIndex;
	
	
	public MatchResult(File file, String methodName, int lineNumber, int startIndex) {
		this.file = file;
		this.methodName = methodName;
		this.setLineNumber(lineNumber);
		this.setStartIndex(startIndex);
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	public String getMethodName() {
		return methodName;
	}


	public void setMethodName(String methodName) {
		this.methodName = methodName;
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
		return file.getName() + ": " + methodName + " on line " + lineNumber;
	}
	
	

	


	
	
	
	
	
	
	
	

}
