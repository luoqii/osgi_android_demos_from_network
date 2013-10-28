package com.example.bundledemoapkplugservice;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import net.tsz.afinal.FinalBitmap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.apkplug.download.AppDownloadCallBack;
import com.apkplug.download.appDownload;
import com.apkplug.msg.DownloadBean;
import com.apkplug.msg.appBean;
import com.apkplug.msg.download;

public class ListViewAdapter extends LListAdapter<appBean>{
	private appDownload service=null;
	public ListViewAdapter(Context c, List<appBean> data) {
		super(c, data);
		// TODO Auto-generated constructor stub
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public appBean getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		final ListViewHolder viewHolder;
		final appBean ab=list.get(position);
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.index_item_listview, null);
			viewHolder = new ListViewHolder();
			viewHolder.imageViewIcon = (ImageView)convertView.findViewById(R.id.image_item_1);
			viewHolder.appName = (TextView)convertView.findViewById(R.id.text_item_1);
			//viewHolder.dsize = (TextView)convertView.findViewById(R.id.text_item_2);
			viewHolder.appinfo = (TextView)convertView.findViewById(R.id.text_item_2);
			viewHolder.appSize = (TextView)convertView.findViewById(R.id.text_item_3);
			viewHolder.download = (TextView)convertView.findViewById(R.id.text_item_4);
			viewHolder.imgSplit = (ImageView)convertView.findViewById(R.id.image_item_2);
			viewHolder.imgDownLoad = (ImageView)convertView.findViewById(R.id.image_item_3);
			
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ListViewHolder)convertView.getTag();
		}
		
		//if(position % 2 == 0) {
			viewHolder.imgDownLoad.setBackgroundResource(R.drawable.down_btn_10);
		//}else {
			//viewHolder.imgDownLoad.setBackgroundResource(R.drawable.down_btn_0);
		//}
		viewHolder.appName.setText(ab.getAppname());
		//viewHolder.dsize.setText(""+ab.getD_num());
		viewHolder.appinfo.setText(ab.getInfo());
		viewHolder.appSize.setText(String.format("%2.2fM", (float)ab.getSize()/(1024*1024)));
		
		
    	
    	if(ab.getUpdatestatus()==0){
			viewHolder.download.setText("下  载") ;
		}else if(ab.getUpdatestatus()==1){
				viewHolder.download.setText("更 新") ;
		}else if(ab.getUpdatestatus()==2){
			viewHolder.download.setText("运 行") ;
			
		}
			
	  FinalBitmap fb=FinalBitmap.create(this.mContext);
				 //System.out.println("iconurl "+ab.getIconurl());
	  fb.display(viewHolder.imageViewIcon, ab.getIconurl());
		viewHolder.imgDownLoad.setOnClickListener(
				new OnClickListener(){
					public void onClick(View v) {
						if(viewHolder.download.getText().equals("下  载") ){
							download(ab,viewHolder);
						}else if(viewHolder.download.getText().equals("更 新") ){
									download(ab,viewHolder);
						}else if(viewHolder.download.getText().equals("运 行")){
									org.osgi.framework.Bundle b= gatHadBundle(ab);
									if(b!=null){
										if(b.getBundleActivity()!=null){
											Intent i=new Intent();
											i.setClassName(mContext, b.getBundleActivity());
											i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
											mContext.startActivity(i);
										}else{
											try {
												b.start();
											} catch (BundleException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									}
										
								}else if(viewHolder.download.getText().equals("停 止")){
									org.osgi.framework.Bundle bb= gatHadBundle(ab);
									if(bb.getState()==org.osgi.framework.Bundle.STOPPING){
										try {
											bb.stop();
										} catch (BundleException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
						}
				
			});
			return convertView;
		}
    	
    public void download(final appBean ab,final ListViewHolder viewHolder){
    	ServiceReference reference=BundleContextFactory.getInstance().getBundleContext()
        		.getServiceReference(appDownload.class.getName());
    	if(null!=reference){
    		service=(appDownload) BundleContextFactory.getInstance().getBundleContext()
    				.getService(reference);
    	}
    	BundleContextFactory.getInstance().getBundleContext()
		.ungetService(reference);
    	if(service!=null){
			
			try {
				
				  service.download(ab,mContext, new AppDownloadCallBack(){
						@Override
						public void callbackProgressListener(
								final int progress, final String Speed,
								DownloadBean bean) {
							// TODO Auto-generated method stub
							viewHolder.appinfo.post(new Runnable(){
					    		public void run(){
					    			viewHolder.appinfo.setText("p:" + progress + "% speed:" + Speed) ;
					    		}
					    	});
						}

						@Override
						public void onDownLoadSuccess(
								DownloadBean arg0,final String info) {
							// TODO Auto-generated method stub
							viewHolder.appinfo.post(new Runnable(){
					    		public void run(){
					    			viewHolder.appinfo.setText(info) ;
					    		}
					    	});
						}

						@Override
						public void onFailure(
								int arg1, final String arg2) {
							viewHolder.appinfo.post(new Runnable(){
				        		public void run(){
				        			viewHolder.appinfo.setText(arg2) ;
				        		}
				        	});
							
						}

				

						@Override
						public void onInstallSuccess(
								org.osgi.framework.Bundle arg0) {
							viewHolder.appinfo.post(new Runnable(){
				        		public void run(){
				        			ab.setUpdatestatus(2);
				        			viewHolder.download.setText("运 行") ;
				        		}
				        	});
						}

						@Override
						public void progress(int arg0,
								final String arg1) {
							// TODO Auto-generated method stub
							viewHolder.appinfo.post(new Runnable(){
				        		public void run(){
				        			ab.setUpdatestatus(2);
				        			viewHolder.appinfo.setText(arg1) ;
				        		}
				        	});
						}
						

					});
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	
	
	}
    }
	
	public org.osgi.framework.Bundle gatHadBundle(appBean ab) {
		// TODO Auto-generated method stub
		 BundleContext context =BundleContextFactory.getInstance().getBundleContext();
    	 org.osgi.framework.Bundle[] bs=context.getBundles();
    	 for(int i=0;i<bs.length;i++)
 		{
 			
 			if(bs[i].getSymbolicName().equals(ab.getSymbolicName())){
 				return bs[i];
 			}    	        

 		}
 		return null;
	}
	
	private final class ListViewHolder {
    	public ImageView imageViewIcon;
    	public TextView appName;
    	public TextView dsize;
    	public TextView appinfo;
    	public TextView appSize;
    	public TextView download;
    	public ImageView imgSplit;
    	public ImageView imgDownLoad;
    	public TextView appPriceFlag;
    }
}
