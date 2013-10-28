package net.neosum.android.nastore.gui;

import java.util.List;


import net.neosum.android.nastore.R;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BundleItems extends ArrayAdapter<BundleItem> 
{
	private List<BundleItem> bundleItems;
	private Context context;

	public BundleItems(Context context, int textViewResourceId,
			List<BundleItem> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.bundleItems = objects;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		   View v = convertView;
           if (v == null) 
           {
               LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               v = vi.inflate(R.layout.bundleitem, null);
           }
           final BundleItem bundleItem = bundleItems.get(position);
           final Bundle bundle = bundleItem.getBundle();
           
           if (bundle != null) {
                   TextView tt = (TextView) v.findViewById(R.id.bundleTitleTv);
                   Button startBundleBtn = (Button) v.findViewById(R.id.startBundleBtn);
                   Button uninstallBundleBtn = (Button) v.findViewById(R.id.uninstallBundleBtn);
                   
                   if (tt != null) {
                	   String stateStr ="";
           				int state = bundle.getState();
           				switch (state){
	           				case Bundle.ACTIVE: 
	           					stateStr = "ACTIVE"; 
		           				startBundleBtn.setText("Stop");
		           				startBundleBtn.setOnClickListener(new OnClickListener(){
		           					public void onClick(View v) {
		           						try {
		           							bundle.stop();
		           						} catch (BundleException e) {
		           							Log.d("Felix", "Stop Failed :" + bundle.getSymbolicName());
		           							e.printStackTrace();
		           						}
		           						BundleItems.this.notifyDataSetChanged();
		           					}
		           	             });
	           				
	           				break;
		           			case Bundle.INSTALLED: 
		           				stateStr = "INSTALLED";
		           				startBundleBtn.setText("Start");
		           				startBundleBtn.setOnClickListener(new OnClickListener(){
		           					public void onClick(View v) {
		           						try {
		           							bundle.start();
		           						} catch (BundleException e) {
		           							Log.d("Felix", "Start Failed :" + bundle.getSymbolicName());
		           							e.printStackTrace();
		           						}
		           						BundleItems.this.notifyDataSetChanged();
		           					}
		           	             });
		           			break;
		           			case Bundle.RESOLVED: 
		           				stateStr = "RESOLVED";
		           				startBundleBtn.setText("Start");
		           				startBundleBtn.setOnClickListener(new OnClickListener(){
		           					public void onClick(View v) {
		           						try {
		           							bundle.start();
		           						} catch (BundleException e) {
		           							Log.d("Felix", "Start Failed :" + bundle.getSymbolicName());
		           							e.printStackTrace();
		           						}
		           						BundleItems.this.notifyDataSetChanged();
		           					}
		           	             });
		           			break;
		           			case Bundle.STARTING: stateStr ="STARTING"; break;
		           			case Bundle.STOPPING: stateStr ="STOPPING"; break;
           				}
           				
           				if (stateStr.length() == 0) stateStr = "UNKNOWN STATE";
           				
           				tt.setText(bundle.getSymbolicName() + " : " + stateStr);
           				uninstallBundleBtn.setOnClickListener(new OnClickListener(){
           					public void onClick(View v) {
           						try {
           							bundle.uninstall();
           							bundleItems.remove(bundleItem);
           							BundleItems.this.notifyDataSetChanged();
           						} catch (BundleException e) {
           							Toast failed = Toast.makeText(BundleItems.this.context, "Cannot uninstall : "+bundle.getSymbolicName(), 3000);
           							failed.show();
           							//e.printStackTrace();
           						}
           					}
           	             });
                         
              }
           }
		return v;
	}

}
