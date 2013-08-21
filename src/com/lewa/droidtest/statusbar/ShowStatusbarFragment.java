
package com.lewa.droidtest.statusbar;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import com.android.internal.statusbar.IStatusBarService;

import com.lewa.droidtest.Utils;

public class ShowStatusbarFragment extends Fragment {
    private BroadcastReceiver mRecentTaskReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            IStatusBarService mStatusBarService = null;
            if (mStatusBarService == null) {
                mStatusBarService = IStatusBarService.Stub.asInterface(
                        ServiceManager.getService("statusbar"));
            }
            try {
                mStatusBarService.toggleRecentApps();
            }
            catch (RemoteException e) {
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = new LinearLayout(getActivity());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(layoutParams);
        Utils.addButton(this, layout, "register", "register");
        Utils.addButton(this, layout, "unregister", "unregister");
        return layout;
    }

    public void register() {
        getActivity().registerReceiver(mRecentTaskReceiver,
                new IntentFilter("ShowStatusbarFragment.ACTION"));
    }

    public void unregister() {
        getActivity().unregisterReceiver(mRecentTaskReceiver);
    }

    @Override
    public void onDestroy() {
        unregister();
        super.onDestroy();
    }

}
