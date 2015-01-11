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
import java.util.List;

/**
 * DexMoveImageView created by Diego on 06/01/2015.
 */
public class Section implements Serializable {
    private String name;
    private boolean checked;
    private SECTION_TYPE sectionType;
    private List<SubSection> subsectionTypeList;

    public Section(String name, SECTION_TYPE sectionType) {
        setName(name);
        setSectionType(sectionType);
    }

    public Section() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SECTION_TYPE getSectionType() {
        return sectionType;
    }

    public void setSectionType(SECTION_TYPE sectionType) {
        this.sectionType = sectionType;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<SubSection> getSubsectionTypeList() {
        return subsectionTypeList;
    }

    public void setSubsectionTypeList(List<SubSection> subsectionTypeList) {
        this.subsectionTypeList = subsectionTypeList;
    }

    public enum SECTION_TYPE {
        HEADER, SUBSECTION
    }


}
