package net.neosum.android.draggingsmile;

import android.app.Activity;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import net.neosum.android.nastore.R;
import net.neosum.android.view.ViewFactory;

public class ViewFactoryImpl implements ViewFactory, OnTouchListener{

	private boolean dragging;
	private final String title ="D.Smile";
	
	private Point startDragPoint;
	
	@Override
	public View createView(Activity activity) {
		startDragPoint = new Point();
		LayoutInflater li = activity.getLayoutInflater();
		FrameLayout layout = (FrameLayout) li.inflate(R.layout.draggingsmilelayout, null);
		final ImageView smileMan = (ImageView) layout.findViewById(R.id.smileMan);
		smileMan.setOnTouchListener(this);
		smileMan.setClickable(true);
		dragging = false;
		
		
		return layout;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void regenView() {
		
	}

	@Override
	public boolean onTouch(View view, MotionEvent evt) {
		
		if (evt.getAction() == MotionEvent.ACTION_DOWN) {
			dragging = true;
			startDragPoint.x = (int)evt.getX();
			startDragPoint.y = (int)evt.getY();
			//Log.i(title	, "Start Dragging");
		}
		if (evt.getAction() == MotionEvent.ACTION_UP) {
			dragging = false;
			//Log.i(title	, "Stop Dragging");
		} else if (evt.getAction() == MotionEvent.ACTION_MOVE) {
			//Log.i(title	, "TouchMoving");
			if (dragging) {
				view.setPadding(startDragPoint.x + ((int)evt.getX()-startDragPoint.x), 
						startDragPoint.y + ((int) evt.getY()-startDragPoint.y), 0, 0);
			}
		}
		
		return false;
	}

}
