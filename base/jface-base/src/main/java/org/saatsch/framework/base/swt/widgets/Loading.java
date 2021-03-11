package org.saatsch.framework.base.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.saatsch.framework.base.swt.DialogUtil;
import org.saatsch.framework.base.swt.Openable;

/**
 * Displays a loading animation. 
 * Usage: <code>loading = new Loading(shell); loading.withTask(r).open(); </code>
 * 
 * Can a.t.m. be exited with the escape key. this should maybe be forbidden.
 * 
 * @author saatsch
 * 
 */
public class Loading extends Dialog implements Openable<Void> {

  private Shell loading;

  private Display display;

  private Runnable task;

  private Thread animateThread;
  private Image image;
  private Color shellBackground;
  private final boolean useGIFBackground = false;
  private GC shellGC;

  
  
  /**
   * Create the dialog.
   * 
   * @param parent
   * @param question
   */
  public Loading(Shell parent) {
    super(parent);

    display = getParent().getDisplay();
  }

  public Loading withTask(Runnable r) {
    task = r;
    return this;
  }
  
  /**
   * Open the dialog.
   * 
   */
  public Void open() {
    createContents();
    loading.open();
    loading.layout();

    display.asyncExec(task);

    while (!loading.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }



    return null;

  }

  /**
   * Create contents of the dialog.
   */
  private void createContents() {
    loading = new Shell(getParent(), SWT.APPLICATION_MODAL);
    loading.setSize(200, 200);
    loading.setLayout(new FillLayout(SWT.HORIZONTAL));

    fillContents();

    DialogUtil.center(loading, getParent());

  }

  private void fillContents() {

    shellGC = new GC(loading);
    shellBackground = loading.getBackground();

    ImageLoader loader = new ImageLoader();
    try {
      ImageData[] imageDataArray =
          loader.load(getClass().getResourceAsStream("Rolling-1s-200px.gif"));
      if (imageDataArray.length > 1) {
        animateThread = new Thread("Animation") {
          public void run() {
            /* Create an off-screen image to draw on, and fill it with the shell background. */
            Image offScreenImage =
                new Image(display, loader.logicalScreenWidth, loader.logicalScreenHeight);
            GC offScreenImageGC = new GC(offScreenImage);
            offScreenImageGC.setBackground(shellBackground);
            offScreenImageGC.fillRectangle(0, 0, loader.logicalScreenWidth,
                loader.logicalScreenHeight);

            try {
              /* Create the first image and draw it on the off-screen image. */
              int imageDataIndex = 0;
              ImageData imageData = imageDataArray[imageDataIndex];
              if (image != null && !image.isDisposed())
                image.dispose();
              image = new Image(display, imageData);
              offScreenImageGC.drawImage(image, 0, 0, imageData.width, imageData.height,
                  imageData.x, imageData.y, imageData.width, imageData.height);

              /*
               * Now loop through the images, creating and drawing each one on the off-screen image
               * before drawing it on the shell.
               */
              int repeatCount = loader.repeatCount;
              while (loader.repeatCount == 0 || repeatCount > 0) {
                switch (imageData.disposalMethod) {
                  case SWT.DM_FILL_BACKGROUND:
                    /* Fill with the background color before drawing. */
                    Color bgColor = null;
                    if (useGIFBackground && loader.backgroundPixel != -1) {
                      bgColor =
                          new Color(display, imageData.palette.getRGB(loader.backgroundPixel));
                    }
                    offScreenImageGC.setBackground(bgColor != null ? bgColor : shellBackground);
                    offScreenImageGC.fillRectangle(imageData.x, imageData.y, imageData.width,
                        imageData.height);
                    if (bgColor != null)
                      bgColor.dispose();
                    break;
                  case SWT.DM_FILL_PREVIOUS:
                    /* Restore the previous image before drawing. */
                    offScreenImageGC.drawImage(image, 0, 0, imageData.width, imageData.height,
                        imageData.x, imageData.y, imageData.width, imageData.height);
                    break;
                }

                imageDataIndex = (imageDataIndex + 1) % imageDataArray.length;
                imageData = imageDataArray[imageDataIndex];
                image.dispose();
                image = new Image(display, imageData);
                offScreenImageGC.drawImage(image, 0, 0, imageData.width, imageData.height,
                    imageData.x, imageData.y, imageData.width, imageData.height);

                /* Draw the off-screen image to the shell. */
                if (!offScreenImage.isDisposed() && !shellGC.isDisposed()) {
                  shellGC.drawImage(offScreenImage, 0, 0);                  
                }

                /*
                 * Sleep for the specified delay time (adding commonly-used slow-down fudge
                 * factors).
                 */
                try {
                  int ms = imageData.delayTime * 10;
                  if (ms < 20)
                    ms += 30;
                  if (ms < 30)
                    ms += 10;
                  Thread.sleep(ms);
                } catch (InterruptedException e) {
                }

                /*
                 * If we have just drawn the last image, decrement the repeat count and start again.
                 */
                if (imageDataIndex == imageDataArray.length - 1)
                  repeatCount--;
              }
            } catch (SWTException ex) {
              ex.printStackTrace();
              System.out.println("There was an error animating the GIF");
            } finally {
              if (offScreenImage != null && !offScreenImage.isDisposed())
                offScreenImage.dispose();
              if (offScreenImageGC != null && !offScreenImageGC.isDisposed())
                offScreenImageGC.dispose();
              if (image != null && !image.isDisposed())
                image.dispose();
            }
          }
        };
        animateThread.setDaemon(true);
        animateThread.start();
      }
    } catch (SWTException ex) {
      System.out.println("There was an error loading the GIF");
    }

  }

  public void close() {
    animateThread.interrupt();
    image.dispose();
    shellGC.dispose();
    loading.dispose();
  }

}

