package org.saatsch.framework.base.jfxbase.control;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class EnumCombo extends ComboBox<String> implements ExtendedNode<EnumCombo> {

  private Class<? extends Enum> e;

  public EnumCombo(Class<? extends Enum> e) {

    this.e = e;
    
    setEditable(false);

    List<String> collect = enumConstantsStream().map(o -> o.toString())
        .collect(Collectors.toList());

    ObservableList<String> list = FXCollections.observableArrayList();
    list.addAll(collect);

    setItems(list);

  }


  
  private Stream<? extends Enum> enumConstantsStream() {
    return Arrays.asList(e.getEnumConstants()).stream();
  }
}
