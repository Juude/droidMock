
package com.juude.droidmocks.files;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.stetho.dumpapp.DumpException;
import com.facebook.stetho.dumpapp.DumpUsageException;
import com.facebook.stetho.dumpapp.DumperContext;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.juude.droidmocks.stetho.StethoHelper;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * based on FilesDumperPlugin but can access storage
 * */
public class StoragePlugin implements DumperPlugin{

    private final Application application;

    public StoragePlugin(Application application) {
        this.application = application;
    }
    @Override
    public String getName() {
        return "storage";
    }

    @Override
    public void dump(DumperContext dumpContext) throws DumpException {
        PrintWriter printWriter = StethoHelper.getPrintWriter(dumpContext);
        try {
            CommandLine commandLine = StethoHelper.parse(dumpContext, getOptions());
            String baseDirString = commandLine.getOptionValue('b');
            File baseDir = null;
            if (TextUtils.isEmpty(baseDirString)) {
                baseDir = getBaseDir(this.application);
            } else {
                baseDir = new File(baseDirString);
            }
            if(commandLine.hasOption("ls")) {
                this.doLs(printWriter, baseDir);
            } else if(commandLine.hasOption("tree")) {
                this.doTree(printWriter, baseDir);
            } else {
                printDirs(printWriter);
            }
        } catch (ParseException e) {
            printWriter.println(Log.getStackTraceString(e));
            printWriter.flush();
        } finally {
            printWriter.close();
        }


    }

    private void printDirs(PrintWriter printWriter) {
        printWriter.println( " external storage : " +  Environment.getExternalStorageDirectory());
        printWriter.println( " getDataDirectory : " + Environment.getDataDirectory());
        printWriter.println( " getDownloadCacheDirectory : " + Environment.getDownloadCacheDirectory());
        printWriter.println( " getCacheDir : " + application.getCacheDir());
        printWriter.println( " getFilesDir : " + application.getFilesDir());
        printWriter.println( " getExternalCacheDirs: " + Arrays.toString(application.getExternalCacheDirs()));
        printWriter.println( " filesDir : " + Arrays.toString(application.fileList()));
        printWriter.println( " getExternalFilesDirs()" + Arrays.toString(application.getExternalFilesDirs("")));
        printWriter.flush();
    }

    private static File getBaseDir(Context context) {
        return context.getFilesDir().getParentFile();
    }

    private Options getOptions() {
        return new Options()
                .addOption("l", "ls", false, "ls files")
                .addOption("d", "download", false, "download files")
                .addOption("t", "tree", false, "list file tree")
                .addOption("b", "base", true, "base dir");
    }

    private void doLs(PrintWriter writer, File file) throws DumpUsageException {
        File baseDir = file;
        if(baseDir.isDirectory()) {
            printDirectoryText(baseDir, "", writer);
        }
    }

    private void doTree(PrintWriter writer, File file) throws DumpUsageException {
        File baseDir = file;
        printDirectoryVisual(baseDir, 0, writer);
    }

    private static void printDirectoryVisual(File dir, int depth, PrintWriter writer) {
        File[] listFiles = dir.listFiles();
        for(int i = 0; i < listFiles.length; ++i) {
            printHeaderVisual(depth, writer);
            File file = listFiles[i];
            writer.print("+---");
            writer.print(file.getName());
            writer.println();
            if(file.isDirectory()) {
                printDirectoryVisual(file, depth + 1, writer);
            }
        }
    }

    private static void printHeaderVisual(int depth, PrintWriter writer) {
        for(int i = 0; i < depth; ++i) {
            writer.print("|   ");
        }

    }

    private static void printDirectoryText(File dir, String path, PrintWriter writer) {
        File[] listFiles = dir.listFiles();
        for(int i = 0; i < listFiles.length; ++i) {
            File file = listFiles[i];
            if(file.isDirectory()) {
                printDirectoryText(file, path + file.getName() + "/", writer);
            } else {
                writer.println(path + file.getName());
            }
        }
    }
}
