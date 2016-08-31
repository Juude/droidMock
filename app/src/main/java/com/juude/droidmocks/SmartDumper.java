package com.juude.droidmocks;

import android.util.Log;

import com.facebook.stetho.dumpapp.DumpException;
import com.facebook.stetho.dumpapp.DumperContext;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.juude.droidmocks.stetho.StethoHelper;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.PrintWriter;

/**
 * Created by juude on 16/8/31.
 */
public abstract class SmartDumper implements DumperPlugin {

    private CommandLine commandLine;
    private PrintWriter printWriter;

    @Override
    public void dump(DumperContext dumpContext) throws DumpException {
        try {
            printWriter = StethoHelper.getPrintWriter(dumpContext);
            commandLine = StethoHelper.parse(dumpContext, getOptions());
            printWriter.println("dumpInner begin");
            dumpInner(this.commandLine);
            printWriter.println("dumpInner end");
        } catch (ParseException e) {
            printWriter.println(Log.getStackTraceString(e));
        } finally {
            printWriter.close();
        }
    }

    protected abstract void dumpInner(CommandLine commandLine);

    protected PrintWriter getWriter() {
        return Preconditions.checkNotNull(this.printWriter);
    }

    public String getOptionValue(char ch) {
        return this.commandLine.getOptionValue(ch);
    }

    public String getOptionValue(String str) {
        return this.commandLine.getOptionValue(str);
    }

    public String getOptionValue(String str, String defaultValue) {
        return this.commandLine.getOptionValue(str, defaultValue);
    }

    public abstract Options getOptions();
}
