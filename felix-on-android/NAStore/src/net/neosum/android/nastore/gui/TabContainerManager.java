package net.neosum.android.nastore.gui;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;

public class TabContainerManager {
	private ViewGroup tabContainer;
	private final List<TabItem> tabItems;
	private TabItem currentSelectedItem;

	public TabContainerManager(ViewGroup tabContainer)
	{
		this.tabContainer = tabContainer;
		tabItems = new ArrayList<TabItem>();
	}
	
	public void addTabItem(TabItem tabItem)
	{
		tabItem.setParent(this);
		tabItems.add(tabItem);
		this.tabContainer.addView(tabItem.getBtn());
	}
	public void removeTabItem(TabItem tabItem)
	{
		if(tabItems.contains(tabItem))
		{
			this.tabContainer.removeView(tabItem.getBtn());
			tabItems.remove(tabItem);
		}
	}
	
	public List<TabItem> getTabItems() {
		return tabItems;
	}

	public void refreshTabItemBtnsStates(TabItem selectedTabItem) 
	{
		Log.i("NAStore",selectedTabItem.getBtn().getText()+" Touched");
		if(currentSelectedItem!=null && !currentSelectedItem.equals(selectedTabItem))
		{
			currentSelectedItem.getBtn().setTextColor(Color.BLACK);
		}
		
		currentSelectedItem = selectedTabItem;
		currentSelectedItem.getBtn().setTextColor(Color.YELLOW);
	}
	
	public TabItem getCurrentSelectedItem() {
		return currentSelectedItem;
	}
}
