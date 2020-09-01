## 1. Dependency
Your Project needs a dependency to the jmmo-data-editor
```
<dependency>
   <groupId>de.jmmo</groupId>
   <artifactId>jmmo.data-editor</artifactId>
   <version>${jmmo.version}</version>
</dependency>
```

## 2. Config
In your IDE, under the dependencies of your project, locate `jmmo.data-editor` locate the class `de.jmmo.data.editor.tool.CreateEditorConfig` and execute it. A file named `jmmo-data-editor-config.xml` is created in the directory from where the execution was invoked. It's contents is:
```
<de.jmmo.data.editor.config.EditorConfig>
  <classMappings class="linked-hash-map">       (1)
    <entry>
      <string>Item</string>                     (2)
      <list>
        <string>subclass</string>               (3)
      </list>
    </entry>
  </classMappings>
</de.jmmo.data.editor.config.EditorConfig>
```




