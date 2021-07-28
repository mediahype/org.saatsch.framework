package org.saatsch.framework.jmmo.basegame.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.api.FileService;
import org.saatsch.framework.jmmo.data.api.model.JmmoFile;

public class Assets {


  private final String basedir;

  private Git git;
 

  public Assets(String basedir) {
    this.basedir = basedir;
  }

  public Assets init() throws GitAPIException, IOException {
    
    File x = new File(basedir);
    if (x.exists()) {
      return open().pull();
    }else {
      return checkout();
    }
    
 
  }
  
  public Assets checkout() throws GitAPIException {

    File checkout = new File(basedir);
    checkout.mkdirs();

    git = Git.cloneRepository()
        // .setCredentialsProvider(new UsernamePasswordCredentialsProvider("user", "pass"))
        .setURI("http://116.203.180.123:8380/saatsch/osrg.assets.git").setDirectory(checkout)
        .call();

    return this;
  }


  public Assets pull() throws GitAPIException {
    git.pull().call();
    return this;
  }


  public Assets open() throws IOException {
    git = Git.open(new File(basedir));
    return this;
  }

  public byte[] getFile(JmmoFile file) throws IOException {

    byte[] bytes = Files.readAllBytes(new File(dir() + file.getFilename()).toPath());
    
     return bytes;
    
  }


  public void export(JmmoFile file) throws IOException {

    FileService fileService = JmmoContext.getBean(FileService.class);
    InputStream initialStream = fileService.getFileAsStream(file);

    File targetFile = new File( dir() + file.getFilename());

    java.nio.file.Files.copy(initialStream, targetFile.toPath(),
        StandardCopyOption.REPLACE_EXISTING);


    initialStream.close();



  }

  private String dir() {
    return basedir + "/files/";
  }

}
