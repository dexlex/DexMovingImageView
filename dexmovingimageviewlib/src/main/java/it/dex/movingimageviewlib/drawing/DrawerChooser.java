/*
 * Copyright 2014-2015 Diego Grancini
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.dex.movingimageviewlib.drawing;

import it.dex.movingimageviewlib.drawing.drawers.RotationDrawer;
import it.dex.movingimageviewlib.drawing.drawers.ScaleDrawer;
import it.dex.movingimageviewlib.drawing.drawers.TranslationDrawer;

/**
 * Utility class used to return the right Drawer instance based on the DRAWERS enumeration value
 * <p/>
 * DrawerChooser created by Diego Grancini on 08/12/2014.
 */
public class DrawerChooser {

    /**
     * Return an instance of Drawer using the DRAWERS enumeration value
     *
     * @param drawerType the type of drawer to create
     * @return the right instance of Evaluator
     * @throws IllegalArgumentException if evaluatorType doesn't match any EVALUATORS value
     */
    public static Drawer get(DRAWERS drawerType) throws IllegalArgumentException {
        switch (drawerType) {
            case SCALE:
                return new ScaleDrawer();
            case TRANSLATE:
                return new TranslationDrawer();
            case ROTATE:
                return new RotationDrawer();
            default:
                throw new IllegalArgumentException();
        }
    }
}
