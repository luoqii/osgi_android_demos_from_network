package net.neosum.android.nadiary.impl;

import java.util.List;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import net.neosum.android.nadiary.NADiaryService;
import net.neosum.android.view.ViewFactory;


public class NADiaryActivator implements BundleActivator{

	private BundleContext bundleContext;
	@Override
	public void start(BundleContext bundleContext) throws Exception {
			this.bundleContext = bundleContext;
	        
			Properties props = new Properties();
	        props.put("Type", "Korean");

	        NADiaryServiceImpl diaryService = new NADiaryServiceImpl();
	        List<String> messageList = diaryService.getMessageList();
	        bundleContext.registerService(
		            NADiaryService.class.getName(),diaryService , props);
	        
	        
	        String bundleVersionString =
	        	Integer.toString(this.bundleContext.getBundle().getVersion().getMajor()); 
	        
	        diaryService.insertNewMessage(
	        		"Welcome Diary Service Ver." +
	        		bundleVersionString+
	        		" !!"
	        );
	        
	        ViewFactoryImpl vi = new ViewFactoryImpl(messageList);
	        vi.setViewFactoryTitle("Diary Ver."+bundleVersionString);
	        Properties props2 = new Properties();
	        props2.put("Type", "Korean");
	        bundleContext.registerService(
	        		ViewFactory.class.getName(),vi , props2);
	        
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		bundleContext = null;
	}

}
