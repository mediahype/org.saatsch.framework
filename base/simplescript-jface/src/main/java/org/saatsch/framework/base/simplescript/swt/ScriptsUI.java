package org.saatsch.framework.base.simplescript.swt;

import static org.saatsch.framework.base.swt.SwtUtil.addMenuItem;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import org.saatsch.framework.base.simplescript.JfaceStep;
import org.saatsch.framework.base.simplescript.OpenableEditor;
import org.saatsch.framework.base.simplescript.SimpleStep;
import org.saatsch.framework.base.simplescript.Simplescript;
import org.saatsch.framework.base.simplescript.StepTypeRegistry;
import org.saatsch.framework.base.simplescript.StepperCallback;
import org.saatsch.framework.base.swt.MessageBoxUtil;

public class ScriptsUI extends Composite implements StepperCallback {

  private final Table tableScriptSteps;
  private final Menu scriptsContextMenu;
  private final Tree treeScripts;
  private final Menu scriptStepsContextMenu;
  private ScriptsModel model;

  public ScriptsUI(Composite parent, ScriptsModel model) {
    super(parent, SWT.NONE);
    this.model = model;
    setLayout(new FillLayout(SWT.HORIZONTAL));

    SashForm sashForm = new SashForm(this, SWT.NONE);

    Composite cmpScripts = new Composite(sashForm, SWT.NONE);
    cmpScripts.setLayout(new GridLayout(1, false));

    Label lblScripts = new Label(cmpScripts, SWT.NONE);
    lblScripts.setBounds(0, 0, 49, 13);
    lblScripts.setText("Scripts:");

    treeScripts = new Tree(cmpScripts, SWT.BORDER);
    getTreeScripts().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    treeScripts.addSelectionListener(new CmdRenameScript(this));
    treeScripts.addSelectionListener(new OnScriptSelectionChanged(this));

    Composite cmpScriptSteps = new Composite(sashForm, SWT.NONE);
    cmpScriptSteps.setLayout(new GridLayout(2, false));

    Label lblScriptsteps = new Label(cmpScriptSteps, SWT.NONE);
    lblScriptsteps.setText("Script Steps:");

    Composite cmpCommands = new Composite(cmpScriptSteps, SWT.NONE);
    cmpCommands.setLayout(new RowLayout(SWT.HORIZONTAL));
    GridData gd_cmpCommands = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
    gd_cmpCommands.heightHint = 29;
    cmpCommands.setLayoutData(gd_cmpCommands);

    Button btnDown = new Button(cmpCommands, SWT.NONE);
    btnDown.addListener(SWT.Selection, event -> moveStepDown());
    btnDown.setImage(Images.getDown());

    Button btnUp = new Button(cmpCommands, SWT.NONE);
    btnUp.addListener(SWT.Selection, event -> moveStepUp());
    btnUp.setImage(Images.getUp());
    
    Button btnDel = new Button(cmpCommands, SWT.NONE);
    btnDel.addListener(SWT.Selection, event -> deleteStep());
    btnDel.setImage(Images.getDelete());

    tableScriptSteps = new Table(cmpScriptSteps, SWT.BORDER | SWT.FULL_SELECTION);
    tableScriptSteps.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
    tableScriptSteps.setHeaderVisible(true);
    tableScriptSteps.setLinesVisible(true);
    
    TableColumn tableColumn = new TableColumn(tableScriptSteps, SWT.NONE);
    tableColumn.setWidth(27);
    tableColumn.setText("#");

    
    
    TableColumn tblclmnStep = new TableColumn(tableScriptSteps, SWT.NONE);
    tblclmnStep.setWidth(100);
    tblclmnStep.setText("Step");

    TableColumn tblclmnType = new TableColumn(tableScriptSteps, SWT.NONE);
    tblclmnType.setWidth(100);
    tblclmnType.setText("Type");

    scriptsContextMenu = new Menu(getTreeScripts());
    getTreeScripts().setMenu(scriptsContextMenu);

    scriptStepsContextMenu = new Menu(tableScriptSteps);
    tableScriptSteps.setMenu(scriptStepsContextMenu);
    
    TableColumn tblclmnInfo = new TableColumn(tableScriptSteps, SWT.NONE);
    tblclmnInfo.setWidth(301);
    tblclmnInfo.setText("Info");
    new Label(cmpScriptSteps, SWT.NONE);
    new Label(cmpScriptSteps, SWT.NONE);
    sashForm.setWeights(new int[] {137, 455});

    tableScriptSteps.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseDoubleClick(MouseEvent e) {
        TableItem ti = tableScriptSteps.getItem(tableScriptSteps.getSelectionIndex());
        JfaceStep selectedStep = (JfaceStep) ti.getData();
        editStep(selectedStep);
      }

    });
    
    postCreate();

  }



  private void postCreate() {

    // create scripts context menu
    addMenuItem(scriptsContextMenu, "New Script", new CmdNewScript(this, model));
    addMenuItem(scriptsContextMenu, "Execute Script", new CmdExecuteScript(this));

    
    
    StepTypeRegistry.getTypes().forEach(type -> {
      addMenuItem(scriptStepsContextMenu, "Add "+ type.getSimpleName() + " ... ", new CmdAdd(this, type));
    });
    
    
    // load scripts
    if (model.getScripts() != null) {
      redrawScripts(model.getScripts());
    }
  }

  private void moveStepDown() {
   getSelectedStep().ifPresent(step -> {
     Simplescript.moveStepDown(getSelectedScript().get(), getSelectedStep().get());
     redrawScriptSteps(getSelectedScript().get());
   });
  }

  private void moveStepUp() {
    getSelectedStep().ifPresent(step -> {
      Simplescript.moveStepUp(getSelectedScript().get(), getSelectedStep().get());
      redrawScriptSteps(getSelectedScript().get());
    });
  }

  private void deleteStep() {
    getSelectedStep().ifPresent(step -> {
      Simplescript.removeStepFromScript(getSelectedScript().get(), getSelectedStep().get());
      redrawScriptSteps(getSelectedScript().get());
    });
  }

  public Optional<SimpleStep> getSelectedStep() {
    if (tableScriptSteps.getSelection().length == 0) {
      return Optional.empty();
    }
    return Optional.of((SimpleStep) tableScriptSteps.getSelection()[0].getData());

  }

  public Optional<Simplescript> getSelectedScript() {
    if (treeScripts.getSelection().length == 0) {
      MessageBoxUtil.showErrorMessage("Please select a script!", getShell());
      return Optional.empty();
    }
    return Optional.of((Simplescript) treeScripts.getSelection()[0].getData());
  }


  public Tree getTreeScripts() {
    return treeScripts;
  }

  public void redrawScriptSteps(Simplescript script) {
    tableScriptSteps.removeAll();
    script.getSteps().forEach((step) -> {
      TableItem ti = new TableItem(tableScriptSteps, SWT.NONE);
      ti.setData(step);
      ti.setText(1, step.getName());
      ti.setText(2, step.getClass().getSimpleName());
      ti.setText(3, step.getMoreInfo());
    });
  }

  public void redrawScripts(List<Simplescript> scripts) {

    treeScripts.removeAll();

    scripts.forEach((script) -> {
      TreeItem item = new TreeItem(treeScripts, SWT.NONE);
      item.setText(script.getName());
      item.setData(script);
    });

  }
  
  private void editStep(JfaceStep step) {
    
    OpenableEditor editor = step.createEditDialog(getShell());
    editor.setToEdit(step);
    editor.open();
    
    getSelectedScript().ifPresent(script -> redrawScriptSteps(script)); 
    
  }

  public void markCurrentStep(Integer stepIndex) {
      
    Display.getDefault().asyncExec(() -> {

      Arrays.asList(tableScriptSteps.getItems()).forEach(item -> {
        item.setImage(0, null);
      });
        
      if (null == stepIndex) {
        return;
      }
        
      tableScriptSteps.getItems()[stepIndex].setImage(0, Images.getEdit());
      
    });
        
  }
  
}
