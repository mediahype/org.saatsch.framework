package org.saatsch.framework.jmmo.server;

import java.io.File;
import java.io.IOException;

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

public class Assets {

  
  private final String basedir;
  
  private Git git;


  public Assets(String basedir) {
    this.basedir = basedir;
  }
  
  public static void main(String[] args) throws InvalidRemoteException, TransportException, GitAPIException, IOException {
    
    
    // new Assets("e:/dev/ws_all/osrg.assets").checkout().pull();
    Assets assets = new Assets("e:/dev/ws_all/osrg.assets").open().pull();
    
   
    
  }
  
  
  public Assets checkout() throws InvalidRemoteException, TransportException, GitAPIException {

    File checkout = new File(basedir);
    checkout.mkdirs();
    
    git = Git.cloneRepository()
        //.setCredentialsProvider(new UsernamePasswordCredentialsProvider("user", "pass"))
        .setURI("http://116.203.180.123:8380/saatsch/osrg.assets.git")
        .setDirectory(checkout)
        .call();
    
    return this;
  }
  
  
  public Assets pull() throws WrongRepositoryStateException, InvalidConfigurationException, InvalidRemoteException, CanceledException, RefNotFoundException, RefNotAdvertisedException, NoHeadException, TransportException, GitAPIException {
    git.pull().call();
    return this;
  }

  
  public Assets open() throws IOException {
    git = Git.open(new File(basedir));
    return this;
  }
  
  public void getFile(String filename) {
    
  }
  
}
