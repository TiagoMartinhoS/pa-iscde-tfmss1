package pa.iscde.search.internal.visitors;

import java.io.File;
import java.util.ArrayList;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import pa.iscde.search.model.MatchResult;

public class TypeVisitor extends ASTVisitor implements Searcher {

	private ArrayList<MatchResult> matches = new ArrayList<MatchResult>();
	private String searchInput = null;
	private File file = null;
	
	private static int sourceLine(ASTNode node) {
		return ((CompilationUnit) node.getRoot()).getLineNumber(node.getStartPosition());
	}
	
	// visits class/interface declaration
	@Override
	public boolean visit(TypeDeclaration node) {
		String name = node.getName().toString();
		if (name.toLowerCase().contains(searchInput.toLowerCase())) {
			matches.add(new MatchResult(file, name, sourceLine(node), node.getName().getStartPosition()));
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
