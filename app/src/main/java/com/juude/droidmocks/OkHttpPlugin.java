package com.juude.droidmocks;

import android.util.Log;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by juude on 16/8/31.
 */
@Plugin
public class OkHttpPlugin extends SmartDumper {


    @Override
    public String getName() {
        return "okhttp";
    }


    @Option
    public void openUrl() {
        String url = getOptionValue('u');
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            Response response = okHttpClient.newCall(new Request.Builder().url(url).build()).execute();
            dumpResponse(response);
        } catch (IOException e) {
            getWriter().println(Log.getStackTraceString(e));
        }
    }

    private void dumpResponse(Response response) {
        getWriter().println("code : " + response.code());
        getWriter().println("headers : ");
        for (String name : response.headers().names()) {
            getWriter().println("\t" + name + " : " + response.header(name));
        }
        getWriter().flush();
        try {
            getWriter().println("body : " + response.body().string());
        } catch (IOException e) {
            getWriter().println(Log.getStackTraceString(e));
        }
        getWriter().flush();
    }

    @Override
    protected void dumpInner(CommandLine commandLine) {
        if (commandLine.hasOption('u')) {
            openUrl();
        }
    }

    @Override
    public Options getOptions() {
        return new Options()
                .addOption("u", "url", true, "url")
                .addOption("r", "redirect", false, "getredirect");
    }

    @interface Option {

    }

    @interface Parameter {
        //public Parameter(String key);
    }

}
