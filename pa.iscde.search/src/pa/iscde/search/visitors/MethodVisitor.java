package pa.iscde.search.visitors;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import pa.iscde.search.internal.MatchResult;
import pa.iscde.search.internal.Searcher;

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
		//decision to add methodDeclaration here
		String name = node.getName().toString();
		if (name.equals(searchInput)) {
			matches.add(new MatchResult(file, name, sourceLine(node), node.getStartPosition()));
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
