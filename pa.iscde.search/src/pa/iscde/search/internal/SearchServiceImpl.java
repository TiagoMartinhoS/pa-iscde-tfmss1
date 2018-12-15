package pa.iscde.search.internal;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.Assert;

import pa.iscde.search.model.MatchResult;
import pa.iscde.search.services.SearchListener;
import pa.iscde.search.services.SearchService;
import pa.iscde.search.visitors.FieldVisitor;
import pa.iscde.search.visitors.MethodVisitor;
import pa.iscde.search.visitors.PackageVisitor;
import pa.iscde.search.visitors.Searcher;
import pa.iscde.search.visitors.TypeVisitor;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;

public class SearchServiceImpl implements SearchService {

	private ArrayList<MatchResult> search(Searcher searcher, String input, PackageElement dir) {
		searcher.clearResults();
		searcher.setSearchInput(input);
		Scanner.iterateAndWrite(dir, searcher);
		return searcher.getResults();
	}
	
	@Override
	public List<MatchResult> searchType(String input, PackageElement dir) {
		TypeVisitor visitor = new TypeVisitor();
		List<MatchResult> resultList = search(visitor, input, dir);
		SearchActivator.getInstance().notifySearchComplete(input, resultList);
		return resultList;
	}

	@Override
	public List<MatchResult> searchMethod(String input, PackageElement dir) {
		MethodVisitor visitor = new MethodVisitor();
		List<MatchResult> resultList = search(visitor, input, dir);
		SearchActivator.getInstance().notifySearchComplete(input, resultList);
		return resultList;
	}

	@Override
	public List<MatchResult> searchField(String input, PackageElement dir) {
		FieldVisitor visitor = new FieldVisitor();
		List<MatchResult> resultList = search(visitor, input, dir);
		SearchActivator.getInstance().notifySearchComplete(input, resultList);
		return resultList;
	}
	
	@Override
	public List<MatchResult> searchPackage(String input, PackageElement dir) {
		PackageVisitor visitor = new PackageVisitor();
		List<MatchResult> resultList = search(visitor, input, dir);
		SearchActivator.getInstance().notifySearchComplete(input, resultList);
		return resultList;
	}
	
	

	@Override
	public void addListener(SearchListener listener) {
		Assert.isNotNull(listener, "argument cannot be null");
		SearchActivator.getInstance().addListener(listener);
		
	}

	@Override
	public void removeListener(SearchListener listener) {
		Assert.isNotNull(listener, "argument cannot be null");
		SearchActivator.getInstance().removeListener(listener);
		
	}

	
}
