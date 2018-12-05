package pa.iscde.search.visitors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import pa.iscde.search.internal.MapAccessor;

public class MethodVisitor extends ASTVisitor implements MapAccessor {

	private HashMap<String, ArrayList<Integer>> methods = new HashMap<>();
	
	private static int sourceLine(ASTNode node) {
		return ((CompilationUnit) node.getRoot()).getLineNumber(node.getStartPosition());
	}

	// visits method declaration
	@Override
	public boolean visit(MethodDeclaration node) {
		String name = node.getName().toString();
		if (!methods.containsKey(name)) {
			ArrayList<Integer> sourceLines = new ArrayList<>();
			sourceLines.add(sourceLine(node));
			methods.put(name, sourceLines);
		} else {
			methods.get(name).add(sourceLine(node));
		}
		return true;
	}

	@Override
	public HashMap<String, ArrayList<Integer>> getMap() {
		return methods;
	}

	@Override
	public void clearMap() {
		methods.clear();
		
	}
	
	

	
	
}
