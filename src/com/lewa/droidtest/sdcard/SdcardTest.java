
package com.lewa.droidtest.sdcard;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;

import com.lewa.droidtest.mock.MockReceiver;
import com.lewa.droidtest.mock.MockUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SdcardTest {
    public Context mContext;
    public Bundle mBundle;
    
    public SdcardTest(Context context, Bundle bundle) {
        mContext = context;
        mBundle  = bundle;
    }
    
    public void test() {
        write();
    }
    
    public void write() {
        String path = MockUtils.getString(mBundle, "path", Environment.getExternalStorageDirectory()
                .getPath() + File.separator + "0000.jpg");
        
        File dest = new File(path);
        System.out.println("writing...to" + path);

        if (dest.exists())
        {
            dest.delete();
        }
        try {
            FileOutputStream fop = new FileOutputStream(dest);
            fop.write(0);
            fop.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

}
