LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE    := libstack

include $(BUILD_STATIC_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE    := ndkhelloworld
LOCAL_SRC_FILES += ndkhelloworld.c
LOCAL_LDLIBS += -llog  
LOCAL_STATIC_LIBRARIES := libstack
include $(BUILD_SHARED_LIBRARY)