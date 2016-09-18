package com.ragentek.imclient.core;

import com.ragentek.imclient.bean.QQMessage;

/**
 * Description:
 * Copyright  : Copyright (c) 2016
 * Company    : RGK
 * Author     : qi.guan
 * Date       : 2016/9/18 14:07
 */
public interface QQMessageListener {
    void onMessageReceive(QQMessage msg);
}
