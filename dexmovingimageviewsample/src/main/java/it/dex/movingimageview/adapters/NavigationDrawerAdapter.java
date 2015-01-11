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

package it.dex.movingimageview.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import java.util.List;

import it.dex.movingimageview.R;
import it.dex.movingimageview.data.Section;

/**
 * DexMoveImageView created by Diego on 06/01/2015.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {
    private List<Section> sections;
    private OnItemClickListener onItemClickListener;
    private int checkedItem = 0;

    public NavigationDrawerAdapter(List<Section> sections) {
        this.sections = sections;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_navigation_drawer_header, parent, false);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_navigation_drawer_section, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (sections != null) {
            switch (sections.get(position).getSectionType()) {
                case HEADER:
                    return 0;
                case SUBSECTION:
                    return 1;
            }
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name.setText(sections.get(position).getName());
        holder.name.setChecked(checkedItem==position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (sections != null)
            return sections.size();
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setItemChecked(int position) {
        checkedItem = position;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CheckedTextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (CheckedTextView) itemView.findViewById(R.id.text);
        }
    }
}
