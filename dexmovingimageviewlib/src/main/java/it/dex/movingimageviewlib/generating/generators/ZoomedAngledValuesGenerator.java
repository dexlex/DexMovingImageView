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

import it.dex.movingimageviewlib.parameters.Parameters;

/**
 * The ZoomedAngled ValuesGenerator implementation: it generates:
 * <p/>
 * - X value: it's calculated sinusoidally considering the image can be positioned from -viewWidth to deviceWidth + viewWidth. Then the value is normalized by angle value
 * <p/>
 * - Y value: it's calculated sinusoidally considering the image can be positioned from -viewHeight to deviceHeight + viewHeight. Then the value is normalized by angle value
 * <p/>
 * - Zoom value: it's calculated sinusoidally from minZoom to maxZoom
 * <p/>
 * - Angle value: as the default angle
 * <p/>
 * ZoomedAngledValuesGenerator created by Diego Grancini on 13/12/2014.
 */
public class ZoomedAngledValuesGenerator extends AngledValuesGenerator {

    public ZoomedAngledValuesGenerator(Parameters parameters) {
        super(parameters);
    }

    @Override
    public float getZoom(float zoom, float defaultZoom) {
        float minZoom = getParameters().getMinZoom();
        float maxZoom = getParameters().getMaxZoom();
        float range = maxZoom - minZoom;
        float t = (float) (Math.abs(Math.sin(Math.toRadians(zoom))) * range + minZoom);
        return t;
    }
}
