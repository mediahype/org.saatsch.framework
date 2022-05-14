package org.saatsch.framework.jmmo.data.editor.fx.dialog.texts;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.util.Callback;
import org.saatsch.framework.base.jfxbase.control.*;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.cdi.container.Lazy;
import org.saatsch.framework.jmmo.data.api.DataConfig;
import org.saatsch.framework.jmmo.data.api.IntlStringService;
import org.saatsch.framework.jmmo.data.api.model.IntlString;

import java.util.List;
import java.util.Optional;

/**
 * Presents the selection of an internationalizable Text
 */
public class TextSelection extends VBox {

  private final TableView<IntlString> tblTexts = new TableView<>();

  private final Lazy<DataConfig> cfg = Lazy.of(() -> JmmoContext.getBean(DataConfig.class));
  private final Lazy<IntlStringService> service = Lazy.of(() -> JmmoContext.getBean(IntlStringService.class));

  /**
   * callback that gets the text for the current language
   */
  private final IntlStringToStringCallback getText =
      param -> new SimpleStringProperty(param.getValue().getForLanguage(cfg.get().getCurrentLanguage()));

  private final IntlStringToStringCallback getTranslations =
      param -> new SimpleStringProperty(param.getValue().getStrings().keySet().toString());


  public TextSelection(Optional<TextWindow> client) {

    GridPane input = new GridPane();

    new Label("Language:").withParent(input).withLayoutColRow(0, 0).withLayoutHalign(HPos.CENTER);
    ComboBox<String> cmbLanguage = new ComboBox<String>().withParent(input).withLayoutColRow(1, 0).withItems("de", "en");
    cmbLanguage.getSelectionModel().select(cfg.get().getCurrentLanguage());
    cmbLanguage.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      cfg.get().setCurrentLanguage(cmbLanguage.getSelectionModel().selectedItemProperty().getValue());
      refreshTable();
      client.ifPresent(TextWindow::languageChanged);
    });


    new Label("Search:").withParent(input).withLayoutColRow(0, 1).withLayoutHalign(HPos.CENTER);
    new TextField().withParent(input).withLayoutColRow(1, 1).withChangeListener(this::search);


    new TableColumn<IntlString, String>("Text").withCellValueFactory(getText).withTable(tblTexts);
    new TableColumn<IntlString, String>("ID").withTable(tblTexts).withPropertyValueFactory(IntlString.meta().coordinate().name());
    new TableColumn<IntlString, String>("Translations").withTable(tblTexts).withCellValueFactory(getTranslations);


    withChildren(input, tblTexts);

    tblTexts.withStretchVertical();
    tblTexts.withSelectionChangedListener((observable, oldValue, newValue) -> client.ifPresent(c -> c.setTextToEdit(newValue)));


    setStrings(loadAll());
  }

  private void search(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
    if (newValue.isEmpty()){
      setStrings(loadAll());
    }else {
      setStrings(filter(newValue));
    }
    refreshTable();
  }

  private List<IntlString> filter(String newValue) {
    return service.get().search(newValue);
  }

  private List<IntlString> loadAll() {
    return service.get().loadAll();
  }

  void refreshTable() {
    tblTexts.refresh();
  }

  private void setStrings(List<IntlString> intlStrings) {
    tblTexts.setItems(FXCollections.observableArrayList(intlStrings));
  }

  public Optional<IntlString> getSelected(){
     return Optional.ofNullable(tblTexts.getSelectionModel().selectedItemProperty().get());
  }

  /**
   * this is purely an abbreviation
   */
  private interface IntlStringToStringCallback extends Callback<TableColumn.CellDataFeatures<IntlString, String>, ObservableValue<String>>{}

}
