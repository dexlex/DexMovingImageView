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
import android.widget.GridView;

import it.dex.movingimageview.R;
import it.dex.movingimageview.adapters.ItemAbsListAdapter;
import it.dex.movingimageview.data.SubSection;

public class GridViewFragment extends Fragment {
    private GridView mListView;
    private ItemAbsListAdapter mAdapter;

    public static GridViewFragment newInstance() {
        GridViewFragment fragment = new GridViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ItemAbsListAdapter(getActivity(), SubSection.SUBSECTION_TYPE.GRID_VIEW);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_grid, container, false);
        mListView = (GridView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        return view;
    }
}
