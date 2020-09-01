package de.jmmo.data.editor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.DisplayRealm;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.joda.beans.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.data.api.DataConfig;
import de.jmmo.data.api.Pointer;
import de.jmmo.data.api.PointerUtil;
import de.jmmo.data.editor.ui.dialog.images.ImagesWindow;
import de.jmmo.data.editor.ui.dialog.textseditor.TextEditorWindow;
import de.jmmo.data.editor.ui.tabcontent.EditorTabContent;
import de.osrg.base.swt.image.IconFontSwt;
import de.osrg.base.swt.safeapploop.SafeAppLoop;
import jiconfont.icons.font_awesome.FontAwesome;

public class MainGui {
  private static final Logger LOG = LoggerFactory.getLogger(MainGui.class);
  private Shell shell;
  private TabFolder tabFolder;

  private List<TabItem> tabs = new ArrayList<>();
  private TabItem currentTab;
  private Label lblMem;

  private Thread updater;
  private CoolItem coolItem;

  /**
   * Open the window.
   * 
   */
  public void open() {
    Display display = Display.getDefault();
    createContents();

    installKeyboardShortcuts();

    Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
      @Override
      public void run() {
        shell.open();
        shell.layout();

        SafeAppLoop.run(shell, display);

      }
    });

    display.dispose();

  }


  /**
   * Create contents of the window.
   * 
   * @wbp.parser.entryPoint
   */
  protected void createContents() {
    shell = new Shell();
    shell.setSize(1000, 800);
    shell.setText(getAppTitle());
    shell.setLayout(new GridLayout(1, false));
    shell.setImage(new Image(Display.getDefault(), IconFontSwt.buildSwtIcon(FontAwesome.PENCIL, 16, Color.BLACK )));

    tabFolder = new TabFolder(shell, SWT.NONE);
    tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    tabFolder.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        currentTab = (TabItem) e.item;
      }
    });

    Menu menu = new Menu(shell, SWT.BAR);
    shell.setMenuBar(menu);

    MenuItem mntmView = new MenuItem(menu, SWT.CASCADE);
    mntmView.setText("View");

    Menu mnuView = new Menu(mntmView);
    mntmView.setMenu(mnuView);

    MenuItem mntmToggleEditMode = new MenuItem(mnuView, SWT.NONE);
    mntmToggleEditMode.setText("Toggle Edit Mode");
    mntmToggleEditMode.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        currentTabContent().toggleEditMode();
      }
    });

    MenuItem mntmToggleInspect = new MenuItem(mnuView, SWT.NONE);
    mntmToggleInspect.setText("Toggle Inspect");
    mntmToggleInspect.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        currentTabContent().toggleInspect();
      }
    });


    MenuItem mntmWindow = new MenuItem(menu, SWT.CASCADE);
    mntmWindow.setText("Window");

    Menu mnuWindow = new Menu(mntmWindow);
    mntmWindow.setMenu(mnuWindow);

    MenuItem mntmTextWindow = new MenuItem(mnuWindow, SWT.NONE);
    mntmTextWindow.setText("Text");
    
    MenuItem mntmImages = new MenuItem(mnuWindow, SWT.NONE);
    mntmImages.setText("Images");
    mntmImages.addListener(SWT.Selection, (event) -> new ImagesWindow(shell).open() );

    CoolBar coolBar = new CoolBar(shell, SWT.FLAT);
    coolBar.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));

    coolItem = new CoolItem(coolBar, SWT.NONE);

    lblMem = new Label(coolBar, SWT.NONE);
    coolItem.setControl(lblMem);
    lblMem.setText("Mem XXXXXXXXXXXXXXX");
    mntmTextWindow.addListener(SWT.Selection, (event) -> new TextEditorWindow(shell).open() );


    fillContents();



  }

  private void fillContents() {
    for (Class<?> c : JmmoContext.getBean(DataConfig.class).getBaseClasses()) {
      addTab(c);
    }

    if (tabs.iterator().hasNext()) {
      currentTab = tabs.iterator().next();
    }

    updater = new Thread(new MemUpdater(lblMem, shell, coolItem));
    updater.start();

    lblMem.pack();
    Point size = lblMem.getSize();
    coolItem.setSize(coolItem.computeSize(size.x, size.y));

  }

  /**
   * adds a tab to the editor. Sets the content of the new tab to a new instance of
   * {@link EditorTabContent}
   * 
   * @param oc
   */
  private void addTab(Class<?> oc) {

    TabItem tab = new TabItem(tabFolder, SWT.NONE);
    tab.setText(oc.getSimpleName());
    EditorTabContent composite = new EditorTabContent(tabFolder, SWT.NONE, oc);
    composite.fill();
    composite.addMenu(shell);
    tab.setControl(composite);

    tabs.add(tab);



  }

  private EditorTabContent currentTabContent() {
    return tabItemToContent(tabs.get(tabs.indexOf(currentTab)));
  }

  private String getAppTitle() {
    return JmmoContext.getBean(DataConfig.class).getAppName() + " Data Editor";
  }

  private EditorTabContent tabItemToContent(TabItem item) {
    return (EditorTabContent) item.getControl();
  }

  public Shell getShell() {
    return shell;
  }

  public void selectObject(Pointer pointer) {
    EditorTabContent switched = switchToTypeTab(pointer.getBaseClass());

    if (switched != null) {
      switched.select((Bean) PointerUtil.resolve(pointer));
    }

    
  }

  
  /**
   * switches to a tab, given the object class of the desired tab.
   * 
   * @param objectClass
   * @return the {@link EditorTabContent} to which the view was switched or <code>null</code> if no matching {@link EditorTabContent} was found.
   */
  public EditorTabContent switchToTypeTab(Class<? extends Bean> objectClass) {
    for (int i = 0; i< tabFolder.getItemCount();i++) {
      if (tabItemToContent(tabFolder.getItem(i)).getObjectClass().isAssignableFrom(objectClass) ) {
        tabFolder.setSelection(i);
        return tabItemToContent(tabFolder.getItem(i));
      } 
    }
    return null;
  }
  
  private void installKeyboardShortcuts() {
    Display display = Display.getDefault();
    display.addFilter(SWT.KeyDown, e -> {
      if (((e.stateMask & SWT.CTRL) == SWT.CTRL) && (e.keyCode == 'e')) {
        currentTabContent().toggleEditMode();
      } else if (((e.stateMask & SWT.CTRL) == SWT.CTRL) && (e.keyCode == 'i')) {
        currentTabContent().toggleInspect();
      }
    });
    
  }


}
