package pa.iscde.search.internal;


import java.util.Map;
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

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {

		BundleContext context = Activator.getContext();
		viewArea.setLayout(new GridLayout(1, false));
		
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
		Button methodRadio = new Button(group, SWT.RADIO);
		methodRadio.setText("Method");
		methodRadio.setSelection(true);
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
		
		
//		//Tree test
//		Composite treeArea = new Composite(viewArea, SWT.NONE);
//		treeArea.setLayout(new GridLayout(1, true));
//		GridData treeData = new GridData(410, 300);
//		Tree tree = new Tree(treeArea, SWT.NONE);
//		tree.setLayoutData(treeData);
		
		//TextArea
		Text textArea = new Text(viewArea, SWT.MULTI);
		textArea.setEditable(false);
		Color bg = new Color(Display.getCurrent(), 255, 255, 255);
		textArea.setBackground(bg);
		GridData textAreaData = new GridData(410, 300);
		textArea.setLayoutData(textAreaData);
		textArea.setText("");

		
		//Services
		ServiceReference<ProjectBrowserServices> serviceReference = context.getServiceReference(ProjectBrowserServices.class);
		ProjectBrowserServices projBrowser= context.getService(serviceReference);
		ServiceReference<JavaEditorServices> serviceReference2 = context.getServiceReference(JavaEditorServices.class);
		JavaEditorServices editor = context.getService(serviceReference2);
		
		//Visitors
		MethodVisitor methodVisitor = new MethodVisitor();
		FieldVisitor fieldVisitor = new FieldVisitor();
		TypeVisitor typeVisitor = new TypeVisitor();
		
		//Search Listener
		searchButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				textArea.setText("");
				PackageElement root = projBrowser.getRootPackage();
				if (methodRadio.getSelection()) {
					Scanner.iterateAndWrite(root, methodVisitor, editor, searchBox.getText(), textArea);
				} else if (fieldRadio.getSelection()) {
					Scanner.iterateAndWrite(root, fieldVisitor, editor, searchBox.getText(), textArea);
				} else if (typeRadio.getSelection()) {
					Scanner.iterateAndWrite(root, typeVisitor, editor, searchBox.getText(), textArea);
				}
			}
		});
	}
	
	
	
	
}
