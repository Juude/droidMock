package com.juude.droidmocks.shortcut;

import android.app.Application;
import android.content.Intent;
import com.facebook.stetho.dumpapp.DumpException;
import com.facebook.stetho.dumpapp.DumperContext;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.juuda.droidmock.R;

public class ShortcutMocker implements DumperPlugin {

    private final Application application;

    public void dump() {
        Intent shorcCut = new Intent();
        shorcCut.setClassName("com.tencent.mm", "com.tencent.mm.plugin.scanner.ui.BaseScanUI");
        shorcCut.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent intent = new Intent();
        intent.putExtra("duplicate", false);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shorcCut);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "ShortcutDemo");
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(application.getApplicationContext(), R.drawable.ic_launcher));
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        application.getApplicationContext().sendBroadcast(intent);
    }

    @Override
    public String getName() {
        return "shortcuts";
    }

    public ShortcutMocker(Application application) {
        this.application = application;
    }

    @Override
    public void dump(DumperContext dumpContext) throws DumpException {
        dump();
    }
}
