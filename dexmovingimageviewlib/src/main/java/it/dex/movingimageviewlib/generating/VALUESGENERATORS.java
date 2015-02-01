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

package it.dex.movingimageviewlib.generating;

/**
 * Helpful enumeration used to map a value to the corresponding ValuesGenerator
 * <p/>
 * VALUESGENERATORS created by Diego Grancini on 08/12/2014.
 */
public enum VALUESGENERATORS {
    /**
     * It maps 0 with Base value
     */
    BASE(0),
    /**
     * It maps 1 with Angled value
     */
    ANGLED(1),
    /**
     * It maps 2 with Zoomed value
     */
    ZOOMED(2);

    private int type;

    VALUESGENERATORS(int type) {
        this.type = type;
    }

    /**
     * Map the ValuesGenerator value with the type
     *
     * @param type corresponding to the enumeration value
     * @return the corresponding value
     * @throws IllegalArgumentException if type doesn't match any value
     */
    public static VALUESGENERATORS map(int type) throws IllegalArgumentException {
        switch (type) {
            case 0:
                return BASE;
            case 1:
                return ANGLED;
            case 2:
                return ZOOMED;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Get the mapping type of a VALUESGENERATOR Value
     *
     * @return type of the value
     */
    public int getType() {
        return type;
    }
}
