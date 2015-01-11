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

package it.dex.movingimageviewlib.generating.generators;

import it.dex.movingimageviewlib.generating.ValuesGenerator;
import it.dex.movingimageviewlib.parameters.Parameters;

/**
 * DexMoveImageView created by Diego on 13/12/2014.
 */
public class BaseValuesGenerator extends ValuesGenerator {

    public BaseValuesGenerator(Parameters parameters) {
        super(parameters);
    }

    @Override
    public float getX(float x) {
        float width = getParameters().getWidth();
        float deviceWidth = getParameters().getDeviceWidth();
        float zoom = getParameters().getZoom();
        float delta = (deviceWidth - width) / 2 - x;
        float t = delta * (width / (deviceWidth + width));
        return t * (zoom - 1) / zoom;
    }

    @Override
    public float getY(float y) {
        float height = getParameters().getHeight();
        float deviceHeight = getParameters().getDeviceHeight();
        float zoom = getParameters().getZoom();
        float delta = (deviceHeight - height) / 2 - y;
        float t = delta * (height / (deviceHeight + height));
        return t * (zoom - 1) / zoom;
    }
}
