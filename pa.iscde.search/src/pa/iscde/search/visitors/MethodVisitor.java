package pa.iscde.search.visitors;

import java.io.File;
import java.util.ArrayList;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;

import pa.iscde.search.internal.MatchResult;

public class MethodVisitor extends ASTVisitor implements Searcher {

	private ArrayList<MatchResult> matches = new ArrayList<MatchResult>();
	private String searchInput = null;
	private File file = null;
	
	private static int sourceLine(ASTNode node) {
		return ((CompilationUnit) node.getRoot()).getLineNumber(node.getStartPosition());

	}

	// visits method declaration
	@Override
	public boolean visit(MethodDeclaration node) {
		SimpleName methodName = node.getName();
		String name = methodName.toString();
		if (name.toLowerCase().contains(searchInput.toLowerCase())){
			matches.add(new MatchResult(file, name, sourceLine(methodName), methodName.getStartPosition()));
		}
		
		return true;
	}
	
	@Override
	public void setFile(File file) {
		this.file = file;
	}
	
	@Override
	public void setSearchInput(String input) {
		this.searchInput = input;
	}

	@Override
	public ArrayList<MatchResult> getResults() {
		return matches;
	}

	@Override
	public void clearResults() {
		matches.clear();
		
	}
	







	
	

	
	
}
