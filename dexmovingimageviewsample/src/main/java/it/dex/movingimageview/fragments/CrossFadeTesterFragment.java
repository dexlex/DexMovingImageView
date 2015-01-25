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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import it.dex.movingimageview.R;
import it.dex.movingimageviewlib.DexCrossFadeImageView;

public class CrossFadeTesterFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private DexCrossFadeImageView dexCrossFadeImageView, play;

    public static CrossFadeTesterFragment newInstance() {
        CrossFadeTesterFragment fragment = new CrossFadeTesterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cross_fade_tester, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dexCrossFadeImageView = (DexCrossFadeImageView) view.findViewById(R.id.image);
        play = (DexCrossFadeImageView) view.findViewById(R.id.play);
        play.setOnClickListener(this);
        SeekBar transitionSeekBar = (SeekBar) view.findViewById(R.id.transition_seek_bar);
        SeekBar stillImageSeekBar = (SeekBar) view.findViewById(R.id.still_image_seek_bar);
        transitionSeekBar.setOnSeekBarChangeListener(this);
        stillImageSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (dexCrossFadeImageView.isPlaying()) {
                    dexCrossFadeImageView.pause();
                    play.setFadingImageResource(android.R.drawable.ic_media_play);
                } else {
                    dexCrossFadeImageView.start();
                    play.setFadingImageResource(android.R.drawable.ic_media_pause);
                }
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.transition_seek_bar:
                dexCrossFadeImageView.setTransitionDurationMillis(progress * 2000 / 100);
                break;
            case R.id.still_image_seek_bar:
                dexCrossFadeImageView.setStillImageDurationMillis(progress * 2000 / 100);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
