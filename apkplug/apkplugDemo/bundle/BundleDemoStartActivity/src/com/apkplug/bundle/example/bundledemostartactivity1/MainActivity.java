package com.apkplug.bundle.example.bundledemostartactivity1;
import org.apkplug.Bundle.BundleActivity;
import org.apkplug.Bundle.StartActivity;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

import com.apkplug.bundle.example.bundledemostartactivity1.R;
import com.testActivity.testActivity2;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
						button1.setText("你点击了我！");	
					}
		});
		final Button button2=(Button)this.findViewById(R.id.button2);
		button2.setOnClickListener(
				new OnClickListener(){
					public void onClick(View v) {
						//再插件中启动Activity 可以不用通过获取StartActivity服务来启动
						//与普通android程序一样的方式来启动
						Intent i=new Intent();
						i.setClass(MainActivity.this,testActivity.class);
						i.putExtra("name", "apkplug");
						//i.setClass(MainActivity.this, PicCutDemoActivity.class);
						//i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(i);
					}
		});
		final Button button3=(Button)this.findViewById(R.id.button3);
		button3.setOnClickListener(
				new OnClickListener(){
					public void onClick(View v) {
						//获取插件启动时的BundleContext
						BundleContext mcontext= BundleContextFactory.getInstance().getBundleContext();
						//查询服务（OSGI知识）
						ServiceReference reference=mcontext.getServiceReference(StartActivity.class.getName());
				    	if(null!=reference){
				    		//查询到服务
				    		StartActivity service=(StartActivity) mcontext.getService(reference);
				    		if(service!=null){
				    			//该Intent可以传递一些参数
				    			Intent i=new Intent();
				    			i.putExtra("v", "可以传递一些参数");
								i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				    			service.StartActivity(mcontext, i,testActivity2.class);
				    		}
				    		//用完以后应该注销服务
				    	mcontext.ungetService(reference);
				    	}
					}
		});
	
	
	}
	
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() != KeyEvent.ACTION_UP) {
			//关闭插件有利用软件回收内存
			try {
				BundleContextFactory.getInstance().
				getBundleContext().getBundle().stop();
			} catch (BundleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return super.dispatchKeyEvent(event);
		} else {
			return super.dispatchKeyEvent(event);
		}
	} 
}
