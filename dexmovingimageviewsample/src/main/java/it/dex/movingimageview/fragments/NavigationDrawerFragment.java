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


import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import it.dex.movingimageview.R;
import it.dex.movingimageview.adapters.NavigationDrawerAdapter;
import it.dex.movingimageview.data.AppConfiguration;
import it.dex.movingimageview.data.Section;
import it.dex.movingimageview.utils.DividerItemDecoration;

public class NavigationDrawerFragment extends Fragment implements View.OnClickListener {
    public static final int GOOGLE_PLUS = 0;
    public static final int TWITTER = 1;
    public static final int LINKED_IN = 2;
    public static final int GITHUB = 3;
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private NavigationDrawerCallbacks mCallbacks;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mDrawerListView;
    private View mFragmentContainerView;
    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    private NavigationDrawerAdapter navigationDrawerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);
        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }
        selectItem(mCurrentSelectedPosition);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_navigation_drawer, container, false);
        mDrawerListView = (RecyclerView) view.findViewById(R.id.list);
        navigationDrawerAdapter = new NavigationDrawerAdapter(AppConfiguration.get().getSections());
        navigationDrawerAdapter.setOnItemClickListener(new NavigationDrawerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectItem(position);
            }
        });
        mDrawerListView.setAdapter(navigationDrawerAdapter);
        mDrawerListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDrawerListView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        view.findViewById(R.id.google_plus).setOnClickListener(this);
        view.findViewById(R.id.twitter).setOnClickListener(this);
        view.findViewById(R.id.linkedin).setOnClickListener(this);
        view.findViewById(R.id.github).setOnClickListener(this);
        return view;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }
                getActivity().invalidateOptionsMenu();
            }
        };
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void selectItem(int position) {
        if (AppConfiguration.get().getSections().get(position).getSectionType() == Section.SECTION_TYPE.SUBSECTION) {
            mCurrentSelectedPosition = position;
            if (navigationDrawerAdapter != null) {
                navigationDrawerAdapter.setItemChecked(position);
            }
            if (mDrawerLayout != null) {
                mDrawerLayout.closeDrawer(mFragmentContainerView);
            }
            if (mCallbacks != null) {
                mCallbacks.onNavigationDrawerItemSelected(AppConfiguration.get().getSections().get(position));
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (isDrawerOpen()) {
                mDrawerLayout.closeDrawer(mFragmentContainerView);
            } else {
                mDrawerLayout.openDrawer(mFragmentContainerView);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void close() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    @Override
    public void onClick(View v) {
        int selectedSocial = 0;
        switch (v.getId()) {
            case R.id.google_plus:
                selectedSocial = GOOGLE_PLUS;
                break;
            case R.id.twitter:
                selectedSocial = TWITTER;
                break;
            case R.id.linkedin:
                selectedSocial = LINKED_IN;
                break;
            case R.id.github:
                selectedSocial = GITHUB;
                break;
        }
        mCallbacks.onSocialIconSelected(selectedSocial);
    }

    public static interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(Section section);

        void onSocialIconSelected(int selectedSocial);
    }
}
