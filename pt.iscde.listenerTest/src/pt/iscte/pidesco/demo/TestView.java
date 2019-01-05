package pt.iscte.pidesco.demo;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pa.iscde.search.model.MatchResult;
import pa.iscde.search.services.SearchListener;
import pa.iscde.search.services.SearchService;
import pt.iscte.pidesco.extensibility.PidescoView;

public class TestView implements PidescoView {

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		
		BundleContext context = Activator.getContext();
		ServiceReference<SearchService> searchReference = context.getServiceReference(SearchService.class);
		SearchService search = context.getService(searchReference);
		
		Text textArea = new Text(viewArea, SWT.VERTICAL | SWT.V_SCROLL | SWT.H_SCROLL);
		textArea.setEditable(false);
		
		search.addListener(new SearchListener() {
			
			@Override
			public void searchComplete(String searchInput, List<MatchResult> resultList) {
				textArea.append("Search: " + searchInput + System.lineSeparator());
				textArea.append("List of results: " + resultList.toString() + System.lineSeparator());
				textArea.append(System.getProperty("line.separator"));
				
			}
		});
	}

}
