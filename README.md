droidMock
=========

androidMock



https://raw.githubusercontent.com/Juude/droidMock/master/release/droidMock.apk


<a href="https://www.the-qrcode-generator.com/"><img src="http://chart.apis.google.com/chart?chs=200x200&amp;cht=qr&amp;chld=|1&amp;chl=https%3A%2F%2Fwww.dropbox.com%2Fs%2F8ekgxg1r8h4ho7n%2FdroidTest.apk" alt="QR Code" /></a>

###Functions
1. build a framework for building apps that can access and modify state of applications
2. mock.py notification  pop up a notification : popup a notification
3. mock.py settings method=toggleAdb : open/close adb 
4. mock.py alarmManagerService : schedule and unschedule task test
5. method.py Am method=forceStop package=packageName : execute ActivityManager.forceStopPackage for app whose package name is packageName
6. method.py Am method=killProcess package=packageName : use Process.kill process whose package name is packageName
7. method.py Am method=killBackground package=packageName : use ActivityManager.killBackgroundProcesses process whose package name is packageName
8. method.py Am method=anr :ause a anr 
9. mock.py Am method=resolve : PackageManager.queryIntentActivities to reolve intent
10. mock.py Am startAll : start multiple activies 
11. mock.py network method=toggle wifi=1 : toggle wifi data
12. mock.py network method=profile : get the generic connection of network,such as mobile and wifi state 
13. mock.py Pm start package=packageName : start launcher application of packageName
14. mock.py Pm list package=packageName: list general information of a package
15. mock.py shortcut : create a shortcut of a application
16. mock.py telehphony : get inserted simcard size

###How to..
####set up the framework
1. add droidMocks.jar to libs
2. add this 
        <receiver android:name="com.juuda.droidmock.mock.MockReceiver" >
            <intent-filter>
                <action android:name="android.mock" />
            </intent-filter>
        </receiver>
    to AndroidManifest.xml
3. add application instance in <application> tag,such as DemoDroidMocksApplication 
4. in the Application's onCreate, put : 

####add mocks
1. write a class that extends Mocker
2. do what you want in dump() 

####use mock.py 
1. add the mock.py file to PATH
2. useage: mock.py [module] [key=value] [key1=value2] .... 



###TODO
1. add usage for every module when mock.py [module] is typed
2. su custom command executor 
3. use multiple methods to invoke ANR
4. move locks to ViewTest
5. add mock to sendsms and mms
6. redirect logs to a View
7. move resources to view
8. move systemui related to viewTest
