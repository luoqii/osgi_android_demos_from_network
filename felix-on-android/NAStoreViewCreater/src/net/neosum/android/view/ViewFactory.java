package net.neosum.android.view;

import android.app.Activity;
import android.view.View;

public interface ViewFactory 
{
	public View createView(Activity activity);
	public void regenView();
	public String getTitle();
}
