package net.neosum.android.nastore.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.neosum.android.nastore.R;

import org.osgi.framework.BundleContext;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FileListTabItem extends TabItem
{
	private FileItems fis = null;
	private BundleContext bundleContext;
	private Timer refreshTimer;
	
	public FileListTabItem(BundleContext bundleContext, Activity context, String buttonTitle,
			ViewGroup viewContainer) {
		super(context, buttonTitle, viewContainer);
		this.bundleContext = bundleContext;
	}

	protected View generateInnerView() {
		File sdDir = new File("/sdcard/bundles");
		if(sdDir.exists()==false)
		{
		sdDir.mkdir();
		}
		File[] files = sdDir.listFiles();
		
		List<FileItem> fileList = new ArrayList<FileItem>();
			
		for (File f : files) {
			fileList.add(new FileItem(f));
		}
    	
    	  fis = new FileItems(
					super.context, 
					R.layout.flieitem,
					fileList,
					bundleContext);
		LayoutInflater li = context.getLayoutInflater();
		ListView lv = (ListView) li.inflate(R.layout.filelist, null);
		lv.setAdapter(fis);
		
		final Runnable refreshRunnable = new Runnable()
		{
			public void run() {
				FileListTabItem.this.regen();
			}
		};
		final TimerTask refresh = new TimerTask()
		{
			public void run()
			{
				TabItem selectedItem =parent.getCurrentSelectedItem();
				if(fis != null 
					&& selectedItem != null 
					&& selectedItem.equals(FileListTabItem.this))
				{
					FileListTabItem.this.context.runOnUiThread(refreshRunnable);
				}
			}
		};
		
		refreshTimer = new Timer();
		context.runOnUiThread(new Runnable(){
			public void run() {
				refreshTimer.schedule(refresh, 0, 3000);
			}
		});
		
		
		return lv;
	}

	@Override
	protected void regen() {
		fis.clear();
		File sdDir = new File("/sdcard/bundles");
		if(sdDir.exists()==false)
		{
		sdDir.mkdir();
		}
		File[] files = sdDir.listFiles();
		

			
		for (File f : files) {

			fis.add(new FileItem(f));
		}
		fis.notifyDataSetChanged();
		
		
	}

}
