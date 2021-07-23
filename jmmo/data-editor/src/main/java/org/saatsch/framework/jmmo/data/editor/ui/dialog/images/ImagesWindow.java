package org.saatsch.framework.jmmo.data.editor.ui.dialog.images;

import java.io.InputStream;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.api.ImageService;
import org.saatsch.framework.jmmo.data.api.model.JmmoImage;
import org.saatsch.framework.base.swt.OpenableDialog;

public class ImagesWindow extends OpenableDialog {

  private Shell shell;
  private Object ret;
  private Label lblImage;
  private ListViewer listViewer;
  private Image image;
  private Label lblSize;

  public ImagesWindow(Shell parent) {
    super(parent, SWT.CLOSE | SWT.RESIZE | SWT.PRIMARY_MODAL);
  }

  public Object open() {
    createContents();
    shell.open();
    shell.layout();
    Display display = getParent().getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    return ret;

  }

  private void createContents() {
    shell = new Shell(getParent(), getStyle());
    shell.setSize(507, 322);
    shell.setText("Select Image");
    shell.setLayout(new GridLayout(2, false));

    SashForm sashForm = new SashForm(shell, SWT.NONE);
    sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

    Composite cmpList = new Composite(sashForm, SWT.NONE);
    cmpList.setLayout(new FillLayout(SWT.HORIZONTAL));

    listViewer = new ListViewer(cmpList, SWT.BORDER | SWT.V_SCROLL);
    listViewer.addSelectionChangedListener(new ISelectionChangedListener() {

      @Override
      public void selectionChanged(SelectionChangedEvent event) {
        JmmoImage firstElement = getSelectedImage();
        if (firstElement != null) {
          load(firstElement.getFilename());          
        }
      }
    });

    Composite cmpImage = new Composite(sashForm, SWT.NONE);
    cmpImage.setLayout(new GridLayout(1, false));

    lblImage = new Label(cmpImage, SWT.NONE | SWT.CENTER);
    lblImage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    
    lblSize = new Label(cmpImage, SWT.NONE);
    lblSize.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
    lblSize.setText("Size");
    sashForm.setWeights(new int[] {1, 1});

    Composite cmpButtons = new Composite(shell, SWT.NONE);
    cmpButtons.setLayout(new GridLayout(1, false));

    Button btnAdd = new Button(cmpButtons, SWT.NONE);
    btnAdd.addListener(SWT.Selection, (event) -> {
      showFileSelection();
    });
    btnAdd.setText("Add...");

    Button btnSelect = new Button(shell, SWT.NONE);
    btnSelect.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        ret = getSelectedImage();
        shell.dispose();
      }
    });
    btnSelect.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
    btnSelect.setText("Select");

    fillContents();

  }

  private void showFileSelection() {
    FileDialog fd = new FileDialog(shell, SWT.OPEN);
    fd.setText("Select Image");

    String[] filterExt = {"*.png", "*.jpg"};
    fd.setFilterExtensions(filterExt);
    String selected = fd.open();
    if (selected != null) {
      JmmoContext.getBean(ImageService.class).newImage(selected);
      updateImages();
    }
  }

  private void fillContents() {

    listViewer.setContentProvider(new ImagesContentProvider());
    updateImages();

  }

  private void updateImages() {
    listViewer.setInput(JmmoContext.getBean(ImageService.class).getImages());
  }

  public class ImagesContentProvider implements IStructuredContentProvider {

    @Override
    public Object[] getElements(Object inputElement) {
      return ((java.util.List<Object>) inputElement).toArray();
    }

  }

  private void load(String fileName) {
    if (image != null) {
      image.dispose();
    }

    InputStream stream = JmmoContext.getBean(ImageService.class).getImageAsStream(fileName);
    image = new Image(Display.getDefault(), stream);
    lblSize.setText("H:" + image.getBounds().height + "/W:" + image.getBounds().width);
    lblImage.setImage(image);

  }

  /**
   * @return the selected image or <code>null</code> if nothing is selected.
   */
  private JmmoImage getSelectedImage() {
    return (JmmoImage) listViewer.getStructuredSelection().getFirstElement();
  }

}
