package com.example.bundledemoapkplugservice;

import java.util.ArrayList;
import java.util.List;

import org.apkplug.Bundle.BundleListActivity;
import org.apkplug.Bundle.StartActivity;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.apkplug.SearchApp.AppSearchCallBack;
import com.apkplug.SearchApp.appSearch;
import com.apkplug.SearchApp.appSearchBean;
import com.apkplug.msg.appBean;
import com.apkplug.msg.msg;
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
	private appSearchBean bean=null;
	private ListViewAdapter adapter;

    private ArrayList<appBean> apps = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher);
        
        apps = new ArrayList<appBean>();
        updataDate();
        //adapter = new ArrayAdapter<ActivityInfo>(this,
        //  R.layout.launcher_item, R.id.text, mActivities);
        adapter = new ListViewAdapter(MainActivity.this,apps);

        setListAdapter(adapter);
    }
	public void updataDate(){
		bean=new appSearchBean();
    	bean.setB_keywords("test");
    	bean.setG_order(appSearchBean.order_desc);
    	bean.setPagenum(10);
    	bean.setPage(0);
	    if(apps.size()==0){	
	    	search(bean, new AppSearchCallBack(){
						@Override
						public void onSuccess(int stutas,msg msg,appSearchBean bean) {
							 
							if(stutas>=0){
								if(msg.getStutes()>=0){
									 List<appBean> aps=(List)msg.getMsg();
				    				 for (int i = 0; i < aps.size(); i++) {
				 							appBean ab=aps.get(i);
				 							
				    						apps.add(ab);
				    					
				    					}
				    				 MainActivity.this.getListView().post(new Runnable(){
				    					 public void run(){
				    						 adapter.notifyDataSetChanged();
				    						 
				    						
				    					 }
				    				 });
							      
								}
							}else{
								MainActivity.this.getListView().post(new Runnable(){
									@Override
									public void run() {
										Toast.makeText(MainActivity.this, "网络连接错误",
									     Toast.LENGTH_SHORT).show();
									}
								});	
							}
						}
						
						@Override
						public void onFailure(int arg0, final String arg1) {
							// TODO Auto-generated method stub
							MainActivity.this.getListView().post(new Runnable(){
								@Override
								public void run() {
									Toast.makeText(MainActivity.this, arg1,
								     Toast.LENGTH_SHORT).show();
								}
							});	
						}
	    			});
	    }
	}
	public void search(appSearchBean bean,AppSearchCallBack callback){
		
    	ServiceReference reference=BundleContextFactory.getInstance().getBundleContext()
        		.getServiceReference(appSearch.class.getName());
    	if(null!=reference){
    		appSearch service=(appSearch) BundleContextFactory.getInstance().getBundleContext()
    				.getService(reference);
    		if(service!=null){
    			
    				service.search(bean,callback);
    		}
    		BundleContextFactory.getInstance().getBundleContext()
    		.ungetService(reference);
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
    	
    }

    private class MyAdapter extends LListAdapter<appBean> {
      MyAdapter() {
        super(MainActivity.this, apps);
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
    	convertView = LayoutInflater.from(mContext).inflate(R.layout.launcher_item, null);
    	appBean b=this.list.get(position);
        TextView title = (TextView) convertView.findViewById(R.id.activity_title);
        TextView desc = (TextView) convertView.findViewById(R.id.activity_desc);

        title.setText(b.getAppname());
        desc.setText(b.getInfo());
        return convertView;
      }

    }

}
