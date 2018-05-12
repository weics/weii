package com.weii.pay.common.core.utils.httpclient;

import java.io.IOException;
import java.io.InputStream;
/**
 * @Author: weics
 * @Date: Created in 17:13 2018/5/6
 * @Description: 支付宝属性文件加载工具.
 * @Modified By:
 */
public interface HttpResponseCallBack {

	public void processResponse(InputStream responseBody) throws IOException;
}
