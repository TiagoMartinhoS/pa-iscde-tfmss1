package pa.iscde.search.internal;

import org.eclipse.jface.viewers.IStructuredContentProvider;

public class ResultContentProvider implements IStructuredContentProvider  {

	@Override
	public Object[] getElements(Object arg0) {
	    return ((ResultList) arg0).getResultList().toArray();
	    
	}

}
