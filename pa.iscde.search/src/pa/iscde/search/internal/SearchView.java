package pa.iscde.search.internal;

import java.util.Map;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import pa.iscde.search.visitors.FieldVisitor;
import pa.iscde.search.visitors.MethodVisitor;
import pa.iscde.search.visitors.TypeVisitor;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class SearchView implements PidescoView {
	
	//Visitors
	MethodVisitor methodVisitor = new MethodVisitor();
	FieldVisitor fieldVisitor = new FieldVisitor();
	TypeVisitor typeVisitor = new TypeVisitor();
	
	//Services
	ProjectBrowserServices projBrowser;
	JavaEditorServices editor;
	

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {

		BundleContext context = Activator.getContext();
		viewArea.setLayout(new GridLayout(1, false));
		
		//Service references
		ServiceReference<ProjectBrowserServices> serviceReference = context.getServiceReference(ProjectBrowserServices.class);
		projBrowser= context.getService(serviceReference);
		ServiceReference<JavaEditorServices> serviceReference2 = context.getServiceReference(JavaEditorServices.class);
		editor = context.getService(serviceReference2);
		
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
		Button typeRadio = new Button(group, SWT.RADIO);
		typeRadio.setText("Type");
		typeRadio.setSelection(true);
		Button methodRadio = new Button(group, SWT.RADIO);
		methodRadio.setText("Method");
		Button fieldRadio = new Button(group, SWT.RADIO);
		fieldRadio.setText("Field");
		GridData groupLayoutData = new GridData(150, 80);
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
		final ListViewer listViewer = new ListViewer(viewArea);
		listViewer.getControl().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		listViewer.setContentProvider(new ResultContentProvider());
		listViewer.setLabelProvider(new ResultLabelProvider());
		ResultList resultList = new ResultList();
		
		
		//ListViewer listener
		listViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent arg0) {
				int selection = listViewer.getList().getSelectionIndex();
				MatchResult selected = (MatchResult) listViewer.getElementAt(selection);
				editor.openFile(selected.getFile());
				/*
				 * Not working as expected
				 */
//				editor.selectText(selected.getFile(), selected.getStartIndex(), 0);
				
				
			}
		});
		
		//Search Listener
		searchButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PackageElement root = projBrowser.getRootPackage();

				if (methodRadio.getSelection()) {
					search(methodVisitor, searchBox.getText(), root, resultList);
				} else if (fieldRadio.getSelection()) {
					search(fieldVisitor, searchBox.getText(), root, resultList);
				} else if (typeRadio.getSelection()) {
					search(typeVisitor, searchBox.getText(), root, resultList);
				}
				listViewer.setInput(resultList);
			}
		});
	}
	
	private void search(Searcher searcher, String input, PackageElement dir, ResultList resultList) {
		searcher.clearResults();
		searcher.setSearchInput(input);
		Scanner.iterateAndWrite(dir, searcher, editor);
		resultList.setResultList(searcher.getResults());
	}
	
	
	
	
}
