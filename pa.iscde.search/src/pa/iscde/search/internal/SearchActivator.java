package pa.iscde.search.internal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.core.runtime.Assert;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pa.iscde.search.services.SearchListener;
import pa.iscde.search.services.SearchService;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;


public class SearchActivator implements BundleActivator {

	private static SearchActivator instance;
	private static BundleContext context;
	private Set<SearchListener> listeners;
	private SearchService search;
	private JavaEditorServices editor;
	private ProjectBrowserServices browser;

	public SearchActivator() {
		listeners = new HashSet<SearchListener>();
	}
	
	public static SearchActivator getInstance() {
		return instance;
	}
	
	public void addListener(SearchListener l) {
		Assert.isNotNull(l);
		listeners.add(l);
	}
	
	public void removeListener(SearchListener l) {
		Assert.isNotNull(l);
		listeners.remove(l);
	}
	
	void notifySearchComplete(String searchInput, List<MatchResult> resultList) {
		for(SearchListener l : listeners) {
			l.searchComplete(searchInput, resultList);
		}
	}
	
	public static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		SearchActivator.context = bundleContext;
		ServiceReference<ProjectBrowserServices> browserReference = context.getServiceReference(ProjectBrowserServices.class);
		ServiceReference<JavaEditorServices> editorReference = context.getServiceReference(JavaEditorServices.class);
		browser = context.getService(browserReference);
		editor = context.getService(editorReference);
		search = new SearchServiceImpl();
		instance = this;
	}
	
	public SearchService getService() {
		return search;
	}
	
	public JavaEditorServices getEditor() {
		return editor;
	}
	
	public ProjectBrowserServices getBrowser() {
		return browser;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		SearchActivator.context = null;
	}
	

}
