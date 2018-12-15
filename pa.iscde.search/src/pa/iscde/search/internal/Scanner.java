package pa.iscde.search.internal;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import pa.iscde.search.visitors.Searcher;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;

/**
 * Utility class that allows to iterate through files and inspect them with a visitor
 * @author tiagomartinho.soares
 *
 */
public class Scanner {

	static JavaEditorServices editor;
	
	//since this is an utility class we want to prevent instantiation
	private Scanner() {}
	
	//called one time in order to get JavaEditorServices
	static {
		BundleContext context = SearchActivator.getContext();
		ServiceReference<JavaEditorServices> ref = context.getServiceReference(JavaEditorServices.class);
		editor = context.getService(ref);
	}
	
	/**
	 * Iterates through a SourceElement and its children and parses the files with a visitor
	 * @param e SourceElement that is being parsed
	 * @param visitor parses the files and writes the results
	 */
	public static void iterateAndWrite(final SourceElement e, final Searcher visitor) {
		if (e.isClass()) {
			visitor.setFile(e.getFile());
			//have to cast because visitor is currently a Searcher and not every Searcher is an ASTVisitor
			editor.parseFile(e.getFile(), (ASTVisitor) visitor); 
		} else {
			for (SourceElement c : ((PackageElement) e).getChildren()) {
				iterateAndWrite(c, visitor);
			}
		}
	}

}
