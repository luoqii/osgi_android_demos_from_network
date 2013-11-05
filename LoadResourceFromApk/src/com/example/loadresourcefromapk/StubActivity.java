
package com.example.loadresourcefromapk;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class StubActivity extends Activity {

    private static final String TAG = StubActivity.class.getSimpleName();
    StubContentActivity mProxyActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mProxyActivity = new StubContentActivity();
//        attach();
        copyFields();
        mProxyActivity.onCreate(savedInstanceState);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        mProxyActivity.onStart();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        mProxyActivity.onResume();
    }
    
    void copyFields() {
        String[] copiedFields = new String[]{
                "mMainThread",
                "mInstrumentation",
                "mToken",
                "mIdent",
                "mApplication",
                "mIntent",
                "mActivityInfo",
                "mTitle",
                "mParent",
                "mEmbeddedID",
                "mLastNonConfigurationInstances",
                "mFragments",//java.lang.IllegalStateException FragmentManagerImpl.moveToState
                "mWindow",
                "mWindowManager",
        "mCurrentConfig"};
        try {
            Class<?> ACTIVITY = Class.forName("android.app.Activity");
            for (String f: copiedFields){
                Field declaredField = ACTIVITY.getDeclaredField(f);
                setField(mProxyActivity, declaredField, getFiledValue(this, f));
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    void attach() {
        try {
            Class AT = Class.forName("android.app.ActivityThread");
            Class NFI = Class.forName("android.app.Activity$NonConfigurationInstances");
            Class<?> ACTIVITY = Class.forName("android.app.Activity");
//            ACTIVITY = mProxyActivity.getClass();
//            Method[] methods = ACTIVITY.getMethods();
//            for (Method m : methods) {
//                Log.d(TAG, "m: " + m.getName());
//            }
//            Log.d(TAG, "getDeclaredMethods");
//            methods = ACTIVITY.getDeclaredMethods();
//            for (Method m : methods) {
//                if (!m.getName().contains("att")) {
//                    continue;
//                }
//                Log.d(TAG, "m: " + m.getName());
//                Class<?>[] parameterTypes = m.getParameterTypes();
//                for (Class c : parameterTypes) {
//                    Log.d(TAG, "  p: " + c);
//                }
//            }
            Method method = ACTIVITY.getDeclaredMethod("attach",
                    new Class[]{
                    Context.class, 
                    AT, 
                    Instrumentation.class,
                    IBinder.class, 
                    int.class,
                    Application.class, 
                    Intent.class,
                    ActivityInfo.class, 
                    CharSequence.class,
                    Activity.class, 
                    String.class,
                    NFI,
                    Configuration.class});
            Object[] parameters = new Object[]{
                    this,
                    getFiledValue(this, "mMainThread"),
                    getFiledValue(this, "mInstrumentation"),
                    getFiledValue(this, "mToken"),
                    getFiledValue(this, "mIdent"),
                    getFiledValue(this, "mApplication"),
                    getFiledValue(this, "mIntent"),
                    getFiledValue(this, "mActivityInfo"),
                    getFiledValue(this, "mTitle"),
                    getFiledValue(this, "mParent"),
                    getFiledValue(this, "mEmbeddedID"),
                    getFiledValue(this, "mLastNonConfigurationInstances"),
                    getFiledValue(this, "mCurrentConfig")
            };
            method.setAccessible(true);
            method.invoke(mProxyActivity, parameters);
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        };
    }

    private void setField(Object object, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private Object getFiledValue(Object object, String fieldName) {
        Object f = null;
        try {
            Class<?> ACTIVITY = Class.forName("android.app.Activity");
            Field declaredField = ACTIVITY.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            f = declaredField.get(object);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        Log.d(TAG, "getFiledValue(). fieldName: " + fieldName + " field: " + f);
        return f;
    }

}
