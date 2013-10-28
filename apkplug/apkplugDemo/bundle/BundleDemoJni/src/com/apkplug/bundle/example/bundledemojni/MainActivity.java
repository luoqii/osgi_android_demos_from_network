package com.apkplug.bundle.example.bundledemojni;
import org.apkplug.Bundle.BundleActivity;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends BundleActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 TextView jni=(TextView)this.findViewById(R.id.jni);
		 jni.setText(stringFromJNI());
	}
	static
    {
		try {
			System.loadLibrary("ndkhelloworld");

		} catch (UnsatisfiedLinkError e) {
			Log.e(MainActivity.class.getCanonicalName(),
					"Native code library failed to load.\n" + e.getMessage());
		} catch (Exception e) {
			Log.e(MainActivity.class.getCanonicalName(),
					"Native code library failed to load.\n" + e.getMessage());
		}
    }
	public  native String stringFromJNI();

}
