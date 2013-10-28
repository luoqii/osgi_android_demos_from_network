package net.neosum.android.nastore.felix;

import java.util.Properties;

public class FelixProperties extends Properties 
{
	private static final long serialVersionUID = 1L;
	
	private String m_felixAbsolutePath;
	
	public FelixProperties(String rootpath)
	{
		m_felixAbsolutePath = rootpath;
		
		put("org.osgi.framework.storage", m_felixAbsolutePath+"/felix/cache");
		put("felix.cache.rootdir",m_felixAbsolutePath+"/felix");
		put("felix.log.level", "4");
		put("felix.startlevel.bundle", "1");
		
		put("org.osgi.framework.system.packages.extra", ANDROID_PACKAGES_FOR_EXPORT);
	}
	
	private final String ANDROID_PACKAGES_FOR_EXPORT= 
		// ANDROID (here starts semicolon as separator -> Why?
        "android, " + 
        "android.app," + 
        "android.content," + 
        "android.database," + 
        "android.database.sqlite," + 
        "android.graphics, " + 
        "android.graphics.drawable, " + 
        "android.graphics.glutils, " + 
        "android.hardware, " + 
        "android.location, " + 
        "android.media, " + 
        "android.net, " + 
        "android.opengl, " + 
        "android.os, " + 
        "android.provider, " + 
        "android.sax, " + 
        "android.speech.recognition, " + 
        "android.telephony, " + 
        "android.telephony.gsm, " + 
        "android.text, " + 
        "android.text.method, " + 
        "android.text.style, " + 
        "android.text.util, " + 
        "android.util, " + 
        "android.view, " + 
        "android.view.animation, " + 
        "android.webkit, " + 
        "android.widget, " + 
        // JAVAx
        "javax.crypto; " + 
        "javax.crypto.interfaces; " + 
        "javax.crypto.spec; " + 
        "javax.microedition.khronos.opengles; " + 
        "javax.net; " + 
        "javax.net.ssl; " + 
        "javax.security.auth; " + 
        "javax.security.auth.callback; " + 
        "javax.security.auth.login; " + 
        "javax.security.auth.x500; " + 
        "javax.security.cert; " + 
        "javax.sound.midi; " + 
        "javax.sound.midi.spi; " + 
        "javax.sound.sampled; " + 
        "javax.sound.sampled.spi; " + 
        "javax.sql; " + 
        "javax.xml.parsers; " + 
        //JUNIT
        "junit.extensions; " + 
        "junit.framework; " + 
        //APACHE
        "org.apache.commons.codec; " + 
        "org.apache.commons.codec.binary; " + 
        "org.apache.commons.codec.language; " + 
        "org.apache.commons.codec.net; " + 
        "org.apache.commons.httpclient; " + 
        "org.apache.commons.httpclient.auth; " + 
        "org.apache.commons.httpclient.cookie; " + 
        "org.apache.commons.httpclient.methods; " + 
        "org.apache.commons.httpclient.methods.multipart; " + 
        "org.apache.commons.httpclient.params; " + 
        "org.apache.commons.httpclient.protocol; " + 
        "org.apache.commons.httpclient.util; " + 
        
        //OTHERS
        "org.bluez; " + 
        "org.json; " + 
        "org.w3c.dom; " + 
        "org.xml.sax; " + 
        "org.xml.sax.ext; " + 
        "org.xml.sax.helpers; " +
        "net.neosum.android.view;";
	
}
