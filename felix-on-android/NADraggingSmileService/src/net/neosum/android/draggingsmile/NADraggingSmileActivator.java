package net.neosum.android.draggingsmile;

import java.util.Properties;

import net.neosum.android.view.ViewFactory;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class NADraggingSmileActivator implements BundleActivator
{

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		ViewFactoryImpl vi = new ViewFactoryImpl();

		Properties props = new Properties();
        bundleContext.registerService(
        		ViewFactory.class.getName(),vi , props);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		
	}

}
