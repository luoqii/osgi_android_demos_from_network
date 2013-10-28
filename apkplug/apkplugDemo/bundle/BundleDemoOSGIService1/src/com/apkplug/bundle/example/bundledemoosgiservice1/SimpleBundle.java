/*
 * 一个简单的插件在启动该插件时启动插件的activity
 *
**/
package com.apkplug.bundle.example.bundledemoosgiservice1;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import com.service.sayHelloImp;

import android.content.Intent;
public class SimpleBundle implements BundleActivator
{
    private BundleContext mcontext = null;
    private ServiceRegistration m_reg = null;
    private sayMyName my=null;
    public void start(BundleContext context) throws Exception
    {
        System.err.println("你好我是插件,我将为你提供sayHelloImp服务 我已经启动了 我的BundleId为："+context.getBundle().getBundleId());
        this.mcontext = context;
        //需要再plugin.xml引出com.service包
        my=new sayMyName();
        m_reg = context.registerService(
        		sayHelloImp.class.getName(),
				my
	            ,
	            null);
    }
   
    public void stop(BundleContext context)
    {
    	m_reg.unregister();
    	System.err.println("你好我是插件,我被停止了 我的BundleId为："+context.getBundle().getBundleId());
      
    }
	class sayMyName implements sayHelloImp{

		@Override
		public String sayHello() {
			// TODO Auto-generated method stub
			return "你好我是OSGI测试插件1";
		}

		@Override
		public Class myActivity() {
			// TODO Auto-generated method stub
			return testActivity2.class;
		}
	
	}
}
