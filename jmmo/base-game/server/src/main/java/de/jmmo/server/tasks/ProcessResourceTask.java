package de.jmmo.server.tasks;

import de.jmmo.data.api.Pointer;
import java.io.IOException;
import java.util.Optional;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jmmo.basegame.common.resources.ResolveImagePointerRequest;
import de.jmmo.basegame.common.resources.GiveResourceRequest;
import de.jmmo.basegame.common.resources.GiveStringsRequest;
import de.jmmo.basegame.common.resources.GiveStringsResponse;
import de.jmmo.basegame.common.resources.ImagePointerResponse;
import de.jmmo.cdi.container.JmmoContext;
import de.jmmo.clustering.SessionLocalTask;
import de.jmmo.data.api.ImageService;
import de.jmmo.data.api.PointerUtil;
import de.jmmo.data.api.model.IntlString;
import de.jmmo.data.api.model.JmmoImage;
import de.jmmo.data.mongo.MorphiaMongoDataSink;

/**
 * processes a resource request
 */
public class ProcessResourceTask extends SessionLocalTask {

  private static final Logger LOG = LoggerFactory.getLogger(ProcessResourceTask.class);

  private GiveResourceRequest request;

  public ProcessResourceTask(GiveResourceRequest request, IoSession session) {
    super(session);
    this.request = request;
  }

  @Override
  public void runTask() {

    LOG.info("ProcessResourceRequest");

    if (request instanceof ResolveImagePointerRequest) {
      Pointer<JmmoImage> pointer = ((ResolveImagePointerRequest) request).getPointer();
      Optional<JmmoImage> resolve = PointerUtil.resolveOptional(pointer);
      resolve.ifPresent(image -> {
        byte[] readImage = readImage(image);
        respond(new ImagePointerResponse(readImage, pointer) );
      });
    }
    
    if (request instanceof GiveStringsRequest) {
      giveStrings();
    }
    
    

  }

  /**
   * responds with all available strings. maybe
   */
  private void giveStrings() {
    GiveStringsRequest r = (GiveStringsRequest) request;
    
    MorphiaMongoDataSink data = JmmoContext.getBean(MorphiaMongoDataSink.class);
    GiveStringsResponse response = new GiveStringsResponse(data.getAll(IntlString.class));
    
    respond(response);
    
  }

  private byte[] readImage(JmmoImage image) {
    ImageService imageService = JmmoContext.getBean(ImageService.class);
    try {
      return imageService.getImageAsStream(image).readAllBytes();
    } catch (IOException e) {
      LOG.error("Error: ", e);
    }
    return null;
  }

}
