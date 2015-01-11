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

import it.dex.movingimageview.R;
import it.dex.movingimageviewlib.DexCrossFadeImageView;

/**
 * DexMoveImageView created by Diego on 29/12/2014.
 */
public class CrossFadeImageAdapter extends RecyclerView.Adapter<CrossFadeImageAdapter.ViewHolder> {
    private int[] drawableResources;
    private OnImageClick onImageClick;

    public CrossFadeImageAdapter(int[] drawableResources, OnImageClick onImageClick) {
        this.drawableResources = drawableResources;
        this.onImageClick = onImageClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recycler_cross_fade, null);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.imageView.setImageResource(drawableResources[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onImageClick != null)
                    onImageClick.onImageClick(drawableResources[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (drawableResources != null)
            return drawableResources.length;
        return 0;
    }

    public interface OnImageClick {
        public void onImageClick(int resource);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        DexCrossFadeImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (DexCrossFadeImageView) itemView.findViewById(R.id.image);
        }
    }
}




