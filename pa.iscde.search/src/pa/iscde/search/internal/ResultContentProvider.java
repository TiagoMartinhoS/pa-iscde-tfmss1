package pa.iscde.search.internal;

import java.util.List;
import org.eclipse.jface.viewers.IStructuredContentProvider;

public class ResultContentProvider implements IStructuredContentProvider  {

	@SuppressWarnings("unchecked")
	@Override
	public Object[] getElements(Object arg0) {
	    List<MatchResult> list = (List<MatchResult>) arg0;
		return list.toArray();
	    
	}

}
