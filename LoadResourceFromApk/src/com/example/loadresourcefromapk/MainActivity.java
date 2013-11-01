
package com.example.loadresourcefromapk;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Resources res = loadApkResource("/sdcard/test.apk");

        String appName = res.getString(R.string.res);
        ((TextView)findViewById(R.id.text)).setText(appName);
    }

    private Resources loadApkResource(String apkFilePath) {
        
        AssetManager assets = null;
        try {
            assets = AssetManager.class.getConstructor(null).newInstance(null);
            Method method = assets.getClass().getMethod("addAssetPath", new Class[]{String.class});
            Object r = method.invoke(assets, apkFilePath);
            Log.d(TAG, "result: " + r);

            DisplayMetrics metrics = null;
            Configuration config = null; 
            Resources res = new Resources(assets, metrics, config);
            return res;
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
