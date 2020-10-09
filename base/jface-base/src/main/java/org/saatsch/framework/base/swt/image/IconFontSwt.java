package org.saatsch.framework.base.swt.image;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JLabel;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

import jiconfont.IconCode;
import jiconfont.icons.font_awesome.FontAwesome;
import jiconfont.swing.IconFontSwing;

/**
 * copied from the internet. Can probably be improved.
 * 
 * @author saatsch
 *
 */
public class IconFontSwt {

  static {
    IconFontSwing.register(FontAwesome.getIconFont());
  }


  /**
   * Entry to the API that loads Images from fonts. This API does not cache Images. Applications
   * must build a layer on top of this API and that layer must have a resource managing role.
   * 
   * @param iconCode
   * @param size the fontSize
   * @param color
   * @return
   */
  public static ImageData buildSwtIcon(IconCode iconCode, float size, Color color) {

    Font font = buildFont(iconCode, size);
    String text = Character.toString(iconCode.getUnicode());
    return convertToSWT(buildImage(text, font, color));

  }

  private static Font buildFont(IconCode iconCode, float size) {
    Font font = IconFontSwing.buildFont(iconCode.getFontFamily());
    return font.deriveFont(size);
  }


  private static BufferedImage buildImage(String text, Font font, Color color) {
    JLabel label = new JLabel(text);
    label.setForeground(color);
    label.setFont(font);
    Dimension dim = label.getPreferredSize();
    int width = dim.width + 1;
    int height = dim.height + 1;
    label.setSize(width, height);
    BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = bufImage.createGraphics();
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
        RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    label.print(g2d);
    g2d.dispose();
    return bufImage;
  }

  private static ImageData convertToSWT(BufferedImage bufferedImage) {
    if (bufferedImage.getColorModel() instanceof DirectColorModel) {

      DirectColorModel colorModel = (DirectColorModel) bufferedImage.getColorModel();
      PaletteData palette = new PaletteData(colorModel.getRedMask(), colorModel.getGreenMask(),
          colorModel.getBlueMask());
      ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(),
          colorModel.getPixelSize(), palette);
      for (int y = 0; y < data.height; y++) {
        for (int x = 0; x < data.width; x++) {
          int rgb = bufferedImage.getRGB(x, y);
          int pixel = palette.getPixel(new RGB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF));
          data.setPixel(x, y, pixel);
          if (colorModel.hasAlpha()) {
            data.setAlpha(x, y, (rgb >> 24) & 0xFF);
          }
        }
      }
      return data;
    } else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
      IndexColorModel colorModel = (IndexColorModel) bufferedImage.getColorModel();
      int size = colorModel.getMapSize();
      byte[] reds = new byte[size];
      byte[] greens = new byte[size];
      byte[] blues = new byte[size];
      colorModel.getReds(reds);
      colorModel.getGreens(greens);
      colorModel.getBlues(blues);
      RGB[] rgbs = new RGB[size];
      for (int i = 0; i < rgbs.length; i++) {
        rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF, blues[i] & 0xFF);
      }
      PaletteData palette = new PaletteData(rgbs);
      ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(),
          colorModel.getPixelSize(), palette);
      data.transparentPixel = colorModel.getTransparentPixel();
      WritableRaster raster = bufferedImage.getRaster();
      int[] pixelArray = new int[1];
      for (int y = 0; y < data.height; y++) {
        for (int x = 0; x < data.width; x++) {
          raster.getPixel(x, y, pixelArray);
          data.setPixel(x, y, pixelArray[0]);
        }
      }
      return data;
    } else if (bufferedImage.getColorModel() instanceof ComponentColorModel) {
      ComponentColorModel colorModel = (ComponentColorModel) bufferedImage.getColorModel();
      // ASSUMES: 3 BYTE BGR IMAGE TYPE
      PaletteData palette = new PaletteData(0x0000FF, 0x00FF00, 0xFF0000);
      ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(),
          colorModel.getPixelSize(), palette);
      // This is valid because we are using a 3-byte Data model with no transparent pixels
      data.transparentPixel = -1;
      WritableRaster raster = bufferedImage.getRaster();
      int[] pixelArray = new int[3];
      for (int y = 0; y < data.height; y++) {
        for (int x = 0; x < data.width; x++) {
          raster.getPixel(x, y, pixelArray);
          int pixel = palette.getPixel(new RGB(pixelArray[0], pixelArray[1], pixelArray[2]));
          data.setPixel(x, y, pixel);
        }
      }
      return data;
    }
    return null;
  }

}
