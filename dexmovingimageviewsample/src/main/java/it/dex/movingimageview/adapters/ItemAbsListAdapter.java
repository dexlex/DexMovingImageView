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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import it.dex.movingimageview.R;
import it.dex.movingimageview.data.SubSection;

/**
 * Created by Diego on 07/12/2014.
 */
public class ItemAbsListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private SubSection.SUBSECTION_TYPE type;

    public ItemAbsListAdapter(Context context, SubSection.SUBSECTION_TYPE type) {
        inflater = LayoutInflater.from(context);
        this.type = type;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            int layout = 0;
            switch (type) {
                case LIST_VIEW:
                    layout = R.layout.adapter_list_item;
                    break;
                case GRID_VIEW:
                    layout = R.layout.adapter_grid_item;
                    break;
            }
            convertView = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        int drawable = 0;
        switch (position % 5) {
            case 0:
                drawable = R.drawable.material;
                break;
            case 1:
                drawable = R.drawable.material2;
                break;
            case 2:
                drawable = R.drawable.material3;
                break;
            case 3:
                drawable = R.drawable.material4;
                break;
            case 4:
                drawable = R.drawable.material5;
                break;
        }
        viewHolder.image.setImageResource(drawable);
        return convertView;
    }

    private static class ViewHolder {
        ImageView image;
    }
}
