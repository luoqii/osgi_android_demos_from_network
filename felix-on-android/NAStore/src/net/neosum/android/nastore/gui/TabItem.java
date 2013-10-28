package net.neosum.android.nastore.gui;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public abstract class TabItem 
{
	protected Button btn;
	protected ViewGroup viewContainer;
	protected boolean viewGenerated;
	protected View innerView;
	protected Activity context;
	
	//if want use this class then must be implements this method 
	protected abstract View generateInnerView();
	protected abstract void regen();
	
	protected TabContainerManager parent;
	
	//constructor
	public TabItem(final Activity context, String buttonTitle, ViewGroup viewContainer)
	{
		if(context == null)		return;
		if(viewContainer == null)	return;
		
		this.context = context;
		this.viewGenerated = false;
		this.viewContainer = viewContainer;
		this.btn = new Button(context);
		this.btn.setText(buttonTitle);
		
		OnClickListener onBtnClicked =new OnClickListener(){
			public void onClick(View v) 
			{
				TabItem.this.context.runOnUiThread(new Runnable(){
					public void run() {
						if(parent != null)
						{
							parent.refreshTabItemBtnsStates(TabItem.this);
						}
						if(TabItem.this.viewGenerated == true)
						{
							TabItem.this.regen();
						}
						TabItem.this.viewContainer.removeAllViews();
						if(TabItem.this.viewGenerated == false)
						{
							innerView = TabItem.this.generateInnerView();
							TabItem.this.viewGenerated = true;
						}
						
						TabItem.this.viewContainer.addView(innerView);
					}
				});
			}
		};
		
		btn.setOnClickListener(onBtnClicked);
	}

	public Button getBtn() {
		return btn;
	}

	public TabContainerManager getParent() {
		return parent;
	}
	public void setParent(TabContainerManager parent) {
		this.parent = parent;
	}
}
