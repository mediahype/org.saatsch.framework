package org.saatsch.framework.jmmo.basegame.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotAdvertisedException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.saatsch.framework.jmmo.cdi.container.JmmoContext;
import org.saatsch.framework.jmmo.data.api.FileService;
import org.saatsch.framework.jmmo.data.api.model.JmmoFile;

public class Assets {


  private final String basedir;

  private Git git;
 

  public Assets(String basedir) {
    this.basedir = basedir;
  }

  public Assets checkout() throws InvalidRemoteException, TransportException, GitAPIException {

    File checkout = new File(basedir);
    checkout.mkdirs();

    git = Git.cloneRepository()
        // .setCredentialsProvider(new UsernamePasswordCredentialsProvider("user", "pass"))
        .setURI("http://116.203.180.123:8380/saatsch/osrg.assets.git").setDirectory(checkout)
        .call();

    return this;
  }


  public Assets pull() throws WrongRepositoryStateException, InvalidConfigurationException,
      InvalidRemoteException, CanceledException, RefNotFoundException, RefNotAdvertisedException,
      NoHeadException, TransportException, GitAPIException {
    git.pull().call();
    return this;
  }


  public Assets open() throws IOException {
    git = Git.open(new File(basedir));
    return this;
  }

  public void getFile(String filename) {

  }

  public void getImage(JmmoFile image) {

  }


  public void export(JmmoFile file) throws IOException {

    FileService fileService = JmmoContext.getBean(FileService.class);
    InputStream initialStream = fileService.getFileAsStream(file);

    File targetFile = new File(basedir + "/files/" + file.getFilename());

    java.nio.file.Files.copy(initialStream, targetFile.toPath(),
        StandardCopyOption.REPLACE_EXISTING);


    initialStream.close();



  }



}
