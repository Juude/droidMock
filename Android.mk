LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := optional tests
LOCAL_SRC_FILES := $(call all-java-files-under, src)
LOCAL_PACKAGE_NAME := com.lewa.droidtest
LOCAL_CERTIFICATE := platform
LOCAL_JAVA_LIBRARIES := mms-common
include $(BUILD_PACKAGE) 
