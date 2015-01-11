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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import it.dex.movingimageview.R;
import it.dex.movingimageview.fragments.RecyclerViewFragment;
import it.dex.movingimageviewlib.DexMovingImageView;

/**
 * Created by Diego on 10/12/2014.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Integer> items = new ArrayList<Integer>();
    private RecyclerViewFragment.TYPE type;
    private Context context;

    public RecyclerAdapter(RecyclerViewFragment.TYPE type, Context context) {
        this.type = type;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = null;
        switch (type) {
            case VERTICAL:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_recycler_vertical_item, viewGroup, false);
                break;
            case HORIZONTAL:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_recycler_horizontal_item, viewGroup, false);
                break;
            case PICASSO:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_recycler_remote_picasso_item, viewGroup, false);
                break;
            case UIL:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_recycler_remote_uil_item, viewGroup, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        switch (type) {
            case VERTICAL:
            case HORIZONTAL:
                viewHolder.imageView.setImageResource(items.get(i));
                break;
            case PICASSO:
                Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(viewHolder.imageView);
                break;
            case UIL:
                ImageLoader.getInstance().displayImage("http://i.imgur.com/DvpvklR.png", viewHolder.imageView);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(int res) {
        items.add(0, res);
        notifyItemInserted(0);
    }

    public void remove() {
        if (items.isEmpty()) return;
        items.remove(0);
        notifyItemRemoved(0);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        DexMovingImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (DexMovingImageView) itemView.findViewById(R.id.image);
        }
    }
}
