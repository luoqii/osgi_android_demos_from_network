package com.example.bundledemoapkplugservice;
import org.apkplug.Bundle.BundleInstance;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import android.content.Intent;


public class BundleContextFactory implements BundleInstance{
private static BundleContextFactory _instance=null;
   private BundleContext mcontext = null;
//单例静态工厂方法,同步防止多线程环境同时执行 
   synchronized public static BundleContextFactory getInstance(){
    if(_instance==null){
    _instance=new BundleContextFactory();
    }
    return _instance;
    } 
    //私有的默认构造函数，防止使用构造函数进行实例化
	private BundleContextFactory(){
	
	}

	public BundleContext getBundleContext() {
		// TODO Auto-generated method stub
		return this.mcontext;
	}
	public void setBundleContext(BundleContext arg0) {
		// TODO Auto-generated method stub
		this.mcontext = arg0;
	}

}
