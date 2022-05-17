package org.saatsch.framework.jmmo.data.editor.fx.base;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.saatsch.framework.jmmo.data.editor.fx.DataEditorFxApp;
import org.saatsch.framework.jmmo.data.editor.fx.dialog.ImagesWindow;
import org.saatsch.framework.jmmo.data.editor.fx.dialog.texts.TextWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyCombinations {

  private static final Logger LOG = LoggerFactory.getLogger(KeyCombinations.class);

  public KeyCombinations(DataEditorFxApp app,
      Scene scene) {

    KeyCodeCombination copy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
    KeyCodeCombination paste = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);

    KeyCodeCombination images_accel = new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN);
    KeyCodeCombination texts_accel = new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN);

    scene.getAccelerators().put(copy, () -> LOG.info(">>> copy... {} ... NYI", copy));
    scene.getAccelerators().put(paste, () -> LOG.info(">>> paste... {} ... NYI", paste));


    Menu view = new Menu("View");
    Menu window = new Menu("Window");

    MenuItem toggle_inspect_menu = new MenuItem("Toggle Inspect");
    view.getItems().add(toggle_inspect_menu);
    toggle_inspect_menu.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
    // TODO:
    toggle_inspect_menu.setOnAction(event -> app.getActiveTab().toggleInspect());

    MenuItem toggle_edit_mode_menu = new MenuItem("Toggle Edit Mode");
    view.getItems().add(toggle_edit_mode_menu);
    toggle_edit_mode_menu.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
    toggle_edit_mode_menu.setOnAction(event -> app.getActiveTab().toggleEditMode());


    MenuItem text_menu = new MenuItem("Text");
    window.getItems().add(text_menu);
    text_menu.setAccelerator(texts_accel);
    text_menu.setOnAction(event -> new TextWindow().showAndWait());

    MenuItem images_menu = new MenuItem("Images");
    window.getItems().add(images_menu);
    images_menu.setAccelerator(images_accel);
    images_menu.setOnAction(event ->  new ImagesWindow().showAndWait());

    app.getMenu().getMenus().add(view);
    app.getMenu().getMenus().add(window);

  }


}
