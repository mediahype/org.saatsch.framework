package de.osrg.tools.apiclient.ui;

import java.util.Optional;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Tree;

import de.osrg.base.simplescript.swt.ScriptsUI;
import de.osrg.base.swt.SwtUtil;
import de.osrg.base.swt.widgets.logintercept.Appending;
import de.osrg.base.swt.widgets.logintercept.LogComposite;
import de.osrg.base.swt.widgets.logintercept.LogInterceptorFactory;
import de.osrg.tools.apiclient.ApiClientUserData;
import de.osrg.tools.apiclient.ui.expressions.ExpressionsUI;

/**
 * main composite
 * 
 * @author saatsch
 *
 */
public class ApiClientMainC extends Composite implements Appending {

  public ApiClientMainC(Composite parent, int style) {
    super(parent, style);
    createContents();
  }

  private static final String CALLS = "Calls";
  private static final String METHODS = "Methods";
  private LogComposite cmpLog;
  private Menu methodsContextMenu;
  private TreeViewer tvMethods;
  private TreeViewer tvCalls;
  private Tree treeCalls;

  
  /**
   * Create contents of the window.
   * @param parent 
   */
  protected void createContents() {

    Roots methodsModel = ApisContentProvider.createModel(Impls.getInstance().getTasks());

    SashForm sashMain = new SashForm(this, SWT.BORDER | SWT.VERTICAL);

    Composite composite = new Composite(sashMain, SWT.NONE);
    composite.setLayout(new FillLayout(SWT.HORIZONTAL));

    SashForm sashUpper = new SashForm(composite, SWT.NONE);

    Composite cmpLeft = new Composite(sashUpper, SWT.NONE);
    cmpLeft.setLayout(new FillLayout(SWT.HORIZONTAL));

    TabFolder tabFolder = new TabFolder(cmpLeft, SWT.NONE);

    TabItem tabMethods = new TabItem(tabFolder, SWT.NONE);
    tabMethods.setText(METHODS);

    Composite cmpMethods = new Composite(tabFolder, SWT.NONE);
    tabMethods.setControl(cmpMethods);
    cmpMethods.setLayout(new FillLayout(SWT.HORIZONTAL));

    tvMethods = new TreeViewer(cmpMethods, SWT.BORDER);
    Tree treeMethods = tvMethods.getTree();

    methodsContextMenu = new Menu(treeMethods);
    treeMethods.setMenu(methodsContextMenu);

    TabItem tbtmCalls = new TabItem(tabFolder, SWT.NONE);
    tbtmCalls.setText(CALLS);

    Composite cmpMethodCalls = new Composite(tabFolder, SWT.NONE);
    tbtmCalls.setControl(cmpMethodCalls);
    cmpMethodCalls.setLayout(new FillLayout(SWT.HORIZONTAL));

    tvCalls = new TreeViewer(cmpMethodCalls, SWT.BORDER);
    treeCalls = tvCalls.getTree();
    treeCalls.addSelectionListener(new CmdRenameCall(this));
    
    tvMethods.setContentProvider(new ApisContentProvider());
    tvMethods.setLabelProvider(LabelProviders.APIS_LP);
    tvMethods.setInput(methodsModel);

    
    tvCalls.setContentProvider(new CallsContentProvider());
    tvCalls.setLabelProvider(LabelProviders.CALLS_LP);
    updateCalls();

    Composite cmpMid = new Composite(sashUpper, SWT.NONE);
    cmpMid.setLayout(new FillLayout(SWT.HORIZONTAL));
    MethodWrapperUI cmpEditMethodCall = new MethodWrapperUI(cmpMid);
    MethodsTreeSelectionAdapter listener = new MethodsTreeSelectionAdapter(cmpEditMethodCall);
    treeMethods.addSelectionListener(listener);
    treeCalls.addSelectionListener(listener);
    
    Composite cmpRight = new ExpressionsUI(sashUpper);
    cmpRight.setLayout(new FillLayout(SWT.HORIZONTAL));
    sashUpper.setWeights(new int[] {210, 292, 259});

    Composite cmpLower = new Composite(sashMain, SWT.NONE);
    cmpLower.setLayout(new FillLayout(SWT.HORIZONTAL));

    SashForm sashLower = new SashForm(cmpLower, SWT.NONE);

    ScriptsUI cmpScript = new ScriptsUI(sashLower, ApiClientUserData.getUserData().getScriptsModel());

    cmpLog  = new LogComposite(sashLower);
    
    // logWidget = new LogComposite(sashLower);
    
    sashLower.setWeights(new int[] {1, 1});

    SwtUtil.addMenuItem(methodsContextMenu, "New Call", new CmdNewCall(this));
    sashMain.setWeights(new int[] {214, 254});
    
    
    LogInterceptorFactory.initLogging(this);
  }
  
  @Override
  public void append(String toAppend) {
    cmpLog.append(toAppend);

  }
  /**
   * updates the treeViewer that presents the method calls.
   */
  public void updateCalls() {
    tvCalls.setInput(ApiClientUserData.getUserData().getMethodCalls());
    
  }

  public Tree getTreeCalls() {
    return treeCalls;
  }
  
  public Optional<AbstractMethod> getCurrentlySelectedMethod() {
    IStructuredSelection selection = (IStructuredSelection) tvMethods.getSelection();
    AbstractMethod firstElement = (AbstractMethod) selection.getFirstElement();
    return Optional.ofNullable(firstElement);
  }
  
  
}
