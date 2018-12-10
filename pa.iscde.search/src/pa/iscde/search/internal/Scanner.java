package pa.iscde.search.internal;

import java.util.ArrayList;
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
	public static void iterateAndWrite(final SourceElement e, final MapAccessor visitor, final JavaEditorServices editor, 
			final String input, final Text output) {
		
		if (e.isClass()) {
			editor.parseFile(e.getFile(), (ASTVisitor) visitor);
			if (visitor.getMap().containsKey(input)) {
				ArrayList<Integer> sourceLines = visitor.getMap().get(input);
				output.append(e.getName() + " -> " + input + " node on line(s) "
						+ sourceLines + System.lineSeparator());
				visitor.clearMap();
			}
		} else {
			for (SourceElement c : ((PackageElement) e).getChildren()) {
				iterateAndWrite(c, visitor, editor, input, output);
			}
		}
	}
}
