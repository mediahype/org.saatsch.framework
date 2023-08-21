package org.saatsch.framework.base.jfxbase.binding;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringExpression;
import javafx.beans.value.ObservableBooleanValue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexBinding {

  private RegexBinding() {
  }

  public static ObservableBooleanValue matches(StringExpression string, String regex){

    return new BooleanBinding() {
      private final Pattern pattern = Pattern.compile(regex);
      {bind(string);}
      @Override
      protected boolean computeValue() {
        Matcher matcher = pattern.matcher(string.getValue());
        return matcher.find();
      }
    };
  }

  public static ObservableBooleanValue matches(StringExpression string, StringExpression regex){

    return new BooleanBinding() {
      {bind(string, regex);}
      @Override
      protected boolean computeValue() {
        Pattern pattern = Pattern.compile(regex.getValue());
        Matcher matcher = pattern.matcher(string.getValue());
        return matcher.find();
      }
    };
  }

}
