package net.neosum.android.nastore.gui;

import android.app.Activity;
import android.view.ViewGroup;
import net.neosum.android.view.ViewFactory;

public abstract class TabItemHasViewFactory extends TabItem
{
	private final ViewFactory viewFactory;
	
	public TabItemHasViewFactory(ViewFactory viewFactory, 
								Activity context, 
								String buttonTitle, 
								ViewGroup viewContainer)
	{
		super(context, buttonTitle, viewContainer);
		this.viewFactory = viewFactory;
	}
	
	public ViewFactory getViewFactory() {
		return viewFactory;
	}
}
