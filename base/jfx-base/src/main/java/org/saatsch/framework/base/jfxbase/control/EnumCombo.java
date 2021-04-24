package org.saatsch.framework.base.jfxbase.control;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class EnumCombo extends ComboBox<String> implements ExtendedControl<EnumCombo> {

  public EnumCombo(Class<? extends Enum> e) {

    setEditable(false);

    List<String> collect = Arrays.asList(e.getEnumConstants()).stream().map(o -> o.toString())
        .collect(Collectors.toList());

    ObservableList<String> list = FXCollections.observableArrayList();
    list.addAll(collect);

    setItems(list);

  }

  
}
