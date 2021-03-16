package org.saatsch.framework.jmmo.data.editor.temp;

import java.io.File;
import java.io.IOException;

import org.saatsch.framework.jmmo.data.editor.fx.DataEditorFxApp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TempApp extends Application {

public static void main(String[] args) throws IOException {
  FXMLLoader.load(DataEditorFxApp.class.getResource("Editor.fxml"));
  Application.launch(TempApp.class, "");
}

  private VBox root;

  @Override
  public void start(Stage primaryStage) throws Exception {


    root = new VBox();
    Scene scene = new Scene(root, 800, 600);
    primaryStage.setScene(scene);
    fillContents();
    primaryStage.show();
  }

  private void fillContents() {
    
    root.getChildren().add(buildFileSystemBrowser());
    
  }

  private TreeView buildFileSystemBrowser() {
    TreeItem<File> root = createNode(new File("/"));
    return new TreeView<File>(root);
  }

  
  private TreeItem<File> createNode(final File f) {
    return new TreeItem<File>(f) {
        // We cache whether the File is a leaf or not. A File is a leaf if
        // it is not a directory and does not have any files contained within
        // it. We cache this as isLeaf() is called often, and doing the 
        // actual check on File is expensive.
        private boolean isLeaf;

        // We do the children and leaf testing only once, and then set these
        // booleans to false so that we do not check again during this
        // run. A more complete implementation may need to handle more 
        // dynamic file system situations (such as where a folder has files
        // added after the TreeView is shown). Again, this is left as an
        // exercise for the reader.
        private boolean isFirstTimeChildren = true;
        private boolean isFirstTimeLeaf = true;
         
        @Override public ObservableList<TreeItem<File>> getChildren() {
            if (isFirstTimeChildren) {
                isFirstTimeChildren = false;

                // First getChildren() call, so we actually go off and 
                // determine the children of the File contained in this TreeItem.
                super.getChildren().setAll(buildChildren(this));
            }
            return super.getChildren();
        }

        @Override public boolean isLeaf() {
            if (isFirstTimeLeaf) {
                isFirstTimeLeaf = false;
                File f = (File) getValue();
                isLeaf = f.isFile();
            }

            return isLeaf;
        }

        private ObservableList<TreeItem<File>> buildChildren(TreeItem<File> TreeItem) {
            File f = TreeItem.getValue();
            if (f != null && f.isDirectory()) {
                File[] files = f.listFiles();
                if (files != null) {
                    ObservableList<TreeItem<File>> children = FXCollections.observableArrayList();

                    for (File childFile : files) {
                        children.add(createNode(childFile));
                    }

                    return children;
                }
            }

            return FXCollections.emptyObservableList();
        }
    };
}
  
}
