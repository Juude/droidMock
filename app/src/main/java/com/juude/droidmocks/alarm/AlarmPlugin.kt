package com.juude.droidmocks.alarm

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log

import com.facebook.stetho.dumpapp.DumpException
import com.facebook.stetho.dumpapp.DumperContext
import com.facebook.stetho.dumpapp.DumperPlugin
import com.juude.droidmocks.stetho.StethoHelper

import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Options
import org.apache.commons.cli.ParseException

import java.io.PrintWriter


class AlarmPlugin(private val mApplication: Application) : DumperPlugin {
    private var mReceiver: BroadcastReceiver? = null
    private var mPrintWriter: PrintWriter? = null

    private val ALARM_ACTION = "com.android.deskclock.ALARM_ALERT"

    fun cancelTask() {
        val am = mApplication.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val operation = PendingIntent.getBroadcast(mApplication, 0, Intent(ALARM_ACTION),
                PendingIntent.FLAG_UPDATE_CURRENT)
        operation.cancel()//works
        am.cancel(operation)//works
    }

    fun start(delay: Int) {
        mReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                Log.e(TAG, "received.. TAG")
                mPrintWriter!!.println("alarm received" + intent.action)
                mPrintWriter!!.close()
            }
        }
        mApplication.applicationContext.registerReceiver(mReceiver, IntentFilter(ALARM_ACTION))
        val am = mApplication.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val operation = PendingIntent.getBroadcast(mApplication, 0,
                Intent(ALARM_ACTION),
                PendingIntent.FLAG_UPDATE_CURRENT)

        val currentMills = System.currentTimeMillis()
        am.set(AlarmManager.RTC_WAKEUP, currentMills + delay, operation)
    }

    override fun getName(): String {
        return "alarm"
    }

    private val options: Options
        get() = Options().addOption("t", "type", true, "commnd type").addOption("d", "delay", true, "delay")

    override fun dump(dumpContext: DumperContext) {
        mPrintWriter = StethoHelper.getPrintWriter(dumpContext)
        try {
            val commandLine = StethoHelper.parse(dumpContext, options)
            val type = commandLine.getOptionValue('t', "start")
            val delay = Integer.parseInt(commandLine.getOptionValue('d'))
            if ("start" == type) {
                start(delay)
            } else if ("cancel" == type) {
                cancelTask()
            }
        } catch (e: ParseException) {
            mPrintWriter!!.println(Log.getStackTraceString(e))
        } finally {
            mPrintWriter!!.flush()
        }
    }

    companion object {
        private val TAG = "AlarmPlugin"
    }

}