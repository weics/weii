package com.weii.pay.common.core.utils.httpclient;

import javax.net.ssl.TrustManagerFactory;
/**
 * @Author: weics
 * @Date: Created in 17:13 2018/5/6
 * @Description: 支付宝属性文件加载工具.
 * @Modified By:
 */
public class TrustKeyStore {
	private TrustManagerFactory trustManagerFactory;
	
	TrustKeyStore(TrustManagerFactory trustManagerFactory){
		this.trustManagerFactory = trustManagerFactory;
	}
	
	TrustManagerFactory getTrustManagerFactory(){
		return trustManagerFactory;
	}
}
