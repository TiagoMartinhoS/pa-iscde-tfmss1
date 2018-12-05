package pa.iscde.search.visitors;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import pa.iscde.search.internal.MapAccessor;

public class TypeVisitor extends ASTVisitor implements MapAccessor {

	private HashMap<String, ArrayList<Integer>> types = new HashMap<>();
	
	private static int sourceLine(ASTNode node) {
		return ((CompilationUnit) node.getRoot()).getLineNumber(node.getStartPosition());
	}
	
	// visits class/interface declaration
	@Override
	public boolean visit(TypeDeclaration node) {
		String name = node.getName().toString();
		if (!types.containsKey(name)) {
			ArrayList<Integer> sourceLines = new ArrayList<>();
			sourceLines.add(sourceLine(node));
			types.put(name, sourceLines);
		} else {
			types.get(name).add(sourceLine(node));
		}
		return true;
	}
			
	@Override
	public HashMap<String, ArrayList<Integer>> getMap() {
		return types;
	}

	@Override
	public void clearMap() {
		types.clear();
		
	}

}
