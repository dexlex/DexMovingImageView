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

package it.dex.movingimageview;

import java.util.ArrayList;
import java.util.List;

/**
 * DexMoveImageView created by Diego on 06/01/2015.
 */
public class AppConfiguration {
    private static AppConfiguration appConfiguration;
    private List<Section> sections = new ArrayList<>();

    private AppConfiguration() {
        sections.add(new Section("DexMovingImageView", Section.SECTION_TYPE.HEADER));

        Section movingVerticalScrollers = new Section("Vertical Scroller", Section.SECTION_TYPE.SUBSECTION);
        List<SubSection> movingVerticalScrollersSubSections = new ArrayList<>();
        movingVerticalScrollersSubSections.add(new SubSection("RECYCLER VIEW", SubSection.SUBSECTION_TYPE.RECYCLER_VIEW_VERTICAL));
        movingVerticalScrollersSubSections.add(new SubSection("SCROLL VIEW", SubSection.SUBSECTION_TYPE.SCROLL_VIEW));
        movingVerticalScrollersSubSections.add(new SubSection("LIST VIEW", SubSection.SUBSECTION_TYPE.LIST_VIEW));
        movingVerticalScrollersSubSections.add(new SubSection("GRID VIEW", SubSection.SUBSECTION_TYPE.GRID_VIEW));
        movingVerticalScrollers.setSubsectionTypeList(movingVerticalScrollersSubSections);
        sections.add(movingVerticalScrollers);

        Section movingHorizontalScrollers = new Section("Horizontal Scroller", Section.SECTION_TYPE.SUBSECTION);
        List<SubSection> movingHorizontalScrollersSubSections = new ArrayList<>();
        movingHorizontalScrollersSubSections.add(new SubSection("RECYCLER VIEW", SubSection.SUBSECTION_TYPE.RECYCLER_VIEW_HORIZONTAL));
        movingHorizontalScrollersSubSections.add(new SubSection("HORIZONTAL SCROLL VIEW", SubSection.SUBSECTION_TYPE.HORIZONTAL_SCROLL_VIEW));
        movingHorizontalScrollers.setSubsectionTypeList(movingHorizontalScrollersSubSections);
        sections.add(movingHorizontalScrollers);

        Section movingRemoteScrollers = new Section("Horizontal Scroller", Section.SECTION_TYPE.SUBSECTION);
        List<SubSection> movingRemoteScrollersSubSections = new ArrayList<>();
        movingRemoteScrollersSubSections.add(new SubSection("PICASSO", SubSection.SUBSECTION_TYPE.RECYCLER_VIEW_VERTICAL_PICASSO));
        movingRemoteScrollersSubSections.add(new SubSection("UNIVERSAL IMAGE LOADER", SubSection.SUBSECTION_TYPE.RECYCLER_VIEW_VERTICAL_UIL));
        movingRemoteScrollers.setSubsectionTypeList(movingRemoteScrollersSubSections);
        sections.add(movingRemoteScrollers);

        sections.add(new Section("Tester", Section.SECTION_TYPE.SUBSECTION));

        sections.add(new Section("DexCrossFadeImageView", Section.SECTION_TYPE.HEADER));

        Section crossFadeSingleImage = new Section("Single Image", Section.SECTION_TYPE.SUBSECTION);
        List<SubSection> crossFadeSingleImageSubSections = new ArrayList<>();
        crossFadeSingleImageSubSections.add(new SubSection("SINGLE IMAGE", SubSection.SUBSECTION_TYPE.CROSS_FADE_VIEW));
        crossFadeSingleImage.setSubsectionTypeList(crossFadeSingleImageSubSections);
        sections.add(crossFadeSingleImage);

        Section crossFadeMultipleImage = new Section("Multiple Image", Section.SECTION_TYPE.SUBSECTION);
        List<SubSection> crossFadeMultipleImageSubSections = new ArrayList<>();
        crossFadeMultipleImageSubSections.add(new SubSection("MULTIPLE IMAGES", SubSection.SUBSECTION_TYPE.CROSS_FADE_TESTER));
        crossFadeMultipleImage.setSubsectionTypeList(crossFadeMultipleImageSubSections);
        sections.add(crossFadeMultipleImage);
    }

    public static AppConfiguration get() {
        if (appConfiguration == null)
            appConfiguration = new AppConfiguration();
        return appConfiguration;
    }

    public List<Section> getSections() {
        return sections;
    }
}
