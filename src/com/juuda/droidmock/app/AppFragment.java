
package com.juuda.droidmock.app;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.juuda.droidmock.R;
import com.juuda.droidmock.pm.Pm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import lewa.util.IconManager;

public class AppFragment extends Fragment {
    private static final String TAG = "AppFragment";
    final List<String> mPackages = new ArrayList<String>();
    private Activity mActivity;
    private HashMap<String, PackageInfo> mPacakgesMaps = new HashMap<String, PackageInfo>();
    private PackageManager mPackageManager;
    private Pm mPm;
    private LayoutInflater mInflater;
    private BaseAdapter mAdapter;
    private String mSearchingText;
    private List<String>  mFilteredPackages = new ArrayList<String>();
    private boolean mShowLaunch = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = getActivity();
        //IconManager.setEnableFancyDrawable(true);
        mPm = new Pm(mActivity, null);
        mPackageManager = mActivity.getPackageManager();
        mInflater = mActivity.getLayoutInflater();
        mAdapter = new AppAdapter();
        setHasOptionsMenu(true);
        mActivity.getActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.app_menu, menu);
        MenuItem showLaunch = menu.findItem(R.id.showLaunchable);
        if(mShowLaunch) {
            showLaunch.setTitle(R.string.show_launchable);
        }
        else {
            showLaunch.setTitle(R.string.not_show_launchable);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onSearch(null);
                break;
//            case R.id.showLaunchable:
//                mShowLaunch = !mShowLaunch;
//                onSearch(mSearchingText);
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSearch(String text) {
        mSearchingText = text;
        filterPackages();
        mAdapter.notifyDataSetChanged();
    }
    
    private void filterPackages() {
        mFilteredPackages.clear();
        for(String packageName : mPackages) {
            if(shouldShow(packageName)) {
                Log.i(TAG, "add package: " + packageName);
                mFilteredPackages.add(packageName);
            }
        }
    }
    
    private HashMap<String, PackageInfo> getInstalledApplications() {
        List<PackageInfo> packages = mPackageManager.getInstalledPackages(0);
        for(PackageInfo info : packages) {
            mPacakgesMaps.put(info.packageName, info);
        }
        return mPacakgesMaps;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView listView = new ListView(mActivity);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mPacakgesMaps = getInstalledApplications();
        mPackages.addAll(mPacakgesMaps.keySet());
        filterPackages();
        listView.setAdapter(mAdapter);
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle args = new Bundle();
                args.putString("package", mFilteredPackages.get(position));
                mPm.setArgs(args);
                Toast.makeText(mActivity, mPm.list(), Toast.LENGTH_LONG).show();
                return false;
            }

        });
        
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent launchIntent = mPackageManager.getLaunchIntentForPackage(mFilteredPackages.get(position));
                if(launchIntent != null) {
                    mActivity.startActivity(launchIntent);
                }
                else {
                    Toast.makeText(mActivity, "is not launchable ", Toast.LENGTH_LONG).show();
                }
            }
            
        });
        return listView;
    }
    
    private boolean shouldShow(String packageName) {
        if(mSearchingText != null && !packageName.contains(mSearchingText)) {
            return false;
        } 
        if(mPackageManager.getLaunchIntentForPackage(packageName) == null && mShowLaunch ) {
            return false;
        }
        return true;
    }

    class AppAdapter extends BaseAdapter {

        @Override
        public int getCount() {
             return mFilteredPackages.size();
        }

        @Override
        public Object getItem(int position) {
            return mFilteredPackages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v =  mInflater.inflate(R.layout.package_item, null);
            String packageName = mFilteredPackages.get(position);
            if(shouldShow(packageName)) {
                TextView textPackage = (TextView)v.findViewById(R.id.packageName);
                textPackage.setText(mFilteredPackages.get(position)); 
                
                PackageInfo info = mPacakgesMaps.get(packageName);

                
                TextView textTitle = (TextView)v.findViewById(R.id.textTitle);
                textTitle.setText(info.applicationInfo.loadLabel(mPackageManager)); 
                
                ImageView icon = (ImageView)v.findViewById(R.id.icon);
                icon.setImageDrawable(info.applicationInfo.loadIcon(mPackageManager));                
            }
            else {
                v.setVisibility(View.GONE);
            }
            return v;
        }
    }
}
