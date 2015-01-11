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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.dex.movingimageview.R;
import it.dex.movingimageview.adapters.RecyclerAdapter;

public class RecyclerViewFragment extends Fragment {
    private static final String TYPE_ARG = "typeArg";
    private TYPE type;

    public static RecyclerViewFragment newInstance(TYPE type) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE_ARG, type.name());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = TYPE.valueOf(getArguments().getString(TYPE_ARG));
        RecyclerView list = (RecyclerView) view.findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = null;
        switch (type) {
            default:
            case VERTICAL:
                layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                break;
            case HORIZONTAL:
                layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
                break;
            case PICASSO:
                layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                break;
            case UIL:
                layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        switch (position % 3) {
                            default:
                            case 0:
                                return 2;
                            case 1:
                            case 2:
                                return 1;
                        }
                    }
                });
                break;
        }
        list.setLayoutManager(layoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(type, getActivity());
        list.setAdapter(adapter);
        for (int i = 0; i < 30; i++)
            switch (i % 5) {
                case 0:
                    adapter.add(R.drawable.material);
                    break;
                case 1:
                    adapter.add(R.drawable.material2);
                    break;
                case 2:
                    adapter.add(R.drawable.material3);
                    break;
                case 3:
                    adapter.add(R.drawable.material4);
                    break;
                case 4:
                    adapter.add(R.drawable.material5);
                    break;
            }
    }

    public enum TYPE {
        VERTICAL, HORIZONTAL, PICASSO, UIL
    }
}
