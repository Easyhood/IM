package com.rgk.qiguan.dada.utils;

import com.thoughtworks.xstream.XStream;

public class XMLUtils {

	public static String toXML(Object obj){
		XStream xstream = new XStream();
		xstream.alias(obj.getClass().getSimpleName(), obj.getClass());
		return xstream.toXML(obj);
	}
	
	public static  <T> T fromXML(String xml, Class<T> c){
		XStream xstream = new XStream();
		xstream.alias(c.getSimpleName(), c);
		return (T)xstream.fromXML(xml);
	}
}
