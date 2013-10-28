package net.neosum.android.nadiary.impl;

import java.util.List;

import net.neosum.android.nastore.R;
import net.neosum.android.view.ViewFactory;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ViewFactoryImpl implements ViewFactory  
{
	private String viewFactoryTitle;
	
	OBRResourceItems oi;
	List<String> objects;
	public ViewFactoryImpl(	List<String> objects)
	{
		viewFactoryTitle= null;
		this.objects = objects;
		
	}
	@Override
	public View createView(final Activity activity) {
		LayoutInflater li = activity.getLayoutInflater();
		LinearLayout ll = (LinearLayout) li.inflate(R.layout.diarylayout, null);
		
		oi = new OBRResourceItems(activity,R.layout.diaryitem,objects);
		ListView lv = (ListView) ll.findViewById(R.id.diaryList);
		lv.setAdapter(oi);
		
		final EditText et = (EditText) ll.findViewById(R.id.insertEt);
		final Button insertBtn = (Button) ll.findViewById(R.id.InsertBtn);
		insertBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View a0) {
				oi.rItems.add(et.getText().toString());
				et.setText("");
				et.endBatchEdit();
				oi.notifyDataSetChanged();
				
			}
			
		});
		return ll;
	}

	@Override
	public void regenView() 
	{
		//do nothing...
	}
	
	public void setViewFactoryTitle(String title) 
	{
		this.viewFactoryTitle = title;
	}
	
	@Override
	public String getTitle() {
		return viewFactoryTitle!=null?viewFactoryTitle:"Diary";
	}

	
	public class OBRResourceItems extends ArrayAdapter<String> {
		private List<String> rItems;
		public OBRResourceItems(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			this.rItems = objects;
		}


		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			   View v = convertView;
	           if (v == null) 
	           {
	               LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	               v = vi.inflate(R.layout.diaryitem, null);
	           }
	           final String rItem = (String) rItems.get(position);
	        
	           if (rItem != null) {
	                   TextView tt = (TextView) v.findViewById(R.id.diaryTv);
	                   if (tt != null) {
	           				
	           				tt.setText(rItem);
	                    }

	              Button deployBtn = (Button) v.findViewById(R.id.removeBtn);
	                   
	              deployBtn.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						rItems.remove(rItem);
						OBRResourceItems.this.notifyDataSetChanged();

					}
	             });
	           }
			return v;
		}
	}


	
}
