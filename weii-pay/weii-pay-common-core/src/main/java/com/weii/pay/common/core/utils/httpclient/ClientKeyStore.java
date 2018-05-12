package com.weii.pay.common.core.utils.httpclient;

import javax.net.ssl.KeyManagerFactory;

/**
 * @Author: weics
 * @Date: Created in 17:13 2018/5/6
 * @Description: 支付宝属性文件加载工具.
 * @Modified By:
 */
public class ClientKeyStore {
	private KeyManagerFactory keyManagerFactory;
	
	ClientKeyStore(KeyManagerFactory keyManagerFactory){
		this.keyManagerFactory = keyManagerFactory;
	}
	
	KeyManagerFactory getKeyManagerFactory(){
		return keyManagerFactory;
	}
}
