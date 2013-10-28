package com.apkplug.bundle.example.bundledemostartactivity1;

import org.apkplug.Bundle.BundleActivity;

import com.apkplug.bundle.example.bundledemostartactivity1.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class testActivity extends BundleActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
	
	}
}
