
package com.example.loadresourcefromapk;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

/**
 * delegate our context to {@link StubActivity#mProxyActivity} to extract
 * UI from it.
 * 
 * @see {@link android.content.ContextWrapper}
 * @see <a href="http://en.wikipedia.org/wiki/Proxy_pattern">Proxy Pattern</a>
 * @author bysong
 *
 */
public class StubActivity extends Activity {

    private static final String TAG = StubActivity.class.getSimpleName();
    StubContentActivity mProxyActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        {// order is important.
            mProxyActivity = new StubContentActivity();
            copyFields();
            mProxyActivity.onCreate(savedInstanceState);
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu) && mProxyActivity.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu) && mProxyActivity.onPrepareOptionsMenu(menu);
    }

    private void copyFields() {
        String[] copiedFields = new String[] {
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
                "mFragments",// java.lang.IllegalStateException
                             // FragmentManagerImpl.moveToState
                "mWindow",
                "mWindowManager",
                "mCurrentConfig"
        };
        try {
            Class<?> ACTIVITY = Class.forName("android.app.Activity");
            for (String f : copiedFields) {
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
