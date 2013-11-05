
package com.lewa.droidtest;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.lewa.droidtest.adb.SettingsTest;
import com.lewa.droidtest.app.AppFragment;
import com.lewa.droidtest.data.DataFragment;
import com.lewa.droidtest.lock.LockFragment;
import com.lewa.droidtest.mms.sms.SmsSenderFragment;
import com.lewa.droidtest.resources.ResourcesTest;
import com.lewa.droidtest.screen.ScreenTest;
import com.lewa.droidtest.statusbar.StatusBarTest;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {
    
    private HashMap<Class<?>, Fragment> mFragments = new HashMap<Class<?>, Fragment>();
    
    private static final Class<?>[] FRAGMENTS = {
            AppFragment.class,
            IndexFragment.class,
            ResourcesTest.class,
            DataFragment.class,
            LockFragment.class,
            SettingsTest.class,
            ScreenTest.class,
            SmsSenderFragment.class,
            StatusBarTest.class
    };
    private static final String TAG = "MainActivity";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        SearchView searchView = (SearchView) menu.findItem( R.id.action_search ).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo( getComponentName() ) );
        searchView.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                onSearch(newText);
                return true;
            }
            
        });
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout layout = new FrameLayout(this);
        layout.setId(android.R.id.content);
        setContentView(layout);
        initActionBar();
    }
    
    private void onSearch(String text) {
        
        Fragment currentFragment = mFragments.get(FRAGMENTS[getActionBar().getSelectedNavigationIndex()]);
        try {
            currentFragment.getClass().getMethod("onSearch", String.class).invoke(currentFragment, text);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        Log.e(TAG, "onNewIntent");
        super.onNewIntent(intent);
        onSearch(intent.getStringExtra("query"));
    }

    private void initActionBar() {
        ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        bar.setDisplayShowTitleEnabled(false);
        ArrayList<String> titles = new ArrayList<String>();
        for (Class<?> c : FRAGMENTS) {
            titles.add(c.getSimpleName().replace("Fragment", ""));
        }
        bar.setListNavigationCallbacks(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, titles),
                new ActionBar.OnNavigationListener() {
                    @Override
                    public boolean onNavigationItemSelected(int position, long itemId) {
                        selectFragment(position);
                        return true;
                    }

                });

        String fragment = getIntent().getStringExtra("fragment");
        if (fragment != null)
            for (int i = 0; i < FRAGMENTS.length; i++) {
                if (fragment.equals(FRAGMENTS[i].getSimpleName())) {
                    getActionBar().setSelectedNavigationItem(i);
                }
            }

        selectFragment(0);
    }

    private void selectFragment(int position) {
        if (position < FRAGMENTS.length)
            try {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                Class<?> classFragment = FRAGMENTS[position];
                Fragment fragment = mFragments.get(classFragment);
                if(fragment == null) {
                    fragment = (Fragment) classFragment.newInstance();
                }
                mFragments.put(classFragment, fragment);
                transaction.replace(android.R.id.content,
                        fragment, "");
                transaction.commit();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
    }
}
