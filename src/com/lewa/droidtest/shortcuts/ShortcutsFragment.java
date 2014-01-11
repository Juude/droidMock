
package com.lewa.droidtest.shortcuts;
import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lewa.droidtest.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShortcutsFragment extends Fragment {
    public static final String TAG = "AppFragment";
    final List<String> mPackages = new ArrayList<String>();
    private Activity mActivity;
    private HashMap<String, Intent> mShortcutsMap = new HashMap<String, Intent>();
    private BaseAdapter mAdapter;
    private List<String>  mShortcuts = new ArrayList<String>();
    private LayoutInflater mInflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = getActivity();
        mAdapter = new AppAdapter();
        setHasOptionsMenu(true);
        mActivity.getActionBar().setDisplayHomeAsUpEnabled(true);
        mInflater = LayoutInflater.from(mActivity);
        addShortCuts();
        super.onCreate(savedInstanceState);
    }

    private void addShortCuts() {
        Intent i;
        i = new Intent();
        i.setComponent(new ComponentName("com.android.settings", 
                "com.android.settings.Settings$LocalePickerActivity"));
        mShortcutsMap.put("language2", i);
        mShortcuts.addAll(mShortcutsMap.keySet());

    }
    public void onSearch(String text) {
        mAdapter.notifyDataSetChanged();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView listView = new ListView(mActivity);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setAdapter(mAdapter);
        
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent launchIntent = mShortcutsMap.get(mShortcuts.get(position));
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
    

    class AppAdapter extends BaseAdapter {

        @Override
        public int getCount() {
             return mShortcutsMap.keySet().size();
        }

        @Override
        public Object getItem(int position) {
            return mShortcuts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = mInflater.inflate(R.layout.shortcuts_item, null);
           
            TextView title = (TextView) v.findViewById(R.id.textTitle);
            TextView subTitle = (TextView) v.findViewById(R.id.textSubTitle);

            title.setText(mShortcuts.get(position));
            
            subTitle.setText(mShortcutsMap.get(mShortcuts.get(position)).toString());
            return v;
        }
    }
}
