#mk文件必须以定义LOCAL_PATH为开始，返回包含Android.mk的目录路径
LOCAL_PATH := $(call my-dir)
#负责清理LOCAL_xxx，因为所有的编译控制文件由同一个GNU Make解析和执行，
#其变量是全局的，所以清理后才能避免相互影响
include $(CLEAR_VARS)
#生成模块的名字
LOCAL_MODULE := ApkAddUpdateServer
#不检查未定义的符号，用于处理undefined reference to错误
LOCAL_ALLOW_UNDEFINED_SYMBOLS := true

#获取$(LOCAL_PATH)目录即jni目录下的所有要编译的.c文件，并把结果放在变量MY_CPP_LIST里
MY_CPP_LIST := $(wildcard $(LOCAL_PATH)/*.c)
MY_CPP_LIST += $(wildcard $(LOCAL_PATH)/bzip2/*.c)
LOCAL_SRC_FILES := $(MY_CPP_LIST:$(LOCAL_PATH)/%=%)

#BUILD_STATIC_LIBRARY：编译为静态库
#BUILD_SHARED_LIBRARY：编译为动态库 
#BUILD_EXECUTABLE：编译为Native C可执行程序
include $(BUILD_SHARED_LIBRARY) 