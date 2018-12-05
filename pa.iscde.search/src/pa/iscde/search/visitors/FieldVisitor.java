package pa.iscde.search.visitors;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import pa.iscde.search.internal.MapAccessor;

public class FieldVisitor extends ASTVisitor implements MapAccessor {

	private HashMap<String, ArrayList<Integer>> fields = new HashMap<>();
	
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
			if (!fields.containsKey(name)) {
				ArrayList<Integer> sourceLines = new ArrayList<>();
				sourceLines.add(sourceLine(node));
				fields.put(name, sourceLines);
			} else {
				fields.get(name).add(sourceLine(node));
			}
			
		}
		return false; // false to avoid child VariableDeclarationFragment to be processed again
	}
	
	@Override
	public HashMap<String, ArrayList<Integer>> getMap() {
		return fields;
	}

	@Override
	public void clearMap() {
		fields.clear();
		
	}

}
