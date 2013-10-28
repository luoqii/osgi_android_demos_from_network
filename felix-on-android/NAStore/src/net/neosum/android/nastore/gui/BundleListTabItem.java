package net.neosum.android.nastore.gui;

import java.util.ArrayList;
import java.util.List;

import net.neosum.android.nastore.R;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class BundleListTabItem extends TabItem{

	private BundleItems bis = null;
	private BundleContext bundleContext;
	
	
	public BundleListTabItem(BundleContext bundleContext, Activity context, String buttonTitle,
			ViewGroup viewContainer) {
		super(context,buttonTitle,viewContainer);
		this.bundleContext = bundleContext;
	}

	@Override
	protected void regen() {
		bis.clear();
		Bundle[] bundles = this.bundleContext.getBundles();
		
		for (Bundle b : bundles) {
			bis.add(new BundleItem(b));
		}
		
		bis.notifyDataSetChanged();
	}
	
	protected View generateInnerView() {
		Bundle[] bundles = this.bundleContext.getBundles();
		
		List<BundleItem> bundleItemList = new ArrayList<BundleItem>();
			
		for (Bundle b : bundles) {
			bundleItemList.add(new BundleItem(b));
		}
    	
    	 bis = new BundleItems(
					super.context,
					R.layout.bundleitem,
					bundleItemList);
		LayoutInflater li = super.context.getLayoutInflater();
		ListView lv = (ListView) li.inflate(R.layout.bundlelist, null);
		lv.setAdapter(bis);
		
		
		
		return lv;
	}

}
