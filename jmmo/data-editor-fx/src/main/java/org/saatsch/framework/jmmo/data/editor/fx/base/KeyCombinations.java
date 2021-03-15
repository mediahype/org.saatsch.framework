package org.saatsch.framework.jmmo.data.editor.fx.base;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.saatsch.framework.jmmo.data.editor.fx.DataEditorFxApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyCombinations {

  private static final Logger LOG = LoggerFactory.getLogger(KeyCombinations.class);

  private final KeyCodeCombination copy;
  private final KeyCodeCombination paste;
  private final KeyCodeCombination toggle_inspect;
  private final KeyCodeCombination toggle_edit_mode;

  public KeyCombinations(DataEditorFxApp app,
      Scene scene) {

    copy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
    paste = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);

    toggle_inspect= new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN);
    toggle_edit_mode= new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);

    scene.getAccelerators().put(copy, ()->LOG.info(">>> copy... {}", copy));

    scene.getAccelerators().put(toggle_edit_mode, () -> app.getActiveTab().toggleEditMode());

  }



}
