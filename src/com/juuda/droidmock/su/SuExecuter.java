
package com.juuda.droidmock.su;

import android.os.SystemProperties;
import android.util.Log;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import libcore.io.IoUtils;
import libcore.io.Streams;

import java.lang.Process;

public class SuExecuter {
    private static final String CONFIG_SU_BINARY = SystemProperties.get("suname", "su");
    private static final String TAG = "SuExecuter";

    public static class CommandResult {
        public String result = "";
        public String error = "";
        public boolean success = false;

        @Override
        public String toString() {
            return "CommandResult [result=" + result + ", error=" + error + ", success=" + success
                    + "]";
        }
    }

    public static CommandResult runCommandForResult(String command, boolean root) {
        Process process = null;
        DataOutputStream os = null;
        CommandResult ret = new CommandResult();
        Log.d(TAG, "running " + command + "@@ su is " + CONFIG_SU_BINARY);
        try {
            if (root) {
                process = Runtime.getRuntime().exec(CONFIG_SU_BINARY);
                os = new DataOutputStream(process.getOutputStream());
                os.writeBytes(command + "\n");
                os.writeBytes("exit\n");
                os.flush();
            }
            else {
                process = Runtime.getRuntime().exec(command);
            }
            ret.result = Streams.readFully(new InputStreamReader(process.getInputStream(),
                    "UTF-8"));
            ret.error = Streams.readFully(new InputStreamReader(process.getErrorStream(),
            		"UTF-8"));
            ret.success = process.waitFor() == 0;
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
            ret.result = "";
            ret.error = e.getMessage();
        }
        finally {
            IoUtils.closeQuietly(os);
        }
        return ret;
    }
}
