package com.juude.droidmocks.stetho;

import com.facebook.stetho.dumpapp.DumperContext;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.PrintWriter;
import java.util.List;

/**
 * Created by juude on 16/8/5.
 */
public class StethoHelper {
    public static String[] getArgumentsOrNull(DumperContext dumperContext) {
        List<String> list = dumperContext.getArgsAsList();
        if (list == null || list.size() ==0) {
            return null;
        } else {
            String[] arguments = new String[list.size()];
            list.toArray(arguments);
            return arguments;
        }
    }

    public static CommandLine parse(DumperContext dumpContext, Options options) throws ParseException {
        return dumpContext.getParser().parse(options, getArgumentsOrNull(dumpContext));
    }

    public static PrintWriter getPrintWriter(DumperContext dumpContext) {
        return new PrintWriter(dumpContext.getStdout());
    }

    public int getInt(CommandLine commandLine, char key, int defaultValue) {
        try {
            Integer.parseInt(commandLine.getOptionValue(key));
        }catch (NumberFormatException e) {
        }
        return defaultValue;
    }

    public static boolean shouldDump(CommandLine commandLine, DumperContext dumpContext) {
        String[] arguments = getArgumentsOrNull(dumpContext);
        return arguments == null ||  commandLine.hasOption("dump");
    }

    public static String getString(CommandLine commandLine, char key, String defaultValue) {
        if (commandLine.hasOption(key)) {
            return commandLine.getOptionValue(key);
        } else {
            return defaultValue;
        }
    }
}
