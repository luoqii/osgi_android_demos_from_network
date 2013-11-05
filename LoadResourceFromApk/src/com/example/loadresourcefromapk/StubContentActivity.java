
package com.example.loadresourcefromapk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

public class StubContentActivity extends Activity {

    private static final String TAG = StubContentActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stub_content);
    }
    
    @Override
    protected void attachBaseContext(Context newBase) {
        // TODO Auto-generated method stub
        super.attachBaseContext(newBase);
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        // TODO Auto-generated method stub
        super.onPostResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onUserLeaveHint() {
        // TODO Auto-generated method stub
        super.onUserLeaveHint();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub
        super.onLowMemory();
    }

    @Override
    public void onContentChanged() {
        // TODO Auto-generated method stub
        super.onContentChanged();
    }

    @Override
    public void onAttachedToWindow() {
        // TODO Auto-generated method stub
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        // TODO Auto-generated method stub
        super.onDetachedFromWindow();
    }

    @Override
    public void recreate() {
        // TODO Auto-generated method stub
        super.recreate();
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        // TODO Auto-generated method stub
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        // TODO Auto-generated method stub
        return super.onCreateView(parent, name, context, attrs);
    }

    public void attachProxyContext(Context proxy) {
        attachBaseContext(proxy);
    }


}
