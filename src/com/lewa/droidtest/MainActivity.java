package com.lewa.droidtest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import com.lewa.droidtest.adb.TurnOffAdbFragment;
import com.lewa.droidtest.app.AppFragment;
import com.lewa.droidtest.data.DataFragment;
import com.lewa.droidtest.lock.LockFragment;
import com.lewa.droidtest.mms.sms.SmsSenderFragment;
import com.lewa.droidtest.resources.ResourcesFragment;
import com.lewa.droidtest.screen.ScreenSizeFragment;
import com.lewa.droidtest.statusbar.ShowStatusbarFragment;
import com.lewa.droidtest.su.SuTesterFragment;
import com.lewa.droidtest.view.LinkedTextFragment;
import com.lewa.droidtest.view.linkedbutton.LinkedButtonFragment;
import com.lewa.droidtest.view.list.ListAdapterTest;
import com.lewa.droidtest.view.surface.SurfaceViewFragment;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private static final Class<?>[] FRAGMENTS = {
        SuTesterFragment.class, 
        ListAdapterTest.class,
        SurfaceViewFragment.class,
        ResourcesFragment.class,
        DataFragment.class, 
        LinkedTextFragment.class,
        LockFragment.class,
        TurnOffAdbFragment.class,
        LinkedButtonFragment.class,
        ScreenSizeFragment.class,
        SmsSenderFragment.class,
        ShowStatusbarFragment.class,
        AppFragment.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout layout = new FrameLayout(this);
        layout.setId(android.R.id.content);
        setContentView(layout);
        initActionBar();
    }

    private void initActionBar() {
        ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        bar.setDisplayShowTitleEnabled(false);
        ArrayList<String> titles = new ArrayList<String>();
        for(Class<?> c : FRAGMENTS){
            titles.add(c.getSimpleName().replace("Fragment", ""));
        }
        bar.setListNavigationCallbacks(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, titles) , new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int position, long itemId) {
                if (position < FRAGMENTS.length)
                    try {
                        FragmentManager manager = getFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(android.R.id.content,
                                (Fragment) FRAGMENTS[position].newInstance(), "");
                        transaction.commit();
                    } catch (Exception e) {
                    }
                return true;
            }
            
        });
        
        String fragment = getIntent().getStringExtra("fragment");
        if(fragment != null)
            for(int i = 0; i < FRAGMENTS.length; i++){
                if(fragment.equals(FRAGMENTS[i].getSimpleName())){
                    getActionBar().setSelectedNavigationItem(i);
                }
            }

    }
}