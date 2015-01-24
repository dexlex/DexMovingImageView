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

package it.dex.movingimageview.data;

import java.io.Serializable;

/**
 * DexMoveImageView created by Diego on 06/01/2015.
 */
public class SubSection implements Serializable {
    private String name;
    private SUBSECTION_TYPE subsectionType;

    public SubSection() {
    }

    public SubSection(String name, SUBSECTION_TYPE subsectionType) {
        setName(name);
        setSubsectionType(subsectionType);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SUBSECTION_TYPE getSubsectionType() {
        return subsectionType;
    }

    public void setSubsectionType(SUBSECTION_TYPE subsectionType) {
        this.subsectionType = subsectionType;
    }

    public enum SUBSECTION_TYPE {
        RECYCLER_VIEW_HORIZONTAL, RECYCLER_VIEW_VERTICAL, RECYCLER_VIEW_VERTICAL_PICASSO, RECYCLER_VIEW_VERTICAL_UIL,
        SCROLL_VIEW, LIST_VIEW, GRID_VIEW, HORIZONTAL_SCROLL_VIEW, CROSS_FADE_VIEW, CROSS_FADE_TESTER, MOVING_TESTER, NEWS_STAND
    }
}
