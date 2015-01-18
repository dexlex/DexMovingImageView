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

package it.dex.movingimageview.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;

import it.dex.movingimageview.R;
import it.dex.movingimageview.data.Section;
import it.dex.movingimageview.fragments.NavigationDrawerFragment;
import it.dex.movingimageview.fragments.ViewPagerFragment;


public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private NavigationDrawerFragment navigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.left_drawer);
        addSystemBarMargin(navigationDrawerFragment.getView());
        navigationDrawerFragment.setUp(
                R.id.left_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        if (savedInstanceState == null) {
            navigationDrawerFragment.selectItem(1);
        }
    }

    private void addSystemBarMargin(View view) {
        DrawerLayout.LayoutParams lp = (DrawerLayout.LayoutParams) view.getLayoutParams();
        lp.setMargins(lp.leftMargin, lp.topMargin + getStatusBarHeight() + getActionBarHeight(), lp.rightMargin, lp.bottomMargin + getNavigationBarHeight());
        view.setLayoutParams(lp);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getNavigationBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
        if (resourceId > 0 && !hasMenuKey) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getActionBarHeight() {
        int mActionBarSize = 0;
        final TypedArray styledAttributes = getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        mActionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return mActionBarSize;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.menu_share);
        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://github.com/dexlex/DexMovingImageView");
        sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Moving ImageView on GitHub");
        sendIntent.setType("text/plain");
        mShareActionProvider.setShareIntent(sendIntent);
        return true;
    }

    @Override
    public void onNavigationDrawerItemSelected(Section section) {
        Fragment fragment = ViewPagerFragment.newInstance(section);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
    }

    @Override
    public void onSocialIconSelected(int selectedSocial) {
        int urlRes = 0;
        switch (selectedSocial) {
            case NavigationDrawerFragment.GOOGLE_PLUS:
                urlRes = R.string.google_plus;
                break;
            case NavigationDrawerFragment.TWITTER:
                urlRes = R.string.twitter;
                break;
            case NavigationDrawerFragment.LINKED_IN:
                urlRes = R.string.linked_in;
                break;
            case NavigationDrawerFragment.GITHUB:
                urlRes = R.string.github;
                break;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(getResources().getString(urlRes)));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (navigationDrawerFragment.isDrawerOpen())
            navigationDrawerFragment.close();
        else
            super.onBackPressed();
    }
}
