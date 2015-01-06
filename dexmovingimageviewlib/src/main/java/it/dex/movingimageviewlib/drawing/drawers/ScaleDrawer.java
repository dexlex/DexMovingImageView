/*
 * Copyright 2014 Diego Grancini
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

package it.dex.movingimageviewlib.drawing.drawers;

import android.graphics.Canvas;

import it.dex.movingimageviewlib.drawing.Drawer;
import it.dex.movingimageviewlib.parameters.Parameters;

/**
 * DexMoveImageView created by Diego on 05/01/2015.
 */
public class ScaleDrawer implements Drawer {
    @Override
    public void onDraw(Canvas canvas, Parameters parameters) {
        float zoom = parameters.getZoom();
        int width = parameters.getWidth();
        int height = parameters.getHeight();
        canvas.scale(zoom, zoom, width / 2, height / 2);
    }
}
