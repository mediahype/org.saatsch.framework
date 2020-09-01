package de.osrg.base.util.textformat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.osrg.base.util.KeyValuePair;

public abstract class KeyValueSupport {

  private List<KeyValuePair> keysValues = new ArrayList<KeyValuePair>();

  private Map<String, Object> data = new HashMap<String, Object>();

  public List<KeyValuePair> getKeysValues() {
    return keysValues;
  }

  public Map<String, Object> getData() {
    return data;
  }

  public KeyValuePair getKeyValuePairByKey(String key) {
    for (KeyValuePair kvp : keysValues) {
      if (kvp.getKey().equals(key)) {
        return kvp;
      }
    }
    return null;
  }

}
