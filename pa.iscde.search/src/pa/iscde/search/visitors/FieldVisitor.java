package pa.iscde.search.visitors;

import java.io.File;
import java.util.ArrayList;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import pa.iscde.search.internal.MatchResult;

public class FieldVisitor extends ASTVisitor implements Searcher  {

	private ArrayList<MatchResult> matches = new ArrayList<MatchResult>();
	private String searchInput = null;
	private File file = null;
	
	private static int sourceLine(ASTNode node) {
		return ((CompilationUnit) node.getRoot()).getLineNumber(node.getStartPosition());
	}

	// visits field declaration
	@Override
	public boolean visit(FieldDeclaration node) {
		// loop for several variables in the same declaration
		for(Object o : node.fragments()) {
			VariableDeclarationFragment var = (VariableDeclarationFragment) o;
			String name = var.getName().toString();
			if (name.toLowerCase().contains(searchInput.toLowerCase())) {
				matches.add(new MatchResult(file, name, sourceLine(node), var.getName().getStartPosition()));
			}
		}
		return false; // false to avoid child VariableDeclarationFragment to be processed again
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
