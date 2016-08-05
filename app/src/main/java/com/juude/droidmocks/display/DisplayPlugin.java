
package com.juude.droidmocks.display;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.facebook.stetho.dumpapp.DumpException;
import com.facebook.stetho.dumpapp.DumperContext;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.juude.droidmocks.stetho.StethoHelper;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.PrintWriter;

public class DisplayPlugin implements DumperPlugin {

    private final Application application;

    public void printDisplay(PrintWriter printWriter) {
        WindowManager manager = (WindowManager)application.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        Point size = new Point();
        display.getSize(size);
        Point realSize = new Point();
        display.getRealSize(realSize);
        boolean hasNavigationBar = false;
        String info = "realwidth : " + realSize.x + "realHeight: " + realSize.y +
                "width : " + size.x + "height" + size.y +
                "density: " + outMetrics.density + "densityDpi" + outMetrics.densityDpi
                + "scaledDensity " + outMetrics.scaledDensity + "\nhas NavigationBar : " + hasNavigationBar;
        printWriter.println(info);
    }

    @Override
    public String getName() {
        return "display";
    }

    @Override
    public void dump(DumperContext dumpContext) throws DumpException {
        PrintWriter printWriter = StethoHelper.getPrintWriter(dumpContext);
        try {
            CommandLine commandLine = StethoHelper.parse(dumpContext, getOptions());
            if (StethoHelper.shouldDump(commandLine, dumpContext)) {
                printDisplay(printWriter);
            }
        } catch (ParseException e) {
            printWriter.println(Log.getStackTraceString(e));
        } finally {
            printWriter.close();
        }
    }

    private Options getOptions() {
        return new Options()
                .addOption("d", "dump", false, "dump");
    }

    public DisplayPlugin(Application application) {
        this.application = application;
    }
}
