/*
 *
 * Copyright (C) 2014 Sparda-Datenverarbeitung eG
 * Freiligrathstrasse 32, 90482 Nuernberg, Germany
 *
 * This software is the confidential and proprietary information of
 * Sparda-Datenverarbeitung eG ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sparda-Datenverarbeitung eG.
 */
package de.osrg.base.simplescript.swt;

import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 */
public class Images
{

   private Images()
   {
      // utility class
   }

   public static Image getInfo()
   {
      return SWTResourceManager.getImage(Images.class, "/images/information.png");
   }

   public static Image getUp()
   {
      return SWTResourceManager.getImage(Images.class, "/images/arrow_up.png");
   }

   public static Image getDown()
   {
      return SWTResourceManager.getImage(Images.class, "/images/arrow_down.png");
   }

   public static Image getDelete()
   {
      return SWTResourceManager.getImage(Images.class, "/images/cross.png");
   }

   public static Image getRename()
   {
      return SWTResourceManager.getImage(Images.class, "/images/rename.png");
   }

   public static Image getEdit()
   {
      return SWTResourceManager.getImage(Images.class, "/images/pencil.png");
   }

}
