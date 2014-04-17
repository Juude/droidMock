package com.juuda.droidmock.shortcut;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.juuda.droidmock.R;
import com.juuda.droidmock.mock.Mocker;

public class ShortcutMocker extends Mocker{

    public ShortcutMocker(Context context, Bundle extras) {
        super(context, extras);
    }

    @Override
    public void dump() {
        Intent shorcCut = new Intent();
        shorcCut.setClassName("com.tencent.mm", "com.tencent.mm.plugin.scanner.ui.BaseScanUI");
        shorcCut.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        
        Intent intent = new Intent();
        intent.putExtra("duplicate", false);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shorcCut);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "ShortcutDemo");
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, 
                Intent.ShortcutIconResource.fromContext(mContext.getApplicationContext(), R.drawable.ic_launcher));
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        mContext.getApplicationContext().sendBroadcast(intent);

    }

}
