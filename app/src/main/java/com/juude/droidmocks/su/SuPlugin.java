
package com.juude.droidmocks.su;
import android.util.Log;
import com.facebook.stetho.dumpapp.DumpException;
import com.facebook.stetho.dumpapp.DumperContext;
import com.facebook.stetho.dumpapp.DumperPlugin;

import java.io.DataOutputStream;
import java.io.IOException;

public class SuPlugin implements DumperPlugin{

    private static final String TAG = "SuPlugin";

    @Override
    public String getName() {
        return "sud";
    }

    @Override
    public void dump(DumperContext dumpContext) throws DumpException {
        Process sh;
        try {
            sh = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(sh.getOutputStream());
            os.writeBytes("type");
            os.close();
        }
        catch (IOException e) {
            Log.e(TAG, "", e);
        }
    }
}
