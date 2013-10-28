package com.apkplug.bundle.example.bundledemoosgiservice2;
import org.apkplug.Bundle.BundleActivity;
import org.apkplug.Bundle.StartActivity;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import com.service.sayHelloImp;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 确保OSGIService1 比OSGIService1先启动这样才能查询到服务哦
 * @author love
 *
 */
public class MainActivity extends BundleActivity {
	 sayHelloImp service=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final TextView se=(TextView)this.findViewById(R.id.service);
		BundleContext mcontext=BundleContextFactory.getInstance().getBundleContext();
		ServiceReference reference=mcontext.getServiceReference(sayHelloImp.class.getName());
		//查找sayHelloImp服务接口
    	if(null!=reference){
    		service=(sayHelloImp) mcontext.getService(reference);
    		if(service!=null){
    			se.post(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						se.setText(service.sayHello());
					}
    				
    			});
    		}
    	mcontext.ungetService(reference);
    	}else{
			se.post(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					se.setText("获取服务失败");
				}
				
			});
		}
    	
    	final Button button1=(Button)this.findViewById(R.id.button1);
		button1.setOnClickListener(
				new OnClickListener(){
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(service!=null){
							startActivity(service.myActivity());
						}else{
							Toast.makeText(MainActivity.this, "没有查询到服务",
								     Toast.LENGTH_SHORT).show();
						}
					}
		});
	}

	public void startActivity(Class ActivityClass){
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
    			service.StartActivity(mcontext, i,ActivityClass);
    		}
    		//用完以后应该注销服务
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
