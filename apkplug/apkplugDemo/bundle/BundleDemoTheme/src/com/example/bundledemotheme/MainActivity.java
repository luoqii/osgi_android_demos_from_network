package com.example.bundledemotheme;

import org.apkplug.Bundle.BundleActivity;
import org.apkplug.Bundle.ThemeControl;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends BundleActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button button1=(Button)this.findViewById(R.id.button1);
		button1.setOnClickListener(
				new OnClickListener(){
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {
							setTheme();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
		});
		final Button button2=(Button)this.findViewById(R.id.button2);
		button2.setOnClickListener(
				new OnClickListener(){
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {
							setThemeToSystem();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
		});
	}
	public void setTheme() throws Exception{
		BundleContext mcontext=BundleContextFactory.getInstance().getBundleContext();
		ServiceReference reference=mcontext.getServiceReference(ThemeControl.class.getName());
    	if(null!=reference){
    		ThemeControl service=(ThemeControl) mcontext.getService(reference);
    		if(service!=null){
    			service.setTheme(mcontext, mcontext.getBundle());
    		}
    	mcontext.ungetService(reference);
    	}
	}	
	public void setThemeToSystem() throws Exception{
		BundleContext mcontext=BundleContextFactory.getInstance().getBundleContext();
		ServiceReference reference=mcontext.getServiceReference(ThemeControl.class.getName());
    	if(null!=reference){
    		ThemeControl service=(ThemeControl) mcontext.getService(reference);
    		if(service!=null){
    			service.setTheme(mcontext,0);
    		}
    	mcontext.ungetService(reference);
    	}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
