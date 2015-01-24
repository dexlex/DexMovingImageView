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
 * Created by Diego on 08/12/2014.
 */
public enum VALUESGENERATORS {
    BASE(0), ANGLED(1), ZOOMED(2);

    private int type;

    VALUESGENERATORS(int type) {
        this.type = type;
    }

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

    public int getType() {
        return type;
    }
}
