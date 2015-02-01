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

/**
 * Helpful enumeration used to map a value to the corresponding Drawer
 * <p/>
 * DRAWERS created by Diego Grancini on 08/12/2014.
 */
public enum DRAWERS {
    /**
     * It maps 0 with Scale value
     */
    SCALE(0, "Scale"),
    /**
     * It maps 1 with Translate value
     */
    TRANSLATE(1, "Translate"),
    /**
     * It maps 2 with Rotate value
     */
    ROTATE(2, "Rotate");

    private int type;
    private String defaultName;

    DRAWERS(int type, String defaultName) {
        this.type = type;
        this.defaultName = defaultName;
    }


    /**
     * Map the Drawer value with the type
     *
     * @param type corresponding to the enumeration value
     * @return the corresponding value
     * @throws IllegalArgumentException if type doesn't match any value
     */
    public static DRAWERS map(int type) throws IllegalArgumentException {
        switch (type) {
            case 0:
                return SCALE;
            case 1:
                return TRANSLATE;
            case 2:
                return ROTATE;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Get the mapping type of a DRAWER Value
     *
     * @return type of the value
     */
    public int getType() {
        return type;
    }

    /**
     * Get the default name
     *
     * @return default name
     */
    public String getDefaultName() {
        return defaultName;
    }
}
