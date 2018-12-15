package pa.iscde.search.internal;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

/**
 * Label provider for the ListViewer widget. Allows to set the presented text in the ListViewer for the results
 * @author tiagomartinho.soares
 *
 */
public class ResultLabelProvider implements ILabelProvider {

	/**
	 * No need to implement the following 3 methods
	 */
	
	@Override
	public void addListener(ILabelProviderListener arg0) {}
	
	@Override
	public void removeListener(ILabelProviderListener arg0) {}
	
	@Override
	public void dispose() {}
	

	/**
	 * Returns whether changing the specified property for the specified element affects the label
	 * 
	 */
	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		return false;
	}

	/**
	 * ListViewers don't support images
	 */
	@Override
	public Image getImage(Object arg0) {
		return null;
	}

	/**
	 * Text of the MatchResult on the ListViewer
	 */
	@Override
	public String getText(Object arg0) {
		return ((MatchResult) arg0).toString();
	}

	

}
		  
