package net.neosum.android.nastore.gui;

import org.osgi.framework.Bundle;

public class BundleItem 
{
	public BundleItem(Bundle bundle)
	{
		this.bundle = bundle;
	}
	private Bundle bundle;

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}
	
}
