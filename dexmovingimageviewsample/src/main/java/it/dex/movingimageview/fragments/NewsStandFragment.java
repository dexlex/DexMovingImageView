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

package it.dex.movingimageview.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import it.dex.movingimageview.R;
import it.dex.movingimageviewlib.DexMovingImageView;
import it.dex.movingimageviewlib.evaluating.Evaluator;

/**
 * DexMoveImageView created by Diego on 22/01/2015.
 */
public class NewsStandFragment extends Fragment implements Evaluator.OnEventOccurred {
    private DexMovingImageView dexMovingImageView;
    private TypedArray images;

    public static NewsStandFragment newInstance() {
        NewsStandFragment newsStandFragment = new NewsStandFragment();
        return newsStandFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        images = getResources().obtainTypedArray(R.array.images);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_newsstand, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dexMovingImageView = (DexMovingImageView) view.findViewById(R.id.dex_moving_image_view);
        dexMovingImageView.setOnEventOccurred(this);
    }

    @Override
    public void onEventOccurred(View view, Evaluator evaluator, Evaluator.EVENT_STATUS eventStatus, int occurrenceCount) {
        switch (eventStatus) {
            case FIRST_QUARTER:
            case THIRD_QUARTER:
                dexMovingImageView.setFadingImageDrawable(images.getDrawable((int) (Math.random() * images.length())));
                break;
            case END:
            case MIDDLE:
                dexMovingImageView.setAngle((float) (new Random().nextInt((200 - 160) + 1) + 160));
                break;
        }
    }
}
