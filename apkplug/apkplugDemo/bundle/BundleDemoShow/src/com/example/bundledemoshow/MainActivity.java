package com.example.bundledemoshow;

import java.util.ArrayList;

import org.apkplug.Bundle.BundleListActivity;
import org.apkplug.Bundle.StartActivity;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;


import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BundleListActivity {

	private MyAdapter adapter;

    private ArrayList<org.osgi.framework.Bundle> mActivities = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher);
    
        mActivities = new ArrayList<org.osgi.framework.Bundle>();
        updataDate();
        //adapter = new ArrayAdapter<ActivityInfo>(this,
        //  R.layout.launcher_item, R.id.text, mActivities);
        adapter = new MyAdapter();

        setListAdapter(adapter);
    }
	public void updataDate(){
		BundleContext context =BundleContextFactory.getInstance().getBundleContext();
		for(int i=0;i<context.getBundles().length;i++)
		{
			//System.out.println(context.getBundles()[i].getLocation());
			//if(context.getBundles()[i].getBundleActivity()!=null)
				mActivities.add(context.getBundles()[i]);        	        

		}
	}
    /**
     * 获取系统提供的StartActivity服务来启动一个插件中的Activity
     * 前提时插件中已在plugin.xml设置了Export-Package中添加了该
     * Activity完整包路径 否则会找不到该Activity
     * @param name
     * @throws Exception
     */
    public void startActivity(String ActivityClass) throws Exception{
    	System.out.println(ActivityClass);
		BundleContext mcontext=BundleContextFactory.getInstance().getBundleContext();
		ServiceReference reference=mcontext.getServiceReference(StartActivity.class.getName());
    	if(null!=reference){
    		StartActivity service=(StartActivity) mcontext.getService(reference);
    		if(service!=null){
    			Intent i=new Intent();
				i.setClassName(this, ActivityClass);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    			service.StartActivity(mcontext, i);
    		}
    	mcontext.ungetService(reference);
    	}
	}
    
    
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	try {
    		//通过框架服务来启动
    		org.osgi.framework.Bundle b=mActivities.get(position);
    		if(b.getBundleActivity()!=null){
    			if(b.getState()!=org.osgi.framework.Bundle.ACTIVE){
    				//如果插件还未启动
    				b.start();
    			}
    			startActivity(b.getBundleActivity());
    		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private class MyAdapter extends LListAdapter<org.osgi.framework.Bundle> {
      MyAdapter() {
        super(MainActivity.this, mActivities);
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
    	convertView = LayoutInflater.from(mContext).inflate(R.layout.launcher_item, null);
    	org.osgi.framework.Bundle b=this.list.get(position);
        TextView title = (TextView) convertView.findViewById(R.id.activity_title);
        TextView desc = (TextView) convertView.findViewById(R.id.activity_desc);

        title.setText(b.getName());
        if(b.getBundleActivity()!=null){
        	desc.setText("可点击启动Activity");
        }
        return convertView;
      }

    }

}
