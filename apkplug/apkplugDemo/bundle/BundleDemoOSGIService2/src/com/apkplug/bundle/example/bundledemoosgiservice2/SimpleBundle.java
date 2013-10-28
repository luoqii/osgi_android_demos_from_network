/*
 * 一个简单的插件在启动该插件时启动插件的activity
 *
**/
package com.apkplug.bundle.example.bundledemoosgiservice2;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import android.content.Intent;
public class SimpleBundle implements BundleActivator
{
    private BundleContext mcontext = null;
    private ServiceRegistration m_reg = null;
    public void start(BundleContext context) throws Exception
    {
        System.err.println("你好我是插件,我获取已存在的sayHelloImp服务 我已经启动了 我的BundleId为："+context.getBundle().getBundleId());
        this.mcontext = context;
        BundleContextFactory.getInstance().setBundleContext(context);
    }
   
    public void stop(BundleContext context)
    {
    	System.err.println("你好我是插件,我被停止了 我的BundleId为："+context.getBundle().getBundleId());
      
    }
}
