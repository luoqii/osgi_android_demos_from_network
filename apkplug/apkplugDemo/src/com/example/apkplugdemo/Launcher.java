package com.example.apkplugdemo;

import java.util.ArrayList;
import java.util.List;

import org.apkplug.Bundle.StartActivity;
import org.apkplug.app.FrameworkFactory;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Launcher extends ListActivity {
	//插件平台对外接口
	private FrameworkInstance frame=null;
	//private ArrayAdapter<ActivityInfo> adapter;
    private MyAdapter adapter;

    private ArrayList<String> mActivities = null;

    private String[] mActTitles;
    private String[] mActDescs;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher);
        try
		{
       
			frame=FrameworkFactory.getInstance().start(null,Launcher.this,
					MyProperty.getInstance(this.getApplicationContext()));
        }
        catch (Exception ex)
        {
            System.err.println("Could not create : " + ex);
            //ex.printStackTrace();
            StringBuffer buf=new StringBuffer();
			buf.append("插件平台启动失败：\n");
			buf.append(ex.getMessage());
			this.setTitle(buf.toString());
			Toast.makeText(this, "插件平台启动失败",
				     Toast.LENGTH_SHORT).show();
        }
        mActivities = new ArrayList<String>();
        mActivities.add("com.apkplug.bundle.example.bundledemostartactivity1.MainActivity");
        mActivities.add("com.apkplug.bundle.example.bundledemojni.MainActivity");
        mActivities.add("com.apkplug.bundle.example.bundledemoosgiservice2.MainActivity");
        mActivities.add("com.example.bundledemotheme.MainActivity");
        mActivities.add("com.example.bundledemoshow.MainActivity");
        mActivities.add("com.example.bundledemoapkplugservice.MainActivity");
        mActTitles = getResources().getStringArray(R.array.activity_titles);
        mActDescs = getResources().getStringArray(R.array.activity_descs);

        //adapter = new ArrayAdapter<ActivityInfo>(this,
        //  R.layout.launcher_item, R.id.text, mActivities);
        adapter = new MyAdapter();

        setListAdapter(adapter);
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
		BundleContext mcontext=frame.getSystemBundleContext();
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
			startActivity(mActivities.get(position));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private class MyAdapter extends ArrayAdapter<String> {
      MyAdapter() {
        super(Launcher.this, R.layout.launcher_item, R.id.activity_title, mActivities);
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);

        TextView title = (TextView) v.findViewById(R.id.activity_title);
        TextView desc = (TextView) v.findViewById(R.id.activity_desc);

        title.setText(mActTitles[position]);
        desc.setText(mActDescs[position]);
        return v;
      }

    }

}
