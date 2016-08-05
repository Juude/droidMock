
package com.juude.droidmocks.sdcard;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;

import com.facebook.stetho.dumpapp.DumpException;
import com.facebook.stetho.dumpapp.DumperContext;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.juude.droidmocks.mock.MockUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SdcardPlugin implements DumperPlugin{

    public Context mContext;
    public Bundle mBundle;
    
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

    @Override
    public String getName() {
        return "sdcard";
    }

    @Override
    public void dump(DumperContext dumpContext) throws DumpException {
        
    }
}
