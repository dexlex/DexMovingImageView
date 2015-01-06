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

package it.dex.movingimageview.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.dex.movingimageview.R;
import it.dex.movingimageview.adapters.CrossFadeImageAdapter;
import it.dex.movingimageviewlib.DexCrossFadeImageView;

public class CrossFadeFragment extends Fragment implements CrossFadeImageAdapter.OnImageClick {
    private DexCrossFadeImageView dexCrossFadeImageView;

    public static CrossFadeFragment newInstance() {
        CrossFadeFragment fragment = new CrossFadeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cross_fade, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dexCrossFadeImageView = (DexCrossFadeImageView) view.findViewById(R.id.image);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        int[] drawableResources = new int[]{R.drawable.material, R.drawable.material2, R.drawable.material3, R.drawable.material4, R.drawable.material5};
        recyclerView.setAdapter(new CrossFadeImageAdapter(drawableResources, this));
    }

    @Override
    public void onImageClick(int resource) {
        int[] drawableResources = new int[]{R.drawable.material5, R.drawable.material2, R.drawable.material3, R.drawable.material4, R.drawable.material};
        dexCrossFadeImageView.setImageResources(drawableResources);
        dexCrossFadeImageView.start();
    }
}
