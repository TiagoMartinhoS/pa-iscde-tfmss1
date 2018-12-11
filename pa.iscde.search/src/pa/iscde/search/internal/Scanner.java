package pa.iscde.search.internal;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.swt.widgets.Text;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

public class Scanner {

	/**
	 * Iterates and parses the dir with visitor and editor. Input is what we're searching.
	 * Output is where results are shown
	 * @param dir
	 * @param visitor
	 * @param editor
	 * @param input
	 * @param output
	 */
	public static void iterateAndWrite(final SourceElement e, final Searcher visitor, final JavaEditorServices editor) {
		if (e.isClass()) {
			visitor.setFile(e.getFile());
			//have to cast because visitor is currently a Searcher and not every Searcher is an ASTVisitor
			editor.parseFile(e.getFile(), (ASTVisitor) visitor); 
		} else {
			for (SourceElement c : ((PackageElement) e).getChildren()) {
				iterateAndWrite(c, visitor, editor);
			}
		}
	}
}
