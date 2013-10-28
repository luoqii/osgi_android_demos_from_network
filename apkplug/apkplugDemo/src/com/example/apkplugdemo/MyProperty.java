package com.example.apkplugdemo;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apkplug.app.PropertyInstance;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Environment;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;

public class MyProperty implements PropertyInstance{
	private   Context context;
	private static MyProperty _instance=null;
	public MyProperty(Context context){
		this.context=context;
	}
	synchronized public static MyProperty getInstance(Context context){
    if(_instance==null){
    _instance=new MyProperty(context);
    }
    return _instance;
    } 

	public String getProperty(String key) {
		// TODO Auto-generated method stub
		SharedPreferences sharedata = PreferenceManager.getDefaultSharedPreferences(this.context);
		String data = sharedata.getString(key, null);
		return data;
	}
	public void setProperty(String key, String v) {
		// TODO Auto-generated method stub
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.context); 
		Editor edit = settings.edit();
		edit.putString(key, v);
		edit.commit();
	}
	public String[] AutoInstall() {
		// TODO Auto-generated method stub
		return null;
	}
	public String[] AutoStart() {
		//把BundleDemo1.apk从assets文件夹中移至应用安装目录中
		File f0=null,f=null,f1=null,f2=null,f3=null,f4=null,f5=null,f6=null;
		//插件托管服务应该提前启动
		try {
			InputStream in=context.getAssets().open("BundleService.apk");
			f0=new File(context.getFilesDir(),"BundleService.apk");
			copy(in, f0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			InputStream in=context.getAssets().open("BundleDemoOSGIService1.apk");
			f=new File(context.getFilesDir(),"BundleDemoOSGIService1.apk");
			copy(in, f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //应该先启动 服务 再启动获取服务的插件
        try {
			InputStream in=context.getAssets().open("BundleDemoOSGIService2.apk");
			f1=new File(context.getFilesDir(),"BundleDemoOSGIService2.apk");
			copy(in, f1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			InputStream in=context.getAssets().open("BundleDemoJni.apk");
			f2=new File(context.getFilesDir(),"BundleDemoJni.apk");
			copy(in, f2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //插件Activiyt
        try {
			InputStream in=context.getAssets().open("BundleDemoStartActivity1.apk");
			f3=new File(context.getFilesDir(),"BundleDemoStartActivity1.apk");
			copy(in, f3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      //应该先启动 服务 再启动获取服务的插件
        try {
			InputStream in=context.getAssets().open("BundleDemoTheme.apk");
			f4=new File(context.getFilesDir(),"BundleDemoTheme.apk");
			copy(in, f4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      //应该先启动 服务 再启动获取服务的插件
        try {
			InputStream in=context.getAssets().open("BundleDemoShow.apk");
			f5=new File(context.getFilesDir(),"BundleDemoShow.apk");
			copy(in, f5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      //插件托管服务测试
        try {
			InputStream in=context.getAssets().open("BundleDemoApkplugService.apk");
			f6=new File(context.getFilesDir(),"BundleDemoApkplugService.apk");
			copy(in, f6);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return new String[]{"file:"+f0.getAbsolutePath(),"file:"+f.getAbsolutePath(),"file:"+f1.getAbsolutePath(),
			"file:"+f2.getAbsolutePath(),"file:"+f3.getAbsolutePath(),"file:"+
	f4.getAbsolutePath(),"file:"+f5.getAbsolutePath(),"file:"+f6.getAbsolutePath()};
	}
	private void copy(InputStream is, File outputFile)
	        throws IOException
	    {
	        OutputStream os = null;

	        try
	        {
	            os = new BufferedOutputStream(
	                new FileOutputStream(outputFile),4096);
	            byte[] b = new byte[4096];
	            int len = 0;
	            while ((len = is.read(b)) != -1)
	                os.write(b, 0, len);
	        }
	        finally
	        {
	            if (is != null) is.close();
	            if (os != null) os.close();
	        }
	    }
	
}
