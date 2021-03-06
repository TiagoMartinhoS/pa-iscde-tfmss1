package pa.iscde.search.internal;

import java.util.ArrayList;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import java.util.List;
import java.util.Map;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import pa.iscde.search.extensibility.SearchProvider;
import pa.iscde.search.model.MatchResult;
import pa.iscde.search.services.SearchService;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class SearchView implements PidescoView {
	
	private ProjectBrowserServices browser;
	private JavaEditorServices editor;
	private SearchService service;
	private Button typeRadio;
	private Button methodRadio;
	private Button fieldRadio;
	private Button packageRadio;
	private ListViewer listViewer;
	
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {

		viewArea.setLayout(new GridLayout(1, false));
		
		//Services
		browser = SearchActivator.getInstance().getBrowser();
		editor = SearchActivator.getInstance().getEditor();
		service = SearchActivator.getInstance().getService();
	
		//Search label and box
		Composite searchGroup = new Composite(viewArea, SWT.NONE);
		GridLayout searchGroupLayout = new GridLayout(2, false);
		searchGroupLayout.marginTop = 20;
		searchGroupLayout.marginHeight = 20;
		searchGroup.setLayout(searchGroupLayout);
		Label descriptionLabel = new Label(searchGroup, SWT.NONE);
		descriptionLabel.setText("Search any string:");
		Text searchBox = new Text(searchGroup, SWT.SINGLE);
		GridData searchBoxData = new GridData(300, 25);
		searchBox.setLayoutData(searchBoxData);
		
		//Buttons
		Group group = new Group(viewArea, SWT.NONE);
		RowLayout groupLayout = new RowLayout(SWT.VERTICAL);
		groupLayout.marginTop = 5;
		group.setLayout(groupLayout);
		group.setText("Search for:");
		typeRadio = new Button(group, SWT.RADIO);
		typeRadio.setText("Type");
		typeRadio.setSelection(true);
		methodRadio = new Button(group, SWT.RADIO);
		methodRadio.setText("Method / Constructor");
		fieldRadio = new Button(group, SWT.RADIO);
		fieldRadio.setText("Field");
		packageRadio = new Button(group, SWT.RADIO);
		packageRadio.setText("Package");
		GridData groupLayoutData = new GridData(200, 100);
		group.setLayoutData(groupLayoutData);
		
		//Search Button
		Composite searchComposite = new Composite(viewArea, SWT.NONE);
		GridLayout searchLayout = new GridLayout();
		searchLayout.marginTop = 10;
		searchComposite.setLayout(searchLayout);
		GridData searchButtonData = new GridData(150, 30);
		Button searchButton = new Button(searchComposite, SWT.PUSH);
		searchButton.setText("Search");
		searchButton.setLayoutData(searchButtonData);
		
		//ListViewer
		listViewer = new ListViewer(viewArea);
		listViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		listViewer.setContentProvider(new ResultContentProvider());
		listViewer.setLabelProvider(new ResultLabelProvider());
	
		//ListViewer listener
		listViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent arg0) {
				int selection = listViewer.getList().getSelectionIndex();
				MatchResult selected = (MatchResult) listViewer.getElementAt(selection);
				editor.selectText(selected.getFile(), selected.getStartIndex(), selected.getNodeName().length());	
				
			}
		});
		
		//SearchButton Listener
		searchButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				search(searchBox.getText());
			}
		});
		
		//SearchBox EnterKey Listener
		searchBox.addKeyListener(new KeyAdapter() {		
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.character == SWT.CR) {
					search(searchBox.getText());
				}
			}
		});
		
		//Extension point
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = reg.getConfigurationElementsFor("pa.iscde.search.search");
		for(IConfigurationElement e : elements) {
			String name = e.getAttribute("name");
			groupLayoutData.heightHint += 25;
			Button newSearchButton = new Button(group, SWT.RADIO);
			newSearchButton.setText(name);
			try {
				SearchProvider searcher = (SearchProvider) e.createExecutableExtension("class");
				searchButton.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						if (newSearchButton.getSelection()) {
							List<MatchResult> resultList = searcher.searchFor(name, searchBox.getText());
							listViewer.setInput(resultList);
							SearchActivator.getInstance().notifySearchComplete(searchBox.getText(), resultList);
						}
					}
				});
			} catch (CoreException e1) {
				e1.printStackTrace();
			}
			
		}
	}
	
	/**
	 * Common search action for multiple listeners
	 * @param input
	 */
	private void search(String input) {
		PackageElement root = browser.getRootPackage();
		List<MatchResult> results = new ArrayList<>();
		if (methodRadio.getSelection()) {
			results = service.searchMethod(input, root);
		} else if (fieldRadio.getSelection()) {
			results = service.searchField(input, root);
		} else if (typeRadio.getSelection()) {
			results = service.searchType(input, root);
		} else if (packageRadio.getSelection()) {
			results = service.searchPackage(input, root);
		}
		SearchActivator.getInstance().notifySearchComplete(input, results);
		listViewer.setInput(results);
	}
	
	
}
