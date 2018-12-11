package pa.iscde.search.visitors;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import pa.iscde.search.internal.MatchResult;
import pa.iscde.search.internal.Searcher;

public class TypeVisitor extends ASTVisitor implements Searcher {

	private HashMap<File, ArrayList<TypeDeclaration>> types = new HashMap<File, ArrayList<TypeDeclaration>>();
	private String searchInput = null;
	private File file = null;
	
	private static int sourceLine(ASTNode node) {
		return ((CompilationUnit) node.getRoot()).getLineNumber(node.getStartPosition());
	}
	
	// visits class/interface declaration
	@Override
	public boolean visit(TypeDeclaration node) {
		String name = node.getName().toString();
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearResults() {
		// TODO Auto-generated method stub
		
	}

	

	
	

	
			

}
