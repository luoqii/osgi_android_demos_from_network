/*
 * 一个简单的插件在启动该插件时启动插件的activity
 *
**/
package com.example.bundledemotheme;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import android.content.Intent;
public class SimpleBundle implements BundleActivator
{
    private BundleContext mcontext = null;
    public void start(BundleContext context) throws Exception
    {
        System.err.println("你好我是插件,我将为你展示启动acitivty我已经启动了 我的BundleId为："+context.getBundle().getBundleId());
        this.mcontext = context;
        //将BundleContext放入BundleContextFactory工厂中
        //方便插件的其他类里面调用
        BundleContextFactory.getInstance().setBundleContext(context);
    }
   
    public void stop(BundleContext context)
    {
    	System.err.println("你好我是插件,我被停止了 我的BundleId为："+context.getBundle().getBundleId());
      
    }
	
}
