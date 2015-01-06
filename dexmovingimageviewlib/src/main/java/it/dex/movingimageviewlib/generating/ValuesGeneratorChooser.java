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

package it.dex.movingimageviewlib.generating;

import it.dex.movingimageviewlib.generating.generators.AngledValuesGenerator;
import it.dex.movingimageviewlib.generating.generators.BaseValuesGenerator;
import it.dex.movingimageviewlib.parameters.Parameters;

/**
 * Created by Diego on 08/12/2014.
 */
public class ValuesGeneratorChooser {

    public static ValuesGenerator get(VALUESGENERATORS mover, Parameters parameters) throws IllegalArgumentException {
        switch (mover) {
            case BASE:
                return new BaseValuesGenerator(parameters);
            case ANGLED:
                return new AngledValuesGenerator(parameters);
            default:
                throw new IllegalArgumentException();
        }
    }
}
