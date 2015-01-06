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

package it.dex.movingimageviewlib.evaluating;

/**
 * Created by Diego on 08/12/2014.
 */
public enum EVALUATORS {
    SCROLL(0), TIME(1), GYROSCOPE(2);

    private int type;

    EVALUATORS(int type) {
        this.type = type;
    }

    public static EVALUATORS map(int type) throws IllegalArgumentException{
        switch (type) {
            case 0:
                return SCROLL;
            case 1:
                return TIME;
            case 2:
                return GYROSCOPE;
            default:
                throw new IllegalArgumentException();
        }
    }

    public int getType() {
        return type;
    }
}
