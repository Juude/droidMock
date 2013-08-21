
package com.lewa.droidtest.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AppFragment extends Fragment {

    private Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView listView = new ListView(mActivity);
        listView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        final List<String> packages = new ArrayList<String>();
        packages.add("com.lewa.droidtest");

        listView.setAdapter(new AppAdapter(getActivity(), android.R.id.text2, packages));
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mActivity, packages.get(position), Toast.LENGTH_LONG).show();
                return false;
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
