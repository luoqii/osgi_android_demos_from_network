package net.neosum.android.nastore.felix;

import java.io.File;
import java.util.Properties;

import org.apache.felix.framework.Felix;
import org.osgi.framework.BundleException;

public class FelixManager 
{
	private String rootPath;
	
	private Felix felix;

	private Properties felixProperties;
	
	private File bundlesDir;
    private File cacheDir;

    public FelixManager(String rootPath)
	{
		this.rootPath = rootPath;
		
		  felixProperties = new FelixProperties(this.rootPath);
	        
	        
	        bundlesDir = new File(rootPath+"/felix/bundle");
	        if (!bundlesDir.exists()) {
	        	if (!bundlesDir.mkdirs()) {
	        		throw new IllegalStateException("Unable to create bundles dir");
	        	}
	        }
	        cacheDir = new File(rootPath+"/felix/cache");
	        if (!cacheDir.exists()) {
	        	if (!cacheDir.mkdirs()) {
	        		throw new IllegalStateException("Unable to create felixcache dir");
	        	}
	        }
	        
	        try
	        {
	            felix = new Felix(felixProperties);
	            felix.start();
	        }
	        catch (Exception ex)
	        {
	            ex.printStackTrace();
	        }
	}
    
    public Felix getFelix() {
		return felix;
	}

	public Properties getFelixProperties() {
		return felixProperties;
	}
	
	public void stopFelix()
	{
		
		try {
			felix.stop();
		} catch (BundleException e) {
			e.printStackTrace();
		}
		
	}
}
