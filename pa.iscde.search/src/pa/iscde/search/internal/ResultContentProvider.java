package pa.iscde.search.internal;

import java.util.List;
import org.eclipse.jface.viewers.IStructuredContentProvider;

/**
 * Content provider for the ListViewer widget. Provides the list of results for a search
 * @author tiagomartinho.soares
 *
 */
public class ResultContentProvider implements IStructuredContentProvider  {

	@SuppressWarnings("unchecked")
	@Override
	public Object[] getElements(Object arg0) {
	    List<MatchResult> list = (List<MatchResult>) arg0;
		return list.toArray();
	    
	}

}
