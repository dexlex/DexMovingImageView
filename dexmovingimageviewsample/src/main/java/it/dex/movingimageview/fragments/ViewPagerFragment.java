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
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.dex.movingimageview.R;
import it.dex.movingimageview.Section;

public class ViewPagerFragment extends Fragment {
    public static final String SECTION_ARG = "orientationArg";
    private Section section;

    public static ViewPagerFragment newInstance(Section section) {
        ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SECTION_ARG, section);
        viewPagerFragment.setArguments(bundle);
        return viewPagerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        section = (Section) getArguments().getSerializable(SECTION_ARG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getActivity(), section));
    }

    private static class MyPagerAdapter extends FragmentStatePagerAdapter {
        private Section section;

        public MyPagerAdapter(FragmentActivity fragmentActivity, Section section) {
            super(fragmentActivity.getSupportFragmentManager());
            this.section = section;
        }

        @Override
        public Fragment getItem(int position) {
            switch (section.getSubsectionTypeList().get(position).getSubsectionType()) {
                case CROSS_FADE_TESTER:
                    return null;
                case CROSS_FADE_VIEW:
                    return CrossFadeFragment.newInstance();
                case GRID_VIEW:
                    return GridViewFragment.newInstance();
                case HORIZONTAL_SCROLL_VIEW:
                    return HorizontalScrollViewFragment.newInstance();
                case LIST_VIEW:
                    return ListViewFragment.newInstance();
                case RECYCLER_VIEW_HORIZONTAL:
                    return RecyclerViewFragment.newInstance(RecyclerViewFragment.TYPE.HORIZONTAL);
                case RECYCLER_VIEW_VERTICAL:
                    return RecyclerViewFragment.newInstance(RecyclerViewFragment.TYPE.VERTICAL);
                case RECYCLER_VIEW_VERTICAL_PICASSO:
                    return RecyclerViewFragment.newInstance(RecyclerViewFragment.TYPE.PICASSO);
                case RECYCLER_VIEW_VERTICAL_UIL:
                    return RecyclerViewFragment.newInstance(RecyclerViewFragment.TYPE.UIL);
                case SCROLL_VIEW:
                    return ScrollViewFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            if (section != null && section.getSubsectionTypeList() != null)
                return section.getSubsectionTypeList().size();
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return section.getSubsectionTypeList().get(position).getName();
        }
    }
}
