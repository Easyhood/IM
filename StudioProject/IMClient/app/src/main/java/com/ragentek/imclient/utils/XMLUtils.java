package com.ragentek.imclient.utils;

import com.thoughtworks.xstream.XStream;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Company    : RGK
 * Author     : qi.guan
 * Date       : 2016/9/18 14:59
 */
public class XMLUtils {
    public static String toXML(Object obj){
        XStream xStream = new XStream();
        xStream.alias(obj.getClass().getSimpleName(),obj.getClass());
        return xStream.toXML(obj);
    }

    public static <T> T fromXML(String xml, Class<T> c){
        XStream xStream = new XStream();
        xStream.alias(c.getSimpleName(),c);
        return (T)xStream.fromXML(xml);
    }
}
