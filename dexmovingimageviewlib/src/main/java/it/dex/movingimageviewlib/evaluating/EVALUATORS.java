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

package it.dex.movingimageviewlib.evaluating;

/**
 * Helpful enumeration used to map a value to the corresponding Evaluator
 * <p/>
 * EVALUATORS created by Diego Grancini on 08/12/2014.
 */
public enum EVALUATORS {
    /**
     * It maps 0 with Simple value
     */
    SIMPLE(0),
    /**
     * It maps 1 with Scroll value
     */
    SCROLL(1),
    /**
     * It maps 2 with Time value
     */
    TIME(2),
    /**
     * It maps 3 with Gyroscope value
     */
    GYROSCOPE(3);

    private int type;

    EVALUATORS(int type) {
        this.type = type;
    }

    /**
     * Map the Evaluator value with the type
     *
     * @param type corresponding to the enumeration value
     * @return the corresponding value
     * @throws IllegalArgumentException if type doesn't match any value
     */
    public static EVALUATORS map(int type) throws IllegalArgumentException {
        switch (type) {
            case 0:
                return SIMPLE;
            case 1:
                return SCROLL;
            case 2:
                return TIME;
            case 3:
                return GYROSCOPE;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Get the mapping type of a EVALUATOR Value
     *
     * @return type of the value
     */
    public int getType() {
        return type;
    }
}
