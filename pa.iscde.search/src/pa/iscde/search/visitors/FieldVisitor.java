package pa.iscde.search.visitors;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import pa.iscde.search.internal.MatchResult;
import pa.iscde.search.internal.Searcher;

public class FieldVisitor extends ASTVisitor implements Searcher  {

	private HashMap<File, ArrayList<VariableDeclarationFragment>> fields = new HashMap<File, ArrayList<VariableDeclarationFragment>>();
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
		return null;
	}

	@Override
	public void clearResults() {
		// TODO Auto-generated method stub
		
	}




	

}
