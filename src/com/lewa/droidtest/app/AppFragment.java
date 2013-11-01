
package com.lewa.droidtest.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.Toast;

import com.lewa.droidtest.pm.Pm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppFragment extends Fragment {

    private Activity mActivity;
    private HashMap<String, PackageInfo> mPacakgesMaps = new HashMap<String, PackageInfo>();
    private PackageManager mPackageManager;
    private Pm mPm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = getActivity();
        mPm = new Pm(mActivity, null);
        mPackageManager = mActivity.getPackageManager();
        super.onCreate(savedInstanceState);
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
        final List<String> packages = new ArrayList<String>();
        mPacakgesMaps = getInstalledApplications();
        packages.addAll(mPacakgesMaps.keySet());
        listView.setAdapter(new AppAdapter(getActivity(), android.R.id.text2, packages));
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle args = new Bundle();
                args.putString("package", packages.get(position));
                mPm.setArgs(args);
                Toast.makeText(mActivity, mPm.list(), Toast.LENGTH_LONG).show();
                return false;
            }

        });
        
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent launchIntent = mPackageManager.getLaunchIntentForPackage(packages.get(position));
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

    private class AppAdapter extends ArrayAdapter<String> {

        public AppAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
        }
    }
}
