package net.neosum.android.nastore;

import java.util.Formatter;
import java.util.List;

import net.neosum.android.nastore.felix.FelixManager;
import net.neosum.android.nastore.gui.BundleListTabItem;
import net.neosum.android.nastore.gui.FileListTabItem;
import net.neosum.android.nastore.gui.TabContainerManager;
import net.neosum.android.nastore.gui.TabItem;
import net.neosum.android.nastore.gui.TabItemHasViewFactory;
import net.neosum.android.view.ViewFactory;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private TabContainerManager tabContainerManager;
	private ViewGroup currentServiceViewContainer;
	private FelixManager felixManager;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bundlemanagermain);
        
        String packageRootPath = getFilesDir().getAbsolutePath();
      
        felixManager = new FelixManager(packageRootPath);

        //do initViews first if do not follow these. system will occur null error.
        initViews();
        initServiceTracker();
    }
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i("NAStore", "Stopping Felix...");
		felixManager.stopFelix();
	}

	private void initServiceTracker()
    {
    	final BundleContext bundleContext = 
    		felixManager.getFelix().getBundleContext();
    	
    	ServiceTrackerCustomizer serviceController = new ServiceTrackerCustomizer() {
			public Object addingService(ServiceReference ref) {
				final ViewFactory viewFactory = (ViewFactory) bundleContext.getService(ref);
				if (viewFactory != null) {
					runOnUiThread(new Runnable() {
						public void run() {
							TabItemHasViewFactory tabItemhasViewFactory = 
								new TabItemHasViewFactory(viewFactory, 
														MainActivity.this,
														viewFactory.getTitle(), 
														currentServiceViewContainer)
							{
								protected View generateInnerView() {
									return viewFactory.createView(MainActivity.this);
								}

								@Override
								protected void regen() { 
									viewFactory.regenView();
								}
							};
							
							if(tabContainerManager != null)
							{
								tabContainerManager.addTabItem(tabItemhasViewFactory);
							}
							else
							{
								Log.i("NAStore","Tab Container Manager is null");
							}
						}
					});
				}	
				return viewFactory;
			}

			public void modifiedService(ServiceReference ref, Object service) {
				removedService(ref, service);
				addingService(ref);
			}

			
			public void removedService(final ServiceReference ref, Object service) {
				bundleContext.ungetService(ref);
				runOnUiThread(new Runnable() {
					public void run() {
						final ViewFactory viewFactory = (ViewFactory) bundleContext.getService(ref);
						List<TabItem> tabItems = tabContainerManager.getTabItems();
						
						TabItem foundTabItem = null;
						
						for(TabItem tempItem:tabItems)
						{
							if(tempItem instanceof TabItemHasViewFactory)
							{
								ViewFactory tempViewFactory = 
									((TabItemHasViewFactory)tempItem).getViewFactory();
								if(tempViewFactory.equals(viewFactory))
								{
									foundTabItem = tempItem;
									break;
								}
							}
						}
						
						if(foundTabItem != null)
						{
							tabContainerManager.removeTabItem(foundTabItem);
						}
					}
				});
			}};
    	
    	
    	ServiceTracker servicetracker;
    	
    	StringBuffer viewFactoryFilter = new StringBuffer();
    	String filterSyntax = "(%s=%s)";
    	
    	Formatter format = new Formatter(viewFactoryFilter);
    	format.format(filterSyntax, Constants.OBJECTCLASS,ViewFactory.class.getName());
    	
    	try 
		{
			servicetracker = new ServiceTracker(
					bundleContext, 
					bundleContext.createFilter(viewFactoryFilter.toString()),
					serviceController);

			servicetracker.open();
		} 
		catch (InvalidSyntaxException e) 
		{
			e.printStackTrace();
		}
    }
    
    
    private void initViews() {
    	currentServiceViewContainer = (FrameLayout) this.findViewById(R.id.viewContainer);
    	LinearLayout tabItemsConatiner = (LinearLayout)this.findViewById(R.id.tabItemContainer);
    	
    	tabContainerManager = new TabContainerManager(tabItemsConatiner);
    	
    	BundleContext bundleContext = felixManager.getFelix().getBundleContext();
    	
    	
    	BundleListTabItem bundleListTabItem = 
    		new BundleListTabItem(bundleContext,this,"Bundles", currentServiceViewContainer);
    	
    	FileListTabItem fileListTabItem = 
    		new FileListTabItem(bundleContext,this,"/sdcard/bundles",currentServiceViewContainer);
    	
    	tabContainerManager.addTabItem(bundleListTabItem);
    	tabContainerManager.addTabItem(fileListTabItem);
    }
}