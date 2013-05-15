package com.lewa.droidtest.sdcard;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SdcardWriter {
    private String mTempPath = Environment.getExternalStorageDirectory()
    .getPath() + File.separator + "0000.jpg";
    
    public void write() {
        System.out.println("writing...to" + mTempPath);
        File dest = new File(mTempPath);
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
