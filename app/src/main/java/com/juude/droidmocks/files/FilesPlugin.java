
package com.juude.droidmocks.files;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;

import com.facebook.stetho.dumpapp.DumpException;
import com.facebook.stetho.dumpapp.DumperContext;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.juude.droidmocks.mock.MockUtils;
import com.juude.droidmocks.stetho.StethoHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

//1. sd卡的读取,
public class FilesPlugin implements DumperPlugin{

    private final Application application;

    public FilesPlugin(Application application) {
        this.application = application;
    }

//    public void write() {
//        String path = MockUtils.getString(mBundle, "path", Environment.getExternalStorageDirectory()
//                .getPath() + File.separator + "0000.jpg");
//
//        File dest = new File(path);
//        System.out.println("writing...to" + path);
//
//        if (dest.exists())
//        {
//            dest.delete();
//        }
//        try {
//            FileOutputStream fop = new FileOutputStream(dest);
//            fop.write(0);
//            fop.close();
//        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        catch (IOException ex)
//        {
//            ex.printStackTrace();
//        }
//    }


    @Override
    public String getName() {
        return "files";
    }

    @Override
    public void dump(DumperContext dumpContext) throws DumpException {
        PrintWriter printWriter = StethoHelper.getPrintWriter(dumpContext);
        printWriter.println( " external storage : " +  Environment.getExternalStorageDirectory());
        printWriter.println( " getDataDirectory : " + Environment.getDataDirectory());
        printWriter.println( " getDownloadCacheDirectory : " + Environment.getDownloadCacheDirectory());
        printWriter.println( " getCacheDir : " + application.getCacheDir());
        printWriter.println( " getFilesDir : " + application.getFilesDir());
        printWriter.println( " getExternalCacheDirs: " + Arrays.toString(application.getExternalCacheDirs()));
        printWriter.println( " filesDir : " + Arrays.toString(application.fileList()));
        printWriter.println( " getExternalFilesDirs()" + Arrays.toString(application.getExternalFilesDirs("")));
        printWriter.close();
    }
}
