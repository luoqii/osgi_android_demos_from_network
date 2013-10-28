package net.neosum.android.nastore.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import net.neosum.android.nastore.R;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class FileItems extends ArrayAdapter<FileItem>{
	private List<FileItem> fis;
	private BundleContext bc;

	public FileItems(Context context, int textViewResourceId,
			List<FileItem> objects,BundleContext bc) {
		super(context, textViewResourceId, objects);
		this.fis = objects;
		this.bc = bc;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		   View v = convertView;
           if (v == null) 
           {
               LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               v = vi.inflate(R.layout.flieitem, null);
               
           }
           final FileItem fi = fis.get(position);
           final File f = fi.getF();
           if (f != null && f.isFile()) {
                   TextView tt = (TextView) v.findViewById(R.id.fileTitleTv);
                   if (tt != null) {
                	   
           					tt.setText(f.getName());
                         
                    }
             Button instBtn = (Button) v.findViewById(R.id.installFileBtn);
             instBtn.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					try {
						System.out.println("file://"+f.getAbsolutePath());
                        Bundle bb = bc.installBundle(f.getName(), new FileInputStream(f));
						
						if(bb != null)
						{
							bb.start();
							
						}
					} catch (BundleException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
				}
             });
             
             Button uninstallBundleBtn = (Button) v.findViewById(R.id.deleteFileBtn);
             uninstallBundleBtn.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					f.delete();
					fis.remove(fi);
					FileItems.this.notifyDataSetChanged();
				}
             });
           }
		return v;
	}
}
